package pl.prokom.view.controllers.sudokuboard;

import java.util.EnumMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.prokom.view.adapter.correctness.CorrectnessMode;
import pl.prokom.view.bundles.BundleHelper;
import pl.prokom.view.controllers.MainPaneWindowController;
import pl.prokom.view.exception.SudokuBoardDuplicateValuesException;
import pl.prokom.view.menu.AlertBox;

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

    /**
     * Reference to MainPaneWindowController instance to reach this inside.
     */
    public void setParentController(MainPaneWindowController mainPaneWindowController) {
        logger.trace(BundleHelper.getApplication("initializingParentController"));
        this.mainController = mainPaneWindowController;
        setSelectedToggle();
    }

    @FXML
    private void initialize() {
        logger.trace(BundleHelper.getApplication("startingInitialization"));

        modesRadioItemMap = new EnumMap<>(CorrectnessMode.class);
        modesRadioItemMap.put(CorrectnessMode.SUPERVISOR, rmiSupervisor);
        modesRadioItemMap.put(CorrectnessMode.FREESTYLE, rmiFreeMode);

        modesRadioItemMap.forEach((x, y) -> y.setOnAction(e -> changeCorrectnessMode(x)));

        correctnessGroup.selectedToggleProperty().addListener(
                (observable, oldToggle, newToggle) -> {
                    if (newToggle != null) {
                        RadioMenuItem newButton = (RadioMenuItem) newToggle;
                        logger.info(BundleHelper.getApplication("correctnessGroup"),
                                clickedToggleID, newButton.getId());
                    }
                });

        logger.trace(BundleHelper.getApplication("finishedInitialization"));
    }

    /**
     * Change correctness mode that is used during gameplay.
     *
     * @param mode one that match CorrectnessMode
     */
    public void changeCorrectnessMode(CorrectnessMode mode) {
        if (modesRadioItemMap.get(mode).isDisable()) {
            logger.debug(BundleHelper.getApplication("correctnessChangeSame"));
            return;
        }
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
            logger.info(BundleHelper.getApplication("correctnessModeChanging"), mode);
            AlertBox.showAlert(AlertType.INFORMATION,
                    BundleHelper.getApplication("correctnessAlertTitle"),
                    BundleHelper.getApplication("correctnessAlertHeader"),
                    BundleHelper.getApplication("correctnessAlertTextOk"));
        } catch (SudokuBoardDuplicateValuesException e) {
            logger.warn(
                    BundleHelper.getException("correctnessModeChangingDuplicate"), e.getMessage());
            setSelectedToggle();
            AlertBox.showAlert(AlertType.WARNING,
                    BundleHelper.getApplication("correctnessAlertTitle"),
                    BundleHelper.getApplication("correctnessAlertHeader"),
                    BundleHelper.getApplication("correctnessAlertTextError"));
        }

    }

    /**
     * Selector for toggle that is used during initialization or when error occur.
     */
    private void setSelectedToggle() {
        correctnessGroup.getToggles().stream()
                .map(x -> (RadioMenuItem) x)
                .filter(x -> x.getId().equals(clickedToggleID))
                .findFirst()
                .ifPresentOrElse(x -> {
                    x.setSelected(true);
                    x.setDisable(true);
                    logger.info(BundleHelper.getApplication("correctnessChoiceSelAndDis"),
                            clickedToggleID);
                }, () -> {
                    rmiSupervisor.setSelected(true);
                    rmiSupervisor.setDisable(true);
                    logger.info(BundleHelper.getApplication("correctnessChoiceSelAndDis"),
                            rmiSupervisor.getId());
                });
    }
}
