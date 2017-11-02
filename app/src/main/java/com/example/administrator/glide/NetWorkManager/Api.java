package com.example.administrator.glide.NetWorkManager;



import com.example.administrator.glide.Grilmodel.GrilBean;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/10/24.
 */

public interface  Api{

  @POST("197-1")
  @FormUrlEncoded
  Observable<GrilBean> getGirls(@Field("showapi_appid") String showapi_appid,
                                @Field("showapi_sign") String showapi_sign,
                                @Field("num") int num ,
                                @Field("page") int page);

}
