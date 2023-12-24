package com.example.hooks_and_rooks.api;

import org.json.JSONException;
import org.json.JSONObject;

public class Move {

    private String moveString;
    private Space startSpace;
    private Space endSpace;
    private boolean check;
    private boolean capture;
    private String player;

    /**
     *  Constructor that creates a move object with move info to send over a websocket.
     *  Soley for the use of websockets, actual piece movement implemented in the pieces.
     * @param STARTSPACE
     * @param ENDSPACE
     * @param PLAYER
     */
    public Move(String PLAYER, Space STARTSPACE, Space ENDSPACE) throws JSONException {
        startSpace = STARTSPACE;
        endSpace = ENDSPACE;
        player = PLAYER;
    };


    /**
     * Returns the piece moved in the sent move.
     * @return
     */
    public Piece getPiece() {
        return this.startSpace.piece;
    }

    /**
     * Returns the startspace of the move.
     * @return
     */
    public Space getStartSpace() {
        return this.startSpace;
    }

    /**
     * Returns the endspace of the move.
     * @return
     */
    public Space getEndSpace() {
        return this.endSpace;
    }

    /**
     * Returns the player of the move.
     * @return
     */
    public String getPlayer() {
        return this.player;
    }



    /**
     * Converts a move object to a string.
     * @return
     */
    public String toString() {

        //CONVERT STARTSPACE AND ENDSPACE TO STRING
        String startSpaceString;
        String endSpaceString;
        String rank; //row value in chess notation
        String file; //column value in chess notation


        //STARTSPACE TO STRING
        if(this.startSpace.getColumn() == 0) {
            file = "A";
        }
        else if(this.startSpace.getColumn() == 1) {
            file = "B";
        }
        else if(this.startSpace.getColumn() == 2) {
            file = "C";
        }
        else if(this.startSpace.getColumn() == 3) {
            file = "D";
        }
        else if(this.startSpace.getColumn() == 4) {
            file = "E";
        }
        else if(this.startSpace.getColumn() == 5) {
            file = "F";
        }
        else if(this.startSpace.getColumn() == 6) {
            file = "G";
        }
        else {
            file = "H";
        }

        if(this.startSpace.getRow() == 0) {
            rank = "8";
        }
        else if(this.startSpace.getRow() == 1) {
            rank = "7";
        }
        else if(this.startSpace.getRow() == 2) {
            rank = "6";
        }
        else if(this.startSpace.getRow() == 3) {
            rank = "5";
        }
        else if(this.startSpace.getRow() == 4) {
            rank = "4";
        }
        else if(this.startSpace.getRow() == 5) {
            rank = "3";
        }
        else if(this.startSpace.getRow() == 6) {
            rank = "2";
        }
        else {
            rank = "1";
        }

        startSpaceString = file + rank; //will produce chess notation(e.g. A8)


        //ENDSPACE TO STRING
        if(this.endSpace.getColumn() == 0) {
            file = "A";
        }
        else if(this.endSpace.getColumn() == 1) {
            file = "B";
        }
        else if(this.endSpace.getColumn() == 2) {
            file = "C";
        }
        else if(this.endSpace.getColumn() == 3) {
            file = "D";
        }
        else if(this.endSpace.getColumn() == 4) {
            file = "E";
        }
        else if(this.endSpace.getColumn() == 5) {
            file = "F";
        }
        else if(this.endSpace.getColumn() == 6) {
            file = "G";
        }
        else {
            file = "H";
        }

        if(this.endSpace.getRow() == 0) {
            rank = "8";
        }
        else if(this.endSpace.getRow() == 1) {
            rank = "7";
        }
        else if(this.endSpace.getRow() == 2) {
            rank = "6";
        }
        else if(this.endSpace.getRow() == 3) {
            rank = "5";
        }
        else if(this.endSpace.getRow() == 4) {
            rank = "4";
        }
        else if(this.endSpace.getRow() == 5) {
            rank = "3";
        }
        else if(this.endSpace.getRow() == 6) {
            rank = "2";
        }
        else {
            rank = "1";
        }

        endSpaceString = file + rank;


        //INITALIZE STRING
        moveString = "1 " + this.player + " " + startSpaceString + " " + endSpaceString;

        return moveString;
    };


}
