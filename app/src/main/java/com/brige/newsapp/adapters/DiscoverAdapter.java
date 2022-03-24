package com.brige.newsapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brige.newsapp.R;
import com.brige.newsapp.models.Discover;
import com.brige.newsapp.utils.CustomDialog;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder> {
    List<Discover> discoverList;
    Context context;

    public DiscoverAdapter(List<Discover> discoverList, Context context) {
        this.discoverList = discoverList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(discoverList.get(position).getImage()).into(holder.imgDiscover);

    }

    @Override
    public int getItemCount() {
        return discoverList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDiscover;
        FloatingActionButton playVideo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgDiscover = itemView.findViewById(R.id.img_discover);
            playVideo = itemView.findViewById(R.id.btn_discover_play);

            playVideo.setOnClickListener(v ->{

                String url = discoverList.get(getAdapterPosition()).getVideo_url();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Discover discover = discoverList.get(getAdapterPosition());
                    new CustomDialog(context).showDiscoverDialog(discover);

                    return true;
                }
            });

        }
    }
}
