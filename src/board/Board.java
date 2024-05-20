package board;

import pieces.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable {
    private static final long serialVersionUID = 1L;
    private Square[][] squares;

    public Board() {
        squares = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[j][i] = new Square(j, i);
            }
        }
        initPieces();
    }

    public Square getSquare(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            return squares[x][y];
        }
        return null;
    }

    public void initPieces() {
        String[] colors = {"Black", "White"};
        for (int i = 0; i < 2; i++) {
            String color = colors[i];
            int pawnRow = color.equals("White") ? 6 : 1;
            int majorRow = color.equals("White") ? 7 : 0;

            for (int j = 0; j < 8; j++) {
                squares[j][pawnRow].setPiece(new Pawn(color));
            }

            squares[0][majorRow].setPiece(new Rook(color));
            squares[7][majorRow].setPiece(new Rook(color));
            squares[1][majorRow].setPiece(new Knight(color));
            squares[6][majorRow].setPiece(new Knight(color));
            squares[2][majorRow].setPiece(new Bishop(color));
            squares[5][majorRow].setPiece(new Bishop(color));
            squares[3][majorRow].setPiece(new Queen(color));
            squares[4][majorRow].setPiece(new King(color));
        }
    }

    public List<Move> getAllPossibleMoves(String color) {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square start = squares[i][j];
                Piece piece = start.getPiece();
                if (piece != null && piece.getColor().equals(color)) {
                    for (int x = 0; x < 8; x++) {
                        for (int y = 0; y < 8; y++) {
                            Square end = squares[x][y];
                            if (piece.movePiece(start, end, this)) {
                                moves.add(new Move(start, end));
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }

    public void applyMove(Move move) {
        Square start = move.getStart();
        Square end = move.getEnd();
        end.setPiece(start.getPiece());
        start.setPiece(null);
    }

    public void undoMove(Move move) {
        Square start = move.getStart();
        Square end = move.getEnd();
        start.setPiece(end.getPiece());
        end.setPiece(null);
    }
}