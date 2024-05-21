package board;

import java.io.Serializable;

public class Move implements Serializable {
	private static final long serialVersionUID = 1L;
	private final Square start;
	private final Square end;

	public Move(Square start, Square end) {
		this.start = start;
		this.end = end;
	}

	public Square getStart() {
		return start;
	}

	public Square getEnd() {
		return end;
	}

	public int getStartX() {
		return start.getX();
	}

	public int getStartY() {
		return start.getY();
	}

	public int getEndX() {
		return end.getX();
	}

	public int getEndY() {
		return end.getY();
	}

	public String getUCIString() {
		return squareToUCI(start) + squareToUCI(end);
	}

	private String squareToUCI(Square square) {
		char file = (char) ('a' + square.getX());
		int rank = 8 - square.getY();
		return "" + file + rank;
	}

	@Override
	public String toString() {
		return "Move from " + start + " to " + end;
	}
}
