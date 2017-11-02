package com.example.administrator.glide.NetWorkManager;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/10/26.
 */

public class NetWorkManager {

    public static final String baseurl = "http://route.showapi.com/";
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseurl)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static final Api api = retrofit.create(Api.class);
    public static Api getApi() {
        return api;
    }

}
