package com.anonymizer.auth.service.jpa;

import com.anonymizer.auth.model.User;
import com.anonymizer.auth.repository.UserRepository;
import com.anonymizer.auth.security.JpaUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserServiceDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> userCredential = userRepository.findByUserName(userName);
        userCredential.orElseThrow(() -> new UsernameNotFoundException("Not found " + userName));
        return  userCredential.map(JpaUserDetail::new).get();
    }
}
