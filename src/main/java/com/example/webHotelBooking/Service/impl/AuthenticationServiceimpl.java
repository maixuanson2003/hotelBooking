package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Request.AuthenticationRequest;
import com.example.webHotelBooking.DTO.Request.verifytokenRequest;
import com.example.webHotelBooking.DTO.Response.AuthenticationResponse;
import com.example.webHotelBooking.DTO.Response.verifyTokenResponse;
import com.example.webHotelBooking.Entity.AccountHotel;
import com.example.webHotelBooking.Entity.TokenInvalid;
import com.example.webHotelBooking.Entity.actor;
import com.example.webHotelBooking.Enums.Role;
import com.example.webHotelBooking.Repository.AccountHotelRepository;
import com.example.webHotelBooking.Repository.TokenInvalidRepository;
import com.example.webHotelBooking.Repository.userRepository;
import com.example.webHotelBooking.Service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
@Service
public class AuthenticationServiceimpl implements AuthenticationService {
    @Autowired
    private TokenInvalidRepository tokenInvalidRepository;
    @Autowired
    private userRepository actors;
    @Autowired
    private AccountHotelRepository accountHotelRepository;
    protected static final String KEY = "NQc7mrnHIwVaDA519Ka3ph/ZdHVjvu5NhWkNMfExmAIHpDtO3PShgPqK4w3Rivq7";

    @Override
    public verifyTokenResponse veryfitoken(verifytokenRequest request) throws JOSEException, ParseException {

        String token = request.getToken();
        JWSVerifier verifier = new MACVerifier(KEY.getBytes());
        try {
            SignedJWT sign = SignedJWT.parse(token);
            Date tokenExpiry = sign.getJWTClaimsSet().getExpirationTime();
            boolean checkExpiry = false;
            if (tokenExpiry.after(new Date())) {
                checkExpiry = true;
            }
            boolean checkToken = sign.verify(verifier);
            if (checkExpiry && checkToken) {
                return new verifyTokenResponse().builder()
                        .valid(true)
                        .build();

            } else {
                return new verifyTokenResponse().builder()
                        .valid(false)
                        .build();
            }

        } catch (Exception e) {
            throw new ParseException(null, 21);
        }

    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest requests) {
        if (requests.getUsername() == null || requests.getUsername().trim().isEmpty() ||
                requests.getPassword() == null || requests.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Email và mật khẩu không được để trống");
        }

        BCryptPasswordEncoder passwordCheck = new BCryptPasswordEncoder();
        AuthenticationResponse authenCheck = new AuthenticationResponse();
        actor userfind =actors.findByUsername(requests.getUsername());
        AccountHotel accountHotel=accountHotelRepository.findByUsername(requests.getUsername());

        if (userfind == null&&accountHotel==null) {
            throw new RuntimeException("khong xac thuc duoc");
        }
        boolean AuthenUser=false;
        if (userfind!=null){
            AuthenUser= passwordCheck.matches(requests.getPassword(), userfind.getPassword());
        }
        boolean AuthenHotel=false;
        if (accountHotel!=null){
            AuthenHotel = passwordCheck.matches(requests.getPassword(), accountHotel.getPassword());
        }

        if (AuthenUser) {

            final String token = GenerateToken(userfind);
            authenCheck.setToken(token);
            authenCheck.setAuthenticated(true);
            authenCheck.setUsername(userfind.getUsername());
            authenCheck.setType(userfind.getRole());
        } else if (AuthenHotel){
            final String token = GenerateTokenForHotelAdmin(accountHotel);
            authenCheck.setToken(token);
            authenCheck.setAuthenticated(true);
            authenCheck.setUsername(accountHotel.getUsername());
            authenCheck.setType(Role.HOTELADMIN.getDescription());
            authenCheck.setHotelId(accountHotel.getHotel().getId());
        }else{
            throw new RuntimeException("khong xac thuc duoc");
        }
        return authenCheck;

    }

    @Override
    public AuthenticationResponse RefresToken(String token)throws JOSEException, ParseException {
        SignedJWT sign = verifyToken(token);
        Date tokenExpiry = sign.getJWTClaimsSet().getExpirationTime();

        Date currentTime = new Date();
        if (tokenExpiry.after(currentTime) && tokenExpiry.getTime() - currentTime.getTime() > 5 * 60 * 1000) {
            throw new RuntimeException("Token vẫn còn hiệu lực, không cần làm mới");
        }
        String scope = sign.getJWTClaimsSet().getStringClaim("scope");
        if (scope.equals(Role.USER.getDescription())){
            Long userId = sign.getJWTClaimsSet().getLongClaim("userid");
            actor actor=actors.findById(userId).orElseThrow(()->new RuntimeException("not found"));
            return new AuthenticationResponse().builder()
                    .Authenticated(true)
                    .token(GenerateToken(actor))
                    .Type(actor.getRole())
                    .username(actor.getUsername())
                    .HotelId(null)
                    .build();
        }
        if (scope.equals(Role.HOTELADMIN.getDescription())){
            Long Hotelid = sign.getJWTClaimsSet().getLongClaim("HotelId");
            AccountHotel accountHotel=accountHotelRepository.findById(Hotelid).orElseThrow(()->new RuntimeException("not found"));
            return new AuthenticationResponse().builder()
                    .Authenticated(true)
                    .token(GenerateTokenForHotelAdmin(accountHotel))
                    .Type(Role.HOTELADMIN.getDescription())
                    .username(accountHotel.getUsername())
                    .HotelId(accountHotel.getId())
                    .build();
        }

        return null;
    }

    @Override
    public void Logout(String token) throws ParseException, JOSEException {
        var signtoken=verifyToken( token);
        TokenInvalid tokenInvalid=new TokenInvalid().builder()
                .id(signtoken.getJWTClaimsSet().getJWTID())
                .expiryTime(signtoken.getJWTClaimsSet().getExpirationTime())
                .build();
        tokenInvalidRepository.save(tokenInvalid);

    }
    private  SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(KEY.getBytes());
        SignedJWT sign = SignedJWT.parse(token);
        Date tokenExpiry = sign.getJWTClaimsSet().getExpirationTime();
        var verify=sign.verify(verifier);
        if (!(verify&&tokenExpiry.after(sign.getJWTClaimsSet().getExpirationTime()))){
            throw new RuntimeException("khong xac thuc duoc");
        }
        if (tokenInvalidRepository.existsById(sign.getJWTClaimsSet().getJWTID())){
            throw new RuntimeException("khong xac thuc duoc");
        }

        return sign;
    }

    private String GenerateToken(actor actors) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimSet = new JWTClaimsSet.Builder().subject(actors.getUsername()).issuer("son.com").issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(actors.getId().toString()+ + LocalDateTime.now().getHour()+LocalDateTime.now().getSecond())
                .claim("scope", actors.getRole()).claim("userid",actors.getId()).build();
        Payload payload = new Payload(jwtClaimSet.toJSONObject());

        JWSObject object = new JWSObject(header, payload);
        try {
            object.sign(new MACSigner(KEY.getBytes()));
            return object.serialize();

        } catch (Exception e) {
            throw new RuntimeException("error");

        }
    }
    private String GenerateTokenForHotelAdmin(AccountHotel accountHotel) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimSet = new JWTClaimsSet.Builder().subject(accountHotel.getUsername()).issuer("son.com").issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(accountHotel.getId().toString()+ LocalDateTime.now().getHour()+LocalDateTime.now().getSecond())
                .claim("scope", Role.HOTELADMIN.getDescription()).claim("HotelId",accountHotel.getHotel().getId()).build();
        Payload payload = new Payload(jwtClaimSet.toJSONObject());

        JWSObject object = new JWSObject(header, payload);
        try {
            object.sign(new MACSigner(KEY.getBytes()));
            return object.serialize();

        } catch (Exception e) {
            throw new RuntimeException("error");

        }
    }
}
