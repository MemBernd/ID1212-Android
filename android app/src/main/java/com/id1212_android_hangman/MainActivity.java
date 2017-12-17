package com.id1212_android_hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import net.EstablishConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void connect(View v) {
        EditText textIP = findViewById(R.id.txt_ip);
        EditText textPort = findViewById(R.id.txt_port);
        String ip = textIP.getText().toString();
        String port = textPort.getText().toString();
        //System.out.println("Ip: " + ip + " port: " + port);
        EstablishConnection task = new EstablishConnection(this);
        task.execute(ip, port);
    }
}
