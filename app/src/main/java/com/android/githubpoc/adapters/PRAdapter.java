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
import com.android.githubpoc.model.PullRequest;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class PRAdapter extends RecyclerView.Adapter<PRAdapter.ViewHolder> {
    List<PullRequest> pullRequests;

    public PRAdapter() {
        pullRequests = new ArrayList<>();
    }

    public void setPullRequests(List<PullRequest> pullRequests) {
        if (null != pullRequests) {
            int lastSize = this.pullRequests.size();
            this.pullRequests.addAll(pullRequests);
            notifyItemRangeChanged(lastSize, this.pullRequests.size());
        }
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
        return pullRequests.size();
    }

    public void clearData() {
        this.pullRequests.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_avatar_url) ImageView ivAvatarUrl;
        @BindView(R.id.tv_username) TextView tvUserName;
        @BindView(R.id.tv_pr_title) TextView tvPrTitle;
        @BindView(R.id.tv_pr_url) TextView tvPrUrl;
        @BindView(R.id.tv_pr_number) TextView tvPrNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void init(int i) {
            PullRequest pullRequest = pullRequests.get(i);
            tvPrNumber.setText(String.valueOf(pullRequest.getPrNumber()));
            tvPrTitle.setText(pullRequest.getTitle());
            tvPrUrl.setText(pullRequest.getUrl() + "");
            tvUserName.setText(pullRequest.getUser().getUsername() + "");
            Glide.with(ivAvatarUrl.getContext())
                .load(pullRequest.getUser().getAvatarUrl())
                .into(ivAvatarUrl);
        }
    }
}

