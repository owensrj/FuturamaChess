package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import board.Board;
import board.Move;
import board.Square;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;

class BoardTest {

    private Board board;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        // Code to run before all tests
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        // Code to run after all tests
    }

    @BeforeEach
    void setUp() throws Exception {
        board = new Board();
    }

    @Test
    void testPawnMoveForward() {
        // Assuming white pawns start at row 6
        Square startSquare = board.getSquare(0, 6);
        Square endSquare = board.getSquare(0, 5);
        Piece pawn = startSquare.getPiece();

        assertTrue(pawn instanceof Pawn);
        Move move = new Move(startSquare, endSquare);
        board.applyMove(move);

        System.out.println("testPawnMoveForward - Start: " + startSquare + ", End: " + endSquare);
        assertNull(startSquare.getPiece(), "Start square should be empty");
        assertEquals(pawn, endSquare.getPiece(), "End square should have the pawn");
    }

    @Test
    void testPawnInitialTwoSquareMove() {
        // Assuming white pawns start at row 6
        Square startSquare = board.getSquare(0, 6);
        Square endSquare = board.getSquare(0, 4); // Two squares ahead
        Piece pawn = startSquare.getPiece();

        assertTrue(pawn instanceof Pawn);
        Move move = new Move(startSquare, endSquare);
        board.applyMove(move);

        System.out.println("testPawnInitialTwoSquareMove - Start: " + startSquare + ", End: " + endSquare);
        assertNull(startSquare.getPiece(), "Start square should be empty");
        assertEquals(pawn, endSquare.getPiece(), "End square should have the pawn");
    }

    @Test
    void testKnightMove() {
        // Assuming white knights start at (1, 7) and (6, 7)
        Square startSquare = board.getSquare(1, 7);
        Square endSquare = board.getSquare(2, 5); // Valid knight move
        Piece knight = startSquare.getPiece();

        assertTrue(knight instanceof Knight);
        Move move = new Move(startSquare, endSquare);
        board.applyMove(move);

        System.out.println("testKnightMove - Start: " + startSquare + ", End: " + endSquare);
        assertNull(startSquare.getPiece(), "Start square should be empty");
        assertEquals(knight, endSquare.getPiece(), "End square should have the knight");
    }
}
