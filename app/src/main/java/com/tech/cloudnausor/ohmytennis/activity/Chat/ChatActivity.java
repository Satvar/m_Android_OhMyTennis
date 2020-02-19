package com.tech.cloudnausor.ohmytennis.activity.Chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.adapter.ChatAdapter;
import com.tech.cloudnausor.ohmytennis.model.ChatMessageModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    private EditText messageET;
    private RecyclerView messagesContainer;
    private ImageView sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessageModel> chatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_chat);
        initControls();
    }

    private void initControls() {
        messagesContainer = (RecyclerView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (ImageView) findViewById(R.id.chatSendButton);
        messagesContainer.setLayoutManager(new LinearLayoutManager(this));
        loadDummyHistory();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }
                ChatMessageModel chatMessage = new ChatMessageModel();
                chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);
                messageET.setText("");
                displayMessage(chatMessage);
            }
        });
    }

    public void displayMessage(ChatMessageModel message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
//        scroll();
    }

//    private void scroll() {
//        messagesContainer.setSelection(messagesContainer.getCount() - 1);
//    }

    private void loadDummyHistory(){

        chatHistory = new ArrayList<ChatMessageModel>();
        ChatMessageModel msg = new ChatMessageModel();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Hi");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessageModel msg1 = new ChatMessageModel();
        msg1.setId(2);
        msg1.setMe(false);
        msg1.setMessage("How r u doing???");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);

        adapter = new ChatAdapter( new ArrayList<ChatMessageModel>(),ChatActivity.this);
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessageModel message = chatHistory.get(i);
            displayMessage(message);
        }
    }
}
