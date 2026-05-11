package com.aponedelnikov.cms.service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CompetitionDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Long organizerId;

}
