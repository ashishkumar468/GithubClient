package com.android.githubpoc.repository;

import com.android.githubpoc.model.GithubIssue;
import com.android.githubpoc.repository.communucations.RemoteDataSource;
import com.android.githubpoc.repository.communucations.ResponseSingleObserverHelper;
import com.android.githubpoc.repository.communucations.RestResponseCallback;
import java.util.List;

public class BaseRepository {
    private static final String TAG = "#BaseRepository#";
    RemoteDataSource remoteDataSource;
    private ResponseSingleObserverHelper<List<GithubIssue>> providerResponseObserverHelper;

    public BaseRepository() {
        remoteDataSource = new RemoteDataSource();
    }

    public void fetchIssues(final RestResponseCallback restResponseCallback) {
        providerResponseObserverHelper = new ResponseSingleObserverHelper(restResponseCallback);
    }
}
