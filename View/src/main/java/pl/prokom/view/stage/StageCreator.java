package pl.prokom.view.stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class StageCreator {

    private Stage stage;
    private ResourceBundle bundle;

    public void createStage() throws IOException {
        FXMLLoader loader =
                new FXMLLoader(this.getClass().getResource("/fxml/MainPaneWindow.fxml"), bundle);
        Pane mainPaneWindow = loader.load();
        Scene scene = new Scene(mainPaneWindow);
        stage.setScene(scene);
        stage.setTitle(bundle.getString("title"));
        stage.show();
    }

    public void prepareCreator(Stage primaryStage, ResourceBundle bundle){
        try {
            this.stage = primaryStage;
            this.bundle = bundle;
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
