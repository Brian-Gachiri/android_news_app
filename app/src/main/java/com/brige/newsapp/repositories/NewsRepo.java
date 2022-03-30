package com.brige.newsapp.repositories;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.brige.newsapp.networking.ServiceGenerator;
import com.brige.newsapp.networking.URLs;
import com.brige.newsapp.networking.pojos.Article;
import com.brige.newsapp.networking.pojos.Browse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepo {
    
    Context context;
    List<Article> articles = new ArrayList<>();
    private MutableLiveData<List<Article>> articleMutableLiveData = new MutableLiveData<>();


    public NewsRepo(Context context) {
        this.context = context;
    }

    public MutableLiveData<List<Article>> getBrowseData(){

        Call<Browse> call = ServiceGenerator.getInstance()
                .getApiConnector()
                .getNews("Technology", "2022-03-28", "popularity", URLs.API_KEY, 100);

        call.enqueue(new Callback<Browse>() {
            @Override
            public void onResponse(Call<Browse> call, Response<Browse> response) {

                if (response.code() == 200 && response.body() != null){
                    Log.d("TEST::", "onResponse: "+ response.code());

                    articles = response.body().getArticles();
                    articleMutableLiveData.setValue(articles);

                }
                else{
                    Log.d("TEST::", "onResponse: "+ response.message());
                    try {
                        Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                    catch (ActivityNotFoundException e){
                        e.printStackTrace();
                    }
                    articleMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Browse> call, Throwable t) {

                Log.d("TEST::", "onFailure: "+t.getMessage());
                try {
                    Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
                catch (ActivityNotFoundException e){
                    e.printStackTrace();
                }
                articleMutableLiveData.postValue(null);

            }
        });

        return articleMutableLiveData;
    }


}
