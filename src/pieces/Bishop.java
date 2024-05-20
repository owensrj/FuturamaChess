package pieces;

import board.*;
import java.io.Serializable;

public class Bishop extends Piece implements Serializable {
    private static final long serialVersionUID = 1L;

    public Bishop(String color) {
        super(color);
    }

    // No-argument constructor
    public Bishop() {
        this("White");
    }

    @Override
    public boolean movePiece(Square start, Square end, Board board) {
        return Math.abs(start.getX() - end.getX()) == Math.abs(start.getY() - end.getY()) 
            && isPathClear(start, end, board);
    }

    @Override
    public String toString() {
        return super.toString() + "B";
    }
}
