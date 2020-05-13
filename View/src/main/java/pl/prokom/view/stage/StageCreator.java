package pl.prokom.view.stage;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.prokom.view.bundles.BundleHelper;

public class StageCreator {

    private static final Logger logger = LoggerFactory.getLogger(StageCreator.class);

    /**
     * Creating of new Stage class instance.
     *
     * @param primaryStage iniital Stage instance without any parameters set,
     * @param bundle       ResourceBundle with region settings to make correct language choice,
     * @param c            this class.
     */
    public static void createStage(Stage primaryStage, ResourceBundle bundle, Class c)
            throws IOException {
        FXMLLoader loader =
                new FXMLLoader(c.getResource("/fxml/MainPaneWindow.fxml"), bundle);
        logger.debug(BundleHelper.getApplication("stageFXMLLoaderCreated"));
        Pane mainPaneWindow = loader.load();
        Scene scene = new Scene(mainPaneWindow);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle(bundle.getString("title"));
        primaryStage.show();
        logger.debug(BundleHelper.getApplication("stagePrimaryStageShown"));
    }

}
