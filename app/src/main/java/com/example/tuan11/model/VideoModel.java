package com.example.tuan11.model;

import java.io.Serializable;

public class VideoModel implements Serializable{
    private String id;
    private String title;
    private String description;
    private String url;

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getUrl() { return url; }
}
