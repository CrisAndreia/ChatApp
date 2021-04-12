package com.crispereira.projectchatapp.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.crispereira.projectchatapp.Connection.Connection;
import com.crispereira.projectchatapp.Connection.MessageAdapter;
import com.crispereira.projectchatapp.R;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatActivity extends AppCompatActivity implements TextWatcher{

    private String name;
    private WebSocket webSocket;
    //private String SERVER_PATH = "ws://10.0.2.2:3000";
    private EditText inputMessage;
    private View btSend;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private Connection connection = new Connection();
    private OkHttpClient client;
    private Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        name = getIntent().getStringExtra("name");

        connection.initiateConnection();
        client = connection.getClient();
        request = connection.getRequest();

        webSocket = client.newWebSocket(request, new SocketListener());

    }

    private class SocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);

            runOnUiThread(() -> {
                Toast.makeText(ChatActivity.this,
                        "Socket Connection Successful!",
                        Toast.LENGTH_SHORT).show();

                initializeView();
            });
        }

        @Override
        public void onMessage(WebSocket webSocket, String text){
            super.onMessage(webSocket, text);

            runOnUiThread(() -> {

                try {
                    JSONObject jsonObject = new JSONObject(text);
                    jsonObject.put("isSent", false);

                    messageAdapter.addItem(jsonObject);

                    recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            });
        }
    }

    private void initializeView() {

        inputMessage = findViewById(R.id.inputMessage);
        btSend = findViewById(R.id.btSend);

        recyclerView = findViewById(R.id.recyclerView);

        messageAdapter = new MessageAdapter(getLayoutInflater());
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        inputMessage.addTextChangedListener(this);

        btSend.setOnClickListener(v -> {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", name);
                jsonObject.put("message", inputMessage.getText().toString());

                webSocket.send(jsonObject.toString());

                jsonObject.put("isSent", true);
                messageAdapter.addItem(jsonObject);

                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

                resetMessageEdit();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String string = s.toString().trim();

        if (string.isEmpty()) {
            resetMessageEdit();
        } else {
            btSend.setVisibility(View.VISIBLE);
        }

    }

    private void resetMessageEdit() {

        inputMessage.removeTextChangedListener(this);

        inputMessage.setText("");
        btSend.setVisibility(View.INVISIBLE);

        inputMessage.addTextChangedListener(this);

    }

}
