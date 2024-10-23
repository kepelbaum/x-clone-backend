package com.xc.x_clone_backend.config;

import com.xc.x_clone_backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PasswordUpdateRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Update passwords for existing users
            userService.updatePasswordHash("admin", "admin");
            userService.updatePasswordHash("notanadmin", "notanadmin");
            // Add more users as needed

            System.out.println("Passwords updated successfully!");
        } catch (RuntimeException e) {
            System.out.println("Error updating passwords: " + e.getMessage());
        }
    }
}