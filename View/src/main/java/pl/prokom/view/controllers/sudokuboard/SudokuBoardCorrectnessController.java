package pl.prokom.view.controllers.sudokuboard;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.prokom.view.adapter.correctness.CorrectnessMode;
import pl.prokom.view.controllers.MainPaneWindowController;
import pl.prokom.view.exception.SudokuBoardDuplicateValuesException;

public class SudokuBoardCorrectnessController {

    private static final Logger logger =
            LoggerFactory.getLogger(SudokuBoardCorrectnessController.class);

    private static String clickedToggleID = null;

    private Map<CorrectnessMode, RadioMenuItem> modesRadioItemMap;

    private MainPaneWindowController mainController;

    @FXML
    private RadioMenuItem rmiSupervisor;

    @FXML
    private RadioMenuItem rmiFreeMode;

    @FXML
    private ToggleGroup correctnessGroup;

    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        this.mainController = mainPaneWindowController;
        setSelectedToggle();
    }

    @FXML
    private void initialize() {
        modesRadioItemMap = new EnumMap<>(CorrectnessMode.class);
        modesRadioItemMap.put(CorrectnessMode.SUPERVISOR, rmiSupervisor);
        modesRadioItemMap.put(CorrectnessMode.FREESTYLE, rmiFreeMode);

        modesRadioItemMap.forEach((x, y) -> y.setOnAction(e -> changeCorrectnessMode(x)));

        correctnessGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioMenuItem newButton = (RadioMenuItem) newToggle;
                logger.info("Changed id from {} to {}", clickedToggleID, newButton.getId());
            }
        });
    }

    public void changeCorrectnessMode(CorrectnessMode mode) {
        Locale locale;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles.interaction", Locale.getDefault());
        Alert alert;
        try {
            mainController.getSudokuGridController().setBoardListenerMode(mode);
            correctnessGroup.getToggles().stream().map(x -> (RadioMenuItem) x).forEach(x -> {
                x.setDisable(false);
                x.setSelected(false);
            });
            RadioMenuItem clickedButton = modesRadioItemMap.getOrDefault(mode, rmiSupervisor);
            clickedButton.setDisable(true);
            clickedButton.setSelected(true);
            clickedToggleID = clickedButton.getId();
            logger.info("Changing Correctness mode to {}", mode.name());
            alert = new Alert(AlertType.INFORMATION);
            alert.setContentText(resourceBundle.getString("correctness.alert.text.ok"));

        } catch (SudokuBoardDuplicateValuesException e) {
            logger.warn(e.getMessage());
            setSelectedToggle();
            alert = new Alert(AlertType.ERROR);
            alert.setContentText(resourceBundle.getString("correctness.alert.text.error"));
        }
        alert.setTitle(resourceBundle.getString("correctness.alert.title"));
        alert.setHeaderText(resourceBundle.getString("correctness.alert.header"));
        alert.showAndWait();
    }

    private void setSelectedToggle() {
        correctnessGroup.getToggles().stream()
                .map(x -> (RadioMenuItem) x)
                .filter(x -> x.getId().equals(clickedToggleID))
                .findFirst()
                .ifPresentOrElse(x -> {
                    x.setSelected(true);
                    x.setDisable(true);
                    logger.info("{} was selected and disabled", clickedToggleID);
                }, () -> {
                    rmiSupervisor.setSelected(true);
                    rmiSupervisor.setDisable(true);
                    logger.info("{} was selected and disabled", rmiSupervisor.getId());
                });
    }
}
