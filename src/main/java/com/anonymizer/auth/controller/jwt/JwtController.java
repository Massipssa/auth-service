package com.anonymizer.auth.controller.jwt;

import com.anonymizer.auth.model.jwt.JwtRequest;
import com.anonymizer.auth.model.jwt.JwtResponse;
import com.anonymizer.auth.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/jwt")
@CrossOrigin(origins = "http://localhost:4200")
public class JwtController {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping(path = "/authenticate")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {

        try {
            // authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken
                            (
                                    jwtRequest.getUsername(),
                                    jwtRequest.getPassword()
                            ));
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        // get user details
        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        // generate token and respond
        final String jwt = jwtUtility.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

}
