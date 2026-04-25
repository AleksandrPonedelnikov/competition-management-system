package com.aponedelnikov.cms.service.dto;

import lombok.Data;
import java.util.Set;

@Data
public class TeamDTO {
    private Long id;
    private String name;
    private Long competitionId;
    private Set<Long> memberIds;
}
