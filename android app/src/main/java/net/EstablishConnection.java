package net;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.id1212_android_hangman.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Bernardo on 17/12/2017.
 */

public class EstablishConnection extends AsyncTask<String, Void, Void> {

    private Context context;
    GlobalState state;

    public EstablishConnection(Context context) {
        //
        state = (GlobalState) context.getApplicationContext();
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... output) {

        try {

            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(output[0], Integer.parseInt(output[1])), Constants.TIMEOUT_SHORT);
            socket.setSoTimeout(Constants.TIMEOUT_LONG);
            state.setSocket(socket);
            state.setPrintWriter(new PrintWriter(socket.getOutputStream(), true));
            state.setBufferedReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));

            state.setConnected(true);
        } catch (Exception e) {
            e.printStackTrace();
            state.setConnected(false);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        if (state.isConnected()) {
            Intent intent = new Intent(context, Game.class);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Failed to connect", Toast.LENGTH_SHORT).show();
        }
    }


}
