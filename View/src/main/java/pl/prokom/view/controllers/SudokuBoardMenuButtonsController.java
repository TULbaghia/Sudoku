package pl.prokom.view.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import pl.prokom.dao.api.exception.DaoException;
import pl.prokom.dao.api.model.Dao;
import pl.prokom.dao.file.exception.DaoFileException;
import pl.prokom.dao.file.model.SudokuBoardDaoFactory;
import pl.prokom.model.board.SudokuBoard;
import pl.prokom.view.adapter.SudokuBoardAdapter;

public class SudokuBoardMenuButtonsController {

    @FXML
    private Button correctnessButton;

    @FXML
    private Button sudokuReadButton;

    @FXML
    private Button sudokuWriteButton;

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
            System.out.println("Zapisano do pliku.");
        } catch (NullPointerException | DaoException e) {
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
        } catch (NullPointerException e) {
            System.err.println("File not choosen");
        }
        catch (DaoException e) {
            throw new DaoFileException("Illegal file access.", e);
        }
    }

    @FXML
    public void checkCorrectness() {

    }

}
