/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author archer
 */
public class Server {
    ServerSocket serverSocket = null;
    List<DataOutputStream> outStreams = null;
    
    public Server() {
        try {
            serverSocket = new ServerSocket(8000);
            outStreams = new ArrayList<>();
        } catch (IOException ex) {
            System.out.println("Error at creating server socket!" + ex.getMessage());
        }
    }
    
    public void listenClients() {
        while(true) {
            try {
                Socket toClient = serverSocket.accept();
                outStreams.add(new DataOutputStream(toClient.getOutputStream()));
                handleClient(toClient);
            } catch (IOException ex) {
                System.out.println("Error at accepting client sockets!" + ex.getMessage());
            }
        }
    }
    
    public void handleClient(Socket toClient) {
        Thread read = new Thread(() -> {
            try {
                DataInputStream instream = new DataInputStream(toClient.getInputStream());
                while(true) {
                    String clientMsg = instream.readUTF() + "\n(" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + ")\n\n";
                    System.out.println(clientMsg);
                    outStreams.stream().forEach(stream -> {
                        try {
                            stream.writeUTF(clientMsg);
                        } catch (IOException ex) {
                        }
                    });
                }
            } catch (IOException ex) {
                
            }
        });
        read.start();
    }
    
    public static void main(String[] args) {
        Server server = new Server();
        server.listenClients();
    }
}
