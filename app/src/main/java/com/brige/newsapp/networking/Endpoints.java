package com.brige.newsapp.networking;

import com.brige.newsapp.networking.pojos.Browse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Endpoints {

    @GET("everything")
    Call<Browse> getNews(
            @Query("q") String topic,
            @Query("from") String from,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey,
            @Query("pageSize") int size

    );

}
