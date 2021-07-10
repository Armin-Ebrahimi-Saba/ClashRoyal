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
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

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

    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginModel = new LoginModel();
        logoImageView.setImage(new Image("login/images/logo.jpg"));
    }

    @FXML
    public void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void login(ActionEvent event) {
        try {

            if (usernameTextField.getText() != null &&
                passwordField.getText() != null &&
                loginModel.validateUsernameAndPassword(usernameTextField.getText(), passwordField.getText()))
            {
                Stage stage = (Stage)this.loginButton.getScene().getWindow();
                stage.close();
                loginThePlayer();
            } else {
                warningLabel.setText("invalid username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginThePlayer() {
        try {
            Stage playerStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/mainMenu/menu.fxml").openStream());
            MenuController gameController = loader.getController();
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
