package sample;

import javafx.util.Duration;

public abstract class Movement {
    Duration duration;
    public abstract void move();
    public void setDuration(Duration d){
        duration = d;
    }
}
