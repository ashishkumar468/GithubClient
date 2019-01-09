package com.android.githubpoc.presenters;

import android.support.test.espresso.idling.CountingIdlingResource;
import com.android.githubpoc.model.PullRequest;
import com.android.githubpoc.model.RepoDetails;
import com.android.githubpoc.presenters.interfaces.BasePresenter;
import com.android.githubpoc.repository.BaseRepository;
import java.util.List;

public class HomePresenter
    implements BasePresenter<HomeContract.View>, HomeContract.UserActionsListener {

    private final CountingIdlingResource countingIdlingResource;
    private HomeContract.View view;
    private BaseRepository baseRepository;

    public HomePresenter(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
        countingIdlingResource = new CountingIdlingResource("fetch_pr");
        //this one is temporarily used for test cases
    }

    public HomePresenter() {
        baseRepository = new BaseRepository();
        countingIdlingResource = new CountingIdlingResource("fetch_pr");
    }

    @Override public void attachView(HomeContract.View view) {
        this.view = view;
    }

    @Override public void detachView() {
        baseRepository.dispose();
        this.view = null;
    }

    @Override public void fetchPRS(String issueType, RepoDetails repoDetails, int pageNumber) {
        countingIdlingResource.increment();
        view.setProgressIndicator(true);
        baseRepository.fetchIssues(issueType, repoDetails, pageNumber, restResponse -> {
            countingIdlingResource.decrement();
            view.setProgressIndicator(false);
            if (restResponse.isStatus()) {
                view.showIssues((List<PullRequest>) restResponse.getData());
            } else {
                view.showMessage(restResponse.getMessage());
            }
        });
    }
}
