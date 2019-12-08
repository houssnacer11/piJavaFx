/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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
import tn.esprit.overpowered.byusforus.entities.users.CompanyProfile;
import tn.esprit.overpowered.byusforus.entities.users.HRManager;
import util.authentication.SignUp;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author EliteBook
 */
public class HREntrepriseVerifController implements Initializable {

    @FXML
    private AnchorPane parentAnchorPane;
    @FXML
    private JFXTextField companyLicenceID;
    @FXML
    private JFXTextField companyName;
    @FXML
    private JFXPasswordField yourPass;
    @FXML
    private Hyperlink goBackLink;
    @FXML
    private JFXButton continueButton;
    @FXML
    private Label label;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        FXRouter.when("HRConfirmEmailView", "HRConfirmEmail.fxml");
        FXRouter.setRouteContainer("HRConfirmEmailView", parentAnchorPane);
    }

    @FXML
    private void goBackLink(MouseEvent event) throws IOException {
        FXRouter.when("SignUpView", "SignUp.fxml");
        FXRouter.setRouteContainer("SignUpView", parentAnchorPane);
        FXRouter.goTo("SignUpView");
    }

    @FXML
    private void continueButton(MouseEvent event) throws NamingException, IOException {
        String code;

        if (SignUp.isAlphanumeric(companyLicenceID.getText())
                && SignUp.isAlphanumeric(companyName.getText())) {

            if (SignUp.verifyCompUserInfo(companyLicenceID.getText(), companyName.getText(),
                    yourPass.getText(), "HR")) {
                HRManager hrManager = (HRManager) FXRouter.getData();
                code = SignUp.ContinueAsHRManager(hrManager.getEmail());
                hrManager.setIntroduction(code);
                hrManager.setCurriculumVitaes(companyName.getText());
                FXRouter.goTo("HRConfirmEmailView", hrManager);
            } else {
                label.setText("SORRY WE DON'T RECOGNISE THESE INFORMATIONS");
            }
        } else {
            label.setText("Bad Fields Type");
        }
    }

}
