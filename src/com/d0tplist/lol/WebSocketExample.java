package com.d0tplist.lol;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Base64;

public class WebSocketExample {

    /**
     * Simple example showing how to receive websocket events from client
     */
    public static void main(String[] args) throws Exception {
        //Initialize API

        //open web socket
        ClientWebSocket socket = openWebSocket();
        //add event handler, which prints every received event
        socket.setSocketListener(new ClientWebSocket.SocketListener() {
            @Override
            public void onEvent(ClientWebSocket.Event event) {
                System.out.println(event);
            }

            @Override
            public void onClose(int code, String reason) {
                System.out.println("Socket closed, reason: " + reason);
            }
        });
        //close socket when user enters something into console
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.readLine();
        socket.close();
    }

    public static ClientWebSocket openWebSocket() throws Exception {

        String auth = new String(Base64.getEncoder().encode(("riot:" + "Zp7uEtcIfAdHUV3NvboUsw").getBytes()));
        return new ClientWebSocket(auth, 49306);
    }

}
