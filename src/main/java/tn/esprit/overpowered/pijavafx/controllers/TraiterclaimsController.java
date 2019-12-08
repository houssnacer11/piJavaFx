/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author amine
 */
public class TraiterclaimsController implements Initializable {

    @FXML
    private Button changeetat;
    @FXML
    private TextField etatchange;
    private int idreclamation; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void changeretat(ActionEvent event) {
    }
    
    public void Setidreclamation(int id )
     {
         this.idreclamation=id; 
         
         
     }
    
}
