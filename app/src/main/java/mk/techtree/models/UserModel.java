package mk.techtree.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("id")
    public String userID;

    @SerializedName("email")
    public String email;

    @SerializedName("name")
    public String name;

    @SerializedName("picture")
    public String picture;

    @SerializedName("datetime")
    public String loginDate;


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
