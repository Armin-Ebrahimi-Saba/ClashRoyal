package gameUtil;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/* this class hold shapes related to this games restricted ares. */
public class ShapeHolder {
    private static double Y1 = 200;
    private static double X1 = 213.5;
    private static double height = 140;
    private static Rectangle topRectangle = new Rectangle(0, 0, 2 * X1, Y1); // this area is always restricted.
    private static Rectangle leftRectangle = new Rectangle(0, Y1, X1, height); // this area is not always restricted.
    private static Rectangle rightRectangle = new Rectangle(X1, Y1, X1, height); // this area is not always restricted.
    private static final Color color = new Color(1, 0, 0, (float) 0.4); // this color is for indicating restricted areas.

    /**
     * this is a getter
     *
     * @return top rectangle shape
     */
    public static Rectangle getTopRectangle() {
        topRectangle.setFill(color);
        return topRectangle;
    }

    /**
     * this is a getter
     *
     * @return left rectangle shape
     */
    public static Rectangle getLeftRectangle() {
        leftRectangle.setFill(color);
        return leftRectangle;
    }

    /**
     * this is a getter
     *
     * @return right rectangle shape
     */
    public static Rectangle getRightRectangle() {
        rightRectangle.setFill(color);
        return rightRectangle;
    }
}
