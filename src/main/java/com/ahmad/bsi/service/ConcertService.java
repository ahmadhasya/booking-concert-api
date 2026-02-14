package com.ahmad.bsi.service;

import com.ahmad.bsi.model.Booking;
import com.ahmad.bsi.model.Concert;
import com.ahmad.bsi.repository.ConcertRepository;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {
    private final ConcertRepository repository;

    private final Faker faker = new Faker();

    public ConcertService(ConcertRepository repository) {
        this.repository = repository;
    }

    public List<Concert> findAll() {
        return repository.findAll();
    }

    public List<Concert> findSearch(String search) {
        return repository.findByNameContainingIgnoreCaseOrArtistContainingIgnoreCaseOrVenueContainingIgnoreCase(search, search, search);
    }

    public Concert findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concert not found"));
    }

    public Concert factorySave(){
        Concert concert = new Concert();

//        id
//                name
//        artist
//                venue
//        datetime
//                status
//        vip_price
//                vip_capacity
//        vip_sold
//                standard_price
//        standard_capacity
//                standard_sold
//        general_admission_price
//                general_admission_capacity
//        general_admission_sold
//        concert.setName(faker);

        return repository.save(concert);
    }

    public Concert save(Concert concert) {
        return repository.save(concert);
    }

    public Concert update(Long id, Concert concert) {
        Concert existing = findById(id);
        concert.setId(existing.getId());
        concert.setVipSold(existing.getVipSold());
        concert.setGeneralAdmissionSold(existing.getGeneralAdmissionSold());
        concert.setStandardSold(existing.getStandardSold());
        return repository.save(concert);
    }
}
