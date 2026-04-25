package com.aponedelnikov.cms.repository;

import com.aponedelnikov.cms.entity.Competition;
import com.aponedelnikov.cms.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    /* поиск команд по названию (частичное совпадение) */
    List<Team> findByNameContaining(String name);

    /* поиск всех команд для конкретного соревнования */
    List<Team> findByCompetition(Competition competition);

    /* поиск по id соревнования */
    List<Team> findByCompetitionId(Long competitionId);

    /* проверка существования команды с определенным названием в конкретном сорвеновании */
    boolean existsByNameAAndCompetitionId(String name, Long competitionId);

    /* поиск команды по названию и соревнованию */
    Optional<Team> findByNameAAndCompetition(String name, Competition competition);

    /* получение количества команд в соревновании */
    long countByCompetition(Competition competition);
}
