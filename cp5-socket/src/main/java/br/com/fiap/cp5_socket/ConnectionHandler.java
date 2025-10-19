package br.com.fiap.cp5_socket;

import java.io.*;
import java.net.Socket;
import java.math.BigInteger;
import java.util.Scanner;

public class ConnectionHandler {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ConnectionHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void handleServer() throws IOException {
        // Exemplo: valores de p, q, e (substitua pelos do grupo)
        BigInteger p = new BigInteger("137");
        BigInteger q = new BigInteger("191");
        BigInteger e = new BigInteger("7");
        RSAUtil rsa = new RSAUtil(p, q, e);

        // Envia chave pública para o cliente
        out.println(rsa.getN());
        out.println(rsa.getE());
        System.out.println("Chave pública enviada ao cliente.");

        // Recebe chave pública do cliente
        BigInteger clientN = new BigInteger(in.readLine());
        BigInteger clientE = new BigInteger(in.readLine());
        System.out.println("Chave pública recebida do cliente.");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Recebe mensagem criptografada do cliente (CSV)
            String encMsgCsv = in.readLine();
            if (encMsgCsv == null) break;
            System.out.println("Criptografado recebido (CSV): " + encMsgCsv);
            String msg = rsa.decryptFromCsv(encMsgCsv);
            System.out.println("Cliente (descriptografado): " + msg);
            if ("exit".equalsIgnoreCase(msg)) break;

            // Envia resposta
            System.out.print("Servidor (texto puro): ");
            String resposta = scanner.nextLine();
            String encRespostaCsv = RSAUtil.encryptToCsv(resposta, clientE, clientN);
            System.out.println("Criptografado enviado (CSV): " + encRespostaCsv);
            out.println(encRespostaCsv);
            if ("exit".equalsIgnoreCase(resposta)) break;
        }
    }

    public void handleClient() throws IOException {
        // Recebe chave pública do servidor
        BigInteger serverN = new BigInteger(in.readLine());
        BigInteger serverE = new BigInteger(in.readLine());
        System.out.println("Chave pública recebida do servidor.");

    // Exemplo: valores de p, q, e (substitua pelos do grupo)
    BigInteger p = new BigInteger("149");
    BigInteger q = new BigInteger("181");
    BigInteger e = new BigInteger("7");
        RSAUtil rsa = new RSAUtil(p, q, e);

        // Envia chave pública para o servidor
        out.println(rsa.getN());
        out.println(rsa.getE());
        System.out.println("Chave pública enviada ao servidor.");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Envia mensagem
            System.out.print("Cliente (texto puro): ");
            String msg = scanner.nextLine();
            String encMsgCsv = RSAUtil.encryptToCsv(msg, serverE, serverN);
            System.out.println("Criptografado enviado (CSV): " + encMsgCsv);
            out.println(encMsgCsv);
            if ("exit".equalsIgnoreCase(msg)) break;

            // Recebe resposta criptografada do servidor
            String encRespostaCsv = in.readLine();
            if (encRespostaCsv == null) break;
            System.out.println("Criptografado recebido (CSV): " + encRespostaCsv);
            String resposta = rsa.decryptFromCsv(encRespostaCsv);
            System.out.println("Servidor (descriptografado): " + resposta);
            if ("exit".equalsIgnoreCase(resposta)) break;
        }
    }
}
