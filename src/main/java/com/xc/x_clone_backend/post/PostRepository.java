package com.xc.x_clone_backend.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface PostRepository extends JpaRepository<Post, Integer> {

    }
