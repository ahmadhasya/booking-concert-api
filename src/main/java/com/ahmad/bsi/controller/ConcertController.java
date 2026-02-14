package com.ahmad.bsi.controller;

import com.ahmad.bsi.model.Booking;
import com.ahmad.bsi.model.Concert;
import com.ahmad.bsi.model.User;
import com.ahmad.bsi.service.ConcertService;
import com.ahmad.bsi.service.UserService;
import com.ahmad.bsi.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/concerts")
public class ConcertController {
    private final ConcertService service;
    private final UserService userService;

    public ConcertController(ConcertService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping
    public List<Concert> getAllConcerts(@RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            return service.findSearch(search);
        }
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Concert getConcert(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Concert> createConcert(@RequestHeader("Authorization") String authHeader, @RequestBody Concert concert) {
        String token = authHeader.replace("Bearer ", "");
        String email = JwtUtil.validateToken(token);
        User user = this.userService.findByEmail(email);

        if (!user.isAdmin()) {
            return ResponseEntity.status(422).body((Concert) Map.of(
                    "error", "Ticket unavailable"
            ));
        }

        concert.setVipSold(0);
        concert.setGeneralAdmissionSold(0);
        concert.setStandardSold(0);
        return new ResponseEntity<>(service.save(concert), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Concert updateConcert(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody Concert concert) {
        String token = authHeader.replace("Bearer ", "");
        String email = JwtUtil.validateToken(token);
        User user = this.userService.findByEmail(email);

        if (!user.isAdmin()) {
            return ResponseEntity.status(422).body((Concert) Map.of(
                    "error", "Ticket unavailable"
            )).getBody();
        }
        return service.update(id, concert);
    }

    @GetMapping("/{id}/pricing")
    public ResponseEntity<Map<String, Double>> getConcertPricing(@PathVariable Long id) {
        Concert concert = service.findById(id);

        double vipMultiplier = ((double) concert.getVipSold() / concert.getVipCapacity()) * 2.5;
        double generalAdmissionMultiplier = ((double) concert.getGeneralAdmissionSold() / concert.getGeneralAdmissionCapacity()) * 2.5;
        double standardMultiplier = ((double) concert.getStandardSold() / concert.getStandardCapacity()) * 2.5;

        return ResponseEntity.ok(
                Map.of(
                        "vip", Math.ceil(concert.getVipPrice() * (1 + vipMultiplier)),
                        "generalAdmission", Math.ceil(concert.getGeneralAdmissionPrice() * (1 + generalAdmissionMultiplier)),
                        "standard", Math.ceil(concert.getStandardPrice() * (1 + standardMultiplier))
                )
        );
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<Map<String, Integer>> getConcertAvailability(@PathVariable Long id) {
        Concert concert = service.findById(id);

        return ResponseEntity.ok(
                Map.of(
                        "vip", concert.getVipCapacity() - concert.getVipSold(),
                        "generalAdmission", concert.getGeneralAdmissionCapacity() - concert.getGeneralAdmissionSold(),
                        "standard", concert.getStandardCapacity() - concert.getStandardSold()
                )
        );
    }
}
