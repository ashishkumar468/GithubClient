package com.android.githubpoc.repository.communucations.interfaces;

import com.android.githubpoc.model.PullRequest;
import com.android.githubpoc.repository.communucations.ApiConstants;
import io.reactivex.Single;
import java.util.HashMap;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface IData {
    @GET("repos/{username}/{reponame}/" + ApiConstants.REST_API.PULLS)
    Single<List<PullRequest>> getIssues(@Path("username") String issueState,
        @Path("reponame") String username, @QueryMap HashMap<String, String> queryMap);
}