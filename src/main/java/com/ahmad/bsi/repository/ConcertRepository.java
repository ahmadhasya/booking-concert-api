package com.ahmad.bsi.repository;

import com.ahmad.bsi.model.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {
    List<Concert> findByNameContainingIgnoreCaseOrArtistContainingIgnoreCaseOrVenueContainingIgnoreCase(String name, String artist, String Venue);
}
