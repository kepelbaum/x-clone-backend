package com.xc.x_clone_backend.gif;

import java.util.List;

public class GiphyResponse {
    private List<Gif> data;
    
    public List<Gif> getData() {
        return data;
    }
    
    public void setData(List<Gif> data) {
        this.data = data;
    }
}