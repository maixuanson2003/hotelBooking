package com.example.webHotelBooking.Service;


import com.example.webHotelBooking.Entity.Hotel;

import java.util.List;

public interface HotelPolicyService {
    public void createHotelPolicy(String nameHotelPolicy);
    public void updateHotelPolicy(String nameHotelPolicy,Long hotelPolicyId);
    public String deletePolicyById(Long PolicyId);

}
