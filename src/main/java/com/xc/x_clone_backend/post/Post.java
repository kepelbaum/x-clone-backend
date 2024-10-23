package com.xc.x_clone_backend.post;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", unique = true)
    private Integer post_id;

    private String username;

    private String content;

    private Date date;

    private Integer ifreply;

    public Post() {

    }

    public Post(Integer post_id, String username, String content, Date date, Integer ifreply) {
        this.post_id = post_id;
        this.username = username;
        this.content = content;
        this.date = date;
        this.ifreply = ifreply;
    }

    public Integer getPost_id() {
        return post_id;
    }

    public void setPost_id(Integer post_id) {
        this.post_id = post_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getIfreply() {
        return ifreply;
    }

    public void setIfReply(Integer ifReply) {
        this.ifreply = ifreply;
    }
}
