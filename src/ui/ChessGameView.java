package ui;

import board.Board;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.BiConsumer;

public class ChessGameView {
    private JFrame frame;
    private JButton[][] buttons;
    private JLabel statusLabel;
    private JButton saveButton;
    private JButton loadButton;

    public ChessGameView() {
        frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

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

    public void setSquareColor(JButton button, int row, int col) {
        if ((row + col) % 2 == 0) {
            button.setBackground(new Color(238, 238, 210));
        } else {
            button.setBackground(new Color(118, 150, 86));
        }
    }

    public void updateStatusLabel(String text) {
        statusLabel.setText(text);
    }

    public void updateBoard(Board board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getSquare(j, i).getPiece();
                buttons[j][i].setText(piece != null ? piece.toString() : "");
            }
        }
    }

    public void setButtonListener(BiConsumer<Integer, Integer> listener) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                final int x = j;
                final int y = i;
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
