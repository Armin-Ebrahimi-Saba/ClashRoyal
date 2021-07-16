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
import player.Client;
import player.Status;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginController {
    // label which is used for showing a warning to client
    // text field for putting username in it
    // text field for putting password in it
    // button which login
    // an image for showing games' logo
    @FXML private Label warningLabel;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private ImageView logoImageView;

    // is an executor for executing client-side server
    // is a client-side server
    private ExecutorService executor;
    private Client client;



    /**
     * this method initialize the login controller and model and view
     */
    public void initialize() {
        executor = Executors.newCachedThreadPool();
        client = new Client();
        executor.execute(client);
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
            if (usernameTextField.getText().length() != 0 &&
                passwordField.getText().length() != 0 &&
                (retrievedData = authenticate(usernameTextField.getText(), passwordField.getText())) != null)
            {
                Stage stage = (Stage)this.loginButton.getScene().getWindow();
                stage.close();
                client.setStatus(retrievedData);
                loginThePlayer();
            } else {
                warningLabel.setText("invalid username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  login the player into game and show the main menu
     */
    public void loginThePlayer() {
        if (client.getStatus() == null)
            return;
        try {
            Stage playerStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/mainMenu/menu.fxml").openStream());
            MenuController menuController = loader.getController();
            menuController.initialize(client);
            Scene scene = new Scene(root);
            playerStage.setScene(scene);
            playerStage.setTitle("Clash Royal");
            playerStage.setResizable(false);
            playerStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * this method validate username and password by accessing database via server
     * @return retrieved data from database
     * @param username is the given username from the client
     * @param password is the given password from the client
     */
    public Status authenticate(String username, String password) throws Exception {
        String respond = "";
        if (!password.contains(" ") && !username.contains(" ")) {
            client.sendCommand("<LOGIN> " + username + " " + password);
            Thread.sleep(1000);
            respond = client.getLastRespond();
        }
        if (respond != null && respond.length() > 5)
            return (Status)fromString(respond);
        return null;
    }

    /** Read the object from a Base64 string. */
    private static Object fromString( String s ) throws IOException ,
            ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode( s );
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
    }

}
