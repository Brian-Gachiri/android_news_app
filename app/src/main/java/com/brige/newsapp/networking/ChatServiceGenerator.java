package com.brige.newsapp.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatServiceGenerator {
    public static ChatServiceGenerator instanceOfService;
    public static final String BaseUrl = URLs.CHAT_URL;
    private Retrofit retrofit;

    private ChatServiceGenerator() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


    }

    public static synchronized  ChatServiceGenerator getInstance(){
        if (instanceOfService == null){
            instanceOfService = new ChatServiceGenerator();
        }
        return instanceOfService;
    }

    public Endpoints getApiConnector(){

        return retrofit.create(Endpoints.class);
    }
}
