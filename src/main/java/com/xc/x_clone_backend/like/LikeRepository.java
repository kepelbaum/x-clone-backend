package com.xc.x_clone_backend.like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

}
