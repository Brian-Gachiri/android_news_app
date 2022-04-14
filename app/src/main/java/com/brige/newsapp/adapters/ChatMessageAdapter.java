package com.brige.newsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.brige.newsapp.R;
import com.brige.newsapp.networking.pojos.ChatResponse;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {
    private List<ChatResponse> chats;
    private Context context;
    private int user_id;

    public ChatMessageAdapter(List<ChatResponse> chats, Context context, int id) {
        this.chats = chats;
        this.context = context;
        this.user_id = id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_bubbles, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ChatResponse chat = chats.get(position);

        if (user_id == chat.getUserFrom()){
            holder.myBubble.setVisibility(View.GONE);
            holder.theirMessage.setText(chat.getMessage());
            holder.theirTime.setText(chat.getDateCreated());
        }
        else{
            holder.theirBubble.setVisibility(View.GONE);
            holder.myMessage.setText(chat.getMessage());
            holder.myTime.setText(chat.getDateCreated());
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView myBubble, theirBubble;
        TextView myMessage, theirMessage, myTime, theirTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            myBubble = itemView.findViewById(R.id.cardMe);
            theirBubble = itemView.findViewById(R.id.cardThem);
            myMessage = itemView.findViewById(R.id.txt_message_me);
            theirMessage = itemView.findViewById(R.id.txt_message_them);
            myTime = itemView.findViewById(R.id.txt_time_me);
            theirTime = itemView.findViewById(R.id.txt_time_them);


        }
    }
}
