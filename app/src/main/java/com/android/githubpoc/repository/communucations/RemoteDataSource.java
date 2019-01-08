package com.android.githubpoc.repository.communucations;

import com.android.githubpoc.model.GithubIssue;
import io.reactivex.Single;
import java.util.List;

public class RemoteDataSource {
    RestClient restClient;

    public RemoteDataSource() {
        this.restClient = RestClient.getInstance();
    }

    public Single<List<GithubIssue>> fetchIssue(String issueState, String username,
        String reponame) {
        return restClient.getIssues(issueState, username, reponame);
    }
}
