/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Annika
 */
public class ClientCon {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private static final Scanner SCAN = new Scanner(System.in);

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendMessage(String msg) throws IOException {
        out.println(msg);
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
    
    public void getMessage() throws IOException {
        System.out.println(in.readLine());
    }
    
    public void playGame() throws IOException {
        // Sending name to server
        getMessage();
        String name = SCAN.nextLine();
        sendMessage(name);
        
        String inputLine;
        while(true) {
            inputLine = in.readLine();
            if("!".equals(inputLine)) {
                break;
            } 
            System.out.println(inputLine);
        }
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        ClientCon c = new ClientCon();
        c.startConnection("127.0.0.1", 5555);
        
        c.playGame();
        
//        System.out.println("Write name:");
//        String name = SCAN.nextLine();
//        c.sendMessage("Hello, my name is " + name);
//        c.getMessage();
//        TimeUnit.SECONDS.sleep(10);
        c.stopConnection();
    }
}
