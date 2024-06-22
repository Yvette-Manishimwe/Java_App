package com.ecommerce.ne.service;

import com.ecommerce.ne.entity.UserData;
import com.ecommerce.ne.exception.EmailAlreadyExistsException;
import com.ecommerce.ne.repository.UserDataRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserDataService implements UserDetailsService {
    @Autowired
    private UserDataRepository repository;

    @Autowired
    private PasswordEncoder encoder;


    public UserDataService() {

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserData> userData = repository.findByEmail(username);
        //Convert userData to UserDetails
        return userData.map(UserDataDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
    public UserData loadCurrentUser(String username) throws UsernameNotFoundException {
        Optional<UserData> userDetail = repository.findByEmail(username);
        return userDetail
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email " +
                        username));
    }

    @Transactional
    public String addUser(UserData userData) {
        try {
            userData.setPassword(encoder.encode(userData.getPassword()));
            repository.saveAndFlush(userData);
            return "User Added Successfully";
        } catch (DataIntegrityViolationException e) {
            // Catch exception for duplicate email
            throw new EmailAlreadyExistsException("Email already exists: " + userData.getEmail());

        }
    }
    public List<UserData> listAll() {
        return repository.findAll();
    }
}


