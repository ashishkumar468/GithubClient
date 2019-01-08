package com.android.githubpoc.repository;

import com.android.githubpoc.model.RepoDetails;
import com.android.githubpoc.repository.communucations.RemoteDataSource;
import com.android.githubpoc.repository.communucations.RestResponseCallback;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class BaseRepositoryTest {

    @Mock RemoteDataSource remoteDataSource;
    private RepoDetails repoDetails;

    private BaseRepository baseRepository;
    @Mock RestResponseCallback restResponseCallback;

    @Before public void setupHomePresenterTest() {
        MockitoAnnotations.initMocks(this);
        baseRepository = new BaseRepository(remoteDataSource);
        repoDetails = new RepoDetails();
        repoDetails.setUserName("ashish");
        repoDetails.setRepoName("pocgithub");
    }

    @Test public void fetchIssuesTest() {
        baseRepository.fetchIssues("open", repoDetails, restResponseCallback);
        verify(remoteDataSource).fetchIssue(eq("open"), eq(repoDetails.getUserName()),
            eq(repoDetails.getRepoName()));
    }
}
