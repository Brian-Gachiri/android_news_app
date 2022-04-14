package com.brige.newsapp.networking;

import com.brige.newsapp.networking.pojos.Browse;
import com.brige.newsapp.networking.pojos.ChatResponse;
import com.brige.newsapp.networking.pojos.MessageRequest;
import com.brige.newsapp.networking.pojos.PeopleResponse;
import com.brige.newsapp.networking.pojos.RegisterRequest;
import com.brige.newsapp.networking.pojos.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

    @Headers({"Accept: application/json"})
    @GET("api/chats/{id}")
    Call<List<ChatResponse>> getChats(@Path("id") int id, @Header("Authorization") String token);


    @Headers({"Accept: application/json"})
    @GET("api/people")
    Call<List<PeopleResponse>> getPeople(@Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @POST("api/send/message")
    Call<List<ChatResponse>> sendMessage(@Body MessageRequest messageRequest,
                                         @Header("Authorization") String token);

}
