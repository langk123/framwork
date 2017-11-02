package com.example.administrator.glide.Grilmodel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.glide.R;
import com.example.administrator.glide.View.VideoViewActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 */

public class GrilAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener{


    private Context mContext;
    private List<GrilBean.ShowapiResBodyBean.NewslistBean> mList;
    public GrilAdapter(Context context, List<GrilBean.ShowapiResBodyBean.NewslistBean> list) {
        mContext = context;
        mList = list;
    }


    @Override
    public com.example.administrator.glide.Grilmodel.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.girl_item, parent, false);
        MyViewHolder  holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return  holder;
    }

    @Override
    public void onClick(View view){
          int position = ((Integer)view.getTag());
          Intent intent = new Intent();
          intent.setClass(mContext,VideoViewActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void onBindViewHolder(com.example.administrator.glide.Grilmodel.MyViewHolder holder, int position) {
        holder.itemView.setTag(position);
        final String imageUrl = mList.get(position).getPicUrl();
        Glide.with(mContext)
                .load(imageUrl)
                .asBitmap()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .placeholder(R.color.colorAccent)
                .into(holder.mImageView);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

}
