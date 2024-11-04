package com.xc.x_clone_backend.follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/follow")
public class FollowController {
    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping
    public Map<String, Object> getFollows() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return followService.getFollowInfo(username);
    }

    @PostMapping("/{oneToFollow}")
    public ResponseEntity<String> toggleFollow(@PathVariable String oneToFollow) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isFollowed = followService.toggleFollow(oneToFollow, username);
        return ResponseEntity.ok(isFollowed ? "User followed" : "User unfollowed");
    }
}
