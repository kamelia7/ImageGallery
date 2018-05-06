package com.mycorp.imagegallery.api;

import com.mycorp.imagegallery.model.ItemPicturesResponse;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

public interface MyApi {

    String ENDPOINT = "https://cloud-api.yandex.net:443/v1/";

    @GET("disk/public/resources?public_key=https://yadi.sk/d/0HgEN12x3VSEqs")
    Observable<ItemPicturesResponse> getPictures();

    class Creator {
        public static MyApi createMyApi() {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(ENDPOINT)
                    .build();
            return retrofit.create(MyApi.class);
        }
    }
}
