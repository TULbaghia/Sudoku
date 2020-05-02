package pl.prokom.view.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;
import pl.prokom.dao.api.Dao;
import pl.prokom.dao.file.FileSudokuBoardDao;
import pl.prokom.dao.file.SudokuBoardDaoFactory;
import pl.prokom.model.board.SudokuBoard;
import pl.prokom.model.board.SudokuBoardLevel;
import pl.prokom.view.stage.StageCreator;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

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
     * File - DAO (SudokuBoard instances)
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

    @FXML
    public void writeSudokuToFile(){
        String filePath;
        try {
            filePath = fileChooser.showOpenDialog(this.mainController.getMainPaneWindow().getScene().getWindow()).getAbsolutePath();
            SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
            fileSudokuBoardDao = sudokuBoardDaoFactory.getFileDao(filePath);
            fileSudokuBoardDao.write(this.mainController.getSudokuGridController().getSudokuBoard());
            System.out.println("Zapisano do pliku.");
        } catch (NullPointerException e) {
            System.out.println("Niepoprawny pliku.");
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void readSudokuFromFile(){
        String filePath;
        try {
            filePath = fileChooser.showOpenDialog(this.mainController.getMainPaneWindow().getScene().getWindow()).getAbsolutePath();
            SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
            fileSudokuBoardDao = sudokuBoardDaoFactory.getFileDao(filePath);
            this.mainController.getSudokuGridController().setSudokuFromFile(fileSudokuBoardDao.read());

            SudokuBoardLevel sudokuBoardLevel = this.mainController.getSudokuGridController().getBoardCurrentLevel();
            this.mainController.getSudokuGridController().initSudokuCells(sudokuBoardLevel);
        } catch (NullPointerException e) {
            System.out.println("Nie wczytano pliku.");
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void checkCorrectness(){

    }

}
