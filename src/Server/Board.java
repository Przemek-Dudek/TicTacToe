package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Board extends JPanel implements MouseListener {

    public static final int BOARD_WIDTH = 3;
    private final Image imgCross;
    private final Image imgCircle;
    public final char[][] board;
    public char turn;
    private char isGameOver;
    public boolean isSinglePlayer = false;
    private final Timer timer2;
    public Board() {
        Timer timer = new Timer(1000, e -> repaint());
        timer2 = new Timer(1000, e -> {
            if(whoWon()) {
                if(isGameOver == ' ')
                    JOptionPane.showMessageDialog(this, "Draw", "You Win", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(this, isGameOver + " wins", "Win", JOptionPane.INFORMATION_MESSAGE);
                if(!isSinglePlayer)
                    System.exit(0);
            }
        });
        timer.start();
        timer2.start();
        imgCross = Toolkit.getDefaultToolkit().getImage("src/assets/crossResized.png");
        imgCircle = Toolkit.getDefaultToolkit().getImage("src/assets/circleResized.png");
        addMouseListener(this);
        board = new char[BOARD_WIDTH][BOARD_WIDTH];
        turn = 'O';
        initializeBoard();
    }

    public void paint(Graphics g) {
        g.clearRect(0,0,850,600);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 600, 600);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        drawLines(g);
        drawImages(g);
    }

    public void drawLines(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(0, 200, 600, 200);
        g.drawLine(0, 400, 600, 400);
        g.drawLine(200, 0, 200, 600);
        g.drawLine(400, 0, 400, 600);
    }

    public void drawImages(Graphics g) {
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[i][j] == 'X')
                    g.drawImage(imgCross, j*200+5, i*200+5, this);
                else if(board[i][j] == 'O')
                    g.drawImage(imgCircle, j*200+2, i*200+2, this);
            }
        }
    }

    private void initializeBoard() {
        isGameOver = 0;
        for (int i=0; i<BOARD_WIDTH; ++i){
            for(int j=0 ;j<BOARD_WIDTH; ++j) {
                board[i][j]=' ';
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint();
        Point square = whichSquare(p);
        if(square.x == 3 || square.y == 3)
            return;
        if(board[square.y][square.x] == ' ') {
            if(turn == 'O'){
                board[square.y][square.x] = 'O';
                turn = 'X';
            }
            else if(turn == 'X' && isSinglePlayer) {
                board[square.y][square.x] = 'X';
                turn = 'O';
            }
        }
        if(whoWon()) {
            if(isGameOver == ' ')
                JOptionPane.showMessageDialog(this, "Remis!", "Wygrana", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, isGameOver + " wygrał!", "Wygrana", JOptionPane.INFORMATION_MESSAGE);
            askToPlayAgain();
        }
    }

    public void insertChar(int x, int y) {
        if(y<0 || y>2 || x<0 || x>2)
            return;
        if(board[y][x] == ' ') {
            board[y][x] = 'X';
            if(turn == 'X')
                turn = 'O';
            else
                turn = 'X';
        }
    }

    private void askToPlayAgain() {
        int reply = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Play again", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            initializeBoard();
        } else {
            System.exit(0);
        }
    }


    private Point whichSquare(Point p) {
        return new Point(countXorY(p.x),countXorY(p.y));
    }

    private int countXorY(int x) {
        if(200 > x)
            return 0;
        else if(x < 400)
            return 1;
        else if(x < 600)
            return 2;
        else
            return 3;
    }

    private boolean whoWon() {
        if(isBoardFull()) {
            isGameOver = ' ';
            timer2.stop();
            return true;
        }
        for (int row = 0; row < BOARD_WIDTH; row++) {
            if (board[row][0] != ' ' && board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                System.out.println(board[row][0] + " won!");
                isGameOver = board[row][0];
                timer2.stop();
                return true;
            }
        }

        for (int col = 0; col < BOARD_WIDTH; col++) {
            if (board[0][col] != ' ' && board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                System.out.println(board[0][col] + " won!");
                isGameOver = board[0][col];
                timer2.stop();
                return true;
            }
        }

        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            isGameOver = board[0][0];
            timer2.stop();
            return true;
        }
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            isGameOver = board[0][2];
            return true;
        }

        return false;
    }

    private boolean isBoardFull () {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if(board[i][j] == ' ')
                    return false;
            }
        }
        return true;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
