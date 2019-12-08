/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers.authentication;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.naming.NamingException;
import util.authentication.Authenticator;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author aminos
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField loginUsername;
    @FXML
    private JFXPasswordField loginPassword;
    @FXML
    private JFXButton loginButton;
    @FXML
    private Hyperlink createAccount;
    @FXML
    private Hyperlink forgotPassword;
    @FXML
    private AnchorPane parentAnchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FXRouter.when("2FAConfirmView", "Auth2FA.fxml");
        FXRouter.setRouteContainer("2FAConfirmView", parentAnchorPane);
    }

    @FXML
    private void create2FAToken(ActionEvent event) throws IOException {

        loginButton.setDisable(true);
        Task<String> authTask = new Task<String>() {
            @Override
            protected String call() throws NamingException, NoSuchAlgorithmException, IOException {
                String uid = Authenticator.authenticate(loginUsername.getText(), loginPassword.getText());
                if (uid == null) {
                    loginButton.setDisable(false);
                    return null;
                } else {
                    return uid;
                }
            }

        };

        authTask.setOnSucceeded(new EventHandler() {

            @Override
            public void handle(Event event) {
                String uid = authTask.getValue();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (uid != null) {
                            try {
                                FXRouter.goTo("2FAConfirmView", uid);
                            } catch (IOException ex) {
                                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
            }
        }
        );

        new Thread(authTask)
                .start();

    }

    @FXML
    private void createAccountClicked(MouseEvent event) throws IOException {
        FXRouter.when("SignUpView", "SignUp.fxml","Sign Up");
        FXRouter.setRouteContainer("SignUpView", parentAnchorPane);
        FXRouter.goTo("SignUpView",parentAnchorPane);
    }

}
