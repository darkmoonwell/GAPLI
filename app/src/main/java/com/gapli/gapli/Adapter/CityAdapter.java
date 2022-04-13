package com.gapli.gapli.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gapli.gapli.Model.CityModel;
import com.gapli.gapli.R;
import com.gapli.gapli.Screen.Detail;

import java.util.List;


public class CityAdapter extends RecyclerView.Adapter {
    public static final int TYPE_LEFT = 0;
    public static final int TYPE_RIGHT = 1;


    private Context mContext;
    private List<CityModel> categorys;
    private Activity mActivity;

    public CityAdapter(Context mContext, List<CityModel> categorys, Activity mActivity) {
        this.categorys = categorys;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }
    @Override
    public int getItemViewType(int position) {
        if (position%2==0) {
            return TYPE_RIGHT;
        } else {
            return TYPE_LEFT;
        }

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
       if (viewType == TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.citiy_item_right, parent, false);
            vh = new UViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.citiy_item_left, parent, false);
            vh = new UViewHolder(view);
        }


        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CityModel city = categorys.get(position);
        //şehir resmini ekrana basıyoruz
        Glide.with(mContext).load(city.getImageUrl()).into(((UViewHolder) holder).Image);
        //şehir isminin ekrana basıldıgı alan
        ((UViewHolder) holder).Name.setText(city.getName());
        // şehir tıklanma durumu
        ((UViewHolder) holder).root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity, Detail.class);
                i.putExtra("id", city.getId());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return categorys.size();
    }


    public class UViewHolder extends RecyclerView.ViewHolder {

        public TextView Name;
        public ImageView Image;
        public FrameLayout root;

        public UViewHolder(View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.name);
            Image = itemView.findViewById(R.id.image);
            root = itemView.findViewById(R.id.root);

        }

    }


}

