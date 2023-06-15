package Client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Main {
    public static final String IP = "10.128.142.40";
    public static final int PORT = 1234;

    public static void main(String[] args) {
        Socket client = null;
        PrintWriter out;
        BufferedReader in;
        try {
            client = new Socket(IP, PORT);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
        } catch (IOException e) {
            System.out.println("Couldn't connect to a host");
            System.exit(1);
            throw new RuntimeException(e);
        }

        System.out.println("Connected to a server");

        JFrame frame = new JFrame("Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 650);
        Board panel = new Board();
        frame.add(panel);
        frame.setVisible(true);

        while(true) {
            try {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        String temp = in.readLine();
                        panel.board[i][j] = temp.charAt(0);

                        System.out.println("i = " + i + "j = " + j + temp.charAt(0)); //DEBUG
                    }
                }

                String temp = in.readLine();
                panel.turn = temp.charAt(0);
                out.println(panel.x);
                out.println(panel.y);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}