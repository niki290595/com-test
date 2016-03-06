import com.outlook.nikitin_ilya.controllers.LoginFormController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Ilya on 06.03.2016.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new LoginFormController(primaryStage);
    }
}
