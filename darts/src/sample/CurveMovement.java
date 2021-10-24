package sample;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;


public class CurveMovement extends Movement {

    private Duration duration;
    private PathTransition path;
    private CubicCurve curve;

    public CurveMovement(Target target, Duration d) {
        super.setDuration(d);
        double x1 = Math.random() * (Main.WIDTH * 0.8 - Main.WIDTH * 0.1) + Main.WIDTH * 0.1;
        double x2 = Math.random() * (Main.WIDTH * 0.8 - Main.WIDTH * 0.1) + Main.WIDTH * 0.1;
        double y1 = Math.random() * (Main.HEIGHT * 0.8 - Main.HEIGHT * 0.1) + Main.HEIGHT * 0.1;
        double y2 = y1;
        double startX = Math.abs(x1 - x2);
        double endX = Math.max(x1, x2);
        double control1 = Math.random() * (y1 - Main.HEIGHT * 0.1) + Main.HEIGHT * 0.1;
        double control2 = Math.random() * (Main.HEIGHT * 0.9 - y1) + y1;

        curve = new CubicCurve(x1, y1, startX + (startX + endX)/3, control1, (startX + endX)/2 + (startX + endX)/3, control2, x2, y2);
        path = new PathTransition(Duration.seconds(5), curve, target.getTarget());
    }

    @Override
    public void move() {
        double angle = Math.random() * 360;
        curve.setRotate(angle);
        path.setCycleCount(Timeline.INDEFINITE);
        path.setAutoReverse(true);
        path.play();
    }
}
