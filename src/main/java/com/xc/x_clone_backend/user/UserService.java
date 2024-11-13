package com.xc.x_clone_backend.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
public void updatePasswordHash(String username, String plainTextPassword) {
    User user = userRepository.findById(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
            
    user.setPassword(passwordEncoder.encode(plainTextPassword));
    userRepository.save(user);
}

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDisplayname(user.getUsername());
        user.setDate(new Date());
        return userRepository.save(user);
    }

    private void validateDisplayname(String displayname) {
        if (displayname == null || displayname.isEmpty()) {
            throw new IllegalArgumentException("Display name cannot be empty");
        }

        if (displayname.length() > 50) {
            throw new IllegalArgumentException("Display name must not exceed 50 characters");
        }
    }

    public User updateUser(User updatedUser) {
        Optional<User> existingUser = userRepository.findById(updatedUser.getUsername());
    
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            
            if (updatedUser.getDisplayname() != null) {
                validateDisplayname(updatedUser.getDisplayname());
                userToUpdate.setDisplayname(updatedUser.getDisplayname());
            }
            
            if (updatedUser.getAboutme() != null) userToUpdate.setAboutme(updatedUser.getAboutme());
            if (updatedUser.getAvatar() != null) userToUpdate.setAvatar(updatedUser.getAvatar());
            if (updatedUser.getLocation() != null) userToUpdate.setLocation(updatedUser.getLocation());
            if (updatedUser.getIfcheckmark() != null) userToUpdate.setIfcheckmark(updatedUser.getIfcheckmark());
            if (updatedUser.getBackground() != null) userToUpdate.setBackground(updatedUser.getBackground());
    
            return userRepository.save(userToUpdate);
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}