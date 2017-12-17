package net;

import android.content.Context;
import android.os.AsyncTask;

import com.id1212_android_hangman.GlobalState;

import java.io.PrintWriter;

/**
 * Created by Bernardo on 17/12/2017.
 */

public class Sender extends AsyncTask<String, Void, Void> {

    PrintWriter printWriter;

    public Sender(Context context) {
        GlobalState state = (GlobalState) context.getApplicationContext();
        printWriter = state.getPrintWriter();
    }

    @Override
    protected Void doInBackground(String... message) {
        System.out.println("sending message: " + message[0]);
        printWriter.println(message[0]);
        return null;
    }
}
