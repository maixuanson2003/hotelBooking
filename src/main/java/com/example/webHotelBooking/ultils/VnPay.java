package com.example.webHotelBooking.ultils;
import com.example.webHotelBooking.DTO.Response.PaymentDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import com.example.webHotelBooking.DTO.Request.PaymentRequest;
import com.example.webHotelBooking.Entity.Payment;
import com.example.webHotelBooking.config.VnPayConfig;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VnPay extends HttpServlet {
    public static PaymentDTO CreatePay(PaymentRequest payment, HttpServletRequest req) throws UnsupportedEncodingException, ParseException {
        PaymentDTO paymentDTO=new PaymentDTO();

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = payment.getPrice()*100;
        String bankCode = req.getParameter("bankCode");

        String vnp_TxnRef = payment.getBookingId().toString();
        String vnp_IpAddr = VnPayConfig.getIpAddress(req);

        String vnp_TmnCode = VnPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl",VnPayConfig.vnp_Returnurl );
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl =VnPayConfig.vnp_PayUrl + "?" + queryUrl;
        paymentDTO.setUrl(paymentUrl);
        Date date = formatter.parse(vnp_CreateDate);
        paymentDTO.setCreateAt(date);
       return paymentDTO;
    }
    public static PaymentDTO verifyPayment(Payment payment, HttpServletRequest req) throws IOException, ParseException {
        PaymentDTO paymentDTO=new PaymentDTO();
        String vnp_RequestId = VnPayConfig.getRandomNumber(8);
        String vnp_Version = "2.1.0";
        String vnp_Command = "querydr";
        String vnp_TmnCode = VnPayConfig.vnp_TmnCode;
        String vnp_TxnRef = payment.getBooking().getId().toString();
        System.out.println(payment.getBooking().getId());
        String vnp_OrderInfo = "Kiem tra ket qua GD OrderId:" + vnp_TxnRef;
        //String vnp_TransactionNo = req.getParameter("transactionNo");

        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        cld.setTime(payment.getCreate_at());
        String vnp_TransDate = formatter.format(cld.getTime());
        String vnp_IpAddr = VnPayConfig.getIpAddress(req);

        JsonObject  vnp_Params = new JsonObject();

        vnp_Params.addProperty("vnp_RequestId", vnp_RequestId);
        vnp_Params.addProperty("vnp_Version", vnp_Version);
        vnp_Params.addProperty("vnp_Command", vnp_Command);
        vnp_Params.addProperty("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.addProperty("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.addProperty("vnp_OrderInfo", vnp_OrderInfo);
        //vnp_Params.put("vnp_TransactionNo", vnp_TransactionNo);
        vnp_Params.addProperty("vnp_TransactionDate", vnp_TransDate);
        vnp_Params.addProperty("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.addProperty("vnp_IpAddr", vnp_IpAddr);

        String hash_Data= String.join("|", vnp_RequestId, vnp_Version, vnp_Command, vnp_TmnCode, vnp_TxnRef, vnp_TransDate, vnp_CreateDate, vnp_IpAddr, vnp_OrderInfo);
        String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.vnp_HashSecret, hash_Data.toString());

        vnp_Params.addProperty("vnp_SecureHash", vnp_SecureHash);

        URL url = new URL(VnPayConfig.vnp_apiUrl);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(vnp_Params.toString());
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        System.out.println("nSending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + vnp_Params);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();
        System.out.println(response.toString());
        JSONObject json = new JSONObject(response.toString());
        String res_TransactionStatus = (String) json.get("vnp_TransactionStatus");
        String res_TransactionNo=(String) json.get("vnp_TransactionNo");

        if (res_TransactionStatus.equals("00")) {
            paymentDTO.setCheck(true);
            paymentDTO.setTransCode(res_TransactionNo);

            System.out.println("Transaction success");
            return paymentDTO;
        } else {
            paymentDTO.setCheck(false);
            // Giao dịch không thành công
            System.out.println("Transaction failed or pending");
            return  paymentDTO;
        }
    }
    public  static boolean  refundBooking(Payment payment, HttpServletRequest req) throws ServletException, IOException{
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_RequestId =VnPayConfig.getRandomNumber(8);
        String vnp_Version = "2.1.0";
        String vnp_Command = "refund";
        String vnp_TmnCode = VnPayConfig.vnp_TmnCode;
        String vnp_TransactionType = "02";
        String vnp_TxnRef = payment.getBooking().getId().toString();
        long amount = payment.getBooking().getTotalPrice()*100;
        String vnp_Amount = String.valueOf(amount);
        String vnp_OrderInfo = "Hoan tien GD OrderId:" + vnp_TxnRef;
        String vnp_TransactionNo = payment.getTransactionNo(); //Assuming value of the parameter "vnp_TransactionNo" does not exist on your system.
        String vnp_TransactionDate = formatter.format( payment.getCreate_at());
        String vnp_CreateBy = "NGUYEN VAN A";
        String vnp_CreateDate = formatter.format(payment.getCreate_at());
        String vnp_IpAddr = VnPayConfig.getIpAddress(req);

        JsonObject  vnp_Params = new JsonObject ();

        vnp_Params.addProperty("vnp_RequestId", vnp_RequestId);
        vnp_Params.addProperty("vnp_Version", vnp_Version);
        vnp_Params.addProperty("vnp_Command", vnp_Command);
        vnp_Params.addProperty("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.addProperty("vnp_TransactionType", vnp_TransactionType);
        vnp_Params.addProperty("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.addProperty("vnp_Amount", vnp_Amount);
        vnp_Params.addProperty("vnp_OrderInfo", vnp_OrderInfo);

        if(vnp_TransactionNo != null && !vnp_TransactionNo.isEmpty())
        {
            vnp_Params.addProperty("vnp_TransactionNo", vnp_TransactionNo);
        }

        vnp_Params.addProperty("vnp_TransactionDate", vnp_TransactionDate);
        vnp_Params.addProperty("vnp_CreateBy", vnp_CreateBy);
        vnp_Params.addProperty("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.addProperty("vnp_IpAddr", vnp_IpAddr);

        String hash_Data= String.join("|", vnp_RequestId, vnp_Version, vnp_Command, vnp_TmnCode,
                vnp_TransactionType, vnp_TxnRef, vnp_Amount, vnp_TransactionNo, vnp_TransactionDate,
                vnp_CreateBy, vnp_CreateDate, vnp_IpAddr, vnp_OrderInfo);

        String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.vnp_HashSecret, hash_Data.toString());

        vnp_Params.addProperty("vnp_SecureHash", vnp_SecureHash);

        URL url = new URL (VnPayConfig.vnp_apiUrl);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(vnp_Params.toString());
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        System.out.println("nSending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + vnp_Params);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();
        System.out.println(response.toString());
        JSONObject json = new JSONObject(response.toString());
        String res_ResponseCode = (String) json.get("vnp_ResponseCode");
        if (res_ResponseCode.equals("00")) {

            System.out.println("Transaction success");
            return true;
        } else {
            // Giao dịch không thành công
            System.out.println("Transaction failed or pending");
            return false;
        }

    }
}
