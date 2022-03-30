package com.brige.newsapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.brige.newsapp.adapters.BrowseAdapter;
import com.brige.newsapp.adapters.DiscoverAdapter;
import com.brige.newsapp.databinding.FragmentBrowseBinding;
import com.brige.newsapp.databinding.FragmentHomeBinding;
import com.brige.newsapp.models.Discover;
import com.brige.newsapp.networking.ServiceGenerator;
import com.brige.newsapp.networking.URLs;
import com.brige.newsapp.networking.pojos.Article;
import com.brige.newsapp.networking.pojos.Browse;
import com.brige.newsapp.repositories.NewsRepo;
import com.brige.newsapp.ui.home.HomeFragment;
import com.brige.newsapp.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BrowseFragment extends Fragment {

    FragmentBrowseBinding binding;
    private BrowseAdapter browseAdapter;
    private List<Article> articles = new ArrayList<>();
    private HomeViewModel homeViewModel;

    public BrowseFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.displayNews();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBrowseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.browseRecyclerview.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.browseRecyclerview.setNestedScrollingEnabled(true);

        browseAdapter = new BrowseAdapter(articles, requireActivity());
        binding.browseRecyclerview.setAdapter(browseAdapter);

//        getBrowseData();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        homeViewModel.displayNews().observe(requireActivity(), news -> {
            articles.clear();
            articles.addAll(news);
            browseAdapter.notifyDataSetChanged();
        });

    }

    public void getBrowseData(){

        Call<Browse> call = ServiceGenerator.getInstance()
                .getApiConnector()
                .getNews("Technology", "2022-03-28", "popularity", URLs.API_KEY, 100);

        call.enqueue(new Callback<Browse>() {
            @Override
            public void onResponse(Call<Browse> call, Response<Browse> response) {

                if (response.code() == 200 && response.body() != null){
                    Log.d("TEST::", "onResponse: "+response.code());
                    articles.clear();
                    articles.addAll(response.body().getArticles());
                    browseAdapter.notifyDataSetChanged();
                }
                else{
                    Log.d("TEST::", "onResponse: "+ response.message());
                    try {
                        Toast.makeText(requireActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                    catch (ActivityNotFoundException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Browse> call, Throwable t) {

                Log.d("TEST::", "onFailure: "+t.getMessage());
                try {
                    Toast.makeText(requireActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
                catch (ActivityNotFoundException e){
                    e.printStackTrace();
                }
            }
        });
    }


}