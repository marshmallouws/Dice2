/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;


import com.google.gson.Gson;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import jsonentities.JsonParser;

/**
 *
 * @author Annika
 */
public class ClientCon {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private static final Scanner SCAN = new Scanner(System.in);
    private static final Gson GSON = new Gson();

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private void sendMessage(String msg) throws IOException {
        out.println(msg);
    }

    private void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    private boolean getMessage() throws IOException {
        String inputLine = in.readLine();
        JsonParser json = GSON.fromJson(inputLine, JsonParser.class);
        System.out.println(json.getMsg());
        return json.isToAnswer();
    }

    public void playGame() throws IOException {
        // Sending name to server
        getMessage();
        String name = SCAN.nextLine();
        sendMessage(name);

        while (true) {
            String inputLine = in.readLine();
            JsonParser json = GSON.fromJson(inputLine, JsonParser.class);
            System.out.println(json.getMsg());

            if (json.isToAnswer()) {
                if (json.getMax() != json.getMin()) {
                    while (true) {
                        String res = SCAN.nextLine();
                        try {
                            int i = Integer.parseInt(res);
                            if (i > json.getMax() || i < json.getMin()) {
                                System.out.println("Not a choice. Try again:");
                            } else {
                                sendMessage(res);
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Not a number. Try again:");
                        }
                    }
                } else {
                    sendMessage(SCAN.nextLine());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ClientCon c = new ClientCon();
        c.startConnection("127.0.0.1", 5555);

        c.playGame();
        c.stopConnection();
    }
}
