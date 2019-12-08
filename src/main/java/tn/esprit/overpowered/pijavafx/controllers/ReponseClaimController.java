/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.reclamation.Reclamation;
import tn.esprit.overpowered.byusforus.entities.reclamation.TypeReclamation;
import tn.esprit.overpowered.byusforus.services.Reclamation.ReclamationRemote;
import tn.esprit.overpowered.byusforus.services.Reclamation.ReclamationService;
import tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote;

/**
 * FXML Controller class
 *
 * @author amine
 */
public class ReponseClaimController implements Initializable {

    @FXML
    private TableView<Reclamation> table;
    @FXML
    private TableColumn<Reclamation, String> type;
    @FXML
    private TableColumn<Reclamation, String> name;
    @FXML
    private TableColumn<Reclamation, String> description;
    @FXML
    private TableColumn<Reclamation, String> etat;
    @FXML
    private TableColumn<Reclamation, String> date;
    @FXML
    private TableColumn<Reclamation, String> fichier;
    @FXML
    private CheckBox claimfinancier;
    @FXML
    private CheckBox claimservice;
    @FXML
    private CheckBox claims;
    private ObservableList<Reclamation> k ;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            String jndiName = "piJEE-ejb-1.0/ReclamationService!tn.esprit.overpowered.byusforus.services.Reclamation.ReclamationRemote";
            Context context = new InitialContext();
            ReclamationRemote candidateProxy = (ReclamationRemote) context.lookup(jndiName);
            // TODO
            List<Reclamation> list = candidateProxy.All();
            ObservableList<Reclamation> cdtObs = FXCollections.observableArrayList();
            for (Reclamation c : list) {
                cdtObs.add(c);
            }
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            name.setCellValueFactory(new PropertyValueFactory<>("userId"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));
            etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            date.setCellValueFactory(new PropertyValueFactory<>("dateReclamation"));
            fichier.setCellValueFactory(new PropertyValueFactory<>("fichier_a_joindre"));
            table.setItems(cdtObs);
            table.setOnMouseClicked((e)->{
                
                Reclamation r = table.getSelectionModel().getSelectedItem();
                System.out.println(r);
                    
   FXMLLoader fxmlLoader = new FXMLLoader();
   fxmlLoader.setLocation(getClass().getResource("Traiterclaims.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(ReponseClaimController.class.getName()).log(Level.SEVERE, null, ex);
        }
   TraiterclaimsController pop=fxmlLoader.getController();
   pop.Setidreclamation(r.getIdReclamation());
  
   Stage stage =new Stage();
   stage.setScene(new Scene(fxmlLoader.getRoot()));
   stage.show();
  
                
            });
            this.afficherAction("service");
        } catch (NamingException ex) {
            Logger.getLogger(ReponseClaimController.class.getName()).log(Level.SEVERE, null, ex);
        }
  

                   }
    @FXML
    private void handleReclamationProduit(ActionEvent event){
        claimfinancier.setOnAction(e -> {
            System.out.println("clikced");
            if(claimfinancier.selectedProperty().get()==true && claimservice.selectedProperty().get()==true){this.afficherAction("s");}
            else if(claimfinancier.selectedProperty().get()){this.afficherAction("Financier");}
            else {this.afficherAction("s");}
            
        });
        
        claimservice.setOnAction(e -> {
            System.out.println("clikced");
            if(claimfinancier.selectedProperty().get()==true && claimservice.selectedProperty().get()){this.afficherAction("s");}
            else if(claimservice.selectedProperty().get()){this.afficherAction("Service");}
            else {this.afficherAction("s");}
            
        });
            claims.setOnAction(e -> {
            System.out.println("clikced");
            if(claimfinancier.selectedProperty().get()==true && claims.selectedProperty().get()){this.afficherAction("s");}
            else if(claims.selectedProperty().get()){this.afficherAction("Autre");}
            else {this.afficherAction("s");}
            
        });
    }
     @FXML
      public void afficherAction(String type)
    {
        
        try {
            String jndiName = "piJEE-ejb-1.0/ReclamationService!tn.esprit.overpowered.byusforus.services.Reclamation.ReclamationRemote";
            Context context = new InitialContext();
            ReclamationRemote candidateProxy = (ReclamationRemote) context.lookup(jndiName);
            // TODO
            
            List<Reclamation> p= null;
            if(type.equals("Financier")){
                p= candidateProxy.FindByType(TypeReclamation.Financier);
            }else if(type.equals("Service")){
                p= candidateProxy.FindByType(TypeReclamation.Service);
                
            }
            else{
                p= candidateProxy.All();
                
            }
            k=FXCollections.observableArrayList(p);
            System.out.println(p);
            table.setItems(k);
        } catch (NamingException ex) {
            Logger.getLogger(ReponseClaimController.class.getName()).log(Level.SEVERE, null, ex);
        }
  
   
    }
}
