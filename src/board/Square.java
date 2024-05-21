package board;

import java.io.Serializable;

import pieces.Piece;

/**
 * This class represents a square on the chessboard. It holds the position and
 * the piece (if any) on the square.
 */
public class Square implements Serializable {
	private static final long serialVersionUID = 1L;
	private int x, y;
	private Piece piece;

	/**
	 * Constructs a new Square at the specified coordinates.
	 *
	 * @param x The x-coordinate of the square.
	 * @param y The y-coordinate of the square.
	 */
	public Square(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Retrieves the x-coordinate of the square.
	 *
	 * @return The x-coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Retrieves the y-coordinate of the square.
	 *
	 * @return The y-coordinate.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the piece on the square.
	 *
	 * @param piece The piece to place on the square.
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	/**
	 * Retrieves the piece on the square.
	 *
	 * @return The piece on the square, or null if the square is empty.
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * Checks if the square is empty.
	 *
	 * @return true if the square is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return piece == null;
	}
}
