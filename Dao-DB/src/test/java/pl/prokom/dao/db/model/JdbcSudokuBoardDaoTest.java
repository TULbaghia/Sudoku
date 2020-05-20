package pl.prokom.dao.db.model;

import org.junit.jupiter.api.BeforeEach;
import pl.prokom.model.board.SudokuBoard;
import pl.prokom.model.solver.BacktrackingSudokuSolver;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcSudokuBoardDaoTest {
    SudokuBoard sudokuBoard;

    @BeforeEach
    public void setUp() {
        sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    }

}
