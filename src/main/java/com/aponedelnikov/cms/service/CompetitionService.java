package com.aponedelnikov.cms.service;

import com.aponedelnikov.cms.service.dto.CompetitionDTO;
import java.util.List;

public interface CompetitionService {
    CompetitionDTO createCompetition(CompetitionDTO competitionDTO);
    CompetitionDTO getCompetitionById(Long id);
    List<CompetitionDTO> getAllCompetitionsByOrganizer(Long organizer);
    List<CompetitionDTO> getAllCompetitionsByStatus(String status);
    CompetitionDTO updateCompetition(Long id, CompetitionDTO competitionDTO);
    void deleteCompetition(Long id);
    List<CompetitionDTO> getAllCompetitions();
    List<CompetitionDTO> getCompetitionsByOrganizer(Long organizerId);
    List<CompetitionDTO> getCompetitionsByStatus(String status);

}
