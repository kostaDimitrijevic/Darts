package sample;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.css.converter.FontConverter;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;


public class Target {
    private Group target;
    double xCord;
    double yCord;
    private Movement movement;
    private HashMap<Circle, Integer> circlePoints = new HashMap<Circle, Integer>();
    private ArrayList<Circle> circles = new ArrayList<Circle>();
    Scale targetScale = new Scale(1,1);
    FadeTransition fadeTransition;


    final double MIN_DX =  -500;
    final double MAX_DX = 0;
    final double MIN_DY = -500;
    final double MAX_DY = 0;
    final int MIN_CIRCLES = 3;
    final int MAX_CIRCLES = 7;
    final double MIN_HUE = 0;
    final double MAX_HUE = 150;
    final double RADIUS = 20;

    private double getRandomBetween(double min, double max) {
        return Math.random() * (max - min) + min;
    }

    public Group makeTarget(int number_of_circles) {
        Group t = new Group();
        double offset = 20;
        double r = RADIUS * number_of_circles;
        double lastR;

        Color color = Color.hsb(
                this.getRandomBetween(MIN_HUE, MAX_HUE),
                1,1,1
        );

        int points = 120;
        for (int i = 0; i < number_of_circles - 1; i++) {
            Circle circle = new Circle(r);
            circle.setFill(null);
            if (i % 2 != 0) circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(20);
            circlePoints.put(circle, points/(number_of_circles - 1 - i));
            circles.add(circle);
            r = r - offset;
            t.getChildren().addAll(circle);
        }
        r = r - r * 0.1;
        Circle circle = new Circle(r);
        circle.setFill(color);
        circle.setStroke(null);
        circlePoints.put(circle, 150);
        circles.add(circle);
        t.getChildren().addAll(circle);

        points = 150;
        Text p = new Text(Integer.toString(points));
        p.setFill(Color.BLACK);
        p.setFont(Font.font(8));
        Circle center = (Circle) t.getChildren().get(number_of_circles - 1);
        t.getChildren().addAll(p);
        int i = number_of_circles - 1;
        points = 120;
        for (Node n = t.getChildren().get(i); i > 0;) {
            Text po = new Text(Integer.toString(points));
            po.setFill(color);
            po.setFont(Font.font(8));
            double x = ((Circle) n).getRadius() + 0.01 * ((Circle) n).getRadius();
            po.getTransforms().addAll(
                    new Translate(x, 0)
            );
            i = i - 1;
            n = t.getChildren().get(i);
            t.getChildren().addAll(po);
            points -= 20;
        }
        Timeline timelineTarget = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    fadeTransition = new FadeTransition(Duration.seconds(3), target);
                    fadeTransition.setFromValue(1);
                    fadeTransition.setToValue(0);
                    fadeTransition.play();
                }),
                new KeyFrame(Duration.seconds(1.5),event -> {
                    for(int j =0;j<target.getChildren().size();j++){
                        if(target.getChildren().get(j) instanceof  Circle){
                            double pp =(double) circlePoints.get(target.getChildren().get(j));
                            pp = pp + pp*targetScale.getX();
                            circlePoints.put((Circle) target.getChildren().get(j), (int)pp);
                        }
                        if(target.getChildren().get(j) instanceof Text){
                            double pp = Double.parseDouble(((Text) target.getChildren().get(j)).getText());
                            pp = pp + pp*targetScale.getX();
                            ((Text) target.getChildren().get(j)).setText(Integer.toString((int)pp));
                        }
                    }
                }, new KeyValue(targetScale.xProperty(), 0.5), new KeyValue(targetScale.yProperty(), 0.5)),
                new KeyFrame(Duration.seconds(3), event -> {
                    if(Game.root.getChildren().contains(this.getTarget())) {
                        Game.root.getChildren().remove(this.getTarget());
                        Game.getInstance().getTargets().remove(this);
                        --Game.getInstance().i;
                    }
                }, new KeyValue(targetScale.xProperty(), 0.3), new KeyValue(targetScale.yProperty(), 0.3))
        );
        timelineTarget.play();

        return t;
    }

    public Target() {
        int circles = (int) (Math.random() * (MAX_CIRCLES - MIN_CIRCLES) + MIN_CIRCLES);
        target = new Group();
        if (circles % 2 == 0) circles++;
        target = makeTarget(circles);
        xCord = getRandomBetween(MIN_DX, MAX_DX);
        yCord = getRandomBetween(MIN_DY, MAX_DY);
        target.setTranslateX(xCord);
        target.setTranslateY(yCord);
        target.getTransforms().addAll(
                targetScale
        );
    }

    public  HashMap<Circle, Integer> getCirclePoints(){
        return circlePoints;
    }

    public void setTargetMovement(Movement m){
        movement = m;
        movement.move();
    }

    public Group getTarget() {
        return target;
    }

    public ArrayList<Circle> getCircles() {
        return circles;
    }
}
