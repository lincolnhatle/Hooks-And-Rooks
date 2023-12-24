package com.example.chessscreen.api;

import org.json.JSONException;

/**
 * Holds the data and movement logic for a Rook. Extends the abstract class Piece.
 */
public class Rook extends Piece {

    public Rook(String COLOR, ENUM_TYPES PIECETYPE){
        super(COLOR, PIECETYPE);
    };


    public void moveTo(Board board, Space startSpace, Space endSpace, String PlayerColor) throws JSONException {
        String PieceColor = startSpace.getPiece().getColor();

//        //Can't move opponents pieces
//        if(PieceColor != PlayerColor) {
//            return;
//        }

        //Initializing row and column to check
        Space[] rowArray = new Space[8];
        Space[] colArray = new Space[8];
        rowArray = startSpace.getRowVector(board);
        colArray = startSpace.getColVector(board);

//        //for loop to return legal move endspaces
//        for(int i = 0; i < 8; i++) {
//
//        }

        //Spaces to travel from startSpace
        int distance;

        //Can't just stand still
        if(startSpace != endSpace) {
            //Moving horizontally
            if (startSpace.row == endSpace.row && (endSpace.piece == null || endSpace.piece.getColor() != PieceColor)) {
                //New space gets that piece data
            endSpace.piece = startSpace.getPiece();

            //Old space set to new
            startSpace.piece = null;
            }
            //Moving vertically
            else if (startSpace.col == endSpace.col && (endSpace.piece == null || endSpace.piece.getColor() != PieceColor)) {
            //New space gets that piece data
            endSpace.piece = startSpace.getPiece();

            //Old space set to new
            startSpace.piece = null;
            }
        }

//        //check if endspace matches any legal endspaces
//        if(startSpace != endSpace) {
//            //New space gets that piece data
//            endSpace.piece = startSpace.getPiece();
//
//            //Old space set to new
//            startSpace.piece = null;
//        }
    };
}
