
package com.example.smatech.ay5edma.Models.edituser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("status")
    @Expose
    private Boolean status;

    public User getUser() {
        return user;
    }

    public Boolean getStatus() {
        return status;
    }
}
