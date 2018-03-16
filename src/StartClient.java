/**
 * Created by Александр on 14.09.2017.
 */


import client.controler.ClientControler;
import javafx.application.Application;
import javafx.stage.Stage;

public class StartClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new ClientControler();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
