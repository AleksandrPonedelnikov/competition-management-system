package com.aponedelnikov.cms.service.impl;

import com.aponedelnikov.cms.entity.Competition;
import com.aponedelnikov.cms.entity.User;
import com.aponedelnikov.cms.repository.CompetitionRepository;
import com.aponedelnikov.cms.repository.UserRepository;
import com.aponedelnikov.cms.service.CompetitionService;
import com.aponedelnikov.cms.service.dto.CompetitionDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*import com.aponedelnikov.cms.entity.Status;*/

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final UserRepository userRepository;

    public CompetitionServiceImpl(CompetitionRepository competitionRepository, UserRepository userRepository) {
        this.competitionRepository = competitionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CompetitionDTO createCompetition(CompetitionDTO competitionDTO) {
        /* проверка существования организатора */
        User organizer = userRepository.findById(competitionDTO.getOrganizerId()).getOrganizerId()
                .orElseThrow(() -> new RuntimeException("Organizer not found"));

        Competition competition = new Competition();
        competition.setName(competitionDTO.getName());
        competition.setDescription(competitionDTO.getDescription());
        competition.getStartDate(competitionDTO.getStartDate());
        competition.getEndDate(competitionDTO.getEndDate());
        competition.setStatus(Status.valueOf(competitionDTO.getStatus()));
        competition.setOrganizer(organizer);

        Competition savedCompetition = competitionRepository.save(competition);
        return convertToDto(savedCompetition);
    }

    @Override
    public CompetitionDTO getCompetitionById(Long id) {
        Competition competition = competitionRepository.findById(id).orElseThrow(() -> new RuntimeException("Competition not found"));

        return convertToDto(competition);
    }

    @Override
    public List<CompetitionDTO> getAllCompetitionsByOrganizer(Long organizerId) {
        User organizer = userRepository.findById(organizerId).orElseThrow(() -> new RuntimeException("Organizer not found"));
        return competitionRepository.findByOrganizer(organizer.stream().map(this::convertToDto))
                .collect(Collectors.toList());
    }

    @Override
    public List<CompetitionDTO> getAllCompetitionsByStatus(String status) {
        return competitionRepository.findByStatus(Status.valueOf(status)).stream()
                .map(this::convertDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompetitionDTO updateCompetition(Long id, CompetitionDTO competitionDTO) {
        Competition exisingCompetition = competitionRepository.findById(id).orElseThrow(() -> new RuntimeException("Competition not found"));

        exisingCompetition.setName(competitionDTO.getName());
        exisingCompetition.setDescription(competitionDTO.getDescription());
        exisingCompetition.setStartDate(competitionDTO.getStartDate());
        exisingCompetition.setEndDate(competitionDTO.getEndDate());
        exisingCompetition.setStatus(Status.valueOf(competitionDTO.getStatus());

        /* обновляем организатора если нужно */
        if(competitionDTO.getOrganizerId() != null) {
            User newOrganizer = userRepository.findById(competitionDTO.getOrganizerId()).orElseThrow(() -> new RuntimeException("New Organizer not found"));

            exisingCompetition.setOrganizer(newOrganizer);
        }

        Competition updatedCompetition = competitionRepository.save(exisingCompetition);

        return convertToDto(updatedCompetition);
    }

    @Override
    public void deleteCompetition(Long id) {
        if(!competitionRepository.existsById(id)) {
            throw new RuntimeException("Competition not found");
        }

        competitionRepository.deleteById(id);
    }

    private CompetitionDTO convertToDto(Competition competition) {
        CompetitionDTO dto = new CompetitionDTO();

        dto.setId(competition.getId());
        dto.setName(competition.getName());
        dto.setDescription(competition.getDescription());
        dto.setStartDate(competition.getStartDate());
        dto.setEndDate(competition.getEndDate());
        dto.setStatus(competition.getStatus().name());
        dto.setOrganizerId(competition.getOrganizer().getId());

        return dto;
    }


}


