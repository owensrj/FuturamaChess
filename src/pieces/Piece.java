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

    public boolean isPathClear(Square start, Square end, Board board) {
        int startX = start.getX();
        int startY = start.getY();
        int endX = end.getX();
        int endY = end.getY();

        if (this instanceof Knight) {
            return true;
        }

        int stepX = Integer.compare(endX, startX);
        int stepY = Integer.compare(endY, startY);

        int currentX = startX + stepX;
        int currentY = startY + stepY;

        while (currentX != endX || currentY != endY) {
            if (board.getSquare(currentX, currentY).getPiece() != null) {
                return false;
            }
            currentX += stepX;
            currentY += stepY;
        }

        return true;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
}
