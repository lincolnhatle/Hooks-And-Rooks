package com.example.hooks_and_rooks.api;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Holds the data and movement logic for a Bishop. Extends the abstract class Piece.
 */
public class Bishop extends Piece {

    public Bishop(String COLOR, ENUM_TYPES PIECETYPE){
        super(COLOR, PIECETYPE);
    };

    //IMPLEMENT
    public void moveTo(Board board, Space startSpace, Space endSpace, String PlayerColor) throws JSONException {
        String PieceColor = startSpace.getPiece().getColor();

        //REPLACE WITH A TURN COUNTER
        //Can't move opponents pieces
        if(!(PieceColor.equals(PlayerColor))) {
            return;
        }


        //Moving diagonally
        ArrayList<Space> legalMoves = new ArrayList<>();

        //need to catch empty space error (trying to check color on empty space

        //Verify all legal moves
        for(int i = 0; i < 8; i++) {
            //positive slope movement
            try {
                if ((endSpace.row == startSpace.row + i && endSpace.col == startSpace.col - i)
                        && (endSpace.piece == null || endSpace.piece.getColor() != PieceColor)) {
                    //Add to list of possible moves
                    legalMoves.add(board.getSpace(startSpace.row + i, startSpace.col - i));
                }
                if ((endSpace.row == startSpace.row - i && endSpace.col == startSpace.col + i)
                        && (endSpace.piece == null || endSpace.piece.getColor() != PieceColor)) {
                    legalMoves.add(board.getSpace(startSpace.row - i, startSpace.col + i));
                }
                //negative slope movement
                if ((endSpace.row == startSpace.row + i && endSpace.col == startSpace.col + i)
                        && (endSpace.piece == null || endSpace.piece.getColor() != PieceColor)) {
                    legalMoves.add(board.getSpace(startSpace.row + i, startSpace.col + i));
                }
                if ((endSpace.row == startSpace.row - i && endSpace.col == startSpace.col - i)
                        && (endSpace.piece == null || endSpace.piece.getColor() != PieceColor)) {
                    legalMoves.add(board.getSpace(startSpace.row - i, startSpace.col - i));
                }
            } catch (IndexOutOfBoundsException e) {
                continue;
            }
        }

        //For each loop determining whether or not the endspace is one of those moves
        for (Space x : legalMoves) {
            if(endSpace == x) {
                //New space gets that piece data
                endSpace.piece = startSpace.getPiece();

                //Old space set to new
                startSpace.piece = null;
            }
        }
    };
}
