package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

public class MousePointer extends Group {
    private Rectangle r1;
    private Rectangle r2;
    private Circle c;
    private static MousePointer pointer = null;

    private MousePointer() {
        r1 = new Rectangle(2, 10);
        r1.setFill(Color.RED);
        r1.getTransforms().addAll(
                new Translate(-r1.getWidth() / 2, -r1.getHeight()/2)
        );
        r2 = new Rectangle(10, 2);
        r2.setFill(Color.RED);
        r2.getTransforms().addAll(
                new Translate(-r2.getWidth() / 2, -r2.getHeight()/2)
        );
        c = new Circle(8);
        c.setFill(null);
        c.setStroke(Color.BLACK);
        c.setStrokeWidth(2);
        this.getChildren().addAll(r1, r2, c);
    }
    public static MousePointer getInstance(){
        if(pointer == null ) pointer = new MousePointer();
        return pointer;
    }
}
