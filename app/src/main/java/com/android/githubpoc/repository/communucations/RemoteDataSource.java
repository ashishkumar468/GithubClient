package com.android.githubpoc.repository.communucations;

import com.android.githubpoc.model.PullRequest;
import io.reactivex.Single;
import java.util.List;

public class RemoteDataSource {
    RestClient restClient;

    public RemoteDataSource() {
        this.restClient = RestClient.getInstance();
    }

    public Single<List<PullRequest>> fetchIssue(String issueState, String username,
        String repoName) {
        return restClient.getIssues(issueState, username, repoName);
    }
}
