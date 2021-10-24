package sample;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.shape.Line;
import javafx.util.Duration;


public class LinearMovement extends Movement{
    final double X = Math.random() * (0.9 * Main.WIDTH - 0.1 * Main.WIDTH) + 0.1 * Main.WIDTH;
    final double Y = Math.random() * (0.9 * Main.HEIGHT - 0.1 * Main.HEIGHT) + 0.1 * Main.HEIGHT;
    final double cX = Math.random() * (0.9 * Main.WIDTH - 0.1 * Main.WIDTH) + 0.1 * Main.WIDTH;
    final double cY = Math.random() * (0.9 * Main.HEIGHT - 0.1 * Main.HEIGHT) + 0.1 * Main.HEIGHT;
    private PathTransition path;
    private Line line;

    public LinearMovement(Target target, Duration d){
        super.setDuration(d);
        line = new Line(cX, cY, X, Y);
        line.setFill(null);
        path = new PathTransition(super.duration, line, target.getTarget());
    }

    @Override
    public void move() {
        path.setCycleCount(Timeline.INDEFINITE);
        path.setAutoReverse(true);
        path.play();
    }
    public PathTransition getPath(){
        return path;
    }
}
