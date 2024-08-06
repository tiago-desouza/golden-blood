package io.github.tiagodesouza.goldenblood.auth.repository;

import io.github.tiagodesouza.goldenblood.auth.models.Role;
import io.github.tiagodesouza.goldenblood.auth.models.enumerated.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}
