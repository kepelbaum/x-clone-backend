package com.xc.x_clone_backend.message;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", unique = true)
    private Integer id;

    private String sender;
    private String recipient;
    private String content;
    private String media_url;
    private String media_type;
    private Date date;

    public Message() {

    }

    public Message(Integer id, String sender, String recipient, String content, String media_url, String media_type, Date date) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.media_url = media_url;
        this.media_type = media_type;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }
}




