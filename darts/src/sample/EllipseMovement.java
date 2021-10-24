package sample;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;

public class EllipseMovement extends Movement{
    final double rX = Math.random() * (0.3 * Main.WIDTH - 0.1 * Main.WIDTH) + 0.1 * Main.WIDTH;
    final double rY = Math.random() * (0.3 * Main.HEIGHT - 0.1 * Main.HEIGHT) + 0.1 * Main.HEIGHT;
    final double cX = Math.random() * (0.7 * Main.WIDTH - 0.3 * Main.WIDTH) + 0.3 * Main.WIDTH;
    final double cY = Math.random() * (0.7 * Main.HEIGHT - 0.3 * Main.HEIGHT) + 0.3 * Main.HEIGHT;
    private PathTransition path;
    private Duration duration;
    private Ellipse ellipse;

    public EllipseMovement(Target target, Duration d){
        super.setDuration(d);
        ellipse = new Ellipse(cX, cY, rX, rY);
        duration = Duration.seconds(5);
        path = new PathTransition(duration, ellipse, target.getTarget());
    }

    @Override
    public void move() {
        path.setCycleCount(Timeline.INDEFINITE);
        path.setAutoReverse(true);
        path.play();
    }
}
