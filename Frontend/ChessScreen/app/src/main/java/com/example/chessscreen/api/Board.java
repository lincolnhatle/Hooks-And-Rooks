package com.example.chessscreen.api;

public class Board {

    private Space[][] board;
    /**
     * Creates a fresh chess board.
     * an array of Cells
     */
    public Board() {

       board = new Space[8][8];
       int r;
       int c;

        //gets all the empty cells
        for(r = 2; r < 6; r++) { //row loop
            for(c = 0; c < 8; c++) {
                board[r][c] = new Space(r, c);
            }
        }

        //pawns
        for(c = 0; c < 8; c++) {
            board[1][c] = new Space(1, c, new Pawn("BLACK", ENUM_TYPES.PAWN)); //2nd row in
            board[6][c] = new Space(6, c, new Pawn("WHITE", ENUM_TYPES.PAWN)); //2nd row from end
        }

        //Rooks
        board[0][0] = new Space(0, 0, new Rook("BLACK", ENUM_TYPES.ROOK));
        board[0][7] = new Space(0, 7, new Rook("BLACK", ENUM_TYPES.ROOK));
        board[7][0] = new Space(7, 0, new Rook("WHITE", ENUM_TYPES.ROOK));
        board[7][7] = new Space(7, 7, new Rook("WHITE", ENUM_TYPES.ROOK));

        //Knights
        board[0][1] = new Space(0, 1, new Knight("BLACK", ENUM_TYPES.KNIGHT));
        board[0][6] = new Space(0, 6, new Knight("BLACK", ENUM_TYPES.KNIGHT));
        board[7][1] = new Space(7, 1, new Knight("WHITE", ENUM_TYPES.KNIGHT));
        board[7][6] = new Space(7, 6, new Knight("WHITE", ENUM_TYPES.KNIGHT));

        //Bishops
        board[0][2] = new Space(0, 2, new Bishop("BLACK", ENUM_TYPES.BISHOP));
        board[0][5] = new Space(0, 5, new Bishop("BLACK", ENUM_TYPES.BISHOP));
        board[7][2] = new Space(7, 2, new Bishop("WHITE", ENUM_TYPES.BISHOP));
        board[7][5] = new Space(7, 5, new Bishop("WHITE", ENUM_TYPES.BISHOP));

        //Queens
        board[0][3] = new Space(0, 3, new Queen("BLACK", ENUM_TYPES.QUEEN));
        board[7][3] = new Space(7, 3, new Queen("WHITE", ENUM_TYPES.QUEEN));

        //Kings
        board[0][4] = new Space(0, 4, new King("BLACK", ENUM_TYPES.KING));
        board[7][4] = new Space(7, 4, new King("WHITE", ENUM_TYPES.KING));
    };

    /**
     * Returns the entire current board state.
     * @return an array of cells.
     */
    public Board getBoard() {
        return Board.this;
    };

    /**
     * returns the contents of a cell.
     * @return
     */
    public Space getSpace(int r, int c) {
        return board[r][c];
    };

}
