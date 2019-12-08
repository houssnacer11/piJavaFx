/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers.Paiement;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.Paiement.Cheque;
import tn.esprit.overpowered.byusforus.entities.Paiement.Paiment;

import util.proxies.claim.cheque;

/**
 * FXML Controller class
 *
 * @author amine
 */
public class ChequeController implements Initializable {

    @FXML
    private JFXTextField Numcheque;
    @FXML
    private JFXTextField Status;
    @FXML
    private JFXTextField agence;
    @FXML
    private JFXTextField bank;
    @FXML
    private Button confirmer;
    @FXML
    private Label name;
    @FXML
    private Label price;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void confirmAction(ActionEvent event) throws NamingException {
        Cheque r = new Cheque();
       Paiment p = new Paiment();
            r.setNum(Integer.valueOf(Numcheque.getText()));
            r.setBank(bank.getText());
            r.setAgence(Integer.valueOf(agence.getText()));
            r.setStatus(Integer.valueOf(Status.getText()));
            r.setImage("fafa");
            r.setPaiment(p);
            r.setPrice("15");
            cheque.ValiderCheque(r);
    }
    
}
