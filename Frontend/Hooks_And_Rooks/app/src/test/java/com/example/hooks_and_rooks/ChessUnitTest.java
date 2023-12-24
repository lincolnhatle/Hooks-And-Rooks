package com.example.hooks_and_rooks;

import org.json.JSONException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import androidx.lifecycle.viewmodel.CreationExtras;

                                                                                                                                import com.example.hooks_and_rooks.api.*;


public class ChessUnitTest {

//    @Before
//    void setUp() {
//        Board testboard = new Board();
//    }

    @Test
    public void startBoardIsCorrect() {
        Board testboard = new Board();

        //Rooks
        assertEquals(testboard.getSpace(0,0).getPiece().getPieceType(), ENUM_TYPES.ROOK);
        assertEquals(testboard.getSpace(0,7).getPiece().getPieceType(), ENUM_TYPES.ROOK);
        assertEquals(testboard.getSpace(7,0).getPiece().getPieceType(), ENUM_TYPES.ROOK);
        assertEquals(testboard.getSpace(7,7).getPiece().getPieceType(), ENUM_TYPES.ROOK);

        //Knights
        assertEquals(testboard.getSpace(0,1).getPiece().getPieceType(), ENUM_TYPES.KNIGHT);
        assertEquals(testboard.getSpace(0,6).getPiece().getPieceType(), ENUM_TYPES.KNIGHT);
        assertEquals(testboard.getSpace(7,1).getPiece().getPieceType(), ENUM_TYPES.KNIGHT);
        assertEquals(testboard.getSpace(7,6).getPiece().getPieceType(), ENUM_TYPES.KNIGHT);

        //Bishops
        assertEquals(testboard.getSpace(0,2).getPiece().getPieceType(), ENUM_TYPES.BISHOP);
        assertEquals(testboard.getSpace(0,5).getPiece().getPieceType(), ENUM_TYPES.BISHOP);
        assertEquals(testboard.getSpace(7,2).getPiece().getPieceType(), ENUM_TYPES.BISHOP);
        assertEquals(testboard.getSpace(7,5).getPiece().getPieceType(), ENUM_TYPES.BISHOP);

        //Queens
        assertEquals(testboard.getSpace(0,3).getPiece().getPieceType(), ENUM_TYPES.QUEEN);
        assertEquals(testboard.getSpace(0,3).getPiece().getPieceType(), ENUM_TYPES.QUEEN);

        //Kings
        assertEquals(testboard.getSpace(0,4).getPiece().getPieceType(), ENUM_TYPES.KING);
        assertEquals(testboard.getSpace(0,4).getPiece().getPieceType(), ENUM_TYPES.KING);

        for(int i = 0; i < 8; i++) {
            assertEquals(testboard.getSpace(1, i).getPiece().getPieceType(), ENUM_TYPES.PAWN);
            assertEquals(testboard.getSpace(6, i).getPiece().getPieceType(), ENUM_TYPES.PAWN);
        }
    }

    @Test
    public void moveRook() throws JSONException {
        Board testboard = new Board();
        Piece testPiece;

        testPiece = testboard.getSpace(0,0).getPiece();

        //Illegal movement
        testPiece.moveTo(testboard, testboard.getSpace(0,0), testboard.getSpace(0,1), "BLACK");
        assertNotEquals(testboard.getSpace(0,1).getPiece(), testPiece);

        testPiece.moveTo(testboard, testboard.getSpace(0,0), testboard.getSpace(1,0), "BLACK");
        assertNotEquals(testboard.getSpace(1,0).getPiece(), testPiece);

        testPiece.moveTo(testboard, testboard.getSpace(0,0), testboard.getSpace(1,1), "BLACK");
        assertNotEquals(testboard.getSpace(1,1).getPiece(), testPiece);

        //Legal movement
        testPiece.moveTo(testboard, testboard.getSpace(0,0), testboard.getSpace(4,0), "BLACK");
        assertEquals(testboard.getSpace(4,0).getPiece(), testPiece);
    }

//    @Test
//    public void moveKnight() throws JSONException {
//        Board testboard = new Board();
//        Piece testPiece;
//
//        testPiece = testboard.getSpace(0,1).getPiece();
//
//        //Illegal Moves
//        testPiece.moveTo(testboard, testboard.getSpace(0,1), testboard.getSpace(1,3), "BLACK");
//        assertNotEquals(testboard.getSpace(1,3).getPiece(), testPiece);
//
//        //Legal Moves
//        testPiece.moveTo(testboard, testboard.getSpace(0,1), testboard.getSpace(2,0), "BLACK");
//        assertEquals(testboard.getSpace(2,0).getPiece(), testPiece);
//
//        testPiece.moveTo(testboard, testboard.getSpace(2,0), testboard.getSpace(3,3), "BLACK");
//        assertEquals(testboard.getSpace(3,3).getPiece(), testPiece);
//
//        testPiece.moveTo(testboard, testboard.getSpace(3,3), testboard.getSpace(2,5), "BLACK");
//        assertEquals(testboard.getSpace(2,5).getPiece(), testPiece);
//    }
//
//    @Test
//    public void moveBishop() throws JSONException {
//        Board testboard = new Board();
//        Piece testPiece;
//
//        testPiece = testboard.getSpace(0,2).getPiece();
//
//        //Illegal Moves
//        testPiece.moveTo(testboard, testboard.getSpace(0,2), testboard.getSpace(1,1), "BLACK");
//        assertNotEquals(testboard.getSpace(1,1).getPiece(), testPiece);
//
//        testPiece.moveTo(testboard, testboard.getSpace(0,2), testboard.getSpace(1,3), "BLACK");
//        assertNotEquals(testboard.getSpace(1,3).getPiece(), testPiece);
//
//        //Legal Moves
//        testPiece.moveTo(testboard, testboard.getSpace(0,2), testboard.getSpace(2,0), "BLACK");
//        assertEquals(testboard.getSpace(2,0).getPiece(), testPiece);
//
//        testPiece.moveTo(testboard, testboard.getSpace(2,0), testboard.getSpace(4,2), "BLACK");
//        assertEquals(testboard.getSpace(4,2).getPiece(), testPiece);
//    }

    @Test
    public void moveQueen() throws JSONException {
        Board testboard = new Board();
        Piece testPiece;

        testPiece = testboard.getSpace(0,3).getPiece();

        //Illegal moves
        testPiece.moveTo(testboard, testboard.getSpace(0, 3),testboard.getSpace(0,4), "BLACK");
        assertNotEquals(testboard.getSpace(0,4).getPiece(), testPiece);

        //Legal Moves
        testPiece.moveTo(testboard, testboard.getSpace(0, 3),testboard.getSpace(4,3), "BLACK");
        assertEquals(testboard.getSpace(4,3).getPiece(), testPiece);

        testPiece.moveTo(testboard, testboard.getSpace(4, 3),testboard.getSpace(4,0), "BLACK");
        assertEquals(testboard.getSpace(4,0).getPiece(), testPiece);

        testPiece.moveTo(testboard, testboard.getSpace(4, 0),testboard.getSpace(2,2), "BLACK");
        assertEquals(testboard.getSpace(2,2).getPiece(), testPiece);

        testPiece.moveTo(testboard, testboard.getSpace(2, 2),testboard.getSpace(4,4), "BLACK");
        assertEquals(testboard.getSpace(4,4).getPiece(), testPiece);
    }

    @Test
    public void moveKing() throws JSONException {
        Board testboard = new Board();
        Piece testPiece;

        testPiece = testboard.getSpace(0,4).getPiece();

        //Illegal Moves
        testPiece.moveTo(testboard, testboard.getSpace(0, 4), testboard.getSpace(1, 4), "BLACK");
        assertNotEquals(testboard.getSpace(1, 4).getPiece(), testPiece);

        testPiece.moveTo(testboard, testboard.getSpace(0, 4), testboard.getSpace(0, 2), "BLACK");
        assertNotEquals(testboard.getSpace(0, 2).getPiece(), testPiece);

        //Legal Moves
        //move Pawn in front first
        Piece testPawn = testboard.getSpace(1, 4).getPiece();
        testPawn.moveTo(testboard, testboard.getSpace(1, 4), testboard.getSpace(2, 4), "BLACK");

        testPiece.moveTo(testboard, testboard.getSpace(0, 4), testboard.getSpace(1, 4), "BLACK");
        assertEquals(testboard.getSpace(1, 4).getPiece(), testPiece);

        testPiece.moveTo(testboard, testboard.getSpace(1, 4), testboard.getSpace(2, 5), "BLACK");
        assertEquals(testboard.getSpace(2, 5).getPiece(), testPiece);

        testPiece.moveTo(testboard, testboard.getSpace(2, 5), testboard.getSpace(3, 4), "BLACK");
        assertEquals(testboard.getSpace(3, 4).getPiece(), testPiece);
    }

    @Test
    public void movePawn() throws JSONException {
        Board testboard = new Board();
        Piece testPiece;

        testPiece = testboard.getSpace(1,0).getPiece();

        //Move forward 2
        testPiece.moveTo(testboard, testboard.getSpace(1, 0), testboard.getSpace(3, 0), "BLACK");
        assertEquals(testboard.getSpace(3,0).getPiece(), testPiece);

        //Move forward 1
        testPiece.moveTo(testboard, testboard.getSpace(3, 0), testboard.getSpace(4, 0), "BLACK");
        assertEquals(testboard.getSpace(4,0).getPiece(), testPiece);

        //Illegal move diagonal
        testPiece.moveTo(testboard, testboard.getSpace(4, 0), testboard.getSpace(5, 1), "BLACK");
        assertNotEquals(testboard.getSpace(5,1).getPiece(), testPiece);

        //move forward and take
        testPiece.moveTo(testboard, testboard.getSpace(4, 0), testboard.getSpace(5, 0), "BLACK");
        testPiece.moveTo(testboard, testboard.getSpace(5, 0), testboard.getSpace(6, 1), "BLACK");
        assertEquals(testboard.getSpace(6,1).getPiece(), testPiece);
    }



}
