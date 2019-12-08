 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers.authentication;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.authentication.Session;
import util.authentication.Authenticator;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author aminos
 */
public class Auth2FAController implements Initializable {

    String uid;
    @FXML
    private JFXTextField auth2FAToken;
    @FXML
    private AnchorPane parentAnchorPane;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FXRouter.when("baseView", "Base.fxml");
        FXRouter.setRouteContainer("baseView", parentAnchorPane);
        
        FXRouter.setRouteContainer("baseView", parentAnchorPane);
        FXRouter.when("ProfileView", "Profile.fxml");
        FXRouter.setRouteContainer("ProfileView", parentAnchorPane);
    }

    @FXML
    private void finalizeLogin(ActionEvent event) throws IOException {
        uid = (String) FXRouter.getData();
        try {
            Session s = Authenticator.finalizeAuth(uid, auth2FAToken.getText());
            System.out.println("Two factor authentication code: "+ auth2FAToken.getText());
            if (s != null) {
                Authenticator.currentSession = s;
                Authenticator.currentUser = s.getUser();
                
                FXRouter.goTo("baseView");
            } else // err
                ;

        } catch (NamingException ex) {
            // err
            Logger.getLogger(Auth2FAController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
