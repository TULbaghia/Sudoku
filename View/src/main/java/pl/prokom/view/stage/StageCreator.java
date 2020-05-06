package pl.prokom.view.stage;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StageCreator {

    public static void createStage(Stage primaryStage, ResourceBundle bundle, Class c)
            throws IOException {
        FXMLLoader loader =
                new FXMLLoader(c.getResource("/fxml/MainPaneWindow.fxml"), bundle);
        Pane mainPaneWindow = loader.load();
        Scene scene = new Scene(mainPaneWindow);
        primaryStage.setScene(scene);
        primaryStage.setTitle(bundle.getString("title"));
        primaryStage.show();
    }

}
