package pieces;

import board.Board;
import board.Square;

public class Rook extends Piece {
	private static final long serialVersionUID = 1L;

	public Rook(String color) {
		super(color);
	}

	// No-argument constructor
	public Rook() {
		this("White");
	}

	@Override
	public boolean movePiece(Square start, Square end, Board board) {
		return (start.getX() == end.getX() || start.getY() == end.getY()) && isMovePathClear(start, end, board);
	}

	@Override
	public String toString() {
		return super.toString() + "R";
	}
}
