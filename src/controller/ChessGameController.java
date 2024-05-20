package controller;

import board.*;
import pieces.*;
import ui.*;
import ai.*;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * This class manages the game logic for a chess game, including player interactions,
 * game state management, and communication with the chess engine.
 */
public class ChessGameController {
    private Board model; 			// The game board model which tracks the state of the game.
    private ChessGameView view;		// The GUI components for modeling the game 
    private String currentPlayer;	// Tracks the current player ("White" or "Black")
    private Square selectedSquare;	// The currently selected square on the board
    private List<Move> moveHistory; // Array list of all moves made during the game
    private StockfishEngine stockfishEngine;	// The chess engine for computer-controlled moves
    private boolean isSinglePlayer;				// Flag to determine if the game is single player mode (1=single player)
    private ExecutorService executor;			// Multi-thread that executes long-running tasks asynchronously to keep the UI responsive

    /**
     * Constructor to set up the game controller with initial configurations.
     *
     * @param model The game board to be used.
     * @param view The user interface of the game.
     */
    public ChessGameController(Board model, ChessGameView view) {
        this.model = model;
        this.view = view;
        this.currentPlayer = "White"; // White always starts in chess
        this.selectedSquare = null;	  // No square is initially selected
        this.moveHistory = new ArrayList<>(); // Initialize array for the move history list
        this.executor = Executors.newSingleThreadExecutor(); // Sets up and executor to handle the chess engine move
        try { 
        	// Initialize the Stockfish chess engine with the path to the executable
            this.stockfishEngine = new StockfishEngine("G:\\Documents\\School work\\CISC 191 - JAVA 2\\FuturamaChess\\stockfish\\stockfish\\stockfish-windows-x86-64-sse41-popcnt.exe");
        } catch (IOException e) {
            e.printStackTrace(); // Print any errors that occur during initialization
        }
        this.isSinglePlayer = false; // Default to two-player mode
        initView();		// Set up the initial view of the game board
    }

    /**
     * Configures the initial state of the UI, setting up listeners and displaying the initial status.
     */
    public void initView() {
        view.updateBoard(model); // Draw the initial state of the board on the UI
        view.setButtonListener(this::handleButtonClick); // Set up the listener for button clicks
        view.updateStatusLabel("Current turn: " + currentPlayer); // Display whose turn it is
        view.setSaveButtonListener(e -> saveGame());  // Set up the listener for the save game button
        view.setLoadButtonListener(e -> loadGame());  // Set up the listener for the load game button
    }
    /**
     * Switches the game mode to single-player against the chess engine.
     */
    public void setSinglePlayerMode() {
        this.isSinglePlayer = true; // Enables single player mode
        System.out.println("Switched to Single Player Mode"); // Log the mode switch
    }
    
    /**
     * Switches the game mode to local 2-person multiplayer.
     */
    public void setTwoPlayerMode() {
        this.isSinglePlayer = false; // Disables single player mode
        System.out.println("Switched to Two Player Mode"); // Log the mode switch
    }

    /**
     * Processes button clicks on the chessboard, handling piece selection and movement.
     *
     * @param x The x-coordinate of the clicked square (column).
     * @param y The y-coordinate of the clicked square (row).
     */
    public void handleButtonClick(int x, int y) {
        System.out.println("Button clicked at: " + x + ", " + y); // Log which button was clicked
        Square clickedSquare = model.getSquare(x, y);			  // Retrieve the square that was clicked
        
     // Check if a square is already selected to attempt a move
        if (selectedSquare == null) {
        	// No square is selected yet, try to select the square if it has a piece of the current player's color
            if (clickedSquare.getPiece() != null && clickedSquare.getPiece().getColor().equals(currentPlayer)) {
                selectedSquare = clickedSquare;
                System.out.println("Selected piece: " + selectedSquare.getPiece() + " at " + x + ", " + y);
            } else {
                System.out.println("No piece selected or wrong player's piece.");
            }
        } else {
        	// A square is selected, and the player is attempting to move a piece
            Piece piece = selectedSquare.getPiece();
            if (piece != null) {
            	// The move is valid
                System.out.println("Attempting to move piece: " + piece + " from " + selectedSquare + " to " + clickedSquare);  // Record the move
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
    
    /**
     * Executes the AI's best move in a background thread.
     */
    private void performAIMove() {
        String moveList = getMoveList(); // Generate the list of moves made so far
        System.out.println("Generated Move List: " + moveList); // Log the move list
        try {
            String bestMove = stockfishEngine.getBestMove(moveList); // Ask the chess engine for the best move and add it to the move list / update game state
            if (bestMove != null) { // If move by chess engine is found
                SwingUtilities.invokeLater(() -> {
                    applyAIMove(bestMove); // Apply the chess engine's move using the UI thread
                    currentPlayer = "White"; // Switch back to the human player after chess engine move is completed
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

    /**
     * Applies the AI's chosen move on the board.
     *
     * @param bestMove The best move determined by the AI, in UCI format.
     */
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

    /**
     * Generates a string of all moves played in UCI format, suitable for use by the AI.
     *
     * @return A space-separated list of moves in UCI format.
     */
    private String getMoveList() {
        StringBuilder moveList = new StringBuilder();
        for (Move move : moveHistory) {
            moveList.append(move.getUCIString()).append(" ");
        }
        return moveList.toString().trim();
    }
    
    /**
     * Checks if the game is over by determining if either king is missing.
     *
     * @return true if the game is over, false otherwise.
     */
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

    /**
     * Saves the current game state to a .sav file.
     */
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

    /**
     * Loads a game state from a file.
     */
    
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
