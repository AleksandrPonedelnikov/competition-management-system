package com.aponedelnikov.cms.service.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CompetitionDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private Long organizerId;

}
