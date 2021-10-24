package sample;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Bullet {
    private int number_of_bullets;
    private final ArrayList<ImageView> bullets = new ArrayList<ImageView>();
    private Group bulletGroup = new Group();

    public Bullet(int num) {
        number_of_bullets = num;
        int offset = 0;
        try {
            for (int i = 0; i < number_of_bullets; i++) {
                InputStream stream = new FileInputStream("bullet.png");
                Image image = new Image(stream);
                ImageView imageView = new ImageView();
                imageView.setImage(image);
                imageView.setX(Main.WIDTH * 0.02 + offset);
                imageView.setY(Main.HEIGHT * 0.09);
                imageView.setFitWidth(40);
                imageView.setRotate(-90);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
                imageView.setCache(true);
                bulletGroup.getChildren().addAll(imageView);
                bullets.add(imageView);
                offset += 20;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void reloadBullets() {
        for (int i = 0; i < number_of_bullets; i++) {
            bulletGroup.getChildren().addAll(bullets.get(i));
        }
        Game.root.getChildren().addAll(bulletGroup);
    }

    public Group getBulletGroup() {
        return bulletGroup;
    }

    public ArrayList<ImageView> getBullets() {
        return bullets;
    }

    public int getNumber_of_bullets() {
        return number_of_bullets;
    }
}
