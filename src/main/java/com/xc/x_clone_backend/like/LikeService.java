package com.xc.x_clone_backend.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository PostRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
    }

    public Map<Integer, Long> getLikeCounts() {
        return likeRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Like::getPost_id,
                        Collectors.counting()
                ));
    }

    public Map<String, Object> getLikeInfo(String username) {
        Map<String, Object> response = new HashMap<>();

        Map<Integer, Long> likeCounts = getLikeCounts();

        List<Like> notifications = likeRepository.findAll().stream()
                .filter(like -> username.equals(like.getUsername()) || username.equals(like.getPoster()))
                .collect(Collectors.toList());

        response.put("likeCounts", likeCounts);
        response.put("notifications", notifications);
        return response;
    }

    public boolean toggleLike(Integer postId, String username) {
        Optional<Like> existingLike = likeRepository.findAll().stream()
                .filter(like -> postId.equals(like.getPost_id()) && username.equals(like.getUsername()))
                .findFirst();

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            return false;
        } else {
            Like newLike = new Like();
            newLike.setPost_id(postId);
            newLike.setUsername(username);
            newLike.setPoster(postRepository.getPostById(postId).getUsername());
            newLike.setDate(new Date());
            likeRepository.save(newLike);
            return true;
        }
    }
}