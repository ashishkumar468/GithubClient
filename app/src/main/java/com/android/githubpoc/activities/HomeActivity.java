package com.android.githubpoc.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.android.githubpoc.R;
import com.android.githubpoc.adapters.PRAdapter;
import com.android.githubpoc.model.PullRequest;
import com.android.githubpoc.model.RepoDetails;
import com.android.githubpoc.presenters.HomeContract;
import com.android.githubpoc.presenters.HomePresenter;
import com.android.githubpoc.utils.MiscUtils;
import com.jakewharton.rxbinding3.InitialValueObservable;
import com.jakewharton.rxbinding3.widget.RxTextView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.observable.ObservableScan;
import java.util.List;

public class HomeActivity extends BaseActivity implements HomeContract.View {
    public static final int PAGE_SIZE = 10;
    @BindView(R.id.rv_home) RecyclerView rvHome;
    @BindView(R.id.pb_home) ProgressBar pbHome;

    @BindView(R.id.ll_container_repo_details) LinearLayout llContainerRepoDetails;
    @BindView(R.id.et_owner_name) EditText etOwnerName;
    @BindView(R.id.et_repo_name) EditText etRepoName;
    @BindView(R.id.btn_submit) Button btnSubmit;
    private HomePresenter presenter;
    private PRAdapter adapter;

    private String issueType;
    private RepoDetails repoDetails;
    private Snackbar snackbar;
    private boolean isLoading;
    private boolean isLastPage;
    private int pageNumber = 0;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
    }

    @Override protected void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override protected void onStop() {
        super.onStop();
        presenter.detachView();
    }

    private void init() {
        repoDetails = new RepoDetails();
        initPresenter();
        initRecyclerView();
        setUpRxRegisters();
    }

    private void setUpRxRegisters() {
        RxTextView.editorActions(etOwnerName)
            .filter(actionId -> actionId == EditorInfo.IME_ACTION_DONE)
            .subscribe(actionId1 -> {
                etRepoName.requestFocus();
            });

        RxTextView.editorActions(etRepoName)
            .filter(actionId -> actionId == EditorInfo.IME_ACTION_DONE)
            .subscribe(actionId1 -> {
                onSubmitButtonClicked();
                hideKeyboard();
            });
        InitialValueObservable<CharSequence> etOwnerNameTextObservable =
            RxTextView.textChanges(etOwnerName);

        InitialValueObservable<CharSequence> etRepoNameTextObservable =
            RxTextView.textChanges(etRepoName);

        ObservableScan.combineLatest(etOwnerNameTextObservable, etRepoNameTextObservable,
            (charSequence, charSequence2) -> TextUtils.isEmpty(charSequence) || TextUtils.isEmpty(
                charSequence2)).subscribe(new Observer<Boolean>() {
            @Override public void onSubscribe(Disposable d) {

            }

            @Override public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    btnSubmit.setVisibility(View.GONE);
                } else {
                    btnSubmit.setVisibility(View.VISIBLE);
                }
            }

            @Override public void onError(Throwable e) {

            }

            @Override public void onComplete() {

            }
        });
    }

    private void initRecyclerView() {
        issueType = "open";
        adapter = new PRAdapter();
        rvHome.setLayoutManager(new LinearLayoutManager(this));
        rvHome.setAdapter(adapter);
        addOnScrollListener();
    }

    private void addOnScrollListener() {
        rvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {//only positive scrolls are considered for pagination
                    LinearLayoutManager layoutManager =
                        (LinearLayoutManager) rvHome.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && !isLastPage) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                            pageNumber++;
                            loadMoreItems();
                        }
                    }
                }
            }
        });
    }

    private void loadMoreItems() {
        isLoading = true;
        presenter.fetchPRS(issueType, repoDetails, pageNumber);
    }

    private void initPresenter() {
        presenter = new HomePresenter();
    }

    @Override public void setProgressIndicator(boolean active) {
        if (active) {
            pbHome.setVisibility(View.VISIBLE);
            dismissSnackBar();
        } else {
            disableLLContainerRepoDetails(false);
            pbHome.setVisibility(View.GONE);
            isLoading = false;
        }
    }

    private void dismissSnackBar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    @Override public void showIssues(List<PullRequest> issues) {
        if (issues == null || issues.size() == 0) {
            isLastPage = true;
        } else {
            adapter.setPullRequests(issues);
        }
    }

    @Override public void showMessage(String message) {
        snackbar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getString(R.string.retry), view -> {
            snackbar.dismiss();
            onSubmitButtonClicked();
        });
        snackbar.show();
    }

    @OnClick(R.id.btn_submit) public void onSubmitButtonClicked() {
        if (TextUtils.isEmpty(etOwnerName.getText().toString())) {
            etOwnerName.setError(getString(R.string.this_field_is_required));
        } else if (TextUtils.isEmpty(etRepoName.getText().toString())) {
            etRepoName.setError(getString(R.string.this_field_is_required));
        } else {
            resetDefaults();
            hideKeyboard();
            askPresenterToFetchData();
        }
    }

    private void resetDefaults() {
        pageNumber = 0;
        isLoading = false;
        isLastPage = false;
        adapter.clearData();
    }

    private void askPresenterToFetchData() {
        if (MiscUtils.isConnectedToInternet()) {
            disableLLContainerRepoDetails(true);
            repoDetails.setUserName(etOwnerName.getText().toString());
            repoDetails.setRepoName(etRepoName.getText().toString());
            loadMoreItems();
        } else {
            showMessage(getString(R.string.no_internet_connection));
        }
    }

    private void disableLLContainerRepoDetails(boolean shouldHide) {
        if (shouldHide) {
            llContainerRepoDetails.setVisibility(View.GONE);
        } else {
            llContainerRepoDetails.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        InputMethodManager imm =
            (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
