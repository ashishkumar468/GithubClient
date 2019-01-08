package com.android.githubpoc.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.android.githubpoc.R;
import com.android.githubpoc.adapters.IssuesAdapter;
import com.android.githubpoc.presenters.HomePresenter;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.rv_home) RecyclerView rvHome;
    private HomePresenter presenter;
    private IssuesAdapter adapter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initPresenter();
        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter = new IssuesAdapter();
    }

    private void initPresenter() {
        presenter = new HomePresenter();
    }
}