package com.example.administrator.glide.Grilmodel;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.glide.R;

/**
 * Created by Administrator on 2017/10/27.
 */
public class MyViewHolder extends RecyclerView.ViewHolder {
    //RecyclerView中只包含一个ImageView
    public ImageView mImageView;

    public MyViewHolder(View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.girl_picture);
    }
}
