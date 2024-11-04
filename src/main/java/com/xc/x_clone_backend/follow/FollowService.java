package com.xc.x_clone_backend.follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class FollowService {
    private final FollowRepository followRepository;

    @Autowired
    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public Map<String, Long> getFollowerCounts() {
        return followRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Follow::getFollowing,
                        Collectors.counting()
                ));
    }

    public Map<String, Long> getFollowingCounts() {
        return followRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Follow::getFollower,
                        Collectors.counting()
                ));
    }

    public Map<String, Object> getFollowInfo(String username) {
        Map<String, Object> response = new HashMap<>();

        Map<String, Long> followerCounts = getFollowerCounts();
        Map<String, Long> followingCounts = getFollowingCounts();

        List<Follow> notifications = followRepository.findAll().stream()
                .filter(follow -> username.equals(follow.getFollowing()))
                .collect(Collectors.toList());

        response.put("followerCounts", followerCounts);
        response.put("followingCounts", followingCounts);
        response.put("notifications", notifications);
        return response;
    }

    public boolean toggleFollow(String oneToFollow, String username) {
        Optional<Follow> existingFollow = followRepository.findAll().stream()
                .filter(follow -> username.equals(follow.getFollower()) && oneToFollow.equals(follow.getFollowing()))
                .findFirst();

        if (existingFollow.isPresent()) {
            followRepository.delete(existingFollow.get());
            return false;
        } else {
            Follow newFollow = new Follow();
            newFollow.setFollowing(oneToFollow);
            newFollow.setFollower(username);
            newFollow.setDate(new Date());
            followRepository.save(newFollow);
            return true;
        }
    }
}