package com.id1212_android_hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import net.Listener;
import net.Sender;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Game extends AppCompatActivity {

    private GlobalState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        state = (GlobalState) getApplicationContext();
        System.out.println("Game socket: " + state.getSocket().getInetAddress() + " port: " + state.getSocket().getPort());

    }

    public void startGame(View v) {
        Sender sender = new Sender(this);
        sender.execute("START");
        Listener task = new Listener(this, (TextView) findViewById(R.id.tv_output));
        task.execute();
    }

    public void sendInput(View v) {
        Sender sender = new Sender(this);
        EditText edit = (EditText) findViewById(R.id.textToSend);
        sender.execute(edit.getText().toString());
        Listener task = new Listener(this, (TextView) findViewById(R.id.tv_output));
        task.execute();
        edit.setText("");
    }
}
