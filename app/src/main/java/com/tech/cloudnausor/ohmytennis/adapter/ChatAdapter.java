package com.tech.cloudnausor.ohmytennis.adapter;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.model.ChatMessageModel;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {

    private final ArrayList<ChatMessageModel> chatMessages;
    private Activity context;

    public ChatAdapter(ArrayList<ChatMessageModel> chatMessages, Activity context) {
        this.chatMessages = chatMessages;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chat_messages,parent,false);
        return new ChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        ChatMessageModel chatMessageModel = chatMessages.get(position);
        setAlignment(holder,chatMessageModel.getIsme());
                holder.txtMessage.setText(chatMessageModel.getMessage());
                holder.txtInfo.setText(chatMessageModel.getDate());

    }
    private void setAlignment(ChatHolder holder, boolean isMe) {
        if (!isMe) {
            holder.contentWithBG.setBackgroundResource(R.drawable.chat_small);
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.contentWithBG.setLayoutParams(layoutParams);
            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.contents.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.contents.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.txtMessage.setLayoutParams(layoutParams);
            layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.txtInfo.setLayoutParams(layoutParams);
        } else {
            holder.contentWithBG.setBackgroundResource(R.drawable.chat_small);
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.contentWithBG.setLayoutParams(layoutParams);
            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.contents.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.contents.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.txtMessage.setLayoutParams(layoutParams);
            layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.txtInfo.setLayoutParams(layoutParams);
        }
    }
    @Override
    public int getItemCount() {
        return chatMessages.size();
    }
    public void add(ChatMessageModel message) {
        chatMessages.add(message);
    }

    public class ChatHolder extends RecyclerView.ViewHolder {

        public TextView txtMessage;
        public TextView txtInfo;
        public LinearLayout contents;
        public LinearLayout contentWithBG;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            txtMessage = (TextView) itemView.findViewById(R.id.txtMessage);
            contents = (LinearLayout) itemView.findViewById(R.id.content);
            contentWithBG = (LinearLayout) itemView.findViewById(R.id.contentWithBackground);
            txtInfo = (TextView) itemView.findViewById(R.id.txtInfo);
        }
    }

}
