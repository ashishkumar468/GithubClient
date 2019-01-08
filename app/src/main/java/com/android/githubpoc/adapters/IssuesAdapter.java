package com.android.githubpoc.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.android.githubpoc.R;
import com.android.githubpoc.model.GithubIssue;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.ViewHolder> {
    List<GithubIssue> githubIssues;

    public IssuesAdapter() {
        githubIssues = new ArrayList<>();
    }

    public void setGithubIssues(List<GithubIssue> githubIssues) {
        if (null == githubIssues) {
            this.githubIssues.clear();
        } else {
            this.githubIssues = githubIssues;
        }
        notifyDataSetChanged();
    }

    @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.row_item_issue, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.init(i);
    }

    @Override public int getItemCount() {
        return githubIssues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_avatar_url) ImageView ivAvatarUrl;
        @BindView(R.id.tv_username) TextView tvUserName;
        @BindView(R.id.tv_pr_title) TextView tvPrTitle;
        @BindView(R.id.tv_pr_number) TextView tvPrNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void init(int i) {
            GithubIssue githubIssue = githubIssues.get(i);
            tvPrNumber.setText(githubIssue.getPrNumber() + "");
            tvPrTitle.setText(githubIssue.getTitle() + "");
            tvUserName.setText(githubIssue.getUser().getUsername() + "");
            Glide.with(ivAvatarUrl.getContext())
                .load(githubIssue.getUser().getAvatarUrl())
                .into(ivAvatarUrl);
        }
    }
}

