package com.example.hooks_and_rooks.api;

import org.json.JSONException;

/**
 * Holds the data and movement logic for a King. Extends Absract class Piece.
 */
public class King extends Piece {

    public King(String COLOR, ENUM_TYPES PIECETYPE){
        super(COLOR, PIECETYPE);
    };

    public void moveTo(Board board, Space startSpace, Space endSpace, String PlayerColor) throws JSONException {
        String PieceColor = startSpace.getPiece().getColor();

        //REPLACE WITH A TURN COUNTER
        //Can't move opponents pieces
        if(!(PieceColor.equals(PlayerColor))) {
            return;
        }

        //Initialize a 2d array of neighbors to check for movement
        Space[][] neighbors = startSpace.getNeighbors(board);

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                //Can't move to your current space
                if(startSpace != endSpace) {
                    //need to add check condition to both statements
                    //MOVING TO AN EMPTY SPACE
                    try {
                        if (endSpace == neighbors[i][j] && endSpace.piece.getColor() != PieceColor) {
                            //New space gets that piece data
                            endSpace.piece = startSpace.getPiece();

                            //Old space set to new
                            startSpace.piece = null;
                        }
                    } catch (Exception e) { //Catches the ovserver null exception if the space has no piece to getColor() of
                        //New space gets that piece data
                        endSpace.piece = startSpace.getPiece();

                        //Old space set to new
                        startSpace.piece = null;
                    }
                }
            }
        }
    };



    @Override
    public String getColor() {
        return super.getColor();
    }
}
