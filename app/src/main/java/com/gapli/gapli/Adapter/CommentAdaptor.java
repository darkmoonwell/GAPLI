package com.gapli.gapli.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gapli.gapli.Model.Comment;
import com.gapli.gapli.R;

import java.util.List;

public class CommentAdaptor extends RecyclerView.Adapter {


private Context mContext;
private List<Comment> commentList;
private Activity mActivity;

public CommentAdaptor(Context mContext, List<Comment> commentList, Activity mActivity) {
        this.commentList = commentList;
        this.mContext = mContext;
        this.mActivity = mActivity;
        }

@NonNull
@Override
public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View view = LayoutInflater.from(mContext).inflate(R.layout.comment_item, parent, false);
        vh = new UViewHolder(view);


        return vh;
        }

@Override
public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    Comment comment = commentList.get(position);
    ((UViewHolder) holder).username.setText(comment.getUserName());
    ((UViewHolder) holder).comment.setText(comment.getComment());
    Glide.with(mContext).load(comment.getUserImage()).error(R.drawable.user).into(((UViewHolder) holder).userImage);


}

@Override
public int getItemCount() {
        return commentList.size();
        }


public class UViewHolder extends RecyclerView.ViewHolder {

    public TextView comment ,username;
    public ImageView userImage;

    public UViewHolder(View itemView) {
        super(itemView);

        comment = itemView.findViewById(R.id.userComment);
        username = itemView.findViewById(R.id.userName);
        userImage = itemView.findViewById(R.id.userImage);

    }

}


}

