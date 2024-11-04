package com.xc.x_clone_backend.follow;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "follows")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    private String follower;
    private String following;
    private Date date;

    public Follow() {

    }

    public Follow(Integer id, String follower, String following, Date date) {
        this.id = id;
        this.follower = follower;
        this.following = following;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
