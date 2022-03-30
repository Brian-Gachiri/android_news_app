package com.brige.newsapp.ui.home;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.brige.newsapp.networking.pojos.Article;
import com.brige.newsapp.repositories.NewsRepo;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<Article>> articles = new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Article>> getNews(){
        return new NewsRepo(getApplication().getApplicationContext()).getBrowseData();

    }

    public LiveData<List<Article>> displayNews(){
        articles = getNews();
        return articles;
    }
}