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
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.users.Candidate;
import util.authentication.SignUp;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author EliteBook
 */
public class ConfirmEmailController implements Initializable {

    @FXML
    private JFXButton confirmButton;
    @FXML
    private Hyperlink goBackLink;
    @FXML
    private AnchorPane parentAnchorPane;
    @FXML
    private JFXTextField confirmCode;

    private int count = 0;
    @FXML
    private Hyperlink resendCodeLink;
    @FXML
    private Label garbage;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        FXRouter.when("SignUpView", "SignUp.fxml");
        FXRouter.setRouteContainer("SignUpView", parentAnchorPane);
        FXRouter.when("LoginView", "Login.fxml", "Login");
        FXRouter.setRouteContainer("LoginView", parentAnchorPane);
        
        /*FXRouter.when("CandidateHomeView", "CandidateHome.fxml");
        FXRouter.setRouteContainer("CandidateHomeView", parentAnchorPane);*/

    }

    @FXML
    private void confirmButton(MouseEvent event) throws IOException, NamingException {
        count += 1;
        Candidate myData = (Candidate) FXRouter.getData();
        //garbage.setText(myData.getIntroduction());
        System.out.println("CODE : " + myData.getIntroduction());
        if (confirmCode.getText().equals(myData.getIntroduction()) && count <= 1) {
            confirmButton.setDisable(true);
            myData.setIntroduction("");
            SignUp.finishCreation(myData);
            FXRouter.goTo("SignUpView", myData);
        } else if (confirmCode.getText().equals(garbage.getText())) {
            confirmButton.setDisable(true);
            myData.setIntroduction("");
            SignUp.finishCreation(myData);
            FXRouter.goTo("LoginView", myData);

        } else {
            confirmCode.setText("WRONG CODE ");
        }

    }

    @FXML
    private void goBackLink(MouseEvent event) throws IOException {
        FXRouter.goTo("SignUpView");

    }

    @FXML
    private void resendCodeLink(MouseEvent event) throws NamingException, NoSuchAlgorithmException {
        Candidate myData = (Candidate) FXRouter.getData();
        String code = SignUp.ContinueAsCandidate(myData.getEmail());
        garbage.setText(code);
        confirmCode.setPromptText(garbage.getText());
    }

}
