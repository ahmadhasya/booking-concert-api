package com.ahmad.bsi.service;

import com.ahmad.bsi.form.BookingForm;
import com.ahmad.bsi.model.Booking;
import com.ahmad.bsi.model.Concert;
import com.ahmad.bsi.repository.BookingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BookingService {
    private final BookingRepository repository;
    private final ConcertService concertService;

    public BookingService(BookingRepository repository, ConcertService concertService) {
        this.repository = repository;
        this.concertService = concertService;
    }

    public long count() {
        return repository.count();
    }

    public List<Booking> findAll() {
        return repository.findAll();
    }

    public List<Booking> findAllByUserId(Long userId) {
        return repository.getByUserId(userId);
    }

    public List<Booking> findAllByConcertIdAndStatus(Long userId, String status) {
        return repository.getByConcertIdAndStatus(userId, status);
    }

    public Booking findById(Long userId, Long id) {
        return repository.findByUserIdAndId(userId, id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public Booking save(Booking concert) {
        return repository.save(concert);
    }

    @Transactional
    public Booking book(Long userId, BookingForm bookingForm) {
        String code = UUID.randomUUID().toString();
        OffsetDateTime ordertime = OffsetDateTime.now(ZoneOffset.of("+07:00"));

        Long basePrice = 0L, price;
        double multiplier = 0;
        boolean available = true;

        Concert concert = this.concertService.findById(bookingForm.getConcertId());


        if (bookingForm.getCategory().equals("vip")) {
            multiplier = ((double) concert.getVipSold() / concert.getVipCapacity()) * 2.5;
            basePrice = concert.getVipPrice();
            available = (concert.getVipCapacity() - concert.getVipSold()) > bookingForm.getAmount();
            concert.setVipSold(concert.getVipSold() + bookingForm.getAmount());

        } else if (bookingForm.getCategory().equals("general_admission")) {
            multiplier = ((double) concert.getGeneralAdmissionSold() / concert.getGeneralAdmissionCapacity()) * 2.5;
            basePrice = concert.getGeneralAdmissionPrice();
            available = (concert.getGeneralAdmissionCapacity() - concert.getGeneralAdmissionSold()) > bookingForm.getAmount();
            concert.setGeneralAdmissionSold(concert.getGeneralAdmissionSold() + bookingForm.getAmount());

        } else {
            multiplier = ((double) concert.getStandardSold() / concert.getStandardCapacity()) * 2.5;
            basePrice = concert.getStandardPrice();
            available = (concert.getStandardCapacity() - concert.getStandardSold()) > bookingForm.getAmount();
            concert.setStandardSold(concert.getStandardSold() + bookingForm.getAmount());
        }

        if (!available) {
            return ResponseEntity.status(422).body((Booking) Map.of(
                    "error", "Ticket unavailable"
            )).getBody();
        }

        concertService.save(concert);

        price = (long) Math.ceil(basePrice * (1 + multiplier));

        Booking booking = new Booking();
        booking.setCode(code);
        booking.setName(concert.getName());
        booking.setArtist(concert.getArtist());
        booking.setVenue(concert.getVenue());
        booking.setDatetime(concert.getDatetime());
        booking.setOrdertime(ordertime.toString());
        booking.setCategory(bookingForm.getCategory());
        booking.setStatus("booked");
        booking.setBasePrice(basePrice);
        booking.setPrice(price);
        booking.setAmount(booking.getAmount());
        booking.setTotalPrice(booking.getAmount() * price);
        booking.setUserId(userId);
        booking.setConcertId(concert.getId());


        return this.save(booking);
    }

    @Transactional
    public Booking cancel(Long userId, Long id) {
        Booking booking = this.findById(userId, id);

        Concert concert = this.concertService.findById(booking.getConcertId());

        if (booking.getCategory().equals("vip")) {
            concert.setVipSold(concert.getVipSold() - booking.getAmount());

        } else if (booking.getCategory().equals("general_admission")) {
            concert.setGeneralAdmissionSold(concert.getGeneralAdmissionSold() - booking.getAmount());

        } else {
            concert.setStandardSold(concert.getStandardSold() - booking.getAmount());
        }

        concertService.save(concert);

        booking.setStatus("cancelled");
        return repository.save(booking);
    }
}
