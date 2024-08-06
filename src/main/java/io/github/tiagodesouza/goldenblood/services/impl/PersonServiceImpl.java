package io.github.tiagodesouza.goldenblood.services.impl;

import io.github.tiagodesouza.goldenblood.dtos.PersonDTO;
import io.github.tiagodesouza.goldenblood.models.Address;
import io.github.tiagodesouza.goldenblood.models.Contact;
import io.github.tiagodesouza.goldenblood.models.Person;
import io.github.tiagodesouza.goldenblood.repositories.PersonRepository;
import io.github.tiagodesouza.goldenblood.services.PersonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    @Override
    public void registerPersons(List<PersonDTO> personsDTO) {
        List<Person> persons = personsDTO.stream()
                .map(PersonServiceImpl::convertToEntity)
                .toList();

        personRepository.saveAll(persons);
    }

    @Transactional(readOnly = true)
    @Override
    public Map<String, Integer> getCandidateCountByState() {
        try (Stream<Object[]> stream = personRepository.streamCandidateCountByState()) {
            return stream.collect(Collectors.toMap(
                    result -> (String) result[0],
                    result -> ((Number) result[1]).intValue()
            ));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Map<String, Double> getAverageIMCByAgeGroup() {
        List<Object[]> results = personRepository.findAverageIMCByAgeGroup();
        Map<String, Double> averageIMCByAgeGroup = new HashMap<>();

        for (Object[] result : results) {
            String ageGroup = (String) result[0];
            BigDecimal formattedAverageIMC = BigDecimal.valueOf((Double) result[1])
                    .setScale(2, RoundingMode.HALF_UP);

            averageIMCByAgeGroup.put(ageGroup, formattedAverageIMC.doubleValue());
        }

        return averageIMCByAgeGroup;
    }

    @Transactional(readOnly = true)
    @Override
    public Map<String, Double> getObesityPercentageByGender() {
        List<Object[]> obesePeople = personRepository.findObesityPercentageByGender();
        Map<String, Double> obesityPercentageMap = new HashMap<>();

        for (Object[] obesePerson : obesePeople) {
            String gender = (String) obesePerson[0];
            long totalObese = ((Number) obesePerson[1]).longValue();
            long totalPeople = ((Number) obesePerson[2]).longValue();

            double obesityPercentage = totalPeople == 0 ? 0 : (double) totalObese / totalPeople * 100;
            BigDecimal formattedObesityPercentage = BigDecimal.valueOf(obesityPercentage).setScale(2, RoundingMode.HALF_UP);
            obesityPercentageMap.put(gender.toLowerCase(), formattedObesityPercentage.doubleValue());
        }

        return obesityPercentageMap;
    }

    @Transactional(readOnly = true)
    @Override
    public Map<String, Double> getAverageAgeByBloodType() {
        List<Object[]> bloodTypeByAverageAge = personRepository.findAverageAgeByBloodType();

        Map<String, Double> bloodTypeByAverageAgeMap = new HashMap<>();
        for (Object[] result : bloodTypeByAverageAge) {
            String bloodType = (String) result[0];
            double averageAge = ((Number) result[1]).doubleValue();
            BigDecimal formattedAverageAge = BigDecimal.valueOf(averageAge).setScale(2, RoundingMode.HALF_UP);
            bloodTypeByAverageAgeMap.put(bloodType, formattedAverageAge.doubleValue());
        }

        return bloodTypeByAverageAgeMap;
    }

    @Transactional(readOnly = true)
    @Override
    public Map<String, Long> getPotentialDonors() {
        List<Object[]> results = personRepository.findEligibleDonors();
        Map<String, Long> donorsByBloodType = new HashMap<>();

        Map<String, List<String>> recipients = new HashMap<>();
        recipients.put("A+", List.of("A+", "A-", "O+", "O-"));
        recipients.put("A-", List.of("A-", "O-"));
        recipients.put("B+", List.of("B+", "B-", "O+", "O-"));
        recipients.put("B-", List.of("B-", "O-"));
        recipients.put("AB+", List.of("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"));
        recipients.put("AB-", List.of("AB-", "A-", "B-", "O-"));
        recipients.put("O+", List.of("O+", "O-"));
        recipients.put("O-", List.of("O-"));

        for (Object[] objects : results) {
            String bloodType = (String) objects[0];
            long count = ((Number) objects[1]).longValue();
            donorsByBloodType.put(bloodType, count);
        }

        Map<String, Long> potentialDonors = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : recipients.entrySet()) {
            String recipientType = entry.getKey();
            List<String> donorTypes = entry.getValue();
            long totalDonors = donorTypes.stream()
                    .mapToLong(value -> donorsByBloodType.getOrDefault(value, 0L))
                    .sum();

            potentialDonors.put(recipientType, totalDonors);
        }
        return potentialDonors;
    }

    private static Person convertToEntity(PersonDTO personDTO) {
        Contact contact = buildContact(personDTO);
        Address address = buildAddress(personDTO);

        return Person.builder()
                .name(personDTO.name())
                .cpf(personDTO.cpf())
                .rg(personDTO.rg())
                .birthDate(parseDate(personDTO))
                .gender(personDTO.gender())
                .motherName(personDTO.mother())
                .fatherName(personDTO.father())
                .height(personDTO.height())
                .weight(personDTO.weight())
                .bloodType(personDTO.bloodType())
                .contact(contact)
                .address(address)
                .build();
    }

    private static Address buildAddress(PersonDTO personDTO) {
        return Address.builder()
                .postalCode(personDTO.postalCode())
                .street(personDTO.street())
                .number(personDTO.number())
                .neighborhood(personDTO.neighborhood())
                .city(personDTO.city())
                .state(personDTO.state())
                .build();
    }

    private static Contact buildContact(PersonDTO personDTO) {
        return Contact.builder()
                .email(personDTO.email())
                .landline(personDTO.landline())
                .mobile(personDTO.mobile())
                .build();
    }

    private static LocalDate parseDate(PersonDTO personDTO) {
        return LocalDate.parse(personDTO.birthDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
