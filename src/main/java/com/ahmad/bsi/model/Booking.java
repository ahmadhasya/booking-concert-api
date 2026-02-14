package com.ahmad.bsi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String code;
    private String name;
    private String artist;
    private String venue;
    private String datetime;
    private String ordertime;
    private String category;
    private String status;
    private Long basePrice;
    private Integer amount;
    private Long price;
    private Long totalPrice;
    private Long userId;
    private Long concertId;
}