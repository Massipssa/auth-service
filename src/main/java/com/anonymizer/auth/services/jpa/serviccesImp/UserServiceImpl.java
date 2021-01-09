package com.anonymizer.auth.services.jpa.serviccesImp;

import com.anonymizer.auth.services.jpa.UserService;
import com.anonymizer.auth.models.User;
import com.anonymizer.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * TODO
 * Not add existing user
 * Add validation control before adding user
 * Add delete user by id
 * Check password strategy
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    //@Autowired
    //UserValidator userValidator;

    @Override
    public User createUser(User user) {
        //userValidator.apply(user)
        Optional<User> u = userRepository.findByUserName(user.getUserName());
        if (!u.isPresent()) {
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User updateUser(User user, int id) {
        return userRepository.findById(id)
                .map(u -> {
                    u.setUserName(user.getUserName());
                    u.setPassword(passwordEncoder.encode(user.getPassword()));
                    u.setEmail(user.getEmail());
                    return userRepository.save(u);
                })
                .orElse(null);
    }

    @Override
    public void deleteUser(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isPresent()) {
            userRepository.deleteById(user.get().getId());
        }
    }
}
