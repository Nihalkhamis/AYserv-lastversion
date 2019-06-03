package com.example.smatech.ay5edma.Models.Modelss;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModelSatus {

    @SerializedName("activated")
    @Expose
    private Boolean activated;

    public Boolean getActivated() {
        return activated;
    }

    @SerializedName("user")
    @Expose
    private UserModel user;
    @SerializedName("category")
    @Expose
    private CategoryModel category;
    @SerializedName("subcategory")
    @Expose
    private CategoryModel subcategory;
    @SerializedName("subcategory2")
    @Expose
    private CategoryModel subcategory2;
    @SerializedName("rejected")
    @Expose
    private Integer rejected;
    @SerializedName("accepted")
    @Expose
    private Integer accepted;
    @SerializedName("people")
    @Expose
    private Integer people;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("notification_count")
    @Expose
    private Integer notification_count;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("message_ar")
    @Expose
    private String message_ar;

    public CategoryModel getSubcategory2() {
        return subcategory2;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage_ar() {
        return message_ar;
    }

    public void setMessage_ar(String message_ar) {
        this.message_ar = message_ar;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public CategoryModel getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(CategoryModel subcategory) {
        this.subcategory = subcategory;
    }

    public Integer getRejected() {
        return rejected;
    }

    public void setRejected(Integer rejected) {
        this.rejected = rejected;
    }

    public Integer getAccepted() {
        return accepted;
    }

    public void setAccepted(Integer accepted) {
        this.accepted = accepted;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getNotification_count() {
        return notification_count;
    }

    public void setNotification_count(Integer notification_count) {
        this.notification_count = notification_count;
    }
}
