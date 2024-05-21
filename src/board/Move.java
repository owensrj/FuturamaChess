package board;

import java.io.Serializable;

/**
 * This class controls a move in the game. It encapsulates the starting and
 * ending squares of the move.
 */
public class Move implements Serializable {
	private static final long serialVersionUID = 1L;
	private final Square start;
	private final Square end;

	/**
	 * Constructs a new Move.
	 *
	 * @param start The starting square of the move.
	 * @param end   The ending square of the move.
	 */
	public Move(Square start, Square end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * Retrieves the starting square of the move.
	 *
	 * @return The starting square.
	 */
	public Square getStart() {
		return start;
	}

	/**
	 * Retrieves the ending square of the move.
	 *
	 * @return The ending square.
	 */
	public Square getEnd() {
		return end;
	}

	/**
	 * Retrieves the x-coordinate of the starting square.
	 *
	 * @return The x-coordinate of the starting square.
	 */
	public int getStartX() {
		return start.getX();
	}

	/**
	 * Retrieves the y-coordinate of the starting square.
	 *
	 * @return The y-coordinate of the starting square.
	 */
	public int getStartY() {
		return start.getY();
	}

	/**
	 * Retrieves the x-coordinate of the ending square.
	 *
	 * @return The x-coordinate of the ending square.
	 */
	public int getEndX() {
		return end.getX();
	}

	/**
	 * Retrieves the y-coordinate of the ending square.
	 *
	 * @return The y-coordinate of the ending square.
	 */
	public int getEndY() {
		return end.getY();
	}

	/**
	 * Retrieves the move in UCI (Universal Chess Interface) format.
	 *
	 * @return The UCI string of the move.
	 */
	public String getUCIString() {
		return squareToUCI(start) + squareToUCI(end);
	}

	/**
	 * Converts a square to its UCI representation. @credit to chess.com for method
	 *
	 * @param square The square to convert.
	 * @return The UCI string of the square.
	 */
	private String squareToUCI(Square square) {
		char file = (char) ('a' + square.getX());
		int rank = 8 - square.getY();
		return "" + file + rank;
	}

	/**
	 * Returns a string representation of the move.
	 *
	 * @return A string representing the move.
	 */
	public String toString() {
		return "Move from " + start + " to " + end;
	}
}
