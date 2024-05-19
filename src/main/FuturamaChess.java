package main;

import board.Board;
import controller.ChessGameController;
import ui.ChessGameView;

/**
 * Main entry point for the chess game.
 */
public class FuturamaChess {
    public static void main(String[] args) {
        Board model = new Board();
        ChessGameView view = new ChessGameView();
        ChessGameController controller = new ChessGameController(model, view);
    }
}
