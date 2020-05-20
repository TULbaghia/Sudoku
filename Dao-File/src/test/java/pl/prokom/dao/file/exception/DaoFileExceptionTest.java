package pl.prokom.dao.file.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.dao.api.exception.DaoException;
import pl.prokom.dao.api.model.Dao;
import pl.prokom.dao.file.model.SudokuBoardDaoFactory;
import pl.prokom.model.board.SudokuBoard;
import pl.prokom.model.solver.BacktrackingSudokuSolver;
import pl.prokom.model.solver.SudokuSolver;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class DaoFileExceptionTest {
    SudokuBoard sudokuBoard;
    SudokuSolver<SudokuBoard> sudokuSolver;
    Dao<SudokuBoard> fileSudokuBoardDao;
    String testPath;

    @BeforeEach
    public void setUp() throws URISyntaxException {
        sudokuSolver = new BacktrackingSudokuSolver();
        sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.solveGame();

        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        Path path = Paths.get(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
        testPath = path.toString();
        fileSudokuBoardDao = factory.getFileDao(testPath + "test.txt");
    }

    /**
     * Case description:
     * - trying to read from illegal file throws DaoFileException
     * - trying to write to illegal file throws DaoFileException
     */
    @Test
    public void exceptionsTest() throws DaoException, NoSuchFieldException, IllegalAccessException {
        fileSudokuBoardDao.write(sudokuBoard);
        Field field = fileSudokuBoardDao.getClass().getDeclaredField("fileName");
        field.setAccessible(true);
        field.set(fileSudokuBoardDao, "/");
        assertThrows(DaoFileException.class, () -> fileSudokuBoardDao.read());
        assertThrows(DaoFileException.class, () -> fileSudokuBoardDao.write(sudokuBoard));
    }

    @Test
    public void exceptionsTestCase() {
        assertThrows(DaoFileException.class, () -> {
            throw new DaoFileException();
        });
        assertThrows(DaoFileException.class, () -> {
            throw new DaoFileException("TEST");
        });
        assertThrows(DaoFileException.class, () -> {
            throw new DaoFileException(new NullPointerException());
        });
        assertThrows(DaoFileException.class, () -> {
            throw new DaoFileException("TEST", new NullPointerException());
        });
    }

}