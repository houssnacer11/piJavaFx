/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.users.HRManager;
import util.authentication.SignUp;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author EliteBook
 */
public class HRConfirmEmailController implements Initializable {

    @FXML
    private AnchorPane parentAnchorPane;
    @FXML
    private JFXTextField confirmCode;
    @FXML
    private JFXButton confirmButton;
    @FXML
    private Hyperlink goBackLink;
    @FXML
    private Hyperlink resendCodeLink;
    @FXML
    private Label garbage;

    private int count =0;
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        FXRouter.when("LoginView", "Login.fxml");
        FXRouter.setRouteContainer("LoginView", parentAnchorPane);
    }

    @FXML
    private void confirmButton(MouseEvent event) throws NamingException, IOException {
        count += 1;
        HRManager hrManager = (HRManager) FXRouter.getData();

        if (confirmCode.getText().equals(hrManager.getIntroduction()) && count <= 1) {
            confirmButton.setDisable(true);
            FXRouter.goTo("LoginView", hrManager);
            SignUp.finishHRManagerCreation( hrManager);
        } else if (confirmCode.getText().equals(garbage.getText())) {
            confirmButton.setDisable(true);
            FXRouter.goTo("LoginView", hrManager);
            SignUp.finishHRManagerCreation(hrManager);
            

        } else {
            confirmCode.setText("WRONG CODE ");
        }
    }

    @FXML
    private void goBackLink(MouseEvent event) throws IOException {
        FXRouter.when("SignUpView", "SignUp.fxml");
        FXRouter.setRouteContainer("SignUpView", parentAnchorPane);
        FXRouter.goTo("SignUpView");
    }

    @FXML
    private void resendCodeLink(MouseEvent event) throws NamingException {
        HRManager hrManager = (HRManager) FXRouter.getData();
        String code = SignUp.ContinueAsHRManager(hrManager.getEmail());
        garbage.setText(code);
    }

}
