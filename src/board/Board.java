package board;

import pieces.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the chessboard and handles piece positions and
 * movements.
 */
public class Board implements Serializable {
	private static final long serialVersionUID = 1L;
	private Square[][] squares;

	/**
	 * Constructs a new Board and initializes the squares and pieces.
	 */
	public Board() {
		squares = new Square[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares[j][i] = new Square(j, i);
			}
		}
		initPieces();
	}

	/**
	 * Retrieves the square at the specified coordinates.
	 *
	 * @param x The x-coordinate of the square.
	 * @param y The y-coordinate of the square.
	 * @return The square at the specified coordinates, or null if out of bounds.
	 */
	public Square getSquare(int x, int y) {
		if (x >= 0 && x < 8 && y >= 0 && y < 8) {
			return squares[x][y];
		}
		return null;
	}

	/**
	 * Initializes the pieces on the board.
	 */
	public void initPieces() {
		String[] colors = { "Black", "White" };
		for (int i = 0; i < 2; i++) {
			String color = colors[i];
			int pawnRow = color.equals("White") ? 6 : 1;
			int majorRow = color.equals("White") ? 7 : 0;

			// Place pawns
			for (int j = 0; j < 8; j++) {
				squares[j][pawnRow].setPiece(new Pawn(color));
			}

			// Place major pieces
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

	/**
	 * Retrieves all possible moves for pieces of the specified color.
	 *
	 * @param color The color of the pieces to get moves for.
	 * @return A list of all possible moves for the specified color.
	 */
	public List<Move> getAllPossibleMoves(String color) {
		List<Move> moves = new ArrayList<>();
		List<Square> piecesOfColor = getAllSquaresWithColor(color);

		for (Square start : piecesOfColor) {
			Piece piece = start.getPiece();
			List<Move> possibleMoves = getPossibleMovesForPiece(piece, start);
			moves.addAll(possibleMoves);
		}

		return moves;
	}

	/**
	 * Retrieves all squares that contain a piece of the specified color.
	 *
	 * @param color The color of the pieces to find.
	 * @return A list of squares containing pieces of the specified color.
	 */
	private List<Square> getAllSquaresWithColor(String color) {
		List<Square> squaresWithColor = new ArrayList<>();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Square square = squares[i][j];
				Piece piece = square.getPiece();
				if (piece != null && piece.getColor().equals(color)) {
					squaresWithColor.add(square);
				}
			}
		}

		return squaresWithColor;
	}

	/**
	 * Retrieves all possible moves for a given piece from its starting square.
	 *
	 * @param piece The piece to move.
	 * @param start The starting square of the piece.
	 * @return A list of possible moves for the piece.
	 */
	private List<Move> getPossibleMovesForPiece(Piece piece, Square start) {
		List<Move> moves = new ArrayList<>();

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Square end = squares[x][y];
				if (piece.movePiece(start, end, this)) {
					moves.add(new Move(start, end));
				}
			}
		}

		return moves;
	}

	/**
	 * Applies a move on the board.
	 *
	 * @param move The move to apply.
	 */
	public void applyMove(Move move) {
		Square start = move.getStart();
		Square end = move.getEnd();
		end.setPiece(start.getPiece());
		start.setPiece(null);
	}

	/**
	 * Undoes a move on the board.
	 *
	 * @param move The move to undo.
	 */
	public void undoMove(Move move) {
		Square start = move.getStart();
		Square end = move.getEnd();
		start.setPiece(end.getPiece());
		end.setPiece(null);
	}
}