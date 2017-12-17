package net;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.id1212_android_hangman.Constants;
import com.id1212_android_hangman.GlobalState;

import java.lang.ref.WeakReference;

/**
 * Created by Bernardo on 17/12/2017.
 */

public class Listener extends AsyncTask<Void, Void, String> {

    private Context context;
    private WeakReference<TextView> gameStateView;
    private GlobalState state;
    private boolean received = false;

    public Listener(Context context, TextView view) {
        this.gameStateView = new WeakReference<TextView>(view);
        this.context = context;
        state = (GlobalState) context.getApplicationContext();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String message = "";
        try {
            while(!received) {
                message = state.getBufferedReader().readLine();
                received = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "error listening";
        }
        return message;
    }

    @Override
    protected void onPostExecute(String message) {
        String[] msg = message.split(Constants.HEADER_DELIMITER);
        for (String elem : msg) {
            System.out.println(elem);
        }
        if (msg.length < 2) {
            Toast.makeText(context, "Corrupt message received", Toast.LENGTH_SHORT).show();
        } else {
            if (msg[0].equals(Constants.STATE)) {
                if(gameStateView.get() != null) {
                    gameStateView.get().setText(msg[1].replace(Constants.DELIMITER, "\n"));
                }
            } else if(msg[0].equals(Constants.INFORMATION)) {
                Toast.makeText(context, msg[1], Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Incompatible message received", Toast.LENGTH_SHORT).show();
            }
        }

    }



}
