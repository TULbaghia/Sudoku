package pl.prokom.view.controllers.sudokuboard;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.prokom.dao.api.exception.DaoException;
import pl.prokom.dao.api.model.Dao;
import pl.prokom.dao.file.exception.DaoFileException;
import pl.prokom.dao.file.model.SudokuBoardDaoFactory;
import pl.prokom.model.board.SudokuBoard;
import pl.prokom.view.adapter.SudokuBoardAdapter;
import pl.prokom.view.bundles.BundleHelper;
import pl.prokom.view.controllers.MainPaneWindowController;
import pl.prokom.view.menu.AlertBox;

public class SudokuBoardDaoFileController {

    private static final Logger logger =
            LoggerFactory.getLogger(SudokuBoardDaoFileController.class);

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
     * Reference to MainPaneWindowController instance to reach this inside.
     */
    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        logger.trace(BundleHelper.getApplication("initializingParentController"));
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
    public void writeSudokuToFile() {
        try {
            String filePath = fileChooser.showSaveDialog(
                    mainController.getMainPaneWindow().getScene().getWindow()).getAbsolutePath();
            initFileSudokuBoardDao(filePath);
            fileSudokuBoardDao.write(mainController.getSudokuGridController().getSudokuBoard());

            logger.trace(BundleHelper.getApplication("daoFileWriteSuccess"), filePath);
            AlertBox.showAlert(Alert.AlertType.INFORMATION,
                    BundleHelper.getApplication("daoFileAlertTitle"),
                    BundleHelper.getApplication("daoFileAlertHeader"),
                    BundleHelper.getApplication("daoFileAlertWriteContent"));
        } catch (NullPointerException e) {
            logger.warn(BundleHelper.getException("daoFileNotChoosen"), e);
        } catch (DaoException e) {
            logger.error(BundleHelper.getException("daoFileException"), e);
            //throw e;
        }
    }

    /**
     * Deserialize instance od SudokuBoard.
     * Set actual Sudoku difficulty level and initialize deserialized boaard.
     */
    @FXML
    public void readSudokuFromFile() {
        try {
            String filePath = fileChooser.showOpenDialog(
                    mainController.getMainPaneWindow().getScene().getWindow()).getAbsolutePath();
            initFileSudokuBoardDao(filePath);
            SudokuBoardAdapter sudokuBoardDao = (SudokuBoardAdapter) fileSudokuBoardDao.read();

            //Restore difficulty level
            this.mainController.getDifficultyLevelsController()
                    .changeDifficultyLevel(sudokuBoardDao.getSudokuBoardLevel(), false);

            //Restores sudokuBoard settings
            this.mainController.getSudokuGridController()
                    .getSudokuBoard().replaceParametersWith(sudokuBoardDao);

            this.mainController.getCorrectnessController()
                    .changeCorrectnessMode(sudokuBoardDao.getSudokuCorrectnessMode());

            //Refreshes window grid
            this.mainController.getSudokuGridController()
                    .initializeSudokuCellsWith(sudokuBoardDao, false);

            logger.trace(BundleHelper.getApplication("daoFileReadSuccess"), filePath);
            AlertBox.showAlert(Alert.AlertType.INFORMATION,
                    BundleHelper.getApplication("daoFileAlertTitle"),
                    BundleHelper.getApplication("daoFileAlertHeader"),
                    BundleHelper.getApplication("daoFileAlertReadContent"));
        } catch (NullPointerException e) {
            logger.warn(BundleHelper.getException("daoFileNotChoosen"), e);
        } catch (DaoException e) {
            logger.error(BundleHelper.getException("daoFileException"), e);
            //throw e;
        }
    }

}
