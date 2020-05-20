package pl.prokom.view.controllers.sudokuboard;

import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.prokom.dao.api.model.Dao;
import pl.prokom.dao.db.exception.JdbcDaoConnectionException;
import pl.prokom.dao.db.exception.JdbcDaoQueryException;
import pl.prokom.dao.db.model.JdbcSudokuBoardDaoFactory;
import pl.prokom.model.board.SudokuBoard;
import pl.prokom.view.adapter.SudokuBoardAdapter;
import pl.prokom.view.bundles.BundleHelper;
import pl.prokom.view.controllers.MainPaneWindowController;
import pl.prokom.view.menu.AlertBox;

public class SudokuBoardDaoDbController {

    /**
     * Logger instance. Logging events of SudokuBoardDaoDBController class.
     */
    private static final Logger logger = LoggerFactory.getLogger(SudokuBoardDaoDbController.class);

    /**
     * Reference to MainPaneWindowController instance to reach this.
     */
    MainPaneWindowController mainController;

    /**
     * TextInputDialog holding DAO - DB features.
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
     * Setting initial state of TextInputDialog instance.
     * ContentText, Title, HeaderText are being adjusted.
     * Binding disableProperty of OK button -> textProperty.
     */
    @FXML
    public void initialize() {
        textInputDialog.setContentText(
                BundleHelper.getInteraction("sudokuDatabase.textInputDialogContent"));
        textInputDialog.setTitle(
                BundleHelper.getInteraction("sudokuDatabase.textInputDialogTitle"));
        textInputDialog.getDialogPane()
                .lookupButton(ButtonType.OK).disableProperty()
                .bind(textInputDialog.getEditor().textProperty().isEmpty());
    }

    /**
     * Write state of SudokuBoard instance to a database table.
     * throws JdbcDaoConnectionException - connection has not been established.
     * throws JdbcDaoQueryException - incorrect query.
     * sudokuBoardName is essentialy the name of new instance record in database.
     */
    @FXML
    public void writeSudokuToDatabase() {
        textInputDialog.getEditor().setText("");
        textInputDialog.setHeaderText(
                BundleHelper.getInteraction("sudokuDatabase.textInputDialogHeaderWrite"));
        JdbcSudokuBoardDaoFactory jdbcSudokuBoardDaoFactory = new JdbcSudokuBoardDaoFactory();
        Optional<String> sudokuBoardName = textInputDialog.showAndWait();
        try (Dao<SudokuBoard> jdbcSudokuBoardDao =
                     jdbcSudokuBoardDaoFactory.getDbDao(sudokuBoardName.get());) {
            jdbcSudokuBoardDao.write(mainController.getSudokuGridController().getSudokuBoard());

            logger.trace(BundleHelper.getApplication("sudokuDatabase.connectionSuccess"));
            AlertBox.showAlert(Alert.AlertType.INFORMATION,
                    BundleHelper.getApplication("sudokuDatabase.daoDBAlertTitle"),
                    BundleHelper.getApplication("sudokuDatabase.daoDBAlertHeader"),
                    BundleHelper.getApplication("sudokuDatabase.daoDBAlertWriteContent"));

        } catch (JdbcDaoConnectionException e) {
            logger.error(
                    BundleHelper.getException("sudokuDatabase.jdbcDaoConnectionNotEstablished"), e);
            //throw e;
        } catch (JdbcDaoQueryException e) {
            logger.warn(BundleHelper.getException("sudokuDatabase.jdbcDaoQueryException"), e);
            //throw e;
        } catch (Exception e) {
            logger.warn(BundleHelper.getException("sudokuDatabase.jdbcDaoException"), e);
        }
    }

    /**
     * Read state of SudokuBoard instance from a database table.
     * throws JdbcDaoQueryException - incorrect query.
     * throws JdbcDaoConnectionException - connection has not been established.
     * sudokuBoardName is essentialy the name of new instance record in database.
     */
    @FXML
    public void readSudokuFromDatabase() {
        textInputDialog.getEditor().setText("");
        textInputDialog.setHeaderText(
                BundleHelper.getInteraction("sudokuDatabase.textInputDialogHeaderRead"));
        JdbcSudokuBoardDaoFactory jdbcSudokuBoardDaoFactory = new JdbcSudokuBoardDaoFactory();
        Optional<String> sudokuBoardName = textInputDialog.showAndWait();
        try (Dao<SudokuBoard> jdbcSudokuBoardDao =
                     jdbcSudokuBoardDaoFactory.getDbDao(sudokuBoardName.get());) {
            SudokuBoardAdapter sudokuBoardDao = (SudokuBoardAdapter) jdbcSudokuBoardDao.read();

            this.mainController.getDifficultyLevelsController()
                    .changeDifficultyLevel(sudokuBoardDao.getSudokuBoardLevel(), false);

            this.mainController.getSudokuGridController()
                    .getSudokuBoard().replaceParametersWith(sudokuBoardDao);

            this.mainController.getCorrectnessController()
                    .changeCorrectnessMode(sudokuBoardDao.getSudokuCorrectnessMode());

            this.mainController.getSudokuGridController()
                    .initializeSudokuCellsWith(sudokuBoardDao, false);
            logger.trace(BundleHelper.getApplication("sudokuDatabase.connectionSuccess"));
            AlertBox.showAlert(Alert.AlertType.INFORMATION,
                    BundleHelper.getApplication("sudokuDatabase.daoDBAlertTitle"),
                    BundleHelper.getApplication("sudokuDatabase.daoDBAlertHeader"),
                    BundleHelper.getApplication("sudokuDatabase.daoDBAlertReadContent"));

        } catch (JdbcDaoConnectionException e) {
            logger.error(
                    BundleHelper.getException("sudokuDatabase.jdbcDaoConnectionNotEstablished"), e);
            //throw e;
        } catch (JdbcDaoQueryException e) {
            logger.warn(BundleHelper.getException("sudokuDatabase.jdbcDaoQueryException"), e);
            //throw e;
        } catch (NullPointerException e) {
            logger.warn(BundleHelper.getException("sudokuDatabase.jdbcDaoNameException"), e);
        } catch (Exception e) {
            logger.warn(BundleHelper.getException("sudokuDatabase.jdbcDaoException"), e);
        }
    }
}
