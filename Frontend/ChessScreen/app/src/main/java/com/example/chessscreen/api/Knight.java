package com.example.chessscreen.api;

import org.json.JSONException;

/**
 * Holds all the piece data and movement logic for a Knight. Extends the abstract class Piece.
 */
public class Knight extends Piece {

    public Knight(String COLOR, ENUM_TYPES PIECETYPE){
        super(COLOR, PIECETYPE);
    };


    public void moveTo(Board board, Space startSpace, Space endSpace, String PlayerColor) throws JSONException {
        String PieceColor = startSpace.getPiece().getColor();

        //Can't move opponents pieces
        if(PieceColor != PlayerColor) {
            return;
        }


        //Can't move to space you are already in
        if(startSpace != endSpace) {
            //Horizontal L
            if((endSpace.col == startSpace.col + 2 || endSpace.col == startSpace.col - 2)
                    && (endSpace.row == startSpace.row + 1 || endSpace.row == startSpace.row - 1)) {
                //New space gets that piece data
                endSpace.piece = startSpace.getPiece();

                //Old space set to new
                startSpace.piece = null;
            }
            //Vertical L
            if((endSpace.row == startSpace.row + 2 || endSpace.row == startSpace.row - 2)
                    && (endSpace.col == startSpace.col + 1 || endSpace.col == startSpace.col - 1)) {
                //New space gets that piece data
                endSpace.piece = startSpace.getPiece();

                //Old space set to new
                startSpace.piece = null;
            }
        }
    };


}
