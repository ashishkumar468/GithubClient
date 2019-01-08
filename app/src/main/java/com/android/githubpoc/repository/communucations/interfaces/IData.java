package com.android.githubpoc.repository.communucations.interfaces;

import com.android.githubpoc.model.GithubIssue;
import com.android.githubpoc.repository.communucations.ApiConstants;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IData {
    @GET("repos/{username}/{reponame}/" + ApiConstants.REST_API.ISSUES)
    Single<List<GithubIssue>> getIssues(@Path("username") String username,
        @Path("reponame") String repoName, @Query("state") String state);
}