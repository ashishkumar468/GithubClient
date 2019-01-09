package com.android.githubpoc.presenters;

import com.android.githubpoc.model.PullRequest;
import com.android.githubpoc.model.RepoDetails;
import java.util.List;

public interface HomeContract {
    interface View {

        void setProgressIndicator(boolean active);

        void showIssues(List<PullRequest> issues);

        void showMessage(String message);
    }

    interface UserActionsListener {

        void fetchPRS(String issueType, RepoDetails repoDetails, int pageNumber);
    }
}
