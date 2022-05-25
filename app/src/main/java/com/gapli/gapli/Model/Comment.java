package com.gapli.gapli.Model;

public class Comment {
    String id;
    String comment;
    String userId;
    String cityId;
    String userName;
    String userImage;

    public Comment() {
    }

    public Comment(String id, String comment, String userId, String userName, String userImage, String cityId) {
        this.id = id;
        this.comment = comment;
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
        this.cityId = cityId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
