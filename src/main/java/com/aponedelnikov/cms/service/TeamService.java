package com.aponedelnikov.cms.service;

import com.aponedelnikov.cms.service.dto.TeamDTO;
import java.util.List;

public interface TeamService {
    TeamDTO createTeam(TeamDTO teamDTO);
    TeamDTO getTeamById(Long id);
    List<TeamDTO> getTeamsByCompetition(Long competitionId);
    TeamDTO updateTeam(Long id, TeamDTO teamDTO);
    void deleteTeam(Long id);

    List<TeamDTO> getAllTeams();
    TeamDTO addMemberToTeam(Long teamId, Long userId);
    TeamDTO removeMemberFromTeam(Long teamId, Long userId);
}
