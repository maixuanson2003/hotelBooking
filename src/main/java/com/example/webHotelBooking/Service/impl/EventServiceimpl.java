package com.example.webHotelBooking.Service.impl;

import com.example.webHotelBooking.DTO.Request.EventDTO;
import com.example.webHotelBooking.Entity.City;
import com.example.webHotelBooking.Entity.Event;
import com.example.webHotelBooking.Enums.EvenStatus;
import com.example.webHotelBooking.Exception.DuplicateRecordException;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Repository.CityRepository;
import com.example.webHotelBooking.Repository.EventRepository;
import com.example.webHotelBooking.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class EventServiceimpl implements EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CityRepository cityRepository;
    @Override
    public void addEventToCity(Long cityId, EventDTO eventDTO) {
        Optional<Event> eventOpt = Optional.ofNullable(eventRepository.findEventByNameEvent(eventDTO.getNameEvent()));
        City city=cityRepository.findById(cityId).orElseThrow(()->new ResourceNotFoundException("not found"));
        if (!eventOpt.isPresent()){
            Event event1=new Event().builder()
                    .nameEvent(eventDTO.getNameEvent())
                    .image(eventDTO.getImage())
                    .description(eventDTO.getDescription())
                    .DateEnd(eventDTO.getDateEnd())
                    .DateStart(eventDTO.getDateStart())
                    .City(city)
                    .build();
            Event event2=eventRepository.save(event1);
        }else {
            throw new DuplicateRecordException("event exsist");
        }
    }

    @Override
    public String DeleteEventFromCity(Long cityId, Long EventID) {
        City city=cityRepository.findById(cityId).orElseThrow(()->new ResourceNotFoundException("not found"));
        Event event=eventRepository.findById(EventID).orElseThrow(()->new ResourceNotFoundException("not found"));
        List<Event> eventList=city.getEventList();
        eventList.remove(event);
        eventRepository.deleteById(EventID);
        return "OK";
    }

    @Override
    public void UpdateEvent( Long EventId, EventDTO eventDTO) {
        Event event=eventRepository.findById(EventId).orElseThrow(()->new ResourceNotFoundException("not found"));
        event.setNameEvent(eventDTO.getNameEvent());
        event.setDescription(eventDTO.getDescription());
        event.setDateEnd(eventDTO.getDateEnd());
        event.setDateStart(eventDTO.getDateStart());
        event.setImage(eventDTO.getImage());
        eventRepository.save(event);
    }
    private EventDTO ConverDTO(Event event){
        EventDTO eventDTO=new EventDTO().builder()
                .id(event.getId())
                .nameEvent(event.getNameEvent())
                .DateEnd(event.getDateEnd())
                .DateStart(event.getDateStart())
                .description(event.getDescription())
                .image(event.getImage())
                .build();
        return eventDTO;
    }

    @Override
    public List<EventDTO> GetAllEventByCity(Long CityId) {
        City city=cityRepository.findById(CityId).orElseThrow(()->new ResourceNotFoundException("not found"));
        List<Event> eventList=city.getEventList();
        List<EventDTO> eventDTOS=new ArrayList<>();
        for (Event event:eventList){
            if( event.getStatus().equals(EvenStatus.SAPDIENRA.getMessage()))  eventDTOS.add(this.ConverDTO(event));
        }
        return eventDTOS;
    }

    @Override
    public List<EventDTO> GetAllEvent() {
        List<Event> eventList=eventRepository.findAll();
        List<EventDTO> eventDTOS=new ArrayList<>();
        for (Event event:eventList){
            eventDTOS.add(this.ConverDTO(event));
        }
        return  eventDTOS;
    }
    private boolean CheckDate(String DateStart,String DateEnd,Event event){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkDate= LocalDate.parse(event.getDateStart(),formatter);
        LocalDate checkDate1= LocalDate.parse(event.getDateEnd(),formatter);
        LocalDate DateStart2= LocalDate.parse(DateStart,formatter);
        LocalDate DateEnd2= LocalDate.parse(DateEnd,formatter);
        if (checkDate.isEqual(DateStart2)&&checkDate1.isEqual(DateEnd2)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<EventDTO> SearChEvent(String DateStart, String DateEnd, Long CityId) {
        boolean CheckCodition1=DateStart!=null&&DateEnd!=null;
        boolean CheckCodition2=DateStart!=null&&DateEnd==null;
        boolean CheckCodition3=DateStart==null&&DateEnd!=null;
        City city=cityRepository.findById(CityId).orElseThrow(()->new ResourceNotFoundException("not found"));
        List<Event> eventList=city.getEventList();
        List<EventDTO> eventDTOS=new ArrayList<>();
        if (CheckCodition1){
            for (Event event:eventList){
                if (CheckDate(DateStart,DateEnd,event)&&event.getStatus().equals(EvenStatus.SAPDIENRA.getMessage())){
                    eventDTOS.add(this.ConverDTO(event));
                }
            }
        }
        if (CheckCodition2){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate DateStart2= LocalDate.parse(DateStart,formatter);
            for (Event event:eventList){
                LocalDate checkDate= LocalDate.parse(event.getDateStart(),formatter);
                if (DateStart2.isEqual(checkDate) && event.getStatus().equals(EvenStatus.SAPDIENRA.getMessage())){
                    eventDTOS.add(this.ConverDTO(event));
                }
            }
        }
        if (CheckCodition3){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate DateEnd1= LocalDate.parse(DateEnd,formatter);
            for (Event event:eventList){
                LocalDate checkDate= LocalDate.parse(event.getDateEnd(),formatter);
                if (DateEnd1.isEqual(checkDate) && event.getStatus().equals(EvenStatus.SAPDIENRA.getMessage())){
                    eventDTOS.add(this.ConverDTO(event));
                }
            }
        }
        return eventDTOS;
    }
    private boolean CheckStatusEvent(LocalDate datenow,String DateEnd){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate CheckDateEnd= LocalDate.parse(DateEnd,formatter);
        return datenow.isAfter(CheckDateEnd);
    }
//    @Scheduled(cron = "0 0 0 * * ?")
//    public void autoSetStatusForEvent(){
//        LocalDate date=LocalDate.now();
//        List<Event> eventList=eventRepository.findAll();
//        for (Event event:eventList){
//            if(CheckStatusEvent(date,event.getDateEnd())){
//                event.setStatus(EvenStatus.DAKETTHUC.getMessage());
//                eventRepository.save(event);
//            }
//        }
//    }

}
