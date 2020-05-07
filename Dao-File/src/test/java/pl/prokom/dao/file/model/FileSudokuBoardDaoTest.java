package pl.prokom.dao.file.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.dao.api.exception.DaoException;
import pl.prokom.dao.api.model.Dao;
import pl.prokom.model.board.SudokuBoard;
import pl.prokom.model.partial.field.SudokuField;
import pl.prokom.model.solver.BacktrackingSudokuSolver;
import pl.prokom.model.solver.SudokuSolver;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//TODO: exceptions tests

public class FileSudokuBoardDaoTest {
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
     * - serialize instance of SudokuBoard, deserialize that instance, then:
     * - check for equality,
     * - check if one is reference to another (if they are the same).
     */
    @Test
    public void serializeDeserializeTest() throws Exception {
        fileSudokuBoardDao.write(sudokuBoard);
        SudokuBoard sudokuDeserialized = fileSudokuBoardDao.read();

        Assertions.assertEquals(sudokuBoard, sudokuDeserialized);
        assertNotSame(sudokuBoard, sudokuDeserialized);
    }

    /**
     * Case description:
     * - checks if serialized & deserialized conatins of equal values of cells
     * - confirms that there are 2 different instances
     */
    @Test
    public void cellValuesAfterSerialization() throws DaoException, NoSuchFieldException, IllegalAccessException {
        fileSudokuBoardDao.write(sudokuBoard);
        SudokuBoard sudokuDeserialized = fileSudokuBoardDao.read();
        Field field = sudokuBoard.getClass().getDeclaredField("sudokuFields");
        field.setAccessible(true);

        List<List<SudokuField>> fields1 = (List<List<SudokuField>>) field.get(sudokuBoard);
        List<List<SudokuField>> fields2 = (List<List<SudokuField>>) field.get(sudokuDeserialized);

        for (int i = 0; i < sudokuBoard.getBoardSize(); i++) {
            assertNotSame(sudokuBoard.getColumn(i), sudokuDeserialized.getColumn(i));
            assertNotSame(sudokuBoard.getColumn(i), sudokuDeserialized.getRow(i));
            for (int j = 0; j < sudokuBoard.getBoardSize(); j++) {
                Assertions.assertEquals(fields1.get(i).get(j), fields2.get(i).get(j));
                assertNotSame(fields1.get(i).get(j), fields2.get(i).get(j));
            }
        }
    }

}