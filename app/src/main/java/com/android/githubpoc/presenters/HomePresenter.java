package com.android.githubpoc.presenters;

import com.android.githubpoc.model.PullRequest;
import com.android.githubpoc.model.RepoDetails;
import com.android.githubpoc.presenters.interfaces.BasePresenter;
import com.android.githubpoc.repository.BaseRepository;
import java.util.List;

public class HomePresenter
    implements BasePresenter<HomeContract.View>, HomeContract.UserActionsListener {

    private HomeContract.View view;
    private BaseRepository baseRepository;

    public HomePresenter(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
        //this one is temporarily used for test cases
    }

    public HomePresenter() {
        baseRepository = new BaseRepository();
    }

    @Override public void attachView(HomeContract.View view) {
        this.view = view;
    }

    @Override public void detachView() {
        this.view = null;
    }

    @Override public void fetchIssues(String issueType, RepoDetails repoDetails) {
        view.setProgressIndicator(true);
        baseRepository.fetchIssues(issueType, repoDetails, restResponse -> {
            view.setProgressIndicator(false);
            if (restResponse.isStatus()) {
                view.showIssues((List<PullRequest>) restResponse.getData());
            } else {
                view.showMessage(restResponse.getMessage());
            }
        });
    }
}
