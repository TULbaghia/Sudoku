package pl.prokom.sudoku.solver;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import pl.prokom.sudoku.board.SudokuBoard;
import pl.prokom.sudoku.exception.IllegalFieldValueException;

//TODO: handle incorrectly filled board the one that have no solution (throw exception)

/**
 * Implementation of Backtracking algorithm to solve sudoku.
 */
public class BacktrackingSudokuSolver implements SudokuSolver<SudokuBoard> {
    /**
     * Iterative backtracking sudoku solver.
     *
     * @param sudoku reference to sudokuBoard you are working on
     */
    @Override
    public void solve(final SudokuBoard sudoku) {
        int squareSize = sudoku.getBoardSize();
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
                        if (checkIfAllowedAndSet(row, column, randomValues.get(rand - 1), sudoku)) {
                            isCorrect = true;
                            break;
                        }
                        rand = rand % squareSize + 1;
                    } while (rand != helpBoard[row][column]);
                } else {
                    int rand = randomValues.indexOf(sudoku.get(row, column)) + 1;
                    rand = rand % squareSize + 1;
                    sudoku.reset(row, column);

                    while (rand != helpBoard[row][column]) {
                        if (checkIfAllowedAndSet(row, column, randomValues.get(rand - 1), sudoku)) {
                            isCorrect = true;
                            break;
                        }
                        rand = rand % squareSize + 1;
                    }
                }

                if (!isCorrect) {
                    helpBoard[row][column] = 0;
                    sudoku.reset(row, column);
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
     * Checks if there is possibility to change value and change value.
     *
     * @param r      row to edit
     * @param c      column to edit
     * @param value  value you want to set
     * @param sudoku reference to board you are working on
     * @return true when value was changed; false otherwise
     */
    private boolean checkIfAllowedAndSet(
            final int r, final int c, final int value, final SudokuBoard sudoku) {
        try {
            sudoku.set(r, c, value);
        } catch (IllegalFieldValueException e) {
            return false;
        }
        return true;
    }
}
