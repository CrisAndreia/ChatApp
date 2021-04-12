package com.crispereira.projectchatapp.Connection;

import android.app.Application;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import com.crispereira.projectchatapp.Model.User;
import okhttp3.WebSocket;

public class Connection extends Application {
    private WebSocket webSocket;
    private String SERVER_PATH = "ws://10.0.2.2:3000";
    private OkHttpClient client;
    private Request request;

    public void initiateConnection() {
        client = new OkHttpClient();
        request = new Request.Builder().url(SERVER_PATH).build();
    }

    public OkHttpClient getClient(){
        return client;
    }

    public Request getRequest(){
        return request;
    }

}

