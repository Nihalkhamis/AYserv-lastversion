package com.example.smatech.ay5edma.Models.Modelss;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RequestModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("shop_id")
    @Expose
    private String shopId;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("subcategory_id")
    @Expose
    private String subcategoryId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("provider_rate")
    @Expose
    private String provider_rate;
    @SerializedName("user_rate")
    @Expose
    private String user_rate;
    @SerializedName("user")
    @Expose
    private UserModel user;
    @SerializedName("shop")
    @Expose
    private UserModel shop;
    @SerializedName("category_name")
    @Expose
    private Object categoryName;
    @SerializedName("category_name_ar")
    @Expose
    private Object categoryNameAr;
    @SerializedName("sub_name")
    @Expose
    private Object subName;
    @SerializedName("sub_name_ar")
    @Expose
    private Object subNameAr;
    @SerializedName("finished")
    @Expose
    private Boolean finished;
    @SerializedName("favourite")
    @Expose
    private Boolean favourite;
    @SerializedName("Category")
    @Expose
    private CategoryModel category;
    @SerializedName("Subcategory")
    @Expose
    private CategoryModel subcategory;
    @SerializedName("offers_count")
    @Expose
    private int offers_count;
@SerializedName("message_count")
    @Expose
    private int message_count;

    public int getMessage_count() {
        return message_count;
    }

    public void setMessage_count(int message_count) {
        this.message_count = message_count;
    }

    public String getProvider_rate() {
        return provider_rate;
    }

    public void setProvider_rate(String provider_rate) {
        this.provider_rate = provider_rate;
    }

    public String getUser_rate() {
        return user_rate;
    }

    public void setUser_rate(String user_rate) {
        this.user_rate = user_rate;
    }

    public int getOffers_count() {
        return offers_count;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public CategoryModel getSubcategory() {
        return subcategory;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    public Boolean getFinished() {
        return finished;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserModel getShop() {
        return shop;
    }

    public void setShop(UserModel shop) {
        this.shop = shop;
    }

    public Object getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(Object categoryName) {
        this.categoryName = categoryName;
    }

    public Object getCategoryNameAr() {
        return categoryNameAr;
    }

    public void setCategoryNameAr(Object categoryNameAr) {
        this.categoryNameAr = categoryNameAr;
    }

    public Object getSubName() {
        return subName;
    }

    public void setSubName(Object subName) {
        this.subName = subName;
    }

    public Object getSubNameAr() {
        return subNameAr;
    }

    public void setSubNameAr(Object subNameAr) {
        this.subNameAr = subNameAr;
    }
}
