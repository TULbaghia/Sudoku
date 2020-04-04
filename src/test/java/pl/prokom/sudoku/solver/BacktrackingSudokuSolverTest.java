package pl.prokom.sudoku.solver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.sudoku.board.SudokuBoard;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

//TODO: check timings/(algorithm implementation) for column >= 4
//TODO: implement other sudokuSolver (better testing purpose)

class BacktrackingSudokuSolverTest {

    SudokuBoard sudokuBoard;
    SudokuSolver<SudokuBoard> sudokuSolver;

    @BeforeEach
    void setUp() {
        sudokuSolver = new BacktrackingSudokuSolver();
        sudokuBoard = new SudokuBoard(sudokuSolver);
    }

    /**
     * Case description:
     * - checks if solver properly solves game
     */
    @Test
    void solveTestCase() {
        assertFalse(sudokuBoard.isSolved());
        sudokuSolver.solve(sudokuBoard);
        assertTrue(sudokuBoard.isSolved());
    }

    /**
     * Case description:
     * - checks if solver properly solves game with filled values
     */
    @Test
    void solvePartiallyFilledTestCase() {
        IntStream.range(1, 10).forEach(x -> sudokuBoard.set(0, x - 1, x));
        sudokuBoard.set(1, 0, 9);
        sudokuBoard.set(1, 1, 8);
        sudokuBoard.set(1, 2, 7);

//        sudokuBoard.set(4, 4, 8); //no solution after 1 min / unknown execution time
        sudokuSolver.solve(sudokuBoard);
        assertTrue(sudokuBoard.isSolved());
    }
}