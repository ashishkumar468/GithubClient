package com.android.githubpoc.model;

import com.google.gson.annotations.SerializedName;

public class PullRequest {
    @SerializedName("patch_url") private String patchUrl;

    public String getPatchUrl() {
        return patchUrl;
    }

    public void setPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
    }
}
