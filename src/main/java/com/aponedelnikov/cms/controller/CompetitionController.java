package com.aponedelnikov.cms.controller;

import com.aponedelnikov.cms.service.CompetitionService;
import com.aponedelnikov.cms.service.dto.CompetitionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competitions")
public class CompetitionController {

    private final CompetitionService competitionService;

    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @GetMapping
    public ResponseEntity<List<CompetitionDTO>> getAllCompetitions() {
        return ResponseEntity.ok(competitionService.getAllCompetitions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompetitionDTO> getCompetitionById(@PathVariable Long id) {
        return ResponseEntity.ok(competitionService.getCompetitionById(id));
    }

    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<CompetitionDTO>> getCompetitionsByOrganizer(@PathVariable Long organizerId) {
        return ResponseEntity.ok(competitionService.getCompetitionsByOrganizer(organizerId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CompetitionDTO>> getCompetitionsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(competitionService.getCompetitionsByStatus(status));
    }

    @PostMapping
    public ResponseEntity<CompetitionDTO> createCompetition(@RequestBody CompetitionDTO competitionDTO) {
        return ResponseEntity.ok(competitionService.createCompetition(competitionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompetitionDTO> updateCompetition(@PathVariable Long id, @RequestBody CompetitionDTO competitionDTO) {
        return ResponseEntity.ok(competitionService.updateCompetition(id, competitionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetition(@PathVariable Long id) {
        competitionService.deleteCompetition(id);
        return ResponseEntity.noContent().build();
    }


}
