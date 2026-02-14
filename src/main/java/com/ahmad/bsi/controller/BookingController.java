package com.ahmad.bsi.controller;

import com.ahmad.bsi.form.BookingForm;
import com.ahmad.bsi.model.Booking;
import com.ahmad.bsi.model.Concert;
import com.ahmad.bsi.model.User;
import com.ahmad.bsi.service.BookingService;
import com.ahmad.bsi.service.ConcertService;
import com.ahmad.bsi.service.UserService;
import com.ahmad.bsi.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private final BookingService service;
    private final UserService userService;
    private final ConcertService concertService;

    public BookingController(BookingService service, UserService userService, ConcertService concertService) {
        this.service = service;
        this.userService = userService;
        this.concertService = concertService;
    }

    @GetMapping
    public List<Booking> getAllBookings(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = JwtUtil.validateToken(token);
        User user = this.userService.findByEmail(email);
        return service.findAllByUserId(user.getId());
    }

    @GetMapping("/{id}")
    public Booking getBooking(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        String token = authHeader.replace("Bearer ", "");
        String email = JwtUtil.validateToken(token);
        User user = this.userService.findByEmail(email);
        return service.findById(user.getId(), id);
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestHeader("Authorization") String authHeader, @RequestBody BookingForm bookingForm) {
        String token = authHeader.replace("Bearer ", "");
        String email = JwtUtil.validateToken(token);
        User user = this.userService.findByEmail(email);

        Booking booking = this.service.book(user.getId(), bookingForm);

        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancelBooking(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        String token = authHeader.replace("Bearer ", "");
        String email = JwtUtil.validateToken(token);

        User user = this.userService.findByEmail(email);

        Booking booking = this.service.cancel(user.getId(), id);

        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

}
