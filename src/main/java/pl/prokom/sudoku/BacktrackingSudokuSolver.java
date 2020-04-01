package pl.prokom.sudoku;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Implementation of Backtracking algorithm to solve sudoku.
 */
public class BacktrackingSudokuSolver implements SudokuSolver<SudokuBoard> {

    /**
     * Method to solve sudoku board.
     *
     * @param sudoku SudokuBoard object
     */
    public final void solve(final SudokuBoard sudoku) {
        int squareSize = sudoku.getSquareSize();
        byte[][] helpBoard = new byte[squareSize][squareSize];
        List<Integer> randomValues =
                IntStream.range(1, squareSize + 1).boxed().collect(Collectors.toList());
        Collections.shuffle(randomValues);

        for (int row = 0; row < squareSize; row++) {
            for (int column = 0; column < squareSize; column++) {
                if (sudoku.get(row, column) != 0 && helpBoard[row][column] == 0) {
                    continue;
                }
                boolean isCorrect = false;

                if (helpBoard[row][column] == 0) {
                    helpBoard[row][column] = 1;
                    int rand = helpBoard[row][column];

                    do {
                        if (isAllowed(row, column, randomValues.get(rand - 1), sudoku)) {
                            sudoku.setBoardCell(row, column, randomValues.get(rand - 1));
                            isCorrect = true;
                            break;
                        }
                        rand = rand % squareSize + 1;
                    } while (rand != helpBoard[row][column]);
                } else {
                    int rand = randomValues.indexOf(sudoku.get(row, column)) + 1;
                    rand = rand % squareSize + 1;
                    sudoku.getBoardCell(row, column).reset();

                    while (rand != helpBoard[row][column]) {
                        if (isAllowed(row, column, randomValues.get(rand - 1), sudoku)) {
                            sudoku.setBoardCell(row, column, randomValues.get(rand - 1));
                            isCorrect = true;
                            break;
                        }
                        rand = rand % squareSize + 1;
                    }
                }

                if (!isCorrect) {
                    helpBoard[row][column] = 0;
                    sudoku.getBoardCell(row, column).reset();
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
     * @param sudoku SudokuBoard object
     * @return whether is possible to put number in board
     */
    public boolean isAllowed(final int row, final int column, final int num,
                             final SudokuBoard sudoku) {
        return isAllowedValue(num, sudoku)
                && isAllowedInRow(row, num, sudoku)
                && isAllowedInColumn(column, num, sudoku)
                && isAllowedInMiniSquare(row, column, num, sudoku);
    }


    /**
     * Method to check whether {@code num} is allowed value.
     *
     * @param num    int number you want to put in board
     * @param sudoku SudokuBoard object
     * @return whether num is between min and max
     */
    private boolean isAllowedValue(int num, SudokuBoard sudoku) {
        return 0 < num && sudoku.getSquareSize() >= num;
    }

    /**
     * Check, if number already exist in row.
     *
     * @param row    n-th row of sudoku board (from 0)
     * @param num    number you want to add into board
     * @param sudoku SudokuBoard object
     * @return whether is possible to put number in row
     */
    private boolean isAllowedInRow(final int row, final int num, final SudokuBoard sudoku) {
        for (int i = 0; i < sudoku.getSquareSize(); i++) {
            if (sudoku.get(row, i) == num) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check, if number already exist in row.
     *
     * @param column n-th column of sudoku board (from 0)
     * @param num    number you want to add into board
     * @param sudoku SudokuBoard object
     * @return whether is possible to put number in column
     */
    private boolean isAllowedInColumn(final int column, final int num, final SudokuBoard sudoku) {
        for (int i = 0; i < sudoku.getSquareSize(); i++) {
            if (sudoku.get(i, column) == num) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check, if number already exist in miniSquare.
     *
     * @param row    n-th row of sudoku board (from 0)
     * @param column n-th column of sudoku board (from 0)
     * @param num    number you want to add into board
     * @param sudoku SudokuBoard object
     * @return whether is possible to put number in miniSquare
     */
    private boolean isAllowedInMiniSquare(final int row, final int column, final int num,
                                          final SudokuBoard sudoku) {
        int miniRow = row - row % sudoku.getMiniSquareSize();
        int miniColumn = column - column % sudoku.getMiniSquareSize();

        for (int i = miniRow; i < miniRow + sudoku.getMiniSquareSize(); i++) {
            for (int j = miniColumn; j < miniColumn + sudoku.getMiniSquareSize(); j++) {
                if (sudoku.get(i, j) == num) {
                    return false;
                }
            }
        }
        return true;
    }
}
