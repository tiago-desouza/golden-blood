package io.github.tiagodesouza.goldenblood.controllers;

import io.github.tiagodesouza.goldenblood.dtos.PersonDTO;
import io.github.tiagodesouza.goldenblood.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/persons")
@CrossOrigin(origins = "*")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<Void> registerPersons(@RequestBody List<PersonDTO> personsDTO) {
        System.out.println(personsDTO);
        return ResponseEntity.created(URI.create("/")).build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/candidates-by-state")
    public ResponseEntity<Map<String, Integer>> getCandidatesByState() {
        Map<String, Integer> candidatesByState = personService.getCandidateCountByState();
        return ResponseEntity.ok(candidatesByState);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/average-imc-by-age-group")
    public ResponseEntity<Map<String, Double>> getAverageIMCByAgeGroup() {
        Map<String, Double> averageIMCByAgeGroup = personService.getAverageIMCByAgeGroup();
        return ResponseEntity.ok(averageIMCByAgeGroup);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/obesity-percentage")
    public ResponseEntity<Map<String, Double>> getObesityPercentage() {
        Map<String, Double> obesityPercentage = personService.getObesityPercentageByGender();
        return ResponseEntity.ok(obesityPercentage);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/average-age-by-blood-type")
    public ResponseEntity<Map<String, Double>> getAverageAgeByBloodType() {
        Map<String, Double> averageAgeByBloodType = personService.getAverageAgeByBloodType();
        return ResponseEntity.ok(averageAgeByBloodType);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/potential-donors")
    public ResponseEntity<Map<String, Long>> getPotentialDonors() {
        Map<String, Long> potentialDonors = personService.getPotentialDonors();
        return ResponseEntity.ok(potentialDonors);
    }
}
