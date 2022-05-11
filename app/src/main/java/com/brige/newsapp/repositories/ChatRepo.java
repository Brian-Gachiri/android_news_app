package com.brige.newsapp.repositories;

import android.content.Context;
import android.util.Log;

import com.brige.newsapp.networking.ServiceGenerator;
import com.brige.newsapp.networking.pojos.ChatResponse;
import com.brige.newsapp.utils.PreferenceStorage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRepo {



//    public void syncChats(String token){
//        Call<List<ChatResponse>> call = ServiceGenerator.getInstance()
//                .getApiConnector().syncChats("Token "+ token);
//
//        call.enqueue(new Callback<List<ChatResponse>>() {
//            @Override
//            public void onResponse(Call<List<ChatResponse>> call, Response<List<ChatResponse>> response) {
//
//            }
//            @Override
//            public void onFailure(Call<List<ChatResponse>> call, Throwable t) {
//                Log.d("TEST::", "onFailure: "+t.getMessage());
//
//            }
//        });
//    }
}
