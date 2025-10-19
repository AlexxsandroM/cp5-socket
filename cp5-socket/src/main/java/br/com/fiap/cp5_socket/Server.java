package br.com.fiap.cp5_socket;

import java.io.*;
import java.net.*;
import java.math.BigInteger;

public class Server {
    public static void main(String[] args) throws Exception {
        int port = 5000;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Servidor aguardando conexão na porta " + port);
        Socket socket = serverSocket.accept();
        System.out.println("Cliente conectado: " + socket.getInetAddress());

        ConnectionHandler handler = new ConnectionHandler(socket);
        handler.handleServer();

        socket.close();
        serverSocket.close();
    }
}
