package com.aponedelnikov.cms.service.impl;

import com.aponedelnikov.cms.entity.Team;
import com.aponedelnikov.cms.entity.Competition;
import com.aponedelnikov.cms.entity.User;
import com.aponedelnikov.cms.repository.TeamRepository;
import com.aponedelnikov.cms.repository.UserRepository;
import com.aponedelnikov.cms.repository.CompetitionRepository;
import com.aponedelnikov.cms.service.TeamService;
import com.aponedelnikov.cms.service.dto.TeamDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TeamServiceImpl implements TeamService {
    private TeamRepository teamRepository;
    private CompetitionRepository competitionRepository;
    private UserRepository userRepository;

public TeamServiceImpl(TeamRepository teamRepository, CompetitionRepository competitionRepository, UserRepository userRepository) {
    this.teamRepository = teamRepository;
    this.competitionRepository = competitionRepository;
    this.userRepository = userRepository;
}

    @Override
    public TeamDTO createTeam(TeamDTO teamDTO) {
        /* проверяем существование соревановния */
        Competition competition = competitionRepository.findById(teamDTO.getCompetitionId())
                .orElseThrow(() -> new RuntimeException("Competition not found"));

        /* проверяем уникальность названия команды в соревновании */
        if(teamRepository.existsByNameAAndCompetitionId(teamDTO.getName(), teamDTO.getCompetitionId())){
            throw new RuntimeException("Team with this name already exists in the competition");
        }

        Team team = new Team();
        team.setName(teamDTO.getName());
        team.setCompetition(competition);

        /* добавляем уникальных участников */
        if(teamDTO.getMemberIds() != null && !teamDTO.getMemberIds().isEmpty()) {
            Set<User> members = userRepository.findAllById(teamDTO.getMemberIds())
                    .stream()
                    .collect(Collectors.toSet());

            team.setMembers(members);
        }

        Team savedTeam = teamRepository.save(team);
    return convertToDto(team);
    }

    @Override
    public TeamDTO getTeamById(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Team not found"));
        return convertToDto(team);
    }

    @Override
    public List<TeamDTO> getTeamsByCompetition(Long competitionId) {
        Competition competition = competitionRepository.findById(competitionId).orElseThrow(() -> new RuntimeException("Competition not found"));

        return teamRepository.findByCompetition(competition).stream()
                .map(this::convertToDto)
                .collect.toList();
    }

    @Override
    public TeamDTO updateTeam(Long id, TeamDTO teamDTO) {
        Team existingTeam = teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Team not found"));

        /* обновляем название если изменилось и уникальное */
        if(!existingTeam.getName().equals(teamDTO.getName())) {
            if(teamRepository.existsByNameAAndCompetitionId(teamDTO.getName(), teamDTO.getCompetitionId())) {
                throw new RuntimeException("Another team with this name already exists in the competition");
            }
            existingTeam.setName(teamDTO.getName());
        }

        /* обновляем соревнование */
        if(teamDTO.getMemberIds() != null) {
            Competition newCompetition = competitionRepository.findById(teamDTO.getCompetitionId())
                    .orElseThrow(() -> new RuntimeException("New competition not found"));
            existingTeam.setCompetition(newCompetition);
        }

        /* обновляем участников */
        if(teamDTO.getMemberIds() != null) {
            Set<User> newMembers = userRepository.findAllById(teamDTO.getMemberIds())
                    .stream()
                    .collect(Collectors.toSet());
            existingTeam.setMembers(newMembers);
        }
        Team updatedTeam = teamRepository.save(existingTeam);
        return convertToDto(updatedTeam);
    }

    @Override
    public void deleteTeam(Long id) {
        if(!teamRepository.existsById(id)) {
            throw new RuntimeException("Team not found");
        }
        teamRepository.deleteById(id);
    }

    private TeamDTO convertToDto(Team team) {
    TeamDTO dto = new TeamDTO();
    dto.setId(team.getId());
    dto.setName(team.getName());
    dto.setCompetitionId(team.getCompetition().getId());

    /* преобразуем участников в список Id */
        if(team.getMembers() != null) {
            dto.setMemberIds(team.getMembers().stream()
                    .map(User::getId)
                    .collect(Collectors.toSet()));
        }
        else {
            dto.setMemberIds(Set.of());
        }
        return dto;
    }
}
