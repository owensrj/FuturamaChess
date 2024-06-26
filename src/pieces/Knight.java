package pieces;

import board.*;

public class Knight extends Piece {
	private static final long serialVersionUID = 1L;

	public Knight(String color) {
		super(color);
	}

	// No-argument constructor
	public Knight() {
		this("White");
	}

	public boolean movePiece(Square start, Square end, Board board) {
		int x = Math.abs(start.getX() - end.getX());
		int y = Math.abs(start.getY() - end.getY());
		return (x == 2 && y == 1) || (x == 1 && y == 2);
	}

	public String toString() {
		return super.toString() + "N";
	}
}
