/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;

import controllers.PlayerCtrl;
import entities.MeyerRoll;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Annika
 */
public class ServerCon {

    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList();

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        // TODO: Find better way to find clients (Kill after given amount of seconds)
        int i = 0;
        while (i < 2) {
            ClientHandler h = new ClientHandler(serverSocket.accept());
            h.start();
            clients.add(h);
            i++;
        }
    }

    public void writeToAll(String msg) {
        clients.forEach(c -> c.write(msg));
    }

    public List<String> getNames() {
        List<String> msgs = new ArrayList<>();
        clients.forEach(c -> msgs.add(c.getNamen()));
        return msgs;
    }

    public void getSpecificPlayerMessage(/*Recieve something to find player*/) {

    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public List<ClientHandler> getClients() {
        return clients;
    }

    public static class ClientHandler extends Thread {

        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private String name;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void write(String text) {
            out.println(text);
        }
        
        public MeyerRoll getLieMotherFunc(int min, int max, String choices) {
            try {
                while(true) {
                    String input = in.readLine();
                }
            }
        }

        public int getInt(int min, int max, String choices) {
            int res = 0;
            try {
                while (true) {
                    out.println(choices);
                    String input = in.readLine();
                    try {
                        res = Integer.parseInt(input);
                        if (res > max || res < min) {
                            out.print("Not an acceptable input" + choices);
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        out.print("Not a number. " + choices);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }

        public String getMessage() {
            try {
                return in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Failed to recieve message";
        }

        public String getClientName() {
            write("Hello, write your name: ");
            return getMessage();
        }

        public String getNamen() {
            return name;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                name = getClientName();
                String inputLine = in.readLine();

                while (true/*(inputLine = in.readLine()) != null*/) {
                    if (".".equals(inputLine)) {
                        out.println("bye");
                        break;
                    }
                }
                in.close();

                out.close();
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerCon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
