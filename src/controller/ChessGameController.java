package controller;

import board.*;
import pieces.*;
import ui.*;
import ai.*;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChessGameController {
    private Board model;
    private ChessGameView view;
    private String currentPlayer;
    private Square selectedSquare;
    private List<Move> moveHistory;
    private StockfishEngine stockfishEngine;
    private boolean isSinglePlayer;
    private ExecutorService executor;

    public ChessGameController(Board model, ChessGameView view) {
        this.model = model;
        this.view = view;
        this.currentPlayer = "White";
        this.selectedSquare = null;
        this.moveHistory = new ArrayList<>();
        this.executor = Executors.newSingleThreadExecutor();
        try {
            this.stockfishEngine = new StockfishEngine("G:\\Documents\\School work\\CISC 191 - JAVA 2\\FuturamaChess\\stockfish\\stockfish\\stockfish-windows-x86-64-sse41-popcnt.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.isSinglePlayer = false; // Default to two-player mode
        initView();
    }

    public void initView() {
        view.updateBoard(model);
        view.setButtonListener(this::handleButtonClick);
        view.updateStatusLabel("Current turn: " + currentPlayer);
        view.setSaveButtonListener(e -> saveGame());
        view.setLoadButtonListener(e -> loadGame());
    }

    public void setSinglePlayerMode() {
        this.isSinglePlayer = true;
        System.out.println("Switched to Single Player Mode");
    }

    public void setTwoPlayerMode() {
        this.isSinglePlayer = false;
        System.out.println("Switched to Two Player Mode");
    }

    public void handleButtonClick(int x, int y) {
        System.out.println("Button clicked at: " + x + ", " + y);
        Square clickedSquare = model.getSquare(x, y);

        if (selectedSquare == null) {
            if (clickedSquare.getPiece() != null && clickedSquare.getPiece().getColor().equals(currentPlayer)) {
                selectedSquare = clickedSquare;
                System.out.println("Selected piece: " + selectedSquare.getPiece() + " at " + x + ", " + y);
            } else {
                System.out.println("No piece selected or wrong player's piece.");
            }
        } else {
            Piece piece = selectedSquare.getPiece();
            if (piece != null) {
                System.out.println("Attempting to move piece: " + piece + " from " + selectedSquare + " to " + clickedSquare);
                if (piece.movePiece(selectedSquare, clickedSquare, model)) {
                    Piece targetPiece = clickedSquare.getPiece();
                    if (targetPiece == null || !targetPiece.getColor().equals(piece.getColor())) {
                        System.out.println("Move validated for piece: " + piece);
                        moveHistory.add(new Move(selectedSquare, clickedSquare));
                        clickedSquare.setPiece(piece);
                        selectedSquare.setPiece(null);
                        selectedSquare = null;
                        currentPlayer = currentPlayer.equals("White") ? "Black" : "White";
                        view.updateStatusLabel("Current turn: " + currentPlayer);
                        view.updateBoard(model);
                        if (isGameOver()) {
                            view.updateStatusLabel("Game Over! Winner: " + currentPlayer);
                        } else if (isSinglePlayer && currentPlayer.equals("Black")) {
                            executor.submit(this::performAIMove);
                        }
                    } else {
                        System.out.println("Invalid move: Cannot capture your own piece.");
                        selectedSquare = null;
                    }
                } else {
                    System.out.println("Invalid move for piece: " + piece);
                    selectedSquare = null;
                }
            }
        }
    }

    private void performAIMove() {
        String moveList = getMoveList();
        System.out.println("Generated Move List: " + moveList);
        try {
            String bestMove = stockfishEngine.getBestMove(moveList);
            if (bestMove != null) {
                SwingUtilities.invokeLater(() -> {
                    applyAIMove(bestMove);
                    currentPlayer = "White";
                    view.updateStatusLabel("Current turn: " + currentPlayer);
                    if (isGameOver()) {
                        view.updateStatusLabel("Game Over! Winner: Black");
                    }
                    view.updateBoard(model);
                });
            } else {
                System.out.println("No best move found by Stockfish.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void applyAIMove(String bestMove) {
        int startX = bestMove.charAt(0) - 'a';
        int startY = 8 - Character.getNumericValue(bestMove.charAt(1));
        int endX = bestMove.charAt(2) - 'a';
        int endY = 8 - Character.getNumericValue(bestMove.charAt(3));

        Square start = model.getSquare(startX, startY);
        Square end = model.getSquare(endX, endY);
        Piece piece = start.getPiece();

        if (piece != null) {
            moveHistory.add(new Move(start, end));
            end.setPiece(piece);
            start.setPiece(null);
        } else {
            System.out.println("Error: Piece not found at starting square.");
        }
    }

    private String getMoveList() {
        StringBuilder moveList = new StringBuilder();
        for (Move move : moveHistory) {
            moveList.append(move.getUCIString()).append(" ");
        }
        return moveList.toString().trim();
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
