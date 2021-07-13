package game;

import gameUtil.AliveTroop;
import gameUtil.BuildingName;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
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
                //this.getChildren().add(imageView);
            }
        }
    }

    /**
     * this method updates the view
     */
    public void update(GameModel model) {
        System.out.println(model.getPlayersStatus()[0].getAliveAllyTroops());
        secondLayerCellViews.clear();
        this.getChildren().clear();
        for (var troop : model.getPlayersStatus()[0].getAliveEnemyTroops()) {
            if (troop.isAlive())
            {
                var imageView = creatImageView(troop);
                secondLayerCellViews.add(imageView);
                this.getChildren().add(imageView);
            }
        }
        for (var troop : model.getPlayersStatus()[0].getAliveAllyTroops()) {
            if (troop.isAlive())
            {
                var imageView = creatImageView(troop);
                secondLayerCellViews.add(imageView);
                this.getChildren().add(imageView);
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
        Image image = new Image(troop.getCard().getCharacterImageAddress());
        imageView.setImage(image);
        System.out.print(troop.getCard().getName() + ": ");
        System.out.println(troop.getTroopLocation());
        imageView.setLayoutX(troop.getTroopLocation().getX());
        imageView.setLayoutY(troop.getTroopLocation().getY());
        imageView.setSmooth(true);
        imageView.setCache(true);
        if (troop.getTroopVelocityDirection() != null)
            rotateImageView(troop.getTroopVelocityDirection(), imageView);
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
        double degree = direction.angle(0, -1);
//        degree = Math.abs(Math.atan(direction.getY()/ (direction.getX() + 0.0000001231)) / Math.PI) * 180;
//        if (direction.getY() >= 0 && direction.getX() < 0)
//            degree = -degree;
//        else if (direction.getY() <= 0 && direction.getX() > 0)
//            degree = 180 - degree;
//        else if (direction.getY() <= 0 && direction.getX() < 0)
//            degree -= 180;
        imageView.setRotate(degree);
    }
}
