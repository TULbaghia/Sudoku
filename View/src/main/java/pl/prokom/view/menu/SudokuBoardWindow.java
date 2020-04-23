package pl.prokom.view.menu;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SudokuBoardWindow extends Application {

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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/fxml/SudokuBoardWindow.fxml"));
        Pane pane = loader.load();

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku");
        primaryStage.show();
    }
}
