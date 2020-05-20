package pl.prokom.dao.db.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.dao.api.exception.DaoException;
import pl.prokom.dao.api.model.Dao;
import pl.prokom.model.board.SudokuBoard;
import pl.prokom.model.solver.BacktrackingSudokuSolver;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcSudokuBoardDaoTest {
    SudokuBoard sudokuBoard;
    JdbcSudokuBoardDaoFactory factory;

    @BeforeEach
    public void setUp() {
        sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        factory = new JdbcSudokuBoardDaoFactory();
    }

    @Test
    public void rwTestCase() throws DaoException {
        String testName = "TEST" + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        Dao<SudokuBoard> sudokuBoardDao = factory.getDbDao(testName);
        assertThrows(DaoException.class, sudokuBoardDao::read);
        sudokuBoardDao.write(sudokuBoard);
        assertDoesNotThrow(sudokuBoardDao::read);
    }
}
