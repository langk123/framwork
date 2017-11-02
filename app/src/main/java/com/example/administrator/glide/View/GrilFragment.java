package com.example.administrator.glide.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.glide.Grilmodel.GridSpacingItemDecoration;
import com.example.administrator.glide.Grilmodel.GrilAdapter;
import com.example.administrator.glide.Grilmodel.GrilBean;
import com.example.administrator.glide.NetWorkManager.NetWorkManager;
import com.example.administrator.glide.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/10/31.
 */

@SuppressLint("ValidFragment")
public class GrilFragment extends Fragment {

    private View contentView;
    private RecyclerView recyclerView;
    private GridLayoutManager mGridLayoutManager;
    private List<GrilBean.ShowapiResBodyBean.NewslistBean> list = new ArrayList<>();
    private GrilAdapter grilAdapter;
    private int lastVisibleItem;

    public static int page = 1;

    @SuppressLint("ValidFragment")



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (contentView==null){
            contentView  = inflater.inflate(R.layout.fragment_list, null);
            recyclerView= contentView.findViewById(R.id.recyclerview);
            mGridLayoutManager = new GridLayoutManager(getContext(), 2);
            recyclerView.setLayoutManager(mGridLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 20, true));
            grilAdapter  = new GrilAdapter(getContext(),list);
            recyclerView.setAdapter(grilAdapter);

            EventBus.getDefault().register(this);
            networkRequest(1,false);
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    Log.i("Main",lastVisibleItem+"");
                    //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的item时才加载
                    if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                            //mAdapter.getItemCount()通过适配器得到当前Item的数量
                            lastVisibleItem + 1 == grilAdapter.getItemCount()){
                        page++;
                        networkRequest(page,true);
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    lastVisibleItem = mGridLayoutManager.findLastVisibleItemPosition();
                }
            });

        }
        return contentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String string){
        if (string.equals("refresh")){
            networkRequest(1,false);
        }
    }


    public void networkRequest(int page, final Boolean loadMore){
        NetWorkManager.getApi().getGirls("34276","731d68d6d56b4d789d6571f530ee28ef",10,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GrilBean>(){
                    @Override
                    public void onCompleted(){
                        EventBus.getDefault().post("refreshOk");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GrilBean grilBean) {
                        if(loadMore){
                            list.addAll(grilBean.getShowapi_res_body().getNewslist());
                            grilAdapter.notifyDataSetChanged();
                        }else {
                            List<GrilBean.ShowapiResBodyBean.NewslistBean> newlist = new ArrayList<>();
                            newlist.addAll(grilBean.getShowapi_res_body().getNewslist());
                            newlist.addAll(list);
                            list.clear();
                            list.addAll(newlist);
                            grilAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }

}
