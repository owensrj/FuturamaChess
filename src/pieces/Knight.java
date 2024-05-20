package pieces;

import board.*;


@SuppressWarnings("serial")
public class Knight extends Piece {

    public Knight(String color) {
        super(color);
    }

    // No-argument constructor
    public Knight() {
        this("White");
    }

    @Override
    public boolean movePiece(Square start, Square end, Board board) {
        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());
        return (x == 2 && y == 1) || (x == 1 && y == 2);
    }

    @Override
    public String toString() {
        return super.toString() + "N";
    }
}
