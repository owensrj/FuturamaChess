package board;

import java.io.Serializable;

import pieces.Piece;

public class Square implements Serializable {
	private static final long serialVersionUID = 1L;
	private int x, y;
	private Piece piece;

	public Square(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Piece getPiece() {
		return piece;
	}

	public boolean isEmpty() {
		return piece == null;
	}
}
