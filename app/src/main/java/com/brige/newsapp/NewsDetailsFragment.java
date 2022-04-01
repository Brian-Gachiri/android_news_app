package com.brige.newsapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.brige.newsapp.databinding.FragmentNewsDetailsBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

public class NewsDetailsFragment extends Fragment {

    FragmentNewsDetailsBinding binding;

    public NewsDetailsFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false);

        if (getArguments() !=null){
            String title = getArguments().getString("TITLE");
            String image= getArguments().getString("IMAGE");
            String content = getArguments().getString("CONTENT");
            String url = getArguments().getString("URL");
            String author = getArguments().getString("AUTHOR");

            binding.txtNewsDetails.setText(content);
            binding.txtDetailsTitle.setText(title);
            binding.txtDetailsAuthor.setText(author);
            Glide.with(requireActivity()).load(image).into(binding.imgNewsDetails);

            setUrlButtonClickListener(url, title);

            if (((HomeActivity) getActivity()).getSupportActionBar() != null){
                ((HomeActivity) getActivity()).getSupportActionBar().setTitle(title);
            }
        }


        return binding.getRoot();
    }

    private void setUrlButtonClickListener(String url, String title) {

        binding.btnFullArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(url);

                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

                try {
                    startActivity(intent);
                }
                catch(ActivityNotFoundException e){
                    Snackbar.make(binding.getRoot(),"Couldn't find an activity to open this URL",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        binding.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, url);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TITLE, title);

                try {
                    startActivity(Intent.createChooser(intent, null));

                }
                catch(ActivityNotFoundException e){
                    Snackbar.make(binding.getRoot(),"Couldn't find an activity to open this URL",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}