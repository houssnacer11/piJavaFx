/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers.Paiement;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;

/**
 * FXML Controller class
 *
 * @author amine
 */
public class PaiementController implements Initializable {

    @FXML
    private CheckBox virement;
    @FXML
    private CheckBox banckCard;
    @FXML
    private CheckBox cheque;
    @FXML
    private CheckBox espece;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    }    
    
}
