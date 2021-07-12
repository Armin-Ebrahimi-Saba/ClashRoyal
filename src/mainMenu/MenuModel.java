package mainMenu;

import gameUtil.Card;
import javafx.scene.control.Button;
import player.Status;

public class MenuModel {
    private Card stackedCard;
    private Status userData;
    private Button stackedButton;

    /**
     * this method reset onChangeCardDesk and set it to null
     */
    public void resetStacked() {
        stackedCard = null;
        stackedButton = null;
    }

    /**
     * this method is a getter
     * @return the card which is going to be replaced in desk or visa versa
     */
    public Card getStackedCard() {
        return stackedCard;
    }

    /**
     * this method is a setter
     * @param userData is the retrieved data from user
     */
    public void setStatus(Status userData) {
        this.userData = userData;
    }

    /**
     * this method is a getter
     * @return userData is the retrieved data from user
     */
    public Status getStatus() {
        return userData;
    }

    /**
     * this is a setter
     * @param stackedCard is card that might be moved to the desk or moved out of it
     */
    public void setStackedCard(Card stackedCard) {
        this.stackedCard = stackedCard;
    }

    /**
     * this is a getter
     * @return stacked button
     */
    public Button getStackedButton() {
        return stackedButton;
    }

    /**
     * this is a setter
     * @param stackedButton is the stack button
     */
    public void setStackedButton(Button stackedButton) {
        this.stackedButton = stackedButton;
    }
}
