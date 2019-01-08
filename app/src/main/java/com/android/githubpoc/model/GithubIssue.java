package com.android.githubpoc.model;

import com.google.gson.annotations.SerializedName;

public class GithubIssue {
    @SerializedName("number") private int prNumber;
    @SerializedName("title") private String title;
    @SerializedName("user") private GithubUser user;
    @SerializedName("pull_request") private PullRequest pullRequest;
    @SerializedName("state") private String state;

    public int getPrNumber() {
        return prNumber;
    }

    public void setPrNumber(int prNumber) {
        this.prNumber = prNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GithubUser getUser() {
        return user;
    }

    public void setUser(GithubUser user) {
        this.user = user;
    }

    public PullRequest getPullRequest() {
        return pullRequest;
    }

    public void setPullRequest(PullRequest pullRequest) {
        this.pullRequest = pullRequest;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
