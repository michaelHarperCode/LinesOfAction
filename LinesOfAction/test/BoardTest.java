import com.sun.jdi.LocalVariable;
//Michael Harper, Kyle Monteleone, Nuzhat Hoque

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @org.junit.jupiter.api.Test
    void getNoColor() {
        Board testBoard = new Board();
        assertEquals('n', testBoard.getColor(1, 5));
    }

    @org.junit.jupiter.api.Test
    void getTheColorBlack() {
        Board testBoard = new Board();
        assertEquals('b', testBoard.getColor(1, 0));
    }

    @org.junit.jupiter.api.Test
    void getTheColorWhite() {
        Board testBoard = new Board();
        assertEquals('w', testBoard.getColor(0, 4));
    }

    @org.junit.jupiter.api.Test
    void getDirection() {
    }

    @org.junit.jupiter.api.Test
    void switchColor() {
        Board testBoard = new Board();
        testBoard.switchColor();
        assertEquals('w', testBoard.getPlayer());
    }

    @org.junit.jupiter.api.Test
    void clearLegalMoves() {
        Board testBoard = new Board();
        testBoard.clearLegalMoves();
        assertEquals(false, testBoard.getLegalMoves(3,7));
    }

    @org.junit.jupiter.api.Test
    void countAllPiecesInLine() {
        Board testBoard = new Board();
        Location testDirection = new Location(0, 1);
        Location testPosition = new Location(7,5);
        assertEquals(6, testBoard.countAllPiecesInLine(testPosition, testDirection));
    }

    @org.junit.jupiter.api.Test
    void countAllPiecesInDifferentDirection() {
        Board testBoard = new Board();
        Location testDirection = new Location(1, 0);
        Location testPosition = new Location(7,5);
        assertEquals(2, testBoard.countAllPiecesInLine(testPosition, testDirection));
    }

    @org.junit.jupiter.api.Test
    void initialPieceCount() {
        Board test = new Board();

        int white = test.pieceCount('w');
        int black = test.pieceCount('b');

        assertEquals(12, white);
        assertEquals(12, black);
    }

    @org.junit.jupiter.api.Test
    void otherPieceCount() {
        String boardConfig = "nnnn" +
                "nbbn" +
                "nwbn" +
                "nnnw";
        Board test = new Board(boardConfig,4, 4);

        int white = test.pieceCount('w');
        int black = test.pieceCount('b');

        assertEquals(2, white);
        assertEquals(3, black);
    }

    @org.junit.jupiter.api.Test
    void percolates() {
        String winConfig = "nnnn" +
                "nbbn" +
                "nwbn" +
                "nnnw";
        boolean[][] answer = {
            {false, false, false, false},
            {false, true, true, false},
            {false, false, true, false},
            {false, false, false, false},
        };
        boolean[][] testBoolean = new boolean[4][4];
        Location test = new Location(1,2);
        Board winB = new Board(winConfig, 4, 4);
        winB.percolates(test, testBoolean, 'b');
        assertEquals(answer[1][2], testBoolean[1][2]);
        assertEquals(answer[2][1], testBoolean[2][1]);
        assertEquals(answer[0][0], testBoolean[0][0]);
    }

    @org.junit.jupiter.api.Test
    void hasWon() {
        String winConfig = "nnnn" +
                "nbbn" +
                "nwbn" +
                "nnnw";
        Board winB = new Board(winConfig, 4, 4);
        assertEquals(true, winB.hasWon('b'));
        assertEquals(false, winB.hasWon('w'));
    }
}