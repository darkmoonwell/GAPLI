package com.gapli.gapli.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gapli.gapli.Model.Comment;
import com.gapli.gapli.R;
import com.gapli.gapli.Screen.Detail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class CommentAdaptor extends RecyclerView.Adapter {


    private Context mContext;
    private HashMap<String,Comment> commentList;
    private List<String> commentKeyList;
    private Activity mActivity;

    public CommentAdaptor(Context mContext, HashMap<String,Comment>  commentList, Activity mActivity,List<String> commentKeyList) {
        this.commentList = commentList;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.commentKeyList = commentKeyList;
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
        String key = commentKeyList.get(position);
        Comment comment = commentList.get(key);
        ((UViewHolder) holder).username.setText(comment.getUserName());
        ((UViewHolder) holder).comment.setText(comment.getComment());
        Glide.with(mContext).load(comment.getUserImage()).error(R.drawable.user).into(((UViewHolder) holder).userImage);

        if (comment.getUserId().contains(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            ((UViewHolder) holder).delete.setVisibility(View.VISIBLE);
            ((UViewHolder) holder).edit.setVisibility(View.VISIBLE);
            ((UViewHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase.getInstance().getReference("CityDetail")
                            .child(comment.getCityId())
                            .child("comments")
                            .child(comment.getId()).removeValue();
                }
            });
            ((UViewHolder) holder).edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final EditText input = new EditText(mActivity);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setText(comment.getComment());
                    input.setLayoutParams(lp);
                    AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
                    alertDialog.setTitle("Yorumunuzu düzenleyin");
                    alertDialog.setView(input);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Güncelle",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String com = input.getText().toString();
                                    if (!com.isEmpty()) {
                                        FirebaseDatabase.getInstance().getReference("CityDetail")
                                                .child(comment.getCityId())
                                                .child("comments")
                                                .child(comment.getId())
                                                .child("comment").setValue(com);

                                        dialog.dismiss();
                                        Toast.makeText(mActivity, "Yorumunuz güncellendi", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(mActivity, "Lütfen yeni yorumunuzu girin", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "İptal",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }


    public class UViewHolder extends RecyclerView.ViewHolder {

        public TextView comment, username;
        public ImageView userImage, delete, edit;

        public UViewHolder(View itemView) {
            super(itemView);

            comment = itemView.findViewById(R.id.userComment);
            username = itemView.findViewById(R.id.userName);
            userImage = itemView.findViewById(R.id.userImage);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
        }

    }


}

