package com.aponedelnikov.cms.repository;

import com.aponedelnikov.cms.entity.Competition;
import com.aponedelnikov.cms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    List<Competition> findByOrganizer(User organizer);
    List<Competition> findByStatus(String status);

}
