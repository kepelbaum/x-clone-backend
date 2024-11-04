package com.xc.x_clone_backend.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/like")
public class LikeController {
    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public Map<String, Object> getLikes() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return likeService.getLikeInfo(username);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<String> toggleLike(@PathVariable Integer postId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isLiked = likeService.toggleLike(postId, username);
        return ResponseEntity.ok(isLiked ? "Post liked" : "Post unliked");
    }
}