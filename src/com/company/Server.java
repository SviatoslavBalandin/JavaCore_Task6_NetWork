package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1369)) {
            System.out.println("Server started");
            Socket client = serverSocket.accept();
            System.out.println("Client connected!");
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            DataInputStream in = new DataInputStream(client.getInputStream());
            new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Type your message: ");
                while (true) {
                    String message = scanner.nextLine();
                    try {
                        out.writeUTF(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }).start();

            new Thread(() -> {
                while (true) {
                    try {
                        String client_message = in.readUTF();
                        if (client_message.equalsIgnoreCase("/end")) {
                            break;
                        }
                        System.out.println(client_message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
