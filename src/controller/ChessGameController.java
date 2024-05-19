package controller;

import board.Board;
import board.Move;
import board.Square;
import pieces.King;
import pieces.Piece;
import ui.ChessGameView;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChessGameController {
    private Board model;
    private ChessGameView view;
    private String currentPlayer;
    private Square selectedSquare;
    private List<Move> moveHistory;

    public ChessGameController(Board model, ChessGameView view) {
        this.model = model;
        this.view = view;
        this.currentPlayer = "White";
        this.selectedSquare = null;
        this.moveHistory = new ArrayList<>();
        initView();
    }

    public void initView() {
        view.updateBoard(model);
        view.setButtonListener(this::handleButtonClick);
        view.updateStatusLabel("Current turn: " + currentPlayer);
        view.setSaveButtonListener(e -> saveGame());
        view.setLoadButtonListener(e -> loadGame());
    }

    public void handleButtonClick(int x, int y) {
        Square clickedSquare = model.getSquare(x, y);

        if (selectedSquare == null) {
            if (clickedSquare.getPiece() != null && clickedSquare.getPiece().getColor().equals(currentPlayer)) {
                selectedSquare = clickedSquare;
            }
        } else {
            Piece piece = selectedSquare.getPiece();
            if (piece != null && piece.movePiece(selectedSquare, clickedSquare, model)) {
                clickedSquare.setPiece(piece);
                moveHistory.add(new Move(selectedSquare, clickedSquare));
                selectedSquare.setPiece(null);
                selectedSquare = null;
                currentPlayer = currentPlayer.equals("White") ? "Black" : "White";
                view.updateStatusLabel("Current turn: " + currentPlayer);
                if (isGameOver()) {
                    view.updateStatusLabel("Game Over! Winner: " + currentPlayer);
                }
            } else {
                selectedSquare = null;
            }
            view.updateBoard(model);
        }
    }

    public boolean isGameOver() {
        boolean whiteKingExists = false;
        boolean blackKingExists = false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = model.getSquare(i, j).getPiece();
                if (piece instanceof King) {
                    if (piece.getColor().equals("White")) {
                        whiteKingExists = true;
                    } else {
                        blackKingExists = true;
                    }
                }
            }
        }

        return !whiteKingExists || !blackKingExists;
    }

    public void saveGame() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("chessgame_moves.sav"))) {
            out.writeObject(moveHistory);
            out.writeObject(currentPlayer);
            JOptionPane.showMessageDialog(null, "Game saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving game: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("chessgame_moves.sav"))) {
            moveHistory = (List<Move>) in.readObject();
            currentPlayer = (String) in.readObject();
            model = new Board();
            for (Move move : moveHistory) {
                Piece piece = model.getSquare(move.getStartX(), move.getStartY()).getPiece();
                Square endSquare = model.getSquare(move.getEndX(), move.getEndY());
                if (piece != null && piece.movePiece(model.getSquare(move.getStartX(), move.getStartY()), endSquare, model)) {
                    endSquare.setPiece(piece);
                    model.getSquare(move.getStartX(), move.getStartY()).setPiece(null);
                }
            }
            view.updateBoard(model);
            view.updateStatusLabel("Current turn: " + currentPlayer);
            JOptionPane.showMessageDialog(null, "Game loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading game: " + e.getMessage());
        }
    }
}
