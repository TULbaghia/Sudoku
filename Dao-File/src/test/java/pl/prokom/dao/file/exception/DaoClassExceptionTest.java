package pl.prokom.dao.file.exception;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.dao.api.exception.DaoException;
import pl.prokom.dao.api.model.Dao;
import pl.prokom.dao.file.model.SudokuBoardDaoFactory;
import pl.prokom.model.board.SudokuBoard;
import pl.prokom.model.solver.BacktrackingSudokuSolver;
import pl.prokom.model.solver.SudokuSolver;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class DaoClassExceptionTest {
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
     * - trying to read from file without serialized class. (Should throw DaoClassException.)
     */
    @Test
    public void exceptionReadClassNotFoundTest() throws DaoException, NoSuchFieldException, IllegalAccessException {
        fileSudokuBoardDao.write(sudokuBoard);
        try (RandomAccessFile fh = new RandomAccessFile(testPath + "test.txt", "rw")) {
            fh.seek(30L);
            fh.write('Y');
        } catch (IOException e) {
            e.printStackTrace();
        }
        Field field = fileSudokuBoardDao.getClass().getDeclaredField("fileName");
        field.setAccessible(true);
        field.set(fileSudokuBoardDao, testPath + "test.txt");
        assertThrows(DaoClassException.class, () -> fileSudokuBoardDao.read());
    }



}
