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
import tn.esprit.overpowered.byusforus.entities.users.Candidate;
import tn.esprit.overpowered.byusforus.entities.users.CompanyAdmin;
import tn.esprit.overpowered.byusforus.entities.users.Employee;
import tn.esprit.overpowered.byusforus.entities.users.HRManager;
import util.authentication.SignUp;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author EliteBook
 */
public class AdminConfirmEmailController implements Initializable {

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

    private int count = 0;

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
        CompanyAdmin companyAdmin = (CompanyAdmin) FXRouter.getData();

        if (confirmCode.getText().equals(companyAdmin.getIntroduction()) && count <= 1) {
            confirmButton.setDisable(true);
            SignUp.finishAdminCreation(companyAdmin);
            FXRouter.goTo("LoginView", companyAdmin);
        } else if (confirmCode.getText().equals(garbage.getText())) {
            confirmButton.setDisable(true);
            SignUp.finishAdminCreation(companyAdmin);
            FXRouter.goTo("LoginView", companyAdmin);

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
        CompanyAdmin companyAdmin = (CompanyAdmin) FXRouter.getData();
        String code = SignUp.ContinueAsCompanyAdmin(companyAdmin.getEmail());
        garbage.setText(code);
    }

}
