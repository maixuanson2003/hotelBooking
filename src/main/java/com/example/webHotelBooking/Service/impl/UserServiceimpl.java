package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Request.actorRequest;
import com.example.webHotelBooking.DTO.Request.actorRequestADmin;
import com.example.webHotelBooking.DTO.Response.actorResponse;
import com.example.webHotelBooking.Entity.OTP;
import com.example.webHotelBooking.Entity.actor;
import com.example.webHotelBooking.Enums.AccountStatus;
import com.example.webHotelBooking.Enums.Role;
import com.example.webHotelBooking.Repository.OTPRepository;
import com.example.webHotelBooking.Repository.userRepository;
import com.example.webHotelBooking.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@Service
public class UserServiceimpl implements UserService {
    @Autowired
    private userRepository userRepository;
    @Autowired
    private OTPRepository otpRepository;
    @Autowired
    private EmailServiceimpl emailServiceimpl;
    private actor findActorByEmail(String Email){
        List<actor> actorList=userRepository.findAll();
        for (actor actor:actorList){
            if (actor.getEmail().equals(Email)){
                return actor;
            }
        }
        return null;
    }
    private actor findActorPassword(String Password){
        BCryptPasswordEncoder passwordCheck = new BCryptPasswordEncoder();
        List<actor> actorList=userRepository.findAll();
        for (actor actor:actorList){
            boolean Authen = passwordCheck.matches(Password, actor.getPassword());
           if (Authen){
               return  actor;
           }
        }
        return null;
    }
    private actor findActorUsername(String Username){

        List<actor> actorList=userRepository.findAll();
        for (actor actor:actorList){

            if (actor.getPassword().equals(Username)){
                return actor;
            }
        }
        return null;
    }
    @Override
    public actorResponse UserregisterAcount(actorRequest request) {
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        actor actor1=findActorByEmail(request.getEmail());
        actor actor2=findActorUsername(request.getUsername());
        actor actor3=findActorPassword(request.getPassword());
        if (actor1!=null){
            throw new RuntimeException("Email have Already Exsist");
        }
        if (actor2!=null){
            throw new RuntimeException("Username have Already Exsist");
        }
        if (actor3!=null){
            throw new RuntimeException("Password have Already Exsist");
        }
        actor actor=new actor().builder()
                .fullname(request.getFullName())
                .address(request.getAddress())
                .gender(request.getGender())
                .password(passwordEncoder.encode(request.getPassword()))
                .Role(Role.USER.getDescription())
                .birthday(request.getBirthday())
                .status(AccountStatus.CHUAXACTHUC.getMessage())
                .email(request.getEmail())
                .username(request.getUsername())
                .build();
        userRepository.save(actor);
        actorResponse actorResponse=new actorResponse(actor);
        return actorResponse;
    }

    @Override
    public actorResponse CreateActor(actorRequestADmin request) {
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        actor actor1=findActorByEmail(request.getEmail());
        actor actor2=findActorUsername(request.getUsername());
        actor actor3=findActorPassword(request.getPassword());
        if (actor1!=null){
            throw new RuntimeException("Email have Already Exsist");
        }
        if (actor2!=null){
            throw new RuntimeException("Username have Already Exsist");
        }
        if (actor3!=null){
            throw new RuntimeException("Password have Already Exsist");
        }
        actor actor=new actor().builder()
                .fullname(request.getFullName())
                .address(request.getAddress())
                .gender(request.getGender())
                .password(passwordEncoder.encode(request.getPassword()))
                .Role(request.getRole())
                .birthday(request.getBirthday())
                .status(AccountStatus.ĐAĐANGKY.getMessage())
                .email(request.getEmail())
                .username(request.getUsername())
                .build();
        userRepository.save(actor);
        actorResponse actorResponse=new actorResponse(actor);
        return actorResponse;
    }

    @Override
    public String ChangePassword(String password,String oldPassword,String userName) {
        BCryptPasswordEncoder passwordCheck = new BCryptPasswordEncoder();
        actor actor =userRepository.findByUsername(userName);
        if (actor==null){
            return "Nhap dung thong tin username";
        }
        if (!passwordCheck.matches(oldPassword,actor.getPassword())) return "mat khau cu khong khop";
        actor.setPassword( passwordCheck.encode(password));
        userRepository.save(actor);
        return "Thay doi mat khau thanh cong";
    }

    @Override
    public boolean VerifyInformation(String fullName, String email, String phoneNumber) {
        boolean check=false;
        List<actor> actorList=userRepository.findAll();
        for (actor actor:actorList){
            if (actor.getUsername().equals(fullName)&&actor.getEmail().equals(email)&&actor.getPhone().equals(phoneNumber)){
                check=true;
            }
        }
        if (check){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public List<actorResponse> getAllActor() {
        List<actor> actorList=userRepository.findAll();
        List<actorResponse>actorResponseList=new ArrayList<>();
        for(actor actor:actorList){
            actorResponse actorResponse=new actorResponse(actor);
            actorResponseList.add(actorResponse);

        }
        return actorResponseList;
    }

    @Override
    public actorResponse searchActor(String fullName, String username, String phone, String email, String birthday, String address) {
        boolean checkSearchFullName= fullName!=null && username==null && phone==null && email==null && birthday==null && address==null;
        boolean checkSearchcondition1= fullName!=null && username!=null && phone==null && email==null && birthday==null && address==null;
        boolean checkSearchcondition2= fullName!=null && username!=null && phone!=null && email==null && birthday==null && address==null;
        boolean checkSearchcondition3= fullName!=null && username!=null && phone!=null && email!=null && birthday==null && address==null;
        boolean checkSearchcondition4= fullName!=null && username!=null && phone!=null && email!=null && birthday!=null && address==null;
        boolean checkSearchcondition5= fullName!=null && username!=null && phone!=null && email!=null && birthday!=null && address!=null;
        boolean checkSearchUserName=fullName==null && username!=null && phone==null && email==null && birthday==null && address==null;
        boolean checkSearchPhone=fullName==null && username==null && phone!=null && email==null && birthday==null && address==null;
        boolean checkSearchEmail=fullName==null && username==null && phone==null && email!=null && birthday==null && address==null;
        boolean checkSearchBirthday=fullName==null && username==null && phone==null && email==null && birthday!=null && address==null;
        boolean checkSearchAddress=fullName==null && username==null && phone==null && email==null && birthday==null && address!=null;
        List<actor> actorList=userRepository.findAll();
        if (checkSearchFullName){
            for (actor actor:actorList){
                if (actor.getFullname().equals(fullName)){
                    actorResponse actorResponse=new actorResponse(actor);
                    return actorResponse;
                }
            }
        }
        if (checkSearchcondition1){
            for (actor actor:actorList){
                if (actor.getFullname().equals(fullName)&&actor.getUsername().equals(username)){
                    actorResponse actorResponse=new actorResponse(actor);
                    return actorResponse;
                }
            }
        }
        if (checkSearchcondition2){
            for (actor actor:actorList){
                if (actor.getFullname().equals(fullName)&&actor.getUsername().equals(username)&&actor.getPhone().equals(phone)){
                    actorResponse actorResponse=new actorResponse(actor);
                    return actorResponse;
                }
            }

        }
        if (checkSearchcondition3){
            for (actor actor:actorList){
                if (actor.getFullname().equals(fullName)&&actor.getUsername().equals(username)&&actor.getPhone().equals(phone)&&actor.getEmail().equals(email)){
                    actorResponse actorResponse=new actorResponse(actor);
                    return actorResponse;
                }
            }
        }
        if (checkSearchcondition4){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate checkDate=LocalDate.parse(birthday,formatter);

            for (actor actor:actorList){
                LocalDate checkDat2=LocalDate.parse(actor.getBirthday(),formatter);
                if (actor.getFullname().equals(fullName)&&actor.getUsername().equals(username)&&actor.getPhone().equals(phone)&&actor.getEmail().equals(email)&&checkDate.isEqual(checkDat2)){
                    actorResponse actorResponse=new actorResponse(actor);
                    return actorResponse;
                }
            }
        }
        if (checkSearchcondition5){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate checkDate=LocalDate.parse(birthday,formatter);

            for (actor actor:actorList){
                LocalDate checkDat2=LocalDate.parse(actor.getBirthday(),formatter);
                if (actor.getFullname().equals(fullName)&&actor.getUsername().equals(username)&&actor.getPhone().equals(phone)&&actor.getEmail().equals(email)&&checkDate.isEqual(checkDat2)&&actor.getAddress().equals(address)){
                    actorResponse actorResponse=new actorResponse(actor);
                    return actorResponse;
                }
            }
        }
        if (checkSearchUserName){
            actorResponse actorResponse=new actorResponse(findActorUsername(username));
            return actorResponse;
        }
        if (checkSearchPhone){
            for (actor actor:actorList){
                if (actor.getPhone().equals(phone)){
                    actorResponse actorResponse=new actorResponse(actor);
                    return actorResponse;
                }
            }
        }
        if (checkSearchEmail){
            actorResponse actorResponse=new actorResponse(findActorByEmail(email));
            return actorResponse;
        }
        if(checkSearchBirthday){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate checkDate=LocalDate.parse(birthday,formatter);
            for (actor actor:actorList){
                LocalDate checkDat2=LocalDate.parse(actor.getBirthday(),formatter);
                if (checkDate.isEqual(checkDat2)){
                    actorResponse actorResponse=new actorResponse(actor);
                    return actorResponse;
                }
            }
        }
        if(checkSearchAddress){
            for (actor actor:actorList){
                if (actor.getAddress().equals(address)){
                    actorResponse actorResponse=new actorResponse(actor);
                    return actorResponse;
                }
            }
        }
        return null;
    }

    @Override
    public actorResponse getActorById(Long id) {
        actor actor=userRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
        actorResponse actorResponse=new actorResponse(actor);
        return actorResponse;
    }

    @Override
    public String DeleteActorByUsername(String username) {
        actor actor=findActorUsername(username);
        userRepository.delete(actor);
        return "ok";
    }

    @Override
    public String DeleteActorById(Long id) {
        actor actor=userRepository.findById(id).orElseThrow(()->new RuntimeException("notfound"));
        userRepository.delete(actor);
        return "ok";
    }
}
