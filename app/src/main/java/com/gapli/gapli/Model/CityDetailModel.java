package com.gapli.gapli.Model;

import java.util.HashMap;
import java.util.List;

public class CityDetailModel {
    String id;
    String name;
    List<String> images;
    List<PlacesToVsit> placesToVsits;
    HashMap<String,Comment> comments;
    String title;
    String description;


    public CityDetailModel() {
    }

    public CityDetailModel(String id, String name, List<String> images, List<PlacesToVsit> placesToVsits, HashMap<String,Comment>  comments, String title, String description) {
        this.id = id;
        this.name = name;
        this.images = images;
        this.placesToVsits = placesToVsits;
        this.comments = comments;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<PlacesToVsit> getPlacesToVsits() {
        return placesToVsits;
    }

    public void setPlacesToVsits(List<PlacesToVsit> placesToVsits) {
        this.placesToVsits = placesToVsits;
    }

    public HashMap<String,Comment>  getComments() {
        return comments;
    }

    public void setComments(HashMap<String,Comment>  comments) {
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
