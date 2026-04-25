package com.aponedelnikov.cms.repository;

import com.aponedelnikov.cms.entity.Competition;
import com.aponedelnikov.cms.entity.Match;
import com.aponedelnikov.cms.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface MatchRepository {
    /* поиск матчей по соревнованию */
    List<Match> findByCompetition(Competition competition);

    /* поиск матчей по id соревнованию */
    List<Match> findByCompetitionId(Long competitionId);

    /* поиск матче по команде */
    List<Match> findByTeam1OrTeam2(Team team1, Team team2);

    /* поиск матчей в определнном временном промежутке */
    List<Match> findByMatchDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /* поиск предстоящих матчей */
    List<Match> findByMatchDateAfter(LocalDateTime currentDate);

    /* поиск завершенных матчей */
    List<Match> findByResultNoNull();

    /* поиск матчей с определенным результатом */
    List<Match> findByResult(String result);

    /*подсчет матчей в соревновании */
    long countByCompetition(Competition competition);

    /* поиск матей, запланированых на конкретную дату */
    List<Match> findByMatchDate(LocalDateTime matchDate);



}
