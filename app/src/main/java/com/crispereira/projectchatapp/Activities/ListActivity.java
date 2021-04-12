package com.crispereira.projectchatapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.crispereira.projectchatapp.Connection.Connection;
import com.crispereira.projectchatapp.Connection.ListAdapter;
import com.crispereira.projectchatapp.R;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity{

    private String name;
    private WebSocket webSocket;
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private List<JSONObject> users = new ArrayList<>();
    private Connection connection = new Connection();
    private OkHttpClient client;
    private Request request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_initial);

        name = getIntent().getStringExtra("name");

        //iniciando coneccao sockets
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
                Toast.makeText(ListActivity.this,
                        "Socket Connection Successful!",
                        Toast.LENGTH_SHORT).show();
                initializeView();
            });
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);

            runOnUiThread(() -> {
                try {

                    JSONObject jsonObject = new JSONObject(text);
                    jsonObject.put("isDisponivel", false);

                    listAdapter.addItem(jsonObject);
                    recyclerView.smoothScrollToPosition(listAdapter.getItemCount() - 1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            });
        }
    }

    private void initializeView() {
        View btChat = findViewById(R.id.btChat);
        recyclerView = findViewById(R.id.recyclerView);

        listAdapter = new ListAdapter(getLayoutInflater());
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btChat.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        });

        //showUsers();
        newUser();
    }

    public void newUser(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name", name);

            webSocket.send(jsonObject.toString());

            jsonObject.put("isDisponivel", true);
            //adiciona o usuario ao listview
            listAdapter.addItem(jsonObject);
            users.add(jsonObject);

            //scroll automatico
            recyclerView.smoothScrollToPosition(listAdapter.getItemCount()-1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
