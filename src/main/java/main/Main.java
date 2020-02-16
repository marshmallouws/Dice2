/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controllers.GameCtrl;
import controllers.PlayerCtrl;
import entities.PlayerData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import sockets.ServerCon;
import sockets.ServerCon.ClientHandler;

/**
 *
 * @author Annika
 */
public class Main {
    public static void main(String[] args) throws IOException {
        ServerCon s = new ServerCon();
        s.start(5555); // Starting the server and waiting for clients
        List<PlayerCtrl> players = new ArrayList<>();

        // Waiting for players to write their name
        while(true) {
            List<String> names = s.getNames();
            if(!names.contains(null)) {
                break;
            }
        }
        
        // Create PlayerCtrl for each client and getting all their names
        String nameString = "The players are following: ";
        List<ClientHandler> clients = s.getClients();
        for(ClientHandler c: clients) {
            PlayerCtrl p = new PlayerCtrl(c);
            players.add(p);
            nameString += "\n\t" + c.getNamen();
        }
        
        // Broadcast the names of all players to clients
        s.writeToAll(nameString);
        
        GameCtrl g = new GameCtrl(players);
        
        g.play();
        
    }
}
