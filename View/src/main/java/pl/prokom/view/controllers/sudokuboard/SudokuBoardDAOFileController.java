package pl.prokom.view.controllers.sudokuboard;

import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.prokom.dao.api.exception.DaoException;
import pl.prokom.dao.api.model.Dao;
import pl.prokom.dao.file.exception.DaoFileException;
import pl.prokom.dao.file.model.SudokuBoardDaoFactory;
import pl.prokom.model.board.SudokuBoard;
import pl.prokom.view.adapter.SudokuBoardAdapter;
import pl.prokom.view.controllers.MainPaneWindowController;

public class SudokuBoardDAOFileController {

    private static final Logger logger =
            LoggerFactory.getLogger(SudokuBoardDAOFileController.class);

    /**
     * Reference to MainPaneWindowController instance to reach this.
     */
    private MainPaneWindowController mainController;

    /**
     * FileChooser holding file processing.
     */
    private FileChooser fileChooser = new FileChooser();

    /**
     * File - DAO (SudokuBoard instances).
     */
    private Dao<SudokuBoard> fileSudokuBoardDao;

    /**
     * Factory of fileSudokuBoardDao instances, needed to obtain SudokuBoard instance.
     */
    SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();

    public SudokuBoardDAOFileController() {
    }

    /**
     * Reference to MainPaneWindowController instance to reach this inside.
     */
    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        this.mainController = mainPaneWindowController;
    }

    /**
     * Create valid fileSudokuBoardDao instance.
     */
    private void initFileSudokuBoardDao(String filePath) {
        SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
        fileSudokuBoardDao = sudokuBoardDaoFactory.getFileDao(filePath);
    }

    /**
     * Serialize instance of SudokuBoard to a valid file.
     */
    @FXML
    public void writeSudokuToFile() throws DaoFileException {
        String filePath;
        try {
            filePath = fileChooser.showSaveDialog(
                    mainController.getMainPaneWindow().getScene().getWindow()).getAbsolutePath();
            initFileSudokuBoardDao(filePath);
            fileSudokuBoardDao.write(mainController.getSudokuGridController().getSudokuBoard());
            logger.trace("Saved sudoku to file at {}", filePath);
        } catch (NullPointerException e) {
            logger.warn("Unknown sudoku output file");
        } catch (DaoException e) {
            throw new DaoFileException("Illegal file access.", e);
        }
    }

    /**
     * Deserialize instance od SudokuBoard.
     * Set actual Sudoku difficulty level and initialize deserialized boaard.
     */
    @FXML
    public void readSudokuFromFile() throws DaoFileException {
        String filePath;
        try {
            filePath = fileChooser.showOpenDialog(
                    mainController.getMainPaneWindow().getScene().getWindow()).getAbsolutePath();
            initFileSudokuBoardDao(filePath);
            SudokuBoardAdapter sudokuBoardDAO = (SudokuBoardAdapter) fileSudokuBoardDao.read();

            //Restore difficulty level
            this.mainController.getDifficultyLevelsController()
                    .changeDifficultyLevel(sudokuBoardDAO.getSudokuBoardLevel(), false);

            //Restores sudokuBoard settings
            this.mainController.getSudokuGridController()
                    .getSudokuBoard().replaceParametersWith(sudokuBoardDAO);

            //Refreshes window grid
            this.mainController.getSudokuGridController()
                    .initializeSudokuCellsWith(sudokuBoardDAO, false);
            logger.trace("Saved sudoku to file at {}", filePath);
        } catch (NullPointerException e) {
            logger.warn("Unknown sudoku output file");
        } catch (DaoException e) {
            logger.error("Illegal file access {}", Arrays.toString(e.getStackTrace()));
            throw new DaoFileException("Illegal file access.", e);
        }
    }

}
