package com.ahmad.bsi.repository;

import com.ahmad.bsi.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> getByUserId(Long userId);
    Optional<Booking> findByUserIdAndId(Long userId, Long id);
    List<Booking> getByConcertIdAndStatus(Long concertId, String status);
}
