package com.android.githubpoc.repository;

import com.android.githubpoc.model.GithubIssue;
import com.android.githubpoc.model.RepoDetails;
import com.android.githubpoc.repository.communucations.RemoteDataSource;
import com.android.githubpoc.repository.communucations.ResponseSingleObserverHelper;
import com.android.githubpoc.repository.communucations.RestResponseCallback;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class BaseRepository {
    private static final String TAG = "#BaseRepository#";
    RemoteDataSource remoteDataSource;
    private ResponseSingleObserverHelper<List<GithubIssue>> providerResponseObserverHelper;

    public BaseRepository() {
        remoteDataSource = new RemoteDataSource();
    }

    public BaseRepository(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
        //this is temporarily used for test cases
    }

    public void fetchIssues(String issueType, RepoDetails repoDetails,
        final RestResponseCallback restResponseCallback) {
        providerResponseObserverHelper = new ResponseSingleObserverHelper(restResponseCallback);

        Single<List<GithubIssue>> githubIssuesSingle =
            remoteDataSource.fetchIssue(issueType, repoDetails.getUserName(),
                repoDetails.getRepoName());
        githubIssuesSingle.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(providerResponseObserverHelper);
    }
}
