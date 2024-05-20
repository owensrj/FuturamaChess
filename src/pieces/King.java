package pieces;

import board.*;
import java.io.Serializable;

public class King extends Piece implements Serializable {
    private static final long serialVersionUID = 1L;

    public King(String color) {
        super(color);
    }

    // No-argument constructor
    public King() {
        this("White");
    }

    @Override
    public boolean movePiece(Square start, Square end, Board board) {
        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());
        return x <= 1 && y <= 1;
    }

    @Override
    public String toString() {
        return super.toString() + "K";
    }
}
