package com.gapli.gapli.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.gapli.gapli.Model.PlacesToVsit;
import com.gapli.gapli.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends
        SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<String> images = new ArrayList<>();
    private List<PlacesToVsit> placesToVsits = new ArrayList<>();
    public String des;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public void renewImageItems(List<String> imageItems) {
        this.images = imageItems;
        notifyDataSetChanged();
    }
    public void renewPlaceToVsityItems(List<PlacesToVsit> imageItems) {
        this.placesToVsits = imageItems;
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, null);
        return new SliderAdapterVH(inflate);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        if(placesToVsits.size()>0) {
            Log.d("sıra",String.valueOf(position));
            PlacesToVsit placesToVsit = placesToVsits.get(position);
            viewHolder.fl_shadow_container.setBackgroundColor(context.getColor(R.color.black_image));
            //viewHolder.Description.setText(placesToVsit.getDescription());
            viewHolder.Title.setText(placesToVsit.getTitle());
            Glide.with(viewHolder.itemView)
                    .load(placesToVsit.getImageUrl())
                    .fitCenter()
                    .into(viewHolder.imageViewBackground);
        }else if(images.size()>0){
            String image = images.get(position);
            Glide.with(viewHolder.itemView)
                    .load(image)
                    .fitCenter()
                    .into(viewHolder.imageViewBackground);
        }

    }

    @Override
    public int getCount() {
        if(images.size()>0)
        return images.size();
        else
            return placesToVsits.size();
    }


    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        LinearLayout fl_shadow_container;

        TextView Description;
        TextView Title;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            fl_shadow_container = itemView.findViewById(R.id.fl_shadow_container);
            Description = itemView.findViewById(R.id.des);
            Title = itemView.findViewById(R.id.title);
            this.itemView = itemView;
        }
    }

}