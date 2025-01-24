/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchatapplication;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abdel
 */
public class ServerChatApplication {

    ServerSocket serverSocket;

    public ServerChatApplication() {
        try {
            serverSocket = new ServerSocket(5005);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ChatHandler(clientSocket);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerChatApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new ServerChatApplication();
    }

}

class ChatHandler extends Thread {

    DataInputStream ear;
    PrintStream mouth;
    static Vector<ChatHandler> clientsVector = new Vector<ChatHandler>();
    Socket currentClientSocket;
    
    public ChatHandler(Socket clientSocket) {
        try {
            currentClientSocket = clientSocket;
            ear = new DataInputStream(clientSocket.getInputStream());
            mouth = new PrintStream(clientSocket.getOutputStream());

            clientsVector.add(this);
            start();
        } catch (IOException ex) {
            Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        while (true) {
            String str;
            try {
                str = ear.readLine();
                if (str.equals("Close my connection")) {
                    closeConnection();
                    break;
                } else {
                    sendMessageToAll(str);
                }
            } catch (IOException ex) {
                Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void sendMessageToAll(String str) {
        for (ChatHandler client : clientsVector) {
            client.mouth.println(str);
        }
    }

    void closeConnection() {
        try {
            ear.close();
            mouth.close();
            currentClientSocket.close();
            clientsVector.remove(this);
            System.out.println("no of connected clients are : " + clientsVector.size());
        } catch (IOException ex) {
            Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
