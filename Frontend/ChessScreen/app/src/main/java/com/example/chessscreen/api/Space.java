package com.example.chessscreen.api;


import java.util.ArrayList;

public class Space {

    protected int row;
    protected int col;
    //protected String color; //Black/White/None (space color not player color)
    protected Piece piece;


    /**
     * Creates a new space with a piece
     * Might abstract into different constructors for empty and occupied cells.
     */
//    public Space(int ROW, int COL, ENUM_TYPES status, String COLOR) {
    public Space(int ROW, int COL, Piece PIECE) {
        row = ROW;
        col =  COL;
        piece = PIECE;
    };

    /**
     * Creates a new empty space.
     */
    public Space(int ROW, int COL) {
        row = ROW;
        col =  COL;
        piece = null;
    };


    /**
     * Returns piece type of a space.
     * @return
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Returns row of a space.
     * @return
     */
    public int getRow() {
        return this.row;
    };

    /**
     * Returns column of a space.
     * @return
     */
    public int getColumn() {
        return this.col;
    };

    //---------------------------------------------------------------------------------------------------
//HELPER METHODS
//---------------------------------------------------------------------------------------------------

    /**
     * Returns all the neighboring spaces of the piece.
     * @param board
     * @return
     */
    public Space[][] getNeighbors(Board board) {

        Space[][] neighbors = new Space[3][3];

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                try {
                    neighbors[i][j] = board.getSpace(this.row - 1 + i, this.col - 1 + j);
                } catch (IndexOutOfBoundsException e) {
                    neighbors[i][j] = null;
                }
            }
        }

        return neighbors;
    }

    /**
     * Returns an array of space data for a whole row.
     * @param board
     * @return
     */
    public Space[] getRowVector(Board board) {
        Space[] row = new Space[8];

        for(int i = 0; i < 8; i++) {
            row[i] = board.getSpace(this.row, i);
        }
        return row;
    }


    /**
     * Returns an array of space data for a whole column.
     * @param board
     * @return
     */
    public Space[] getColVector(Board board) {
        Space[] col = new Space[8];

        for(int i = 0; i < 8; i++) {
            col[i] = board.getSpace(i, this.col);
        }
        return col;
    }

//    /**
//     * Returns an array of space data for a diagonal.
//     * @param board
//     * @param startSpace
//     * @return
//     */
//    public Space[] getDiagPos(Board board, Space startSpace) {
//        Space[] diag = new Space[8];
//
//        // Determine the size of the diagonal path
//        int arrLength = Math.min(row, Math.min(col, Math.min(8 - row - 1, 8 - col - 1))) + 1;
//
//        // Create and fill the diagonal path array
//        int[] diagonalPath = new int[arrLength];
//        for (int i = 0; i < arrLength; i++) {
//            diag[i] = board.getSpace(row - i, col - i);
//        }
//
//        return diag;
//    }

//    /**
//     * Returns an array of space data for a diagonal.
//     * @param board
//     * @param startSpace
//     * @return
//     */
//    public Space[] getDiagNeg(Board board, Space startSpace) {
//        Space[] diag = new Space[8];
//        int n = 0;
//        int startRow = startSpace.row;
//        int startCol = startSpace.col;
//
//        for(int i = 0; i < 8; i++) {
//            for(int j = 0; j < 8; j++) {
//                if(board.getSpace(i, j) == board.getSpace(startRow - i, startCol - j)) {
//                    diag[n] = board.getSpace(i, j);
//                    n++;
//                }
//                else if(board.getSpace(i, j) == board.getSpace(startRow + i, startCol + j)) {
//                    diag[n] = board.getSpace(i, j);
//                    n++;
//                }
//                else {
//                    continue;
//                }
//            }
//        }
//
//        for(int m = n; m < 8 - n; m++) {
//            diag[m] = null;
//        }
//
//        return diag;
//    }
}
