package com.android.githubpoc.model;

import com.google.gson.annotations.SerializedName;

public class GithubUser {
    @SerializedName("login") private String username;
    @SerializedName("avatar_url") private String avatarUrl;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
