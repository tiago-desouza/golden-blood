package io.github.tiagodesouza.goldenblood.repositories;

import io.github.tiagodesouza.goldenblood.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.stream.Stream;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value = """
            SELECT
                p.state,
                COUNT(*) AS total
            FROM
                persons p
            GROUP BY
                p.state
            """, nativeQuery = true)
    Stream<Object[]> streamCandidateCountByState();

    @Query(value = """
            SELECT
                CONCAT(FLOOR(TIMESTAMPDIFF(YEAR, p.birth_date, CURDATE()) / 10) * 10, '-',
                              FLOOR(TIMESTAMPDIFF(YEAR, p.birth_date, CURDATE()) / 10) * 10 + 9) AS age_group,
                AVG(p.weight / (p.height * p.height)) AS average_imc
            FROM
                persons p
            GROUP BY
                age_group
            """, nativeQuery = true)
    List<Object[]> findAverageIMCByAgeGroup();

    @Query(value = """
            SELECT
                p.gender,
                SUM(CASE WHEN p.weight / (p.height * p.height) > 30 THEN 1 ELSE 0 END) AS total_obese,
                COUNT(*) AS total_people
            FROM
                persons p
            GROUP BY
                p.gender
            """, nativeQuery = true)
    List<Object[]> findObesityPercentageByGender();

    @Query(value = """
            SELECT
                p.blood_type,
                AVG(TIMESTAMPDIFF(YEAR, p.birth_date, CURDATE())) AS average_age
            FROM
                persons p
            GROUP BY
                p.blood_type
            """, nativeQuery = true)
    List<Object[]> findAverageAgeByBloodType();

    @Query(value = """
            SELECT
                p.blood_type,
                SUM(CASE WHEN TIMESTAMPDIFF(YEAR, p.birth_date, CURDATE()) BETWEEN 16 AND 69 AND p.weight > 50 
                    THEN 1 ELSE 0 END) AS count
            FROM
                persons p
            GROUP BY
                p.blood_type
            """, nativeQuery = true)
    List<Object[]> findEligibleDonors();
}