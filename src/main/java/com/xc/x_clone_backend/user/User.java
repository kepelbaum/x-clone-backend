package com.xc.x_clone_backend.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "users")
public class    User {
    @Id
    @Column(name = "username", unique = true)
    private String username;

    private String avatar;

    private String background;

    private String aboutme;

    private String location;

    private Boolean ifcheckmark;

    private String displayname;

    private Date date;

    @JsonIgnore
    private String password;

    public User() {
    }

    public User(String username, String avatar, String aboutme, String location, Boolean ifcheckmark, String password, String background, String displayname, Date date) {
        this.username = username;
        this.avatar = avatar;
        this.aboutme = aboutme;
        this.location = location;
        this.ifcheckmark = ifcheckmark;
        this.password = password;
        this.background = background;
        this.displayname = displayname;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getIfcheckmark() {
        return ifcheckmark;
    }

    public void setIfcheckmark(Boolean ifcheckmark) {
        this.ifcheckmark = ifcheckmark;
    }

    public String getPassword() {
        return password;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
