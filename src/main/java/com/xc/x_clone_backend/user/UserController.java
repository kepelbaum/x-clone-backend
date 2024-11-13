package com.xc.x_clone_backend.user;

import com.xc.x_clone_backend.cloudinary.CloudinaryService;
import com.xc.x_clone_backend.user.User;
import com.xc.x_clone_backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/user")
public class UserController {
    private final UserService userService;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public UserController(UserService userService, CloudinaryService cloudinaryService) {
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping
    public List<User> getUsers(
            @RequestParam(required = false) String username
    ) {
        return userService.getUsers();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                .badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        user.setUsername(username);
        User updatedUser = userService.updateUser(user);
        if (updatedUser != null) return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/avatar")
    public ResponseEntity<User> updateAvatar(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            String imageUrl = cloudinaryService.uploadFile(file, "avatars");
            User user = userService.getUserById(username);
                user.setAvatar(imageUrl);
                User updatedUser = userService.updateUser(user);
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/background")
    public ResponseEntity<User> updateBackground(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            String imageUrl = cloudinaryService.uploadFile(file, "backgrounds");
            User user = userService.getUserById(username);
                user.setBackground(imageUrl);
                User updatedUser = userService.updateUser(user);
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}