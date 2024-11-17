package com.xc.x_clone_backend.gif;

public class Gif {
    private String id;
    private String title;
    private Images images;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Images getImages() {
        return images;
    }
    
    public void setImages(Images images) {
        this.images = images;
    }
}