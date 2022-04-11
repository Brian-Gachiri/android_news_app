package com.brige.newsapp.networking;

import com.brige.newsapp.networking.pojos.Browse;
import com.brige.newsapp.networking.pojos.RegisterRequest;
import com.brige.newsapp.networking.pojos.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @POST("api/register")
    Call<UserResponse> register(@Body RegisterRequest registerRequest);

    @FormUrlEncoded
    @POST("api/login")
    Call<UserResponse> login(@Field("username") String username, @Field("password") String password);

}
