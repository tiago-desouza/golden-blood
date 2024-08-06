package io.github.tiagodesouza.goldenblood.auth.controller;

import io.github.tiagodesouza.goldenblood.auth.controller.dto.LoginRequest;
import io.github.tiagodesouza.goldenblood.auth.controller.dto.LoginResponse;
import io.github.tiagodesouza.goldenblood.auth.controller.dto.SignupRequest;
import io.github.tiagodesouza.goldenblood.auth.models.User;
import io.github.tiagodesouza.goldenblood.auth.security.jwt.JwtUtils;
import io.github.tiagodesouza.goldenblood.auth.service.AuthenticationService;
import io.github.tiagodesouza.goldenblood.auth.service.UserDetailsImpl;
import io.github.tiagodesouza.goldenblood.auth.service.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtils jwtUtils;

    private final AuthenticationService authenticationService;

    public AuthController(JwtUtils jwtUtils, AuthenticationService authenticationService) {
        this.jwtUtils = jwtUtils;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody SignupRequest signupRequest) {
        authenticationService.signup(signupRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        User authenticatedUser = authenticationService.authenticate(loginRequest);

        UserDetailsImpl userDetails = UserDetailsImpl.build(authenticatedUser);

        String jwtToken = jwtUtils.generateToken(userDetails);

        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtUtils.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
