package com.android.githubpoc.repository.communucations;

import com.android.githubpoc.model.response.RestResponse;

public interface RestResponseCallback {
    void onApiResponse(RestResponse restResponse);
}
