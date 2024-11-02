package com.example.webHotelBooking.Service.impl;
import com.example.webHotelBooking.Entity.HotelPolicy;
import com.example.webHotelBooking.Exception.DuplicateRecordException;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.HotelPolicyRepository;
import com.example.webHotelBooking.Service.HotelPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HotelPolicyServiceimpl implements HotelPolicyService {
    @Autowired
    private HotelPolicyRepository hotelPolicyRepository;
    @Override
    public void createHotelPolicy(String nameHotelPolicy) {
        boolean CheckExsits=false;
        List<HotelPolicy> hotelPolicyList=hotelPolicyRepository.findAll();
        for (HotelPolicy hotelPolicy:hotelPolicyList){
            if (hotelPolicy.getNamePolicy().equals(nameHotelPolicy)){
                CheckExsits=true;
            }
        }
        if (CheckExsits) throw new DuplicateRecordException("exsits");
        HotelPolicy hotelPolicy=new HotelPolicy().builder()
                .namePolicy(nameHotelPolicy)
                .build();
        hotelPolicyRepository.save(hotelPolicy);
    }

    @Override
    public void updateHotelPolicy(String nameHotelPolicy,Long hotelPolicyId) {
        HotelPolicy hotelPolicy=hotelPolicyRepository.findById(hotelPolicyId).orElseThrow(()->new ResourceNotFoundException("not found"));
        hotelPolicy.setNamePolicy(nameHotelPolicy);
        hotelPolicyRepository.save(hotelPolicy);

    }

    @Override
    public String deletePolicyById(Long PolicyId) {
        hotelPolicyRepository.deleteById(PolicyId);
        if (hotelPolicyRepository.existsById(PolicyId)){
            return  "faild";
        }else {
            return "Success";
        }
    }
}
