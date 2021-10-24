package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class SceneManager {
    private ArrayList<RoundSystem> rounds = new ArrayList<RoundSystem>();
    private ArrayList<Image> images = new ArrayList<Image>();
    private Scene scene;
    private static SceneManager sceneManager = null;
    private int currRound = 0;
    private Scene endingScene;
    private Stage stage;

    private SceneManager() {
        try {
            InputStream stream = new FileInputStream("streljana1.png");
            InputStream stream1 = new FileInputStream("streljana2.jpg");
            InputStream stream2 = new FileInputStream("streljana3.jpg");
            images.add(new Image(stream));
            images.add(new Image(stream1));
            images.add(new Image(stream2));
            images.add(new Image(stream));
            images.add(new Image(stream1));
            images.add(new Image(stream2));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        rounds.add(new RoundSystem(10,10,images.get(0), RoundSystem.typeOfMovement.LINEAR, Duration.seconds(6)));
        rounds.add(new RoundSystem(15,15, images.get(1), RoundSystem.typeOfMovement.ELIPSE, Duration.seconds(5)));
        rounds.add(new RoundSystem(20, 20, images.get(2), RoundSystem.typeOfMovement.CURVE, Duration.seconds(4)));
        rounds.add(new RoundSystem(25, 25, images.get(0), RoundSystem.typeOfMovement.LINEAR, Duration.seconds(3.5)));
        rounds.add(new RoundSystem(27,27, images.get(1), RoundSystem.typeOfMovement.ELIPSE, Duration.seconds(3)));
        rounds.add(new RoundSystem(30, 30, images.get(2), RoundSystem.typeOfMovement.CURVE, Duration.seconds(2.9)));
        scene = new Scene(Game.root, Main.WIDTH, Main.HEIGHT);
    }

    public static SceneManager getInstance() {
        if (sceneManager == null) sceneManager = new SceneManager();
        return sceneManager;
    }

    public RoundSystem getNextRound(){
        currRound += 1;
        if(currRound == 6){
            Game.getInstance().finishedGame = true;
            return null;
        }
        return rounds.get(currRound);
    }

    public RoundSystem getCurrentRound(){
        return  rounds.get(currRound);
    }

    public int getRound() {return currRound;}

    public Scene getGame() {
        return scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void resetGame(){
        currRound = 0;
        for(int i = 0; i<rounds.size();i++){
            rounds.remove(i);
        }
        rounds.add(new RoundSystem(10,10,images.get(0), RoundSystem.typeOfMovement.LINEAR, Duration.seconds(6)));
        rounds.add(new RoundSystem(15,15, images.get(1), RoundSystem.typeOfMovement.ELIPSE, Duration.seconds(5)));
        rounds.add(new RoundSystem(20, 20, images.get(2), RoundSystem.typeOfMovement.CURVE, Duration.seconds(4)));
        rounds.add(new RoundSystem(25, 25, images.get(0), RoundSystem.typeOfMovement.LINEAR, Duration.seconds(3.5)));
        rounds.add(new RoundSystem(27,27, images.get(1), RoundSystem.typeOfMovement.ELIPSE, Duration.seconds(3)));
        rounds.add(new RoundSystem(30, 30, images.get(2), RoundSystem.typeOfMovement.CURVE, Duration.seconds(2.9)));
        scene = new Scene(Game.root, Main.WIDTH, Main.HEIGHT);
        stage.setScene(scene);
        Game.getInstance().setScene();
    }

    public void ending(){
        Group endGroup = new Group();
        Text points = new Text("Points won: " + Game.getInstance().getPointsWon());
        points.setFont(Font.font(15));
        points.setTextAlignment(TextAlignment.CENTER);
        points.getTransforms().addAll(
                new Translate(260, 260)
        );
        Text targets = new Text("Targets shot: " + Game.getInstance().getTargetsShot());
        targets.setFont(Font.font(15));
        targets.setTextAlignment(TextAlignment.CENTER);
        targets.getTransforms().addAll(
                new Translate(260, 280)
        );
        Button button = new Button("NEW GAME");
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
            Game.getInstance().resetValues();
            Game.root = new Group();
            resetGame();
            Game.getInstance().resetScene();
        });
        button.getTransforms().addAll(
                new Translate(260, 300)
        );
        endGroup.getChildren().addAll(points, targets,button);
        endingScene = new Scene(endGroup, Main.WIDTH, Main.HEIGHT);
        stage.setScene(endingScene);
    }
}
