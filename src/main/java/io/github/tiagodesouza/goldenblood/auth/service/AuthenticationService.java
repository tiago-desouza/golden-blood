package io.github.tiagodesouza.goldenblood.auth.service;

import io.github.tiagodesouza.goldenblood.auth.controller.dto.LoginRequest;
import io.github.tiagodesouza.goldenblood.auth.controller.dto.MessageResponse;
import io.github.tiagodesouza.goldenblood.auth.controller.dto.SignupRequest;
import io.github.tiagodesouza.goldenblood.auth.models.Role;
import io.github.tiagodesouza.goldenblood.auth.models.User;
import io.github.tiagodesouza.goldenblood.auth.models.enumerated.ERole;
import io.github.tiagodesouza.goldenblood.auth.repository.RoleRepository;
import io.github.tiagodesouza.goldenblood.auth.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository, RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.username())) {
            throw new RuntimeException("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(signupRequest.email())) {
            throw new RuntimeException("Error: Email is already taken!");
        }

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        User user = new User(signupRequest.username(),
                signupRequest.email(),
                passwordEncoder.encode(signupRequest.password()));
        user.setRoles(roles);

        userRepository.save(user);
    }

    public User authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        return userRepository.findByUsername(loginRequest.username())
                .orElseThrow();
    }
}