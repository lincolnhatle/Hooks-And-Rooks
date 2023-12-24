package com.example.chessscreen.api;

import java.security.spec.ECField;

/**
 * Class used for enforcing the chess rules. Checking for check or checkmate, stalemates, legal moves, etc.
 */
public class GameLogic {
    //A class responsible for enforcing the rules of chess,
    // such as validating moves, checking for check and checkmate, en passant, castling, pawn promotion, and so on.

    /**
     * If your king is dead game ends. Sends you to end game screen.
     * Should call this method every turn.
     */
    public static void isCheckMate(Board board, String PlayerColor) {

        //Check all spaces for the king of your color
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board.getSpace(i, j).getPiece().getPieceType() == ENUM_TYPES.KING) {
                    if(board.getSpace(i, j).getPiece().getColor() == PlayerColor){
                        //send to end screen
                        System.out.println("you lose");
                    }
                }
            }
        }
    };

    /**
     * Pawn pomotes to a queen if it gets to its end row.
     */
    public static void pawnPromotion(Board board, String PlayerColor) {

        //Checks each row space
        for(int i = 0; i < 8; i++) {
            //WHITE logic
            if (PlayerColor == "WHITE") {
                try {
                    if (board.getSpace(0, i).getPiece().getPieceType() == ENUM_TYPES.PAWN) {
                        if (board.getSpace(0, i).getPiece().getColor() == "WHITE") {
                            //clear space and set to queen
                            board.getSpace(0, i).piece = null;
                            board.getSpace(0, i).piece = new Queen("WHITE", ENUM_TYPES.QUEEN);
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            //BLACK logic
            else {
                try {
                    if (board.getSpace(7, i).getPiece().getPieceType() == ENUM_TYPES.PAWN) {
                        if (board.getSpace(7, i).getPiece().getColor() == "BLACK") {
                            //clear space and set to queen
                            board.getSpace(7, i).piece = null;
                            board.getSpace(7, i).piece = new Queen("BLACK", ENUM_TYPES.QUEEN);
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
    };



}
