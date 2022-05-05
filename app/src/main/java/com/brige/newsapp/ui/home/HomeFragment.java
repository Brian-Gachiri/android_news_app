package com.brige.newsapp.ui.home;

import android.content.ActivityNotFoundException;
import android.os.Bundle;
import android.provider.Browser;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brige.newsapp.ObjectBox;
import com.brige.newsapp.R;
import com.brige.newsapp.adapters.BrowseAdapter;
import com.brige.newsapp.adapters.DiscoverAdapter;
import com.brige.newsapp.databinding.FragmentHomeBinding;
import com.brige.newsapp.models.Discover;
import com.brige.newsapp.networking.ServiceGenerator;
import com.brige.newsapp.networking.URLs;
import com.brige.newsapp.networking.pojos.Article;
import com.brige.newsapp.networking.pojos.Browse;
import com.brige.newsapp.utils.Notifications;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DiscoverAdapter discoverAdapter;
    private BrowseAdapter browseAdapter;
    private RecyclerView discoverRecyclerview, browseRecyclerView;
    private List<Discover> discoverList = new ArrayList<>();
    private List<Browse> browserList = new ArrayList<>();
    private List<Article> articles = new ArrayList<>();
    private Box<Discover> discoverBox = ObjectBox.get().boxFor(Discover.class);
    private Notifications notifications;
    private NotificationManagerCompat notificationManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.recyclerDiscover.setLayoutManager(
                new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        );
        binding.recyclerBrowse.setLayoutManager(new LinearLayoutManager(requireActivity()));

        discoverList.clear();
        discoverList.addAll(discoverBox.getAll());
        discoverAdapter = new DiscoverAdapter(discoverList, getActivity(), this);
        binding.recyclerDiscover.setAdapter(discoverAdapter);
        browseAdapter = new BrowseAdapter(articles, requireActivity());
        binding.recyclerBrowse.setAdapter(browseAdapter);

        binding.btnLoadMore.setOnClickListener(v -> {

            Navigation.findNavController(root).navigate(R.id.browseFragment);
        });

        getBrowseData();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notifications = new Notifications(getActivity());
        notifications.createNotificationChannel("Register", "description", Notifications.REGISTER_NOTIFICATION_ID);

        notificationManager = NotificationManagerCompat.from(getActivity());
        String title = "My notification";
        String text = "This is my first ever notification";
        notificationManager.notify(
                243, notifications.registrationNotification(Notifications.REGISTER_NOTIFICATION_ID,title, text).build());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void getBrowseData(){

        Call<Browse> call = ServiceGenerator.getInstance()
                .getApiConnector()
                .getNews("Technology", "2022-03-28", "popularity", URLs.API_KEY, 5);

        call.enqueue(new Callback<Browse>() {
            @Override
            public void onResponse(Call<Browse> call, Response<Browse> response) {

                if (response.code() == 200 && response.body() != null){

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