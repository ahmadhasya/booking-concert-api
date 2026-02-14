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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ReportController {
    private final BookingService service;
    private final ConcertService concertService;

    public ReportController(BookingService service, ConcertService concertService) {
        this.service = service;
        this.concertService = concertService;
    }

    @GetMapping("/concerts/{id}/settlement")
    public List<Booking> getAllBookingsByConcertId(@PathVariable Long id) {
        return service.findAllByConcertIdAndStatus(id, "booked");
    }

    @GetMapping("/transactions")
    public List<Booking> getAllBookings() {
        return service.findAll();
    }

    @GetMapping("/analytics/dashboard")
    public Map<String, Object> getAnalytics() {
        Map<String, Object> dashboard = new HashMap<>();
        long totalBookings = service.count();
        long totalRevenue = service.findAll().stream().mapToLong(Booking::getPrice).sum();
        long vipSold = concertService.findAll().stream().mapToLong(Concert::getVipSold).sum();
        long standardSold = concertService.findAll().stream().mapToLong(Concert::getStandardSold).sum();
        long gaSold = concertService.findAll().stream().mapToLong(Concert::getGeneralAdmissionSold).sum();
        dashboard.put("totalBookings", totalBookings);
        dashboard.put("totalRevenue", totalRevenue);
        dashboard.put("vipTicketsSold", vipSold);
        dashboard.put("standardTicketsSold", standardSold);
        dashboard.put("generalAdmissionTicketsSold", gaSold);
        return dashboard;
    }
}
