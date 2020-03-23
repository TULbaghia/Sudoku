package pl.prokom.sudoku;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        List<Integer> orderedList =
                IntStream.range(1, squareSize + 1).boxed().collect(Collectors.toList());
        Collections.shuffle(orderedList);

        Integer[] randomValues = new Integer[squareSize];
        orderedList.toArray(randomValues);

        for (int row = 0; row < squareSize; row++) {
            for (int column = 0; column < squareSize; column++) {
                if (sudoku.getBoardCell(row, column) != 0 && helpBoard[row][column] == 0) {
                    continue;
                }
                boolean isCorrect = false;

                if (helpBoard[row][column] == 0) {
                    helpBoard[row][column] = random.nextInt(squareSize) + 1;
                    int rand = helpBoard[row][column];

                    do {
                        if (isAllowed(row, column, rand, sudoku)) {
                            sudoku.setBoardCell(row, column, rand);
                            isCorrect = true;
                            break;
                        }
                        rand = rand % squareSize + 1;
                    } while (rand != helpBoard[row][column]);
                } else {
                    int rand = sudoku.getBoardCell(row, column) % squareSize + 1;
                    sudoku.setBoardCell(row, column, 0);

                    while (rand != helpBoard[row][column]) {
                        if (isAllowed(row, column, rand, sudoku)) {
                            sudoku.setBoardCell(row, column, rand);
                            isCorrect = true;
                            break;
                        }
                        rand = rand % squareSize + 1;
                    }
                }

                if (!isCorrect) {
                    helpBoard[row][column] = 0;
                    sudoku.setBoardCell(row, column, 0);
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
        return isAllowedInRow(row, num, sudoku)
                && isAllowedInColumn(column, num, sudoku)
                && isAllowedInMiniSquare(row, column, num, sudoku);
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
            if (sudoku.getBoardCell(row, i) == num) {
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
            if (sudoku.getBoardCell(i, column) == num) {
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
    private boolean isAllowedInMiniSquare(final int row, final int column, final int num, final SudokuBoard sudoku) {
        int miniRow = row - row % sudoku.getMiniSquareSize();
        int miniColumn = column - column % sudoku.getMiniSquareSize();

        for (int i = miniRow; i < miniRow + sudoku.getMiniSquareSize(); i++) {
            for (int j = miniColumn; j < miniColumn + sudoku.getMiniSquareSize(); j++) {
                if (sudoku.getBoardCell(i, j) == num) {
                    return false;
                }
            }
        }
        return true;
    }
}
