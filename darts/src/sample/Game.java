package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.ArrayList;

public class Game {
    private ArrayList<Target> targets = new ArrayList<Target>();
    int activeTargets = 0;
    int i = 0;
    private int number_of_made_targets = 0;
    public int offset = 5;
    private static Game game;
    static boolean finishedRound = false;
    public static Group root = new Group();
    private double currX = 0;
    private double currY = 0;
    private int pointsWon = 0;
    private SceneManager scene;
    private Text points;
    private Text remainingTargets;
    private RoundSystem currRound;
    public boolean finishedGame = false;
    private int targetsShot = 0;

    private Game() {
        resetScene();
    }

    public static Game getInstance() {
        if (game == null) game = new Game();
        return game;
    }

    double prev = 0;
    double next = 0;
    AnimationTimer timer = new AnimationTimer() {

        @Override
        public void handle(long now) {
            if (prev == 0) prev = now / 1e9f;
            else {
                next = now / 1e9f;
                double diff = next - prev;
                if (diff >= 1 && number_of_made_targets < currRound.number_of_targets) {
                    prev = 0;
                    Target newTarget = new Target();
                    targets.add(newTarget);
                    root.getChildren().addAll(targets.get(i).getTarget());
                    switch (currRound.type){
                        case LINEAR: newTarget.setTargetMovement(new LinearMovement(newTarget, currRound.duration)); break;
                        case ELIPSE: newTarget.setTargetMovement(new EllipseMovement(newTarget, currRound.duration)); break;
                        case CURVE: newTarget.setTargetMovement(new CurveMovement(newTarget, currRound.duration)); break;
                    }
                    number_of_made_targets++;
                    activeTargets++;
                    i++;
                } else if (number_of_made_targets >= currRound.number_of_targets) {
                    number_of_made_targets = 0;
                    finishedRound = true;
                    timer.stop();
                }
            }
        }
    };

    public void resetScene(){
        scene = SceneManager.getInstance();
        currRound = scene.getCurrentRound();
        points = new Text("Points: " + pointsWon);
        points.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        points.setFill(Color.RED);
        points.getTransforms().addAll(
                new Translate(Main.WIDTH * 0.05, Main.HEIGHT *0.05)
        );
        remainingTargets = new Text("TARGETS: " + currRound.hitTargets + "/" + currRound.number_of_targets);
        remainingTargets.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        remainingTargets.getTransforms().addAll(
                new Translate(Main.WIDTH * 0.05, Main.HEIGHT - 30)
        );
        remainingTargets.setFill(Color.RED);
        root.getChildren().addAll(currRound.getImage());
        root.getChildren().addAll(points);
        root.getChildren().addAll(remainingTargets);
        root.getChildren().addAll(currRound.bullets.getBulletGroup());
        root.getChildren().addAll(MousePointer.getInstance());
        timer.start();
    }

    public void setScene() {
        scene.getGame().addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
            currX = event.getX();
            currY = event.getY();
            MousePointer.getInstance().setTranslateX(currX);
            MousePointer.getInstance().setTranslateY(currY);
            if(finishedRound && !finishedGame){
                if(getTargets().isEmpty())
                    generateNextRound();
            }
        });
        scene.getGame().addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if(!finishedGame)
                handleEvent();
        });

        scene.getGame().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if(KeyCode.DOWN.equals(event.getCode())){
                currY += 10;
                MousePointer.getInstance().setTranslateX(currX);
                MousePointer.getInstance().setTranslateY(currY);
            }
            if(KeyCode.UP.equals(event.getCode())){
                currY -= 10;
                MousePointer.getInstance().setTranslateX(currX);
                MousePointer.getInstance().setTranslateY(currY);
            }
            if(KeyCode.LEFT.equals(event.getCode())){
                currX -= 10;
                MousePointer.getInstance().setTranslateX(currX);
                MousePointer.getInstance().setTranslateY(currY);
            }
            if(KeyCode.RIGHT.equals(event.getCode())){
                currX += 10;
                MousePointer.getInstance().setTranslateX(currX);
                MousePointer.getInstance().setTranslateY(currY);
            }
            if(KeyCode.SPACE.equals(event.getCode())){
                handleEvent();
            }
        });
        scene.getGame().setCursor(Cursor.NONE);
    }

    public void generateNextRound() {
        Text round = new Text("END OF ROUND " + (scene.getRound() + 1));
        Text nextRound = new Text("GET READY FOR THE NEXT ROUND");
        round.setFill(Color.RED);
        round.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        round.setTextAlignment(TextAlignment.CENTER);
        nextRound.setFill(Color.RED);
        nextRound.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        nextRound.setTextAlignment(TextAlignment.CENTER);
        round.getTransforms().addAll(
                new Translate(Main.WIDTH / 4, Main.HEIGHT / 3)
        );
        nextRound.getTransforms().addAll(
                new Translate(Main.WIDTH / 4, Main.HEIGHT / 2)
        );
        root.getChildren().addAll(round, nextRound);
        offset += offset;

        root.getChildren().remove(currRound.getImage());
        currRound = scene.getNextRound();
        if(currRound == null){
            SceneManager.getInstance().ending();
        }
        else {
            remainingTargets.setText("TARGETS: " + currRound.hitTargets + "/" + currRound.number_of_targets);
            root.getChildren().add(0, currRound.getImage());
            root.getChildren().addAll(currRound.bullets.getBulletGroup());

            finishedRound = false;

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), new KeyValue(round.opacityProperty(), 1)),
                    new KeyFrame(Duration.seconds(4), new KeyValue(round.opacityProperty(), 0), new KeyValue(nextRound.opacityProperty(), 1)),
                    new KeyFrame(Duration.seconds(6), event1 -> {
                        Game.getInstance().timer.start();
                    }, new KeyValue(nextRound.opacityProperty(), 0))
            );
            timeline.play();
        }

    }

    public void handleEvent(){
        Target toDelete = null;
        int index = 0;
        if(currRound.leftBullets == 0){
            root.getChildren().remove(currRound.bullets.getBulletGroup());
        }
        else {
            currRound.bullets.getBulletGroup().getChildren().remove(currRound.bullets.getBullets().get(currRound.leftBullets-- - 1));
            for (Target t : getTargets()) {
                double cordX = t.getTarget().translateXProperty().get();
                double cordY = t.getTarget().translateYProperty().get();
                int size = t.getCircles().size();
                for (int j = size - 1; j >= 0; --j) {
                    Circle temp = t.getCircles().get(j);
                    double rX1 = temp.getRadius() * t.targetScale.getX() + cordX;
                    double rY1 = temp.getRadius() * t.targetScale.getY() + cordY;
                    double rX2 = cordX - temp.getRadius() * t.targetScale.getX();
                    double rY2 = cordY - temp.getRadius() * t.targetScale.getY();
                    if (currX > rX2 && currX < rX1 && currY > rY2 && currY < rY1) {
                        int point = t.getCirclePoints().get(temp);
                        ++currRound.hitTargets;
                        ++targetsShot;
                        Text stayingText = new Text(Integer.toString(point));
                        stayingText.setFont(Font.font(15));
                        Scale textScale = new Scale(1, 1);
                        stayingText.getTransforms().addAll(
                                new Translate(currX, currY),
                                textScale
                        );
                        Timeline textTime = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(stayingText.opacityProperty(), 1)),
                                new KeyFrame(Duration.seconds(1), new KeyValue(stayingText.xProperty(), 0.5),
                                        new KeyValue(stayingText.yProperty(), 0.5), new KeyValue(stayingText.opacityProperty(), 0.5)),
                                new KeyFrame(Duration.seconds(2), new KeyValue(stayingText.xProperty(), 0),
                                        new KeyValue(stayingText.yProperty(), 0), new KeyValue(stayingText.opacityProperty(), 0))
                        );
                        textTime.play();
                        root.getChildren().addAll(stayingText);
                        pointsWon += point + point * t.targetScale.getX();
                        points.setText("Points: " + pointsWon);
                        remainingTargets.setText("TARGETS: " + currRound.hitTargets + "/" + currRound.number_of_targets);
                        toDelete = t;
                        break;
                    }
                }
                if (toDelete != null) break;
                ++index;
            }
            if (toDelete != null) {
                getTargets().remove(index);
                --i;
                root.getChildren().remove(toDelete.getTarget());
            }
        }
        if(finishedRound && !finishedGame){
            if(getTargets().isEmpty())
                generateNextRound();
        }

    }

    public void resetValues(){
        activeTargets = 0;
        i = 0;
        number_of_made_targets = 0;
        prev = 0;
        next = 0;
        offset = 5;
        finishedGame = false;
        finishedRound = false;
        targetsShot = 0;
        pointsWon = 0;
        timer.stop();
    }

    public ArrayList<Target> getTargets() {
        return targets;
    }

    public int getPointsWon() {
        return pointsWon;
    }

    public int getTargetsShot() {
        return targetsShot;
    }
}
