package com.anonymizer.auth.controller.jpa;

import com.anonymizer.auth.models.User;
import com.anonymizer.auth.services.jpa.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

// TODO (hard coded cross origin)
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    // @PreAuthorize
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    //@Autowired
    //AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

	@Autowired
    UserService userService;

	//@Autowired
    //JwtUtil jwtTokenUtil;

    @PostMapping(path = "/api/v1/user/login")
    public ResponseEntity<String> login() {
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    @PostMapping(path = "/api/v1/user/")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@Valid @NotNull @RequestBody User user) {
        LOG.debug(String.format("user: {} created", user.getUserName()));
        return  userService.createUser(user);
    }

    @GetMapping(path = "/api/v1/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/api/v1/user/{username}")
    public Optional<User> getUserByUserName(@PathVariable("username") String username) {
        return userService.getUserByName(username);
    }

    @DeleteMapping(path = "/api/v1/user/{username}")
    public void deletePersonById(@PathVariable("username") String username) {
        userService.deleteUser(username);
    }

    @PutMapping(path = "/api/v1/user/{id}")
    public User updateUser(@Valid @NotNull @RequestBody User user, @PathVariable("id") int id) {
        return userService.updateUser(user, id);
    }

    /*
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            // authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken
                            (
                                authenticationRequest.getUsername(),
                                authenticationRequest.getPassword()
                            ));
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        // get user Detail infos
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        // genetare token and respond
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

     */
}