package com.brige.newsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.brige.newsapp.R;
import com.brige.newsapp.networking.pojos.ChatResponse;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<ChatResponse> chats;
    private Context context;


    public ChatAdapter(List<ChatResponse> chats, Context context) {
        this.chats = chats;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ChatResponse chat = chats.get(position);

        holder.chatName.setText(chat.getUserFromName());
        holder.chatMessage.setText(chat.getMessage());
        Glide.with(context).load("https://picsum.photos/id/"+(position+100)+"/70/70").into(holder.imgChat);
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView chatName, chatMessage;
        ImageView imgChat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            chatName = itemView.findViewById(R.id.txt_chat_name);
            chatMessage = itemView.findViewById(R.id.txt_chat_message);
            imgChat = itemView.findViewById(R.id.img_chat);

            itemView.setOnClickListener(v ->{

                Navigation.findNavController(v).navigate(R.id.chatDetailsFragment);
//                Snackbar.make(v, "Comming Soon", Snackbar.LENGTH_LONG).show();
            });
        }
    }
}
