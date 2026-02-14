package com.ahmad.bsi.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingForm {
    @NotBlank
    private Long concertId;
    private String category;
    private Integer amount;
}
