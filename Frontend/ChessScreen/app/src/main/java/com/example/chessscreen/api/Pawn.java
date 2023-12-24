package com.example.chessscreen.api;


import org.json.JSONException;


/**
 * Holds the data and movement logic for a Pawn. Extends the abstract class Piece.
 */
public class Pawn extends Piece {

    public Pawn(String COLOR, ENUM_TYPES PIECETYPE){
        super(COLOR, PIECETYPE);
    };


    /**
     * Moves piece if possible.
     *
     * @param board
     * @param startSpace
     * @param endSpace
     */
    public void moveTo(Board board, Space startSpace, Space endSpace, String PlayerColor) throws JSONException {
        //VARIABLES SET FOR WEBSOCKET
        String PieceColor = startSpace.getPiece().getColor();

        //Can't move opponents pieces
        if(PieceColor != PlayerColor) {
            return;
        }

        //Can't move to your current space
        if(startSpace != endSpace) {
            //WHITE PAWN MOVEMENT
            if(PieceColor == "WHITE") {
                //PAWN LOGIC
                if ((startSpace.getRow() == 6 && endSpace.getRow() <= startSpace.getRow() - 2)
                        && (endSpace.getColumn() == startSpace.getColumn()) && (endSpace.piece == null)) { //PAWN ON ROW 1 (need to fix for blockers)

                    //New space gets that piece data
                    endSpace.piece = startSpace.getPiece();

                    //Old space set to new
                    startSpace.piece = null;
                } else if (endSpace.getRow() == startSpace.getRow() - 1 && (endSpace.getColumn() == startSpace.getColumn())
                        && endSpace.piece == null) { //PAWN ON ANY OTHER ROW
                    //New space gets that piece data
                    endSpace.piece = startSpace.getPiece();

                    //Old space set to new
                    startSpace.piece = null;
                } else if (endSpace.piece.getColor() == "BLACK" && endSpace.getRow() == startSpace.getRow() - 1
                        && (endSpace.getColumn() == startSpace.getColumn() - 1 || endSpace.getColumn() == startSpace.getColumn() + 1)) { //PAWN TAKE
                    //New space gets that piece data
                    endSpace.piece = startSpace.getPiece();

                    //Old space set to new
                    startSpace.piece = null;

                }
            }
            //BLACK PAWN MOVEMENT
            if(PieceColor == "BLACK") {
                //PAWN LOGIC
                if ((startSpace.getRow() == 1 && endSpace.getRow() <= startSpace.getRow() + 2)
                        && (endSpace.getColumn() == startSpace.getColumn()) && (endSpace.piece == null)) { //PAWN ON ROW 1 (need to fix for blockers)

                    //New space gets that piece data
                    endSpace.piece = startSpace.getPiece();

                    //Old space set to new
                    startSpace.piece = null;
                } else if (endSpace.getRow() == startSpace.getRow() + 1 && (endSpace.getColumn() == startSpace.getColumn())
                        && endSpace.piece == null) { //PAWN ON ANY OTHER ROW
                    //New space gets that piece data
                    endSpace.piece = startSpace.getPiece();

                    //Old space set to new
                    startSpace.piece = null;
                } else if (endSpace.piece.getColor() == "WHITE" && endSpace.getRow() == startSpace.getRow() + 1
                        && (endSpace.getColumn() == startSpace.getColumn() + 1 || endSpace.getColumn() == startSpace.getColumn() - 1)) { //PAWN TAKE
                    //New space gets that piece data
                    endSpace.piece = startSpace.getPiece();

                    //Old space set to new
                    startSpace.piece = null;

                }
            }

//            /**
//             * WILL REPLACE WITH GAME LOGIC
//             */
//            //CHECK FOR CHECKS
//            if (endSpace.getRow() == startSpace.getRow() - 1 && (endSpace.getColumn() == startSpace.getColumn() - 1
//                    || endSpace.getColumn() == startSpace.getColumn() + 1) && endSpace.getPiece().getPieceType() == ENUM_TYPES.KING) { //IF KING IS DIAGONAL
//                CHECK = true;
//            }
        }
    };


    @Override
    public String getColor() {
        return super.getColor();
    }

    @Override
    public ENUM_TYPES getPieceType() {
        return super.getPieceType();
    }
}
