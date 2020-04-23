package pl.prokom.model.solver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.model.board.SudokuBoard;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

//TODO: check timings/(algorithm implementation) for column >= 4
//TODO: implement other sudokuSolver (better testing purpose)

public class BacktrackingSudokuSolverTest {

    SudokuBoard sudokuBoard;
    SudokuSolver<SudokuBoard> sudokuSolver;

    @BeforeEach
    public void setUp() {
        sudokuSolver = new BacktrackingSudokuSolver();
        sudokuBoard = new SudokuBoard(sudokuSolver);
    }

    /**
     * Case description:
     * - checks if solver properly solves game
     */
    @Test
    public void solveTestCase() {
        assertFalse(sudokuBoard.isSolved());
        sudokuSolver.solve(sudokuBoard);
        assertTrue(sudokuBoard.isSolved());
    }

    /**
     * Case description:
     * - checks if solver properly solves game with filled values
     */
    @Test
    public void solvePartiallyFilledTestCase() {
        IntStream.range(1, 10).forEach(x -> sudokuBoard.set(0, x - 1, x));
        sudokuBoard.set(1, 0, 9);
        sudokuBoard.set(1, 1, 8);
        sudokuBoard.set(1, 2, 7);

//        sudokuBoard.set(4, 4, 8); //no solution after 1 min / unknown execution time
        sudokuSolver.solve(sudokuBoard);
        assertTrue(sudokuBoard.isSolved());
    }

    /**
     * Case description:
     * - toString should contains all variables
     */
    @Test
    public void toStringTestCase() {
        String groupToString = sudokuBoard.toString();
        assertTrue(groupToString.contains("BacktrackingSudokuSolver"));
    }

    /**
     * Case description:
     * - object should be equal to self,
     * - object should be equal to created with same parameters
     */
    @Test
    public void equalsTestCase() {
        assertEquals(sudokuSolver, sudokuSolver);

        assertNotEquals(sudokuSolver, null);
        assertNotEquals(sudokuSolver, "NE");

        assertEquals(sudokuSolver, new BacktrackingSudokuSolver());
    }

    /**
     * Case description:
     * - same object should return same hashcode
     * - object with same settings shoud return same hashCode
     * - object with different settings return different hashCode
     */
    @Test
    public void hashCodeTestCase() {
        assertEquals(sudokuSolver.hashCode(), sudokuSolver.hashCode());

        assertNotEquals(sudokuSolver.hashCode(), "NE".hashCode());

        assertEquals(sudokuSolver.hashCode(), new BacktrackingSudokuSolver().hashCode());
    }

    /**
     * Case description:
     * - cloned object should be equal to self
     * - cloned object is not same as original
     */
    @Test
    public void cloneTestCase() {
        assertEquals(sudokuSolver, ((BacktrackingSudokuSolver) sudokuSolver).clone());
        assertNotSame(sudokuSolver, ((BacktrackingSudokuSolver) sudokuSolver).clone());
    }
}