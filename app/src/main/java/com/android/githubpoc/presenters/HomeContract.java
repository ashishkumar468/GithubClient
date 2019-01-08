package com.android.githubpoc.presenters;

import com.android.githubpoc.model.GithubIssue;
import com.android.githubpoc.model.RepoDetails;
import java.util.List;

public interface HomeContract {
    interface View {

        void setProgressIndicator(boolean active);

        void showIssues(List<GithubIssue> notes);

        void showMessage(String message);
    }

    interface UserActionsListener {

        void fetchIssues(String issueType, RepoDetails repoDetails);
    }
}
