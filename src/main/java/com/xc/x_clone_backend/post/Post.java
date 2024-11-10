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

    private Integer ifretweet;

    private String media_url;

    private String media_type;

    public Post() {

    }

    public Post(Integer post_id, String username, String content, Date date, Integer ifreply, Integer ifretweet, String media_url, String media_type) {
        this.post_id = post_id;
        this.username = username;
        this.content = content;
        this.date = date;
        this.ifreply = ifreply;
        this.ifretweet = ifretweet;
        this.media_url = media_url;
        this.media_type = media_type;
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

    public void setIfReply(Integer ifreply) {
        this.ifreply = ifreply;
    }

    public Integer getIfretweet() {return ifretweet;}

    public void setIfretweet(Integer ifretweet) {this.ifretweet = ifretweet;}

    public String getMedia_url() {return media_url;}

    public void setMedia_url(String media_url) {this.media_url = media_url;}

    public String getMedia_type() {return media_type;}

    public void setMedia_type(String media_type) {this.media_type = media_type;}
}
