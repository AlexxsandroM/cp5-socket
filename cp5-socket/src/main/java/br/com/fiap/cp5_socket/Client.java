package br.com.fiap.cp5_socket;

import java.io.*;
import java.net.*;
import java.math.BigInteger;

public class Client {
    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 5000;
        Socket socket = new Socket(host, port);
        System.out.println("Conectado ao servidor: " + host + ":" + port);

        ConnectionHandler handler = new ConnectionHandler(socket);
        handler.handleClient();

        socket.close();
    }
}
