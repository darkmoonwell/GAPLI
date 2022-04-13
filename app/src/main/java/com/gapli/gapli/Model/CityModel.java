package com.gapli.gapli.Model;

public class CityModel {
    String id;
    String name;
    String ImageUrl;

    public CityModel(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        ImageUrl = imageUrl;
    }

    public CityModel() {
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

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
