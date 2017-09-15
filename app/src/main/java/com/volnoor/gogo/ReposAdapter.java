package com.volnoor.gogo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Eugene on 16.09.2017.
 */

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {
    private List<Repo> mRepos;

    public ReposAdapter(List<Repo> repos) {
        mRepos = repos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_repo_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Repo repo = mRepos.get(position);
        holder.bind(repo);
    }

    @Override
    public int getItemCount() {
        return mRepos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mName, mDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.tv_repo_name);
            mDescription = itemView.findViewById(R.id.tv_repo_description);
        }

        public void bind(Repo repo) {
            mName.setText(repo.getName());
            mDescription.setText(repo.getDescription());
        }
    }
}
