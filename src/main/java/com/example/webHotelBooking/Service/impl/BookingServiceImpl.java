package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Request.BookingRequest;
import com.example.webHotelBooking.DTO.Request.RoombookRequest;
import com.example.webHotelBooking.DTO.Response.BookingResponse;
import com.example.webHotelBooking.DTO.Response.MyApiResponse;
import com.example.webHotelBooking.Entity.*;
import com.example.webHotelBooking.Enums.HotelStatus;
import com.example.webHotelBooking.Enums.bookingStatus;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.bookingChangeDetailsRepository;
import com.example.webHotelBooking.Repository.bookingRepository;
import com.example.webHotelBooking.Repository.userRepository;
import com.example.webHotelBooking.Service.BookingService;
import com.example.webHotelBooking.Service.bookingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private bookingRepository bookingRepository;
    @Autowired
    private bookingDetailsService bookingDetailsService;
    @Autowired
    private bookingChangeDetailsRepository bookingChangeDetailsRepository;
    @Autowired
    private userRepository userRepository;
    @Autowired
    private EmailServiceimpl emailServiceimpl;
    @Override
    public BookingResponse CreateBooking(BookingRequest bookingRequest, String username) {
        actor actor=userRepository.findByUsername(username);
        if (actor==null){
            throw new ResourceNotFoundException("not found");
        }
        int totalRoom=0;
        List<RoombookRequest> roombookRequestList=bookingRequest.getTypeRoom();
        for (RoombookRequest roombookRequest:roombookRequestList){
            totalRoom+=roombookRequest.getAmountRoom();
        }
        booking booking=new booking().builder()
                .CreateAt(LocalDate.now().toString())
                .actor(actor)
                .totalRoom(totalRoom)
                .numberPeople(bookingRequest.getNumberPeople())
                .status(bookingStatus.CHUA_THANH_TOAN.getMessage())
               .build();
        booking booking1= bookingRepository.save(booking);
        for (RoombookRequest roombookRequest:roombookRequestList){
           bookingDetailsService.CreateBookingDetails(roombookRequest,bookingRequest.getHotelId(),bookingRequest.getCheckinDate(),bookingRequest.getCheckOutDate(),bookingRequest.getSaleCode(),booking1.getId());
        }
        List<bookingdetails> bookingList=booking1.getBookingdetailsList();
        long totalPrice=0;
        for (bookingdetails bookingdetails:bookingList){
            totalPrice+=bookingdetails.getPrice();
        }
        booking1.setTotalPrice(totalPrice);
        booking booking2= bookingRepository.save(booking1);
        BookingResponse bookingResponse=new BookingResponse(booking2);
        return bookingResponse;
    }

    @Override
    public List<BookingResponse> GetAllBookingResponse() {
        List<booking> bookingList=bookingRepository.findAll();
        List<BookingResponse> bookingResponseList=new ArrayList<>();
        for (booking booking:bookingList){
            BookingResponse bookingResponse=new BookingResponse(booking);
            bookingResponseList.add(bookingResponse);
        }
        return bookingResponseList;
    }

    @Override
    public List<BookingResponse> GetBookingByUser(String username) {
        actor actor=userRepository.findByUsername(username);
        if (actor==null){
            throw new ResourceNotFoundException("not found");
        }
        List<booking> bookingList=actor.getBookings();
        List<BookingResponse> bookingResponseList=new ArrayList<>();
        for (booking booking:bookingList){
            BookingResponse bookingResponse=new BookingResponse(booking);
            bookingResponseList.add(bookingResponse);
        }
        return bookingResponseList;
    }

    @Override
    public BookingResponse GetBookingById(String username, Long bookingId) {
        actor actor=userRepository.findByUsername(username);
        if (actor==null){
            throw new ResourceNotFoundException("not found");
        }
        List<booking> bookingList=actor.getBookings();
        for (booking booking:bookingList){
            if (booking.getId()==bookingId){
                BookingResponse bookingResponse=new BookingResponse(booking);
                return bookingResponse;
            }
        }
        return null;
    }
    private Long GetFeeChangeByHotel(Hotel hotel){
        List<HotelPolicy> hotelPolicies=hotel.getHotelPolicyList();
        if (hotelPolicies.isEmpty()){
            throw new ResourceNotFoundException("Khach sạn không có chính sách");
        }
        int numberPolicyCheck=0;
        Long fee=0L;
        for (HotelPolicy hotelPolicy:hotelPolicies){
            if (hotelPolicy.getIsRelatedFee()){
                numberPolicyCheck++;
            }
        }
        if (numberPolicyCheck==0 && hotel.getChangefee()==0L ){
            throw new ResourceNotFoundException("Khach sạn không có chính sách");
        }
        fee= hotel.getChangefee();
        return fee;
    }

    @Override
    public MyApiResponse ChangeScheduleBooking(Long BookingId, LocalDate CheckoutDate, LocalDate CheckInDate, String username) {
        LocalDate today=LocalDate.now();
        actor actor=userRepository.findByUsername(username);
        if (actor==null){
            throw new ResourceNotFoundException("not found");
        }
        List<booking> bookingList=actor.getBookings();
        for (booking booking:bookingList){
            if (booking.getId()==BookingId && booking.getStatus().equals(bookingStatus.ĐA_THANH_TOAN.getMessage())){
                LocalDate checkInDate = booking.getBookingdetailsList().get(0).getCheckInDate();
                boolean isBefore3Days = today.isBefore(checkInDate.minusDays(3));
                if (!isBefore3Days){
                    MyApiResponse myApiResponse=new MyApiResponse().builder()
                            .Check(true)
                            .message("Mời thanh toán phí đổi lịch")
                            .build();
                    Hotel hotel=booking.getBookingdetailsList().get(0).getHotelRoom().getHotel();
                    bookingChangeDetails bookingChangeDetails=new bookingChangeDetails().builder()
                            .Price(GetFeeChangeByHotel(hotel))
                            .checkOutDate(CheckoutDate)
                            .checkInDate(CheckInDate)
                            .booking(booking)
                            .createAt(LocalDate.now().toString())
                            .status(bookingStatus.CHUA_THANH_TOAN.getMessage())
                            .build();
                    bookingChangeDetailsRepository.save(bookingChangeDetails);
                    return myApiResponse;
                }else {
                    Hotel hotel=booking.getBookingdetailsList().get(0).getHotelRoom().getHotel();
                    MyApiResponse myApiResponse=new MyApiResponse().builder()
                            .Check(false)
                            .message("Đổi lịch thành công")
                            .build();
                    bookingChangeDetails bookingChangeDetails=new bookingChangeDetails().builder()
                            .Price(GetFeeChangeByHotel(hotel))
                            .checkOutDate(CheckoutDate)
                            .checkInDate(CheckInDate)
                            .booking(booking)
                            .createAt(LocalDate.now().toString())
                            .status(bookingStatus.THANH_TOAN_MIEN_PHI.getMessage())
                            .build();
                    bookingChangeDetailsRepository.save(bookingChangeDetails);
                    List<bookingdetails> bookingdetailsList=booking.getBookingdetailsList();
                    for (bookingdetails bookingdetails:bookingdetailsList){
                        bookingDetailsService.ChangeScheduleBookingDetails(bookingdetails.getHotelRoom().getTypeRoom(),hotel.getId(),CheckoutDate,CheckInDate,booking.getId());
                    }
                    booking.setStatus(bookingStatus.THAYDOI.getMessage());
                    bookingRepository.save(booking);
                    String Content="Đơn"+" "+booking.getId().toString()+" "+"của"+" "+booking.getActor().getUsername()+" "+"ngày check in"+CheckInDate.toString()+" ngày Check out"+" "+CheckoutDate.toString();
                    String HotelEmaile=hotel.getEmail();
                    emailServiceimpl.sendEmail(HotelEmaile, "Yêu cầu đổi lịch",Content);
                    return myApiResponse;

                }
            }
        }
       return null;
    }
    private List<booking> findBookingByPending(){
        List<booking> bookingList=bookingRepository.findAll();
        List<booking> bookingResponse=new ArrayList<>();
        for (booking booking:bookingList){
            if (booking.getStatus().equals(bookingStatus.CHUA_THANH_TOAN.getMessage())){
                bookingResponse.add(booking);
            }
        }
        return bookingResponse;
    }
    @Scheduled(fixedRate = 60000)
    public void autoCancleBooking(){
        List<booking> bookingList=findBookingByPending();
        for (booking booking:bookingList){
            booking.setStatus(bookingStatus.HUY.getMessage());
            bookingRepository.save(booking);
        }
    }
}
