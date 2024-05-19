package pieces;

import board.Board;
import board.Square;
import java.io.Serializable;

public class Queen extends Piece implements Serializable {
    private static final long serialVersionUID = 1L;

    public Queen(String color) {
        super(color);
    }

    // No-argument constructor
    public Queen() {
        this("White");
    }

    @Override
    public boolean movePiece(Square start, Square end, Board board) {
        return (Math.abs(start.getX() - end.getX()) == Math.abs(start.getY() - end.getY())
            || start.getX() == end.getX()
            || start.getY() == end.getY()) && isPathClear(start, end, board);
    }

    @Override
    public String toString() {
        return super.toString() + "Q";
    }
}
