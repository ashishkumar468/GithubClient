package com.android.githubpoc.repository.communucations;

import com.android.githubpoc.activities.HomeActivity;
import com.android.githubpoc.model.PullRequest;
import io.reactivex.Single;
import java.util.HashMap;
import java.util.List;

public class RemoteDataSource {
    RestClient restClient;

    public RemoteDataSource() {
        this.restClient = RestClient.getInstance();
    }

    public Single<List<PullRequest>> fetchIssue(String issueState, String username, String repoName,
        int pageNumber) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("per_page", String.valueOf(HomeActivity.PAGE_SIZE));
        hashMap.put("page", String.valueOf(pageNumber));
        hashMap.put("state", issueState);
        return restClient.getIssues(username, repoName, hashMap);
    }
}
