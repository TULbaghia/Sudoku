package pl.prokom.view.menu;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.prokom.view.controllers.MainPaneWindowController;
import pl.prokom.view.stage.StageCreator;

public class MainPaneWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializing stage, scene and setting Stage instance features.
     * @param primaryStage currently using stage.
     * @throws IOException location of .fxml
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Locale.setDefault(new Locale("pl"));
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.interaction");
        StageCreator.createStage(primaryStage, bundle, this.getClass());
    }


}
