package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Request.BookingRequest;
import com.example.webHotelBooking.DTO.Request.RoombookRequest;
import com.example.webHotelBooking.DTO.Response.BookingResponse;
import com.example.webHotelBooking.DTO.Response.MyApiResponse;
import com.example.webHotelBooking.DTO.Response.PaymentResponse;
import com.example.webHotelBooking.Entity.*;
import com.example.webHotelBooking.Enums.HotelStatus;
import com.example.webHotelBooking.Enums.bookingChangeStatus;
import com.example.webHotelBooking.Enums.bookingStatus;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.bookingChangeDetailsRepository;
import com.example.webHotelBooking.Repository.bookingRepository;
import com.example.webHotelBooking.Repository.bookingdetailsRepository;
import com.example.webHotelBooking.Repository.userRepository;
import com.example.webHotelBooking.Service.BookingService;
import com.example.webHotelBooking.Service.HotelRoomService;
import com.example.webHotelBooking.Service.bookingDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class BookingServiceImpl implements BookingService {
    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);
    private final SimpMessagingTemplate messagingTemplate;

    public BookingServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    @Autowired
    private bookingRepository bookingRepository;
    @Autowired
    private bookingDetailsService bookingDetailsService;
    @Autowired
    private bookingdetailsRepository bookingdetailsRepository;
    @Autowired
    private bookingChangeDetailsRepository bookingChangeDetailsRepository;
    @Autowired
    private userRepository userRepository;
    private Queue<String> Email=new LinkedList<>();
    @Autowired
    private EmailServiceimpl emailServiceimpl;
    @Autowired
    private HotelRoomService hotelRoomService;
    @Override
    @Transactional
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
        List<bookingdetails> bookingdetailsList=new ArrayList<>();
        List<bookingdetails> bookingList=bookingdetailsRepository.findAll();
        for (bookingdetails bookingdetails:bookingList){
            if (bookingdetails.getBooking().getId()==booking1.getId()){
                bookingdetailsList.add(bookingdetails);
            }
        }
        long totalPrice=0;
        for (bookingdetails bookingdetails:bookingdetailsList){
            totalPrice+=bookingdetails.getPrice();
        }
        booking1.setTotalPrice(totalPrice);
        booking booking2= bookingRepository.save(booking1);
        BookingResponse bookingResponse=new BookingResponse(booking1);
        String hotelId=bookingRequest.getHotelId().toString();
        String message="Change"+hotelId;
        messagingTemplate.convertAndSend("/updateHotel/"+ hotelId, message);
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
        List<HotelPolicyDetails> hotelPolicyDetails=hotel.getHotelPolicyDetailsList();
        if (hotelPolicyDetails.isEmpty()){
            throw new ResourceNotFoundException("Khach sạn không có chính sách");
        }
        Long fee=0L;
        for (HotelPolicyDetails hotelPolicyDetails1:hotelPolicyDetails){
            if (hotelPolicyDetails1.getFee()!=null && hotelPolicyDetails1.getHotelPolicy().getNamePolicy().equals("Chính sách đổi lịch")){
                fee+=hotelPolicyDetails1.getFee();
            }
        }
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

                    Hotel hotel=booking.getBookingdetailsList().get(0).getHotelRoom().getHotel();
                    bookingChangeDetails bookingChangeDetails=new bookingChangeDetails().builder()
                            .Price(GetFeeChangeByHotel(hotel))
                            .checkOutDate(CheckoutDate)
                            .checkInDate(CheckInDate)
                            .booking(booking)
                            .createAt(LocalDate.now().toString())
                            .status(bookingStatus.CHUA_THANH_TOAN.getMessage())
                            .build();
                    bookingChangeDetails bookingChangeDetails1=bookingChangeDetailsRepository.save(bookingChangeDetails);
                    MyApiResponse myApiResponse=new MyApiResponse().builder()
                            .bookingChangeId(bookingChangeDetails1.getId())
                            .Check(true)
                            .message("Mời thanh toán phí đổi lịch")
                            .build();
                    return myApiResponse;
                }else {
                    Hotel hotel=booking.getBookingdetailsList().get(0).getHotelRoom().getHotel();

                    bookingChangeDetails bookingChangeDetails=new bookingChangeDetails().builder()
                            .Price(GetFeeChangeByHotel(hotel))
                            .checkOutDate(CheckoutDate)
                            .checkInDate(CheckInDate)
                            .booking(booking)
                            .createAt(LocalDate.now().toString())
                            .status(bookingChangeStatus.CHUAXACNHAN.getMessage())
                            .build();
                    bookingChangeDetails bookingChangeDetails1=bookingChangeDetailsRepository.save(bookingChangeDetails);
                    MyApiResponse myApiResponse=new MyApiResponse().builder()
                            .bookingChangeId(bookingChangeDetails1.getId())
                            .Check(false)
                            .message("Chờ xác nhận ")
                            .build();
                    String Content="Đơn"+" "+booking.getId().toString()+" "+"của"+" "+booking.getActor().getUsername()+" "+"ngày check in"+CheckInDate.toString()+" ngày Check out"+" "+CheckoutDate.toString();
                    String HotelEmaile=hotel.getEmail();
                    emailServiceimpl.sendAsyncEmail(HotelEmaile, "Yêu cầu đổi lịch",Content);
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

    @Scheduled(fixedRate = 840000)
    @Transactional
    public void autoCancleBooking(){
        List<booking> bookingList=findBookingByPending();
        for (booking booking:bookingList){
          try {
              booking.setStatus(bookingStatus.HUY.getMessage());

              List<bookingdetails> bookingdetailsList = booking.getBookingdetailsList();
              for (bookingdetails bookingdetails : bookingdetailsList) {
                  HotelRoom hotelRoom = bookingdetails.getHotelRoom();
                  hotelRoomService.setAmountRoom(hotelRoom.getTypeRoom(), hotelRoom.getHotel().getId(), hotelRoom.getNumbeRoomLast() + bookingdetails.getAmountRoom());
              }
              bookingRepository.save(booking);
          }catch (Exception e){
              logger.error("Error cancelling booking: " + booking.getId(), e);
          }
        }
    }
}
