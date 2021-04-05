package com.anonymizer.auth.controller.jpa;

import com.anonymizer.auth.model.User;
import com.anonymizer.auth.service.jpa.UserService;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

// TODO (hard coded cross origin)
@RestController
@RequestMapping("/api/v1/auth/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {


    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
    UserService userService;

	@PostMapping(path = "/login")
    public ResponseEntity<String> login() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.CREATED)
    //@HystrixCommand(fallbackMethod = "/fallback")
    public User addUser(@Valid @NotNull @RequestBody User user) {
        LOG.debug(String.format("user: {} created", user.getUserName()));
        return  userService.createUser(user);
    }

    @GetMapping(path = "/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/username/{username}")
    public Optional<User> getUserByUserName(@PathVariable("username") String username) {
        return userService.getUserByName(username);
    }

    @GetMapping(path = "/id/{id}")
    public Optional<User> getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @DeleteMapping(path = "/{id}")
    public void deletePersonById(@PathVariable("id") int id) {
        userService.deleteUserById(id);
    }

    @PutMapping(path = "/{id}")
    public User updateUser(@Valid @NotNull @RequestBody User user, @PathVariable("id") int id) {
        return userService.updateUser(user, id);
    }

    @RequestMapping("/fallback")
    public String fallBack() {
	    return  "The service AUTH is not available";
    }
}