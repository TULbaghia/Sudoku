package pl.prokom.view.controllers.sudokuboard;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.prokom.dao.api.exception.DaoException;
import pl.prokom.dao.api.model.Dao;
import pl.prokom.dao.db.exception.*;
import pl.prokom.dao.db.model.*;
import pl.prokom.model.board.SudokuBoard;
import pl.prokom.view.bundles.BundleHelper;
import pl.prokom.view.controllers.MainPaneWindowController;

import java.util.NoSuchElementException;
import java.util.Optional;

public class SudokuBoardDaoDBController {

    /**
     * Logger instance. Logging events of SudokuBoardController class.
     */
    private static final Logger logger = LoggerFactory.getLogger(SudokuBoardController.class);

    /**
     * Reference to MainPaneWindowController instance to reach this.
     */
    MainPaneWindowController mainController;

    /**
     * Database - Jdbc DAO.
     */
    private Dao<SudokuBoard> jdbcSudokuBoardDao;
    /**
     * FileChooser holding file processing.
     */
    private TextInputDialog textInputDialog = new TextInputDialog();

    /**
     * Reference to MainPaneWindowController instance to reach this inside.
     */
    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        logger.trace(BundleHelper.getApplication("initializingParentController"));
        this.mainController = mainPaneWindowController;
    }

    /**
     * Setting required bindings.
     */
    @FXML
    public void initialize(){
       textInputDialog.setHeaderText(BundleHelper.getInteraction("sudokuDatabase.textInputDialog"));
       textInputDialog.getDialogPane()
                .lookupButton(ButtonType.OK).disableProperty()
                .bind(textInputDialog.getEditor().textProperty().isEmpty());
    }

    /**
     * Write state of SudokuBoard instance to a database table.
     * @throws JdbcDaoConnectionException - connection has not been established.
     * @throws JdbcDaoNameException - entered specific board name was incorrect or empty.
     * sudokuBoardName is essentialy the name of new instance record in database.
     */
    @FXML
    public void writeSudokuToDatabase() throws JdbcDaoConnectionException, JdbcDaoNameException {
        textInputDialog.getEditor().setText("");
        JdbcSudokuBoardDaoFactory jdbcSudokuBoardDaoFactory = new JdbcSudokuBoardDaoFactory();
        try {
            Optional<String> sudokuBoardName = textInputDialog.showAndWait();
            jdbcSudokuBoardDaoFactory.getDBDao(sudokuBoardName.get());
        } catch (DaoException e) {
            logger.error(BundleHelper.getException("sudokuDatabase.jdbcDaoConnectionNotEstablished"), e);
            throw new JdbcDaoConnectionException(e);
        } catch (NoSuchElementException e) {
            logger.error(BundleHelper.getException("sudokuDatabase.jdbcDaoNameException"), e);
            throw new JdbcDaoNameException(e);
        }
        logger.trace(BundleHelper.getApplication("sudokuDatabase.connectionSuccess"));

    }

    /**
     * Read state of SudokuBoard instance from a database table.
     */
    @FXML
    public void readSudokuFromDatabase() {

    }
}
