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
import org.json.JSONObject;

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
        clients.forEach(c -> c.write(msg, false));
    }

    public void writeToIdlePlayers(String msg) {
        clients.forEach(c -> {
            c.write(msg, false);
        });
    }

    public List<String> getNames() {
        List<String> msgs = new ArrayList<>();
        clients.forEach(c -> msgs.add(c.getNamen()));
        return msgs;
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

        public void write(String msg, boolean toAnswer) {
            JSONObject obj = new JSONObject();
            obj.put("msg", msg);
            obj.put("toAnswer", toAnswer);
            obj.put("max", 0);
            obj.put("min", 0);
            out.println(obj);
            out.flush();
        }

        public MeyerRoll getLie(int min, int max, List<MeyerRoll> choices) {
            JSONObject obj = new JSONObject();
            int choice = 0;
            try {
                while (true) {
                    obj.put("msg", "You have following choices. Write index: " + choices.toString());
                    obj.put("toAnswer", true);
                    out.println(obj);
                    String input = in.readLine();
                    try {
                        choice = Integer.parseInt(input);
                        if (choice > max || choice < min) {
                            out.println("Not an acceptable input. Try again");
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return choices.get(choice + 1);
        }

        public int getInt(int min, int max, String choices) {
            int res = 0;
            try {
                JSONObject obj = new JSONObject();
                obj.put("msg", choices);
                obj.put("toAnswer", true);
                obj.put("max", max);
                obj.put("min", min);
                out.println(obj);
                out.flush();
                String inp = in.readLine();

                try {
                    res = Integer.parseInt(inp);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }

        public String getMessage() {
            try {
                String i = in.readLine();
                return i;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Failed to recieve message";
        }

        public String getClientName() {
            write("Hello, write your name: ", true);
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
                
            } catch (IOException ex) {
                Logger.getLogger(ServerCon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
