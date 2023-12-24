package com.example.chessscreen.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Abstract class extended by all the different chess pieces.
 */
public abstract class Piece {

    //An abstract class or interface that different types of pieces (such as Pawn, Rook, Knight, Bishop, Queen, King)
    // would extend or implement. It would contain shared behaviors and properties for all pieces.
    private String color;
    private ENUM_TYPES pieceType;


    //piece constructor
        public Piece(String COLOR, ENUM_TYPES PIECETYPE){
            color = COLOR;
            pieceType = PIECETYPE;
        };

    /**
     * Abstract method that all pieces extend. Returns ___ if success.
     * @param board
     * @param startSpace
     * @param endSpace
     */
    public abstract void moveTo(Board board, Space startSpace, Space endSpace, String PlayerColor) throws JSONException;

    public String getColor() {
        return this.color;
    }

    public ENUM_TYPES getPieceType() {
        return this.pieceType;
    }



}
