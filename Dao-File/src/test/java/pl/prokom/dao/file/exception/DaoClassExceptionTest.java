package pl.prokom.dao.file.exception;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.prokom.dao.api.exception.DaoException;
import pl.prokom.dao.api.model.Dao;
import pl.prokom.dao.file.model.SudokuBoardDaoFactory;
import pl.prokom.model.board.SudokuBoard;
import pl.prokom.model.partial.field.SudokuField;
import pl.prokom.model.solver.BacktrackingSudokuSolver;
import pl.prokom.model.solver.SudokuSolver;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DaoClassExceptionTest {
    SudokuBoard sudokuBoard;
    SudokuSolver<SudokuBoard> sudokuSolver;
    Dao<SudokuBoard> fileSudokuBoardDao;
    String testPath;

    @BeforeEach
    public void setUp() {
        sudokuSolver = new BacktrackingSudokuSolver();
        sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.solveGame();

        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        testPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        fileSudokuBoardDao = factory.getFileDao(".//test.txt");
    }

}
