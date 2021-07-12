package login;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mainMenu.MenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import player.Status;

import java.io.IOException;

public class LoginController {

    LoginModel loginModel;
    @FXML
    private Label warningLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private ImageView logoImageView;

    /**
     * this method initialize the login controller and model and view
     */
    public void initialize() {
        loginModel = new LoginModel();
        logoImageView.setImage(new Image("login/images/logo.jpg"));
    }

    /**
     * this method exit the player from the exit button
     * @param event is the event which mouse click on the exit button
     */
    @FXML
    public void exit(ActionEvent event) {
        Platform.exit();
    }

    /**
     * this method login the player into game
     * @param event is the event which is the mouse click
     */
    @FXML
    public void login(ActionEvent event) {
        Status retrievedData;
        try {
            if (usernameTextField.getText() != null &&
                passwordField.getText() != null &&
                (retrievedData = loginModel.validateUsernameAndPassword(usernameTextField.getText(), passwordField.getText())) != null)
            {
                Stage stage = (Stage)this.loginButton.getScene().getWindow();
                stage.close();
                loginThePlayer(retrievedData);
            } else {
                warningLabel.setText("invalid username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  login the player into game and show the main menu
     * @param userData is the users' retrieved data
     */
    public void loginThePlayer(Status userData) {
        try {
            Stage playerStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/mainMenu/menu.fxml").openStream());
            MenuController menuController = loader.getController();
            menuController.initialize(userData);
            Scene scene = new Scene(root);
            playerStage.setScene(scene);
            playerStage.setTitle("Clash Royal");
            playerStage.setResizable(false);
            playerStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
