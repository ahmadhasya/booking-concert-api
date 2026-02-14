package com.ahmad.bsi.service;

import com.ahmad.bsi.model.Concert;
import com.ahmad.bsi.repository.ConcertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {
    private final ConcertRepository repository;

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
