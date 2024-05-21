package main;

import board.Board;
import controller.ChessGameController;
import ui.ChessGameView;

import javax.swing.JOptionPane;

/**
 * Main entry point for the chess game.
 */
public class FuturamaChess {
	public static void main(String[] args) {
		// Prompt for game mode
		String[] options = { "Single Player", "Two Player" };
		int selection = JOptionPane.showOptionDialog(null, "Select Game Mode", "Futurama Chess",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		boolean isSinglePlayer = selection == 0;

		// Initialize model, view, and controller
		Board model = new Board();
		ChessGameView view = new ChessGameView();
		ChessGameController controller = new ChessGameController(model, view);

		// Set the game mode based on user selection
		if (isSinglePlayer) {
			controller.setSinglePlayerMode();
		} else {
			controller.setTwoPlayerMode();
		}

		// Pass the controller to the view
		view.setController(controller);
	}
}
