package pl.prokom.sudoku;

import java.util.Random;

public class BacktrackingSudokuSolver implements SudokuSolver<SudokuBoard> {

    /**
     * Generates/Fills sudoku board with numbers.
     *
     * @param sudoku, which is SudokuBoard type
     */
    public final void solve(final SudokuBoard sudoku) {

        int squareSize = sudoku.getSquareSize();
        Random random = new Random();
        int[][] helpBoard = new int[squareSize][squareSize];

        for (int row = 0; row < squareSize; row++) {
            for (int column = 0; column < squareSize; column++) {
                if (sudoku.getBoardCell(row,column) != 0 && helpBoard[row][column] == 0) {
                    continue;
                }
                boolean isCorrect = false;

                if (helpBoard[row][column] == 0) {
                    helpBoard[row][column] = random.nextInt(squareSize) + 1;
                    int rand = helpBoard[row][column];

                    do {
                        if (isAllowed(row, column, rand, sudoku)) {
                            sudoku.setBoardCell(row,column,rand);
                            isCorrect = true;
                            break;
                        }
                        rand = rand % squareSize + 1;
                    } while (rand != helpBoard[row][column]);
                } else {
                    int rand = sudoku.getBoardCell(row,column) % squareSize + 1;
                    sudoku.setBoardCell(row,column,0);

                    while (rand != helpBoard[row][column]) {
                        if (isAllowed(row, column, rand, sudoku)) {
                            sudoku.setBoardCell(row,column,rand);
                            isCorrect = true;
                            break;
                        }
                        rand = rand % squareSize + 1;
                    }
                }

                if (!isCorrect) {
                    helpBoard[row][column] = 0;
                    sudoku.setBoardCell(row,column,0);
                    column = (column - 2) % squareSize;
                    if (column < 0) {
                        column += squareSize;
                        row = Math.max(row - 1, 0);
                    }
                }
            }
        }
    }

    /**
     * Method to check whether you can put {@code num} in board or not.
     *
     * @param row    int row to search for duplicates
     * @param column int column to search for duplicates
     * @param num    int number you want to put in board
     * @return boolean
     */
    public boolean isAllowed(final int row, final int column, final int num, final SudokuBoard sudoku) {
        int squareSize = sudoku.getSquareSize();
        int miniSquareSize = sudoku.getMiniSquareSize();

        // Check if number already in row
        for (int i = 0; i < squareSize; i++) {
            if (sudoku.getBoardCell(row,i) == num) {
                return false;
            }
        }

        // Check if number already in column
        for (int i = 0; i < squareSize; i++) {
            if (sudoku.getBoardCell(i,column) == num) {
                return false;
            }
        }

        // Check if number already in mini square
        // Set mini{Row,Column) to top left of mini square
        int miniRow = row - row % miniSquareSize;
        int miniColumn = column - column % miniSquareSize;

        // Iterate through mini square
        for (int i = miniRow; i < miniRow + miniSquareSize; i++) {
            for (int j = miniColumn; j < miniColumn + miniSquareSize; j++) {
                if (sudoku.getBoardCell(i,j) == num) {
                    return false;
                }
            }
        }

        return true;
    }
}
