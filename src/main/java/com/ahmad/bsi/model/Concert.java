package com.ahmad.bsi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "concerts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;
    private String artist;
    private String venue;
    private String datetime;
    private String status;

    private Long vipPrice;
    private Integer vipCapacity;
    @Column(nullable = false)
    @org.hibernate.annotations.ColumnDefault("0")
    private Integer vipSold;


    private Long standardPrice;
    private Integer standardCapacity;
    @Column(nullable = false)
    @org.hibernate.annotations.ColumnDefault("0")
    private Integer standardSold;


    private Long generalAdmissionPrice;
    private Integer generalAdmissionCapacity;
    @Column(nullable = false)
    @org.hibernate.annotations.ColumnDefault("0")
    private Integer generalAdmissionSold;
}
