package com.xc.x_clone_backend.gif;

import com.fasterxml.jackson.annotation.JsonProperty; 

public class Images {
    @JsonProperty("fixed_height")
    private ImageDetail fixedHeight;
    
    public ImageDetail getFixedHeight() {
        return fixedHeight;
    }
    
    public void setFixedHeight(ImageDetail fixedHeight) {
        this.fixedHeight = fixedHeight;
    }
}