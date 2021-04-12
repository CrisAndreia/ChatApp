package com.crispereira.projectchatapp.Activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.crispereira.projectchatapp.R;

import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Button;
import android.widget.EditText;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
//import com.crispereira.projectchapapp.connection.InfoApp;
import com.crispereira.projectchatapp.Connection.ListAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);

        EditText inputName = findViewById(R.id.inputName);
        Button btLogin = findViewById(R.id.btLogin);

        btLogin.setOnClickListener(v -> {

            String user = inputName.getText().toString();
            users.add(user);

            Intent listActivity = new Intent(this, ListActivity.class);
            listActivity.putExtra("name", user);
            startActivity(listActivity);

        });
    }
}