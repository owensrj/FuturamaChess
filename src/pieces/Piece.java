package pieces;

import board.Board;
import board.Square;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Piece implements Serializable {
	private static final long serialVersionUID = 1L;
	private String color;

	public Piece(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public abstract boolean movePiece(Square start, Square end, Board board);

	@Override
	public String toString() {
		return color.charAt(0) + "";
	}

	public boolean isMovePathClear(Square start, Square end, Board board) {
		if (isSameColorPieceAtDestination(start, end, board)) {
			return false; // Path is not clear if there is a same color piece at the end
		}

		if (this instanceof Knight) {
			return true; // Knights jump over pieces, so path check is irrelevant
		}

		int stepX = calculateStepIncrement(start.getX(), end.getX());
		int stepY = calculateStepIncrement(start.getY(), end.getY());

		return checkPathForObstructions(start, end, board, stepX, stepY);
	}

	/**
	 * Checks if there is a piece of the same color at the end square.
	 *
	 * @param start The starting square.
	 * @param end   The ending square.
	 * @param board The board.
	 * @return true if there is a same color piece at the end square, false
	 *         otherwise.
	 */
	private boolean isSameColorPieceAtDestination(Square start, Square end, Board board) {
		Piece endPiece = board.getSquare(end.getX(), end.getY()).getPiece();
		return endPiece != null && endPiece.getColor() == this.getColor();
	}

	/**
	 * Calculates the step increment for traversal.
	 *
	 * @param start The starting coordinate.
	 * @param end   The ending coordinate.
	 * @return The step increment.
	 */
	private int calculateStepIncrement(int start, int end) {
		return Integer.compare(end, start);
	}

	/**
	 * Checks if the path between the start and end squares is clear of
	 * obstructions.
	 *
	 * @param start The starting square.
	 * @param end   The ending square.
	 * @param board The board.
	 * @param stepX The step increment for the x-coordinate.
	 * @param stepY The step increment for the y-coordinate.
	 * @return true if the path is unobstructed, false otherwise.
	 */
	private boolean checkPathForObstructions(Square start, Square end, Board board, int stepX, int stepY) {
		int currentX = start.getX() + stepX;
		int currentY = start.getY() + stepY;

		while (currentX != end.getX() || currentY != end.getY()) {
			if (board.getSquare(currentX, currentY).getPiece() != null) {
				return false; // There is a piece blocking the path
			}
			currentX += stepX;
			currentY += stepY;
		}

		return true; // Path is clear
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	}
}
