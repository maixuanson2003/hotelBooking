package com.example.webHotelBooking.Repository;

import com.example.webHotelBooking.Entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CityRepository extends JpaRepository<City,Long> {
    public City findCityByNameCity(String nameCity);
}
