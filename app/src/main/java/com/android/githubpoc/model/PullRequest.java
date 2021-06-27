package com.android.githubpoc.model;

import com.google.gson.annotations.SerializedName;

public class PullRequest {
    @SerializedName("html_url")
    private String url;
    @SerializedName("number")
    private int prNumber;
    @SerializedName("title")
    private String title;
    @SerializedName("user")
    private GithubUser user;
    @SerializedName("state")
    private String state;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("closed_at")
    private String closedAt;

    public int getPrNumber() {
        return prNumber;
    }


    public String getTitle() {
        return title;
    }


    public GithubUser getUser() {
        return user;
    }


    public String getUrl() {
        return url;
    }


    public String getClosedAt() {
        return closedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
