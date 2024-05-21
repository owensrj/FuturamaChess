package ui;

import board.Board;
import controller.ChessGameController;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ChessGameView {
    private JFrame frame;
    private JButton[][] buttons;
    private JLabel statusLabel;
    private JButton saveButton;
    private JButton loadButton;
    private ChessGameController controller;
    private Map<String, ImageIcon> pieceImages;

    public ChessGameView() {
        frame = new JFrame("Futurama Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        // Load piece images early
        loadPieceImages();
        
        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        frame.add(boardPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("Current turn: White");
        frame.add(statusLabel, BorderLayout.SOUTH);

        buttons = new JButton[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton button = new JButton();
                buttons[j][i] = button;
                boardPanel.add(button);
                setSquareColor(button, i, j);
            }
        }

        JPanel controlPanel = new JPanel();
        saveButton = new JButton("Save Game");
        loadButton = new JButton("Load Game");
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.pack();
        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    public void setController(ChessGameController controller) {
        this.controller = controller;
        this.controller.initView();
    }

    private void loadPieceImages() {
        pieceImages = new HashMap<>();
        // Load images for each piece type
        pieceImages.put("WP", new ImageIcon("images/white_pawn.png"));
        pieceImages.put("WN", new ImageIcon("images/white_knight.png"));
        pieceImages.put("WB", new ImageIcon("images/white_bishop.png"));
        pieceImages.put("WR", new ImageIcon("images/white_rook.png"));
        pieceImages.put("WQ", new ImageIcon("images/white_queen.png"));
        pieceImages.put("WK", new ImageIcon("images/white_king.png"));
        pieceImages.put("BP", new ImageIcon("images/black_pawn.png"));
        pieceImages.put("BN", new ImageIcon("images/black_knight.png"));
        pieceImages.put("BB", new ImageIcon("images/black_bishop.png"));
        pieceImages.put("BR", new ImageIcon("images/black_rook.png"));
        pieceImages.put("BQ", new ImageIcon("images/black_queen.png"));
        pieceImages.put("BK", new ImageIcon("images/black_king.png"));
    }
    
    public void setSquareColor(JButton button, int row, int col) {
        if ((row + col) % 2 == 0) {
            button.setBackground(new Color(0, 143, 203));
        } else {
            button.setBackground(new Color(114, 228, 159));
        }
    }

    public void updateStatusLabel(String text) {
        statusLabel.setText(text);
    }

    public void updateBoard(Board board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getSquare(j, i).getPiece();
                if (piece != null) {
                    String pieceKey = piece.toString(); // WP, BK, etc.
                    buttons[j][i].setIcon(pieceImages.get(pieceKey));
                    buttons[j][i].setText(""); // Clear text
                } else {
                    buttons[j][i].setIcon(null);
                    buttons[j][i].setText("");
                }
            }
        }
    }

    public void setButtonListener(BiConsumer<Integer, Integer> listener) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                final int x = j;
                final int y = i;
                // Ensure we remove any existing listeners before adding a new one
                for (ActionListener al : buttons[j][i].getActionListeners()) {
                    buttons[j][i].removeActionListener(al);
                }
                buttons[j][i].addActionListener(e -> listener.accept(x, y));
            }
        }
    }


    public void setSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public void setLoadButtonListener(ActionListener listener) {
        loadButton.addActionListener(listener);
    }
}
