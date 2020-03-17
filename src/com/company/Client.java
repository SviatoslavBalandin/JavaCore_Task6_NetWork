package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final String SERVER_PATH = "localhost";
    private final int PORT = 1369;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Scanner scanner;


    public Client() {
        try {
            connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client();
    }

    public void connect() throws IOException {
        socket = new Socket(SERVER_PATH, PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        sendMessage();

        new Thread(() -> {
            try {
                while (true) {
                    String textFromServer = in.readUTF();
                    if (textFromServer.equalsIgnoreCase("/end")) {
                        break;
                    }
                    System.out.println("From server: ");
                    System.out.println(textFromServer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


    }

    public void sendMessage() {
        scanner = new Scanner(System.in);
        new Thread(() -> {
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
    }
}
