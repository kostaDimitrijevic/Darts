package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static final double WIDTH = 600;
    public static final double HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("ShootingTargets");
        primaryStage.setScene(SceneManager.getInstance().getGame());
        SceneManager.getInstance().setStage(primaryStage);
        Game.getInstance().setScene();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
