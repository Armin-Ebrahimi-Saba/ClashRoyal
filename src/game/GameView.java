package game;

import gameUtil.AliveTroop;
import gameUtil.BuildingName;
import gameUtil.SpellName;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class GameView extends Pane {
    private GameModel gameModel;
    private ImageView[][] firstLayerCellViews;
    private ArrayList<ImageView> secondLayerCellViews;
    private int CELL_WIDTH = 10;
    private int rowCount = 20;
    private int columnCount = 20;

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
        secondLayerCellViews = new ArrayList<>(30);
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
            }
        }
    }

    /**
     * this method updates the view
     */
    public void update(GameModel model) {
        secondLayerCellViews.clear();
        this.getChildren().clear();
        for (var troop : model.getPlayersStatus()[0].getAliveEnemyTroops()) {
            if (troop.isAlive())
            {
                if (troop.getCard().getName().equals(SpellName.RAGE)) {
                    Circle circle = new Circle(troop.getLocation().getX(),
                            troop.getLocation().getY(),
                            troop.getCard().getRange());
                    circle.setFill(new Color(1, 0, 1, 0.4));
                    this.getChildren().add(circle);
                } else {
                    var imageView = creatImageView(troop);
                    secondLayerCellViews.add(imageView);
                    this.getChildren().add(imageView);
                }
            }
        }
        for (var troop : model.getPlayersStatus()[0].getAliveAllyTroops()) {
            if (troop.isAlive())
            {
                if (troop.getCard().getName().equals(SpellName.RAGE)) {
                    Circle circle = new Circle(troop.getLocation().getX(),
                            troop.getLocation().getY(),
                            troop.getCard().getRange());
                    circle.setFill(new Color(1, 0, 1, 0.4));
                    this.getChildren().add(circle);
                } else {
                    var imageView = creatImageView(troop);
                    secondLayerCellViews.add(imageView);
                    this.getChildren().add(imageView);
                }
            }
        }
    }

    /**
     * this method creates an imageView
     * @param troop is the troop which its imageView will rotate
     * @return the imageView which was built
     */
    private ImageView creatImageView(AliveTroop troop) {
        ImageView imageView = new ImageView();
        Image image = new Image(troop.getCard().getCharacterImageAddress());
        imageView.setImage(image);
        imageView.setLayoutX(troop.getLocation().getX());
        imageView.setLayoutY(troop.getLocation().getY());
        imageView.setSmooth(true);
        imageView.setCache(true);
        if (troop.getTroopVelocityDirection() != null)
            rotateImageView(troop.getTroopVelocityDirection(), imageView);
        if (troop.getCard().getName() != BuildingName.ARCHER_TOWER &&
            troop.getCard().getName() != BuildingName.KING_TOWER) {
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
            imageView.setPreserveRatio(true);
        } else {
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
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
        double degree = (direction.getX() < 0 ? -1:1) * direction.angle(0, -1);
        imageView.setRotate(degree);
    }
}
