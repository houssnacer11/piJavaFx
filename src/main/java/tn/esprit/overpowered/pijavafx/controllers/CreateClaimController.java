/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.reclamation.EtatReclamation;
import tn.esprit.overpowered.byusforus.entities.reclamation.Reclamation;
import tn.esprit.overpowered.byusforus.entities.reclamation.TypeReclamation;
import tn.esprit.overpowered.byusforus.entities.users.Candidate;
import tn.esprit.overpowered.byusforus.services.Reclamation.ReclamationRemote;
import tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote;
import util.authentication.Authenticator;
import util.proxies.claim.Claim;

/**
 * FXML Controller class
 *
 * @author amine
 */
public class CreateClaimController implements Initializable {

    @FXML
    private Button addButton;
    @FXML
    private JFXTextField nameLabel;
    @FXML
    private DatePicker datetime;
    @FXML
    private TableColumn<Reclamation, String> descriptionview;
    @FXML
    private TableColumn<Reclamation, String> typeview;
    @FXML
    private TableColumn<Reclamation, String> Etatview;
    @FXML
    private TableColumn<Reclamation, String> fichierview;
   
    @FXML
    private JFXComboBox<String> type;
    @FXML
    private JFXComboBox<String> etat;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
          
    
    
    
    
    
    
       List<String> types = Stream.of(TypeReclamation.values()).map(Enum::name).collect(Collectors.toList());
       List<String> etats = Stream.of(EtatReclamation.values()).map(Enum::name).collect(Collectors.toList());
       
       ObservableList<String> etatList = FXCollections.observableArrayList(etats);
       ObservableList<String> typeList = FXCollections.observableArrayList(types);
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        System.out.println(types.size());
        type.setItems(typeList);
        etat.setItems(etatList);
        etat.getSelectionModel().getSelectedItem();
                type.getSelectionModel().getSelectedItem();
        }

        @FXML
        private void onAddButtonClicked  (ActionEvent event) throws NamingException {
                Reclamation r = new Reclamation();
       
            r.setFichier_a_joindre("fichier");
            r.setDescription(nameLabel.getText());
            r.setDateReclamation(java.sql.Date.valueOf(datetime.getValue()));
            r.setEtat(EtatReclamation.valueOf(etat.getSelectionModel().getSelectedItem()));
            r.setType(TypeReclamation.valueOf(type.getSelectionModel().getSelectedItem()));
            r.setUser(Authenticator.currentUser);
            Claim.createClaim(r);
        }
        
        

}
