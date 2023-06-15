package Server;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static boolean startMenu() {
        Object[] options = {"Offline", "Online"};
        int check = JOptionPane.showOptionDialog(null, "Choose your gamemode: ", "Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (check == 0) {
            return true;
        } else {
            return false;
        }
    }
    public static void main(String[] args) {

        if(startMenu()) {
            JFrame frame = new JFrame("Server");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(650, 650);
            Board panel = new Board();
            panel.isSinglePlayer = true;
            frame.add(panel);
            frame.setVisible(true);
        } else {
            ServerSocket server;
            try {
                server = new ServerSocket(1234);
            }catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Couldn't create server.");
                return;
            }

            Socket client;
            JOptionPane.showMessageDialog(null, "Waiting for a player");
            try {
                client = server.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Player connected");

            JFrame frame = new JFrame("Server");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(650, 650);
            Board panel = new Board();
            frame.add(panel);
            frame.setVisible(true);

            PrintWriter out;
            BufferedReader in;
            try {
                out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            while(true) {
                try {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            out.println((char)panel.board[i][j]);
                        }
                    }

                    out.println(panel.turn);
                    String receive = in.readLine();


                    String receive2 = in.readLine();
                    panel.insertChar(Integer.parseInt(receive), Integer.parseInt(receive2));
                }catch (IOException e) {}
            }
        }
    }
}