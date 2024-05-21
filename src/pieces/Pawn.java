package pieces;

import board.*;

public class Pawn extends Piece {
	private static final long serialVersionUID = 1L;
	private boolean hasMoved;

	public Pawn(String color) {
		super(color);
		this.hasMoved = false;
	}

	// No-argument constructor
	public Pawn() {
		this("White"); // Provide a default color or handle as needed
	}

	public boolean movePiece(Square start, Square end, Board board) {
		int yDiff = getColor().equals("White") ? start.getY() - end.getY() : end.getY() - start.getY();
		int xDiff = Math.abs(start.getX() - end.getX());

		if (xDiff == 0
				&& (yDiff == 1 || (!hasMoved && yDiff == 2 && start.getY() == (getColor().equals("White") ? 6 : 1)))) {
			hasMoved = true;
			return true;
		}

		if (xDiff == 1 && yDiff == 1 && end.getPiece() != null && !end.getPiece().getColor().equals(getColor())) {
			return true;
		}

		return false;
	}

	public String toString() {
		return super.toString() + "P";
	}
}
