package com.example.tugas2_pemrogramanmobile;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button butBatt;
    private Button butIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        butBatt = findViewById(R.id.button3);
        butIntent = findViewById(R.id.button2);

        butBatt.setOnClickListener(v -> broadcastBatt());
        butIntent.setOnClickListener(v -> broadcastIntent());
    }

    public void broadcastIntent() {
        Intent intent = new Intent();
        intent.setAction("com.example.tugas2_pemrogramanmobile.CUSTOM_INTENT");
        sendBroadcast(intent);
    }

    public void broadcastBatt() {
        Intent intent = new Intent(MainActivity.this, BaterryStatus.class);
        startActivity(intent);
    }
}
