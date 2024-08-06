package io.github.tiagodesouza.goldenblood.services;

import io.github.tiagodesouza.goldenblood.dtos.PersonDTO;

import java.util.List;
import java.util.Map;

public interface PersonService {

    void registerPersons(List<PersonDTO> personsDTO);

    Map<String, Integer> getCandidateCountByState();

    Map<String, Double> getAverageIMCByAgeGroup();

    Map<String, Double> getObesityPercentageByGender();

    Map<String, Double> getAverageAgeByBloodType();

    Map<String, Long> getPotentialDonors();
}
