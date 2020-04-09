package pl.prokom.sudoku.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.sudoku.board.SudokuBoard;
import pl.prokom.sudoku.partial.field.SudokuField;
import pl.prokom.sudoku.solver.BacktrackingSudokuSolver;
import pl.prokom.sudoku.solver.SudokuSolver;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//TODO: exceptions tests

public class FileSudokuBoardDaoTest {
    SudokuBoard sudokuBoard;
    SudokuSolver<SudokuBoard> sudokuSolver;
    Dao<SudokuBoard> fileSudokuBoardDao;

    @BeforeEach
    void setUp() {
        sudokuSolver = new BacktrackingSudokuSolver();
        sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.solveGame();

        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        fileSudokuBoardDao = factory.getFileDao("test.txt");
    }

    /**
     * Case description:
     * - serialize instance of SudokuBoard, deserialize that instance, then:
     *  - check for equality,
     *  - check if one is reference to another (if they are the same).
     */
    @Test
    void serializeDeserializeTest() throws IOException, ClassNotFoundException {
        fileSudokuBoardDao.write(sudokuBoard);
        SudokuBoard sudokuDeserialized = fileSudokuBoardDao.read();

        assertEquals(sudokuBoard, sudokuDeserialized);
        assertNotSame(sudokuBoard, sudokuDeserialized);
    }

    /**
     * Case description:
     * - checks if serialized & deserialized conatins of equal values of cells
     * - confirms that there are 2 different instances
     */
    @Test
    void cellValuesAfterSerialization() throws IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
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
                assertEquals(fields1.get(i).get(j), fields2.get(i).get(j));
                assertNotSame(fields1.get(i).get(j), fields2.get(i).get(j));
            }
        }
    }

}
