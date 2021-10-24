package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class RoundSystem {
    int number_of_targets;
    int hitTargets;
    int leftBullets;
    Bullet bullets;
    ImageView image = new ImageView();
    typeOfMovement type;
    Duration duration;
    enum typeOfMovement{LINEAR, ELIPSE, CURVE};

    public RoundSystem(int number_of_targets_, int bullets_, Image image_, typeOfMovement type_, Duration d){
        duration = d;
        number_of_targets = number_of_targets_;
        hitTargets = 0;
        bullets = new Bullet(bullets_);
        leftBullets = bullets.getNumber_of_bullets();
        image.setImage(image_);
        image.setFitWidth(Main.WIDTH);
        image.setFitHeight(Main.HEIGHT);
        type = type_;
    }

    public int getNumber_of_targets() {
        return number_of_targets;
    }

    public int getActiveTargets() {
        return hitTargets;
    }

    public int getLeftBullets() {
        return leftBullets;
    }


    public ImageView getImage() {
        return image;
    }
}
