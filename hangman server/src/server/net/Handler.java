/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.StringJoiner;
import protocol.Constants;
import server.controller.Controller;

/**
 *
 * @author Bernardo
 */
public class Handler extends Thread {
    private Socket socket;
    private boolean exit = false;
    private BufferedReader reader;
    private PrintWriter writer;
    private Controller controller = new Controller();
    
    Handler(Socket client) {
        socket = client;
    }
    
    @Override
    public void run() {
        try {
            reader = new BufferedReader(new
                InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        while(!exit) {
            try {
                String[] message = reader.readLine().split(Constants.DELIMITER);
                String reply = ""; 
                switch (message[0]) {
                    case "QUIT":
                        disconnect();
                        exit = true;
                        break;
                    case "START":
                        controller.startGame();
                        reply = Constants.STATE + Constants.HEADER_DELIMITER;
                        reply += "Game started:" + Constants.DELIMITER;
                        writer.println(reply + stateToString(Constants.DELIMITER));
                        break;
                    default:
                        if (controller.gameStarted()) {
                            try {
                                reply = Constants.STATE + Constants.HEADER_DELIMITER;
                                if(controller.attempt(message[0].toCharArray())) {
                                    
                                    reply += "Game finished as followed:" + Constants.DELIMITER;
                                }
                                reply += stateToString(Constants.DELIMITER);
                            } catch (Exception e) {
                                reply = Constants.INFORMATION + Constants.HEADER_DELIMITER;
                                reply += "Incorrect amount of characters.";
                            }

                        } else {
                            reply = Constants.INFORMATION + Constants.HEADER_DELIMITER;
                            reply += "Game hasn't started yet.";
                        }
                        
                        writer.println(reply);
                }
            } catch(Exception e) {
                //e.printStackTrace();
                System.err.println("Error in clienthandler run, aborting.");
                exit = true;
            }
            
        }
    }
    
    private String stateToString(String delimiter) {
        StringJoiner joinedMessage = new StringJoiner(delimiter);
        String[]state = controller.getGameState();
        for (String element : state) {
            joinedMessage.add(element);
        }
        return joinedMessage.toString();
    }
    
    private void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error closing socket.");
        }
        exit = true;
    }
}
