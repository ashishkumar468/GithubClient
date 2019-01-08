package com.android.githubpoc.presenters;

import com.android.githubpoc.model.PullRequest;
import com.android.githubpoc.model.RepoDetails;
import com.android.githubpoc.model.response.RestResponse;
import com.android.githubpoc.repository.BaseRepository;
import com.android.githubpoc.repository.communucations.RestResponseCallback;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class HomePresenterTest {

    @Mock BaseRepository baseRepository;
    @Mock HomeContract.View iHomeView;

    RepoDetails repoDetails;
    @Mock List<PullRequest> pullRequests;

    private HomePresenter homePresenter;

    @Captor private ArgumentCaptor<RestResponseCallback> restResponseCallbackArgumentCaptor;
    private RestResponse successRestResponse;
    private RestResponse failureRestResponse;

    @Before public void setupHomePresenterTest() {
        MockitoAnnotations.initMocks(this);
        homePresenter = new HomePresenter(baseRepository);
        homePresenter.attachView(iHomeView);
        repoDetails = new RepoDetails();
        repoDetails.setUserName("ashish");
        repoDetails.setRepoName("pocgithub");
        successRestResponse = new RestResponse(true, "success", pullRequests);
        failureRestResponse = new RestResponse(false, "failure", null);
    }

    private void fetchIssuesBaseTest() {
        homePresenter.fetchIssues("open", repoDetails);
        verify(iHomeView).setProgressIndicator(true);
        verify(baseRepository).fetchIssues(eq("open"), eq(repoDetails),
            restResponseCallbackArgumentCaptor.capture());
    }

    @Test public void fetchIssuesSuccessTest() {
        fetchIssuesBaseTest();
        restResponseCallbackArgumentCaptor.getValue().onApiResponse(successRestResponse);
        verify(iHomeView).setProgressIndicator(false);
        verify(iHomeView).showIssues(pullRequests);
    }

    @Test public void fetchIssuesFailureTest() {
        fetchIssuesBaseTest();
        restResponseCallbackArgumentCaptor.getValue().onApiResponse(failureRestResponse);
        verify(iHomeView).setProgressIndicator(false);
        verify(iHomeView).showMessage(failureRestResponse.getMessage());
    }
}
