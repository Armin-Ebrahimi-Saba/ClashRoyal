package game;

import gameUtil.AliveTroop;
import gameUtil.Building;
import gameUtil.BuildingName;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class GameView extends Pane {
    private GameModel gameModel;
    private ImageView[][] firstLayerCellViews;
    private ArrayList<ImageView> secondLayerCellViews;
//    private AnchorPane secondLayerPane;
    private int CELL_WIDTH = 10;
    private int rowCount = 20;
    private int columnCount = 20;
    private Label label = new Label("hello mother fucker ");

    /**
     * this is a constructor
     */
    public GameView() {
    }

    /**
     * this method initialize the view
     */
    public void initialize(GameModel gameModel) {
        this.gameModel = gameModel;
//        secondLayerCellViews.forEach((view) -> this.getChildren().add(view));
//        this.getChildren().add(secondLayerCellViews);
        initializeFirstLayerGrid();
        initializeSecondLayerGrid();
    }

    /**
     * Constructs an empty grid of ImageViews
     */
    private void initializeSecondLayerGrid() {
//        secondLayerPane = new AnchorPane();
//        this.getChildren().add(secondLayerPane);
        secondLayerCellViews = new ArrayList<>(15);
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
                //this.getChildren().add(imageView);
            }
        }
    }

    /**
     * this method updates the view
     */
    public void update(GameModel model) {
        secondLayerCellViews.clear();
        for (var status : model.getPlayersStatus()) {
            for (var troop : status.getAliveAllyTroops()) {
                if (troop.isAlive())
                {
                    var imageView = creatImageView(troop);
                    secondLayerCellViews.add(imageView);
                    this.getChildren().add(imageView);
                }
            }
        }
//        secondLayerCellViews.forEach(
//                cv -> secondLayerPane.getChildren().add(cv));
    }

    /**
     * this method creates an imageView
     * @param troop is the troop which its imageView will rotate
     * @return the imageView which was built
     */
    private ImageView creatImageView(AliveTroop troop) {
        ImageView imageView = new ImageView();
        System.out.println(troop.getCard().getCharacterImageAddress());
        Image image = new Image(troop.getCard().getCharacterImageAddress());
        imageView.setImage(image);
        imageView.setX(troop.getTroopLocation().getX());
        imageView.setY(troop.getTroopLocation().getY());
        if (troop.getTroopVelocity() != null)
            rotateImageView(troop.getTroopVelocity(), imageView);
        if (troop.getCard().getName() != BuildingName.ARCHER_TOWER &&
            troop.getCard().getName() != BuildingName.KING_TOWER) {
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.setPreserveRatio(true);
        } else {
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);
        }
        return imageView;
    }

    /**
     * this method rotates the ImageView
     * @param direction is the direction of the image view which indicates degree of the rotation
     * @param imageView is the image view which should be rotated
     */
    private void rotateImageView(Point2D direction, ImageView imageView) {
        double degree;
        degree = Math.abs(Math.atan(direction.getY()/ (direction.getX() + 0.0000001231)) / Math.PI) * 180;
        if (direction.getY() >= 0 && direction.getX() < 0)
            degree = -degree;
        else if (direction.getY() <= 0 && direction.getX() > 0)
            degree = 180 - degree;
        else if (direction.getY() <= 0 && direction.getX() < 0)
            degree -= 180;
        imageView.setRotate(degree);
    }
}
