package com.brige.newsapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.brige.newsapp.R;
import com.brige.newsapp.networking.pojos.Article;
import com.brige.newsapp.networking.pojos.Browse;
import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BrowseAdapter extends RecyclerView.Adapter<BrowseAdapter.ViewHolder> {

    List<Article> browseList;
    Context context;

    public BrowseAdapter(List<Article> browseList, Context context) {
        this.browseList = browseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);

        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Article article = browseList.get(position);

        Glide.with(context).load(article.getUrlToImage()).into(holder.imgBrowse);
        holder.txtTitle.setText(article.getTitle());
        holder.txtSub.setText(article.getAuthor());
    }

    @Override
    public int getItemCount() {
        return browseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBrowse;
        TextView txtTitle, txtSub;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBrowse = itemView.findViewById(R.id.img_browse);
            txtSub = itemView.findViewById(R.id.txt_browse_author);
            txtTitle = itemView.findViewById(R.id.txt_browse_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("IMAGE",browseList.get(getAdapterPosition()).getUrlToImage());
                    bundle.putString("TITLE", browseList.get(getAdapterPosition()).getTitle());
                    bundle.putString("CONTENT", browseList.get(getAdapterPosition()).getContent());
                    bundle.putString("URL", browseList.get(getAdapterPosition()).getUrl());
                    bundle.putString("AUTHOR", browseList.get(getAdapterPosition()).getAuthor());


                    Navigation.findNavController(view).navigate(R.id.newsDetailsFragment, bundle);
                }
            });
        }
    }


}
