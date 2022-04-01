package com.brige.newsapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.objectbox.Box;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BrowseFragment extends Fragment {

    FragmentBrowseBinding binding;
    private BrowseAdapter browseAdapter;
    private List<Article> articles = new ArrayList<>();
    private HomeViewModel homeViewModel;
    private String topic = "Technology";
    SweetAlertDialog progressDialog;

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

        progressDialog = new SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#5474F1"));
        progressDialog.setTitleText("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        getBrowseData("Technology", toDate(), "relevance");

        binding.textInputLayout2.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.inputSearch.getText().toString().trim().isEmpty()){
                    binding.inputSearch.setError("Please enter a search topic");
                    Snackbar.make(root, "Please enter a search topic",
                            Snackbar.LENGTH_LONG).show();
                }
                else{
                    topic =binding.inputSearch.getText().toString().trim();
                    getBrowseData(topic, toDate(), "popularity");
                }
            }
        });



        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String sortBy = "relevance";
                switch(tab.getPosition()){
                    case 0:
                        sortBy = "relevance";
                        break;
                    case 1:
                        sortBy = "popularity";
                        break;
                    case 2:
                        sortBy = "publishedAt";
                        break;
                }
                getBrowseData(topic, toDate(), sortBy);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        binding.inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_SEARCH){
                    if (binding.inputSearch.getText().toString().trim().isEmpty()){
                        binding.inputSearch.setError("Please enter a search topic");
                        Snackbar.make(root, "Please enter a search topic",
                                Snackbar.LENGTH_LONG).show();
                    }
                    else{
                        topic =binding.inputSearch.getText().toString().trim();
                        getBrowseData(topic, toDate(), "popularity");
                    }
                    return true;
                }
                return false;
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

//        homeViewModel.displayNews().observe(requireActivity(), news -> {
//            articles.clear();
//            articles.addAll(news);
//            browseAdapter.notifyDataSetChanged();
//        });

    }

    public void getBrowseData(String topic, String date, String sortBy){

        progressDialog.show();

        Call<Browse> call = ServiceGenerator.getInstance()
                .getApiConnector()
                .getNews(topic, date, sortBy, URLs.API_KEY, 100);

        call.enqueue(new Callback<Browse>() {
            @Override
            public void onResponse(Call<Browse> call, Response<Browse> response) {

                progressDialog.dismiss();

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
                progressDialog.dismiss();

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

    private String toDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date).toString();
    }


}