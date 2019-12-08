/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.users.Candidate;
import tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote;
import util.authentication.Authenticator;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author EliteBook
 */
public class ContactProfileController implements Initializable {

    @FXML
    private AnchorPane rightMenuAnchorPane;
    @FXML
    private Button profileButton;
    @FXML
    private AnchorPane centralAnchorPane;
    @FXML
    private Label recommendations;
    @FXML
    private Label name;
    @FXML
    private Label lastname;
    @FXML
    private Label email;
    @FXML
    private Label username;
    @FXML
    private Label visits;
    @FXML
    private Button addContactButton;
    @FXML
    private Button recommendButton;
    @FXML
    private AnchorPane topMenuAnchorPane;
    @FXML
    private MenuBar topMenu;
    @FXML
    private Button contactButton;
    @FXML
    private AnchorPane generalAnchorPane;
    @FXML
    private Label about;
    @FXML
    private Label idLabel;
    @FXML
    private Button sendMessageButton;
    Candidate injectedCadidate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       Candidate cdt = (Candidate) FXRouter.getData();
       injectedCadidate = cdt;
      /* if(cdt.getCurriculumVitaes().equals("exists"))
       {
           addContactButton.setDisable(true);
       }
*/
       name.setText(cdt.getFirstName());
       lastname.setText(cdt.getLastName());
       email.setText(cdt.getEmail());
       visits.setText(Integer.toString(cdt.getVisits()));
       recommendations.setText(Integer.toString(cdt.getRecommendations()));
       idLabel.setText(Long.toString(cdt.getId()));
       
    }    


    @FXML
    private void addContactButtonClicked(MouseEvent event) throws NamingException {
         String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byus"
                + "forus.services.candidat.CandidateFacadeRemote";
            Context context = new InitialContext();
            CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) 
                    context.lookup(jndiName); 
          if(candidateProxy.addContact(Authenticator.currentUser.getId()
                  ,Long.parseLong(idLabel.getText())).equals("Contact Added"))
                  {
                      addContactButton.setDisable(true);
                  }
            
    }

    @FXML
    private void recommendButtonClicked(MouseEvent event) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byus"
                + "forus.services.candidat.CandidateFacadeRemote";
            Context context = new InitialContext();
            CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) context.lookup(jndiName); 
            Candidate cdt = (Candidate) FXRouter.getData();
            System.out.println("The id of the data passed is: "+ cdt.getId());
            candidateProxy.recommend(cdt.getId());
            recommendButton.setDisable(true);
            int rating = cdt.getRecommendations()+1 ;
            recommendations.setText(Integer.toString(rating));
    }

    @FXML
    private void profileButtonClicked(MouseEvent event) throws IOException {
        FXRouter.when("ProfileView", "Profile.fxml","Profile", 889, 543);
                 FXRouter.setRouteContainer("ProfileView", generalAnchorPane);
        FXRouter.goTo("ProfileView");
    }

    @FXML
    private void contactButtonClicked(MouseEvent event) throws IOException {
        FXRouter.when("CandidateListView", "CandidateList.fxml","Candidate List", 889, 543);
                 FXRouter.setRouteContainer("CandidateListView", generalAnchorPane);
        FXRouter.goTo("CandidateListView");
    }

    @FXML
    private void sendMessage(ActionEvent event) throws IOException {
                FXRouter.when("inboxView", "Inbox.fxml");
        FXRouter.setRouteContainer("inboxView", centralAnchorPane);
        FXRouter.goTo("inboxView", injectedCadidate);
    }
    
}
