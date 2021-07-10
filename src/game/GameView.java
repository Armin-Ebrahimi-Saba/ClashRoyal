package game;

import com.sun.javafx.geom.Point2D;
import gameUtil.AliveTroop;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class GameView extends Group {
    private ImageView[][] firstLayerCellViews;
    private ArrayList<ImageView> secondLayerCellViews;
    private AnchorPane secondLayerPane;
    private int CELL_WIDTH = 10;
    private int rowCount = 20;
    private int columnCount = 20;


    /**
     * this is a constructor
     */
    public GameView() {
        initializeFirstLayerGrid();
        initializeSecondLayerGrid();
    }

    /**
     * Constructs an empty grid of ImageViews
     */
    private void initializeSecondLayerGrid() {
        secondLayerPane = new AnchorPane();
        secondLayerPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.0)");
        this.getChildren().add(secondLayerPane);
        secondLayerCellViews = new ArrayList<>(15);
        // TODO you must add this cell views to the second layer
    }

    /**
     * Constructs an empty grid of ImageViews
     */
    private void initializeFirstLayerGrid() {
        firstLayerCellViews = new ImageView[rowCount][columnCount];
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                ImageView imageView = new ImageView();
                imageView.setX(column * CELL_WIDTH);
                imageView.setY(row * CELL_WIDTH);
                imageView.setFitWidth(CELL_WIDTH);
                imageView.setFitHeight(CELL_WIDTH);
                firstLayerCellViews[row][column] = imageView;
                this.getChildren().add(imageView);
            }
        }
    }

    /**
     * this method updates the view
     */
    public void update(GameModel model) {
        secondLayerCellViews.clear();
        for (var status : model.getPlayersStatus()) {
            for (var troop : status.getAliveTroops()) {
                if (troop.isAlive())
                    secondLayerCellViews.add(creatImageView(troop));
            }
        }
        secondLayerCellViews.forEach(
                cv -> secondLayerPane.getChildren().add(cv));
    }

    /**
     * this method creats an imageView
     * @param troop is the troop which its imageView will rotate
     * @return the imageView which was built
     */
    private ImageView creatImageView(AliveTroop troop) {
        ImageView imageView = new ImageView();
        Image image = new Image(getClass().getResourceAsStream(troop.getCard().getCharacterImageAddress()));
        imageView.setImage(image);
        imageView.setX(troop.getTroopLocation().x);
        imageView.setY(troop.getTroopLocation().y);
        rotateImageView(troop.getTroopVelocity(), imageView);
        return imageView;
    }

    /**
     * this method rotates the ImageView
     * @param direction is the direction of the image view which indicates degree of the rotation
     * @param imageView is the image view which should be rotated
     */
    private void rotateImageView(Point2D direction, ImageView imageView) {
        double degree;
        degree = Math.abs(Math.atan(direction.y/ (direction.x + 0.0000001231)) / Math.PI) * 180;
        if (direction.y >= 0 && direction.x < 0)
            degree = -degree;
        else if (direction.y <= 0 && direction.x > 0)
            degree = 180 - degree;
        else if (direction.y <= 0 && direction.x < 0)
            degree -= 180;
        imageView.setRotate(degree);
    }
}
