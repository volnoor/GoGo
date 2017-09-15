package com.volnoor.gogo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Eugene on 14.09.2017.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private List<User> mUsers;
    private OnUserClickListener mListener;

    public UsersAdapter(List<User> users, OnUserClickListener listener) {
        mUsers = users;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_user_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = mUsers.get(position);
        holder.bindUser(user, mListener);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mLogin, mId;

        public ViewHolder(View v) {
            super(v);

            mLogin = v.findViewById(R.id.tv_user_login);
            mId = v.findViewById(R.id.tv_user_id);
        }

        public void bindUser(final User user, final OnUserClickListener listener) {
            mLogin.setText(user.getLogin());
            mId.setText(user.getId().toString());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(user);
                }
            });
        }
    }
}
