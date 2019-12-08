/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.users.Candidate;
import tn.esprit.overpowered.byusforus.entities.users.CompanyAdmin;
import tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote;
import tn.esprit.overpowered.byusforus.services.users.CompanyAdminFacadeRemote;
import util.authentication.Authenticator;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author EliteBook
 */
public class ProfileController implements Initializable {

    @FXML
    private AnchorPane rightMenuAnchorPane;
    @FXML
    private Button profileButton;
    @FXML
    private JFXButton messagesButton;
    @FXML
    private JFXButton notificationsButton;
    @FXML
    private AnchorPane centralAnchorPane;
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
    private Label recommendations;
    @FXML
    private AnchorPane topMenuAnchorPane;
    @FXML
    private MenuBar topMenu;
    @FXML
    private AnchorPane generalAnchorPane;
    @FXML
    private Button contactsButtons;
    @FXML
    private Button homeButton;
    @FXML
    private Button jobOfferButton;
    @FXML
    private Button companyButton;
    @FXML
    private Button editButton;
    @FXML
    private Button addDetailsButton;
    @FXML
    private Label positionLabel;
    @FXML
    private Label orginazationLabel;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label cursusEndDate;
    @FXML
    private Label cursusDegree;
    @FXML
    private Label cursusUniversity;
    @FXML
    private Label cursusStartDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String type = Authenticator.currentUser.getDiscriminatorValue();
        if (type.equals("CANDIDATE")) {
            String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote";
            Context context;
            try {
                context = new InitialContext();
                CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) context.lookup(jndiName);
                Candidate cdt = new Candidate();
                cdt = candidateProxy.find(Authenticator.currentUser.getId());
                name.setText(cdt.getFirstName());
                lastname.setText(cdt.getLastName());
                email.setText(cdt.getEmail());
                recommendations.setText(Integer.toString(cdt.getRecommendations()));
                visits.setText(Integer.toString(cdt.getVisits()));
                username.setText(cdt.getUsername());
                

            } catch (NamingException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (type.equals("COMPANY_ADMIN")) {
            try {
                String jndiName = "piJEE-ejb-1.0/CompanyAdminFacade!tn.esprit.overpowered.byusforus.services.users.CompanyAdminFacadeRemote";
                Context context;
                context = new InitialContext();
                CompanyAdminFacadeRemote adminProxy = (CompanyAdminFacadeRemote) context.lookup(jndiName);
                CompanyAdmin admin = new CompanyAdmin();
                admin = adminProxy.find(Authenticator.currentUser.getId());
                name.setText(admin.getFirstName());
                lastname.setText(admin.getLastName());
                email.setText(admin.getEmail());
                recommendations.setText(Integer.toString(admin.getRecommendations()));
                visits.setText(Integer.toString(admin.getVisits()));
                username.setText(admin.getUsername());
            } catch (NamingException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void profileButtonClicked(ActionEvent event) throws IOException {
        FXRouter.when("Profile", "Profile.fxml");
        FXRouter.setRouteContainer("Profile", generalAnchorPane);
        FXRouter.goTo("Profile");
    }

    @FXML
    private void contactsButtonClicked(MouseEvent event) throws IOException {
        FXRouter.when("CandidateListView", "CandidateList.fxml");
        FXRouter.setRouteContainer("CandidateListView", generalAnchorPane);
        FXRouter.goTo("CandidateListView");
    }

    @FXML
    private void homeButtonClicked(MouseEvent event) throws IOException {
        FXRouter.when("BaseView", "Base.fxml");
        FXRouter.setRouteContainer("BaseView", generalAnchorPane);
        FXRouter.goTo("BaseView");
    }

    @FXML
    private void jobOfferButtonClicked(MouseEvent event) throws IOException {
        FXRouter.when("JobOfferView", "CandidateJobOfferList.fxml");
        FXRouter.setRouteContainer("JobOfferView", generalAnchorPane);
        FXRouter.goTo("JobOfferView");
    }

    @FXML
    private void companyButtonClicked(MouseEvent event) throws IOException {
        FXRouter.when("CompanyListView", "CompanyList.fxml");
        FXRouter.setRouteContainer("CompanyListView", generalAnchorPane);
        FXRouter.goTo("CompanyListView");

    }

    @FXML
    private void editButtonClicked(MouseEvent event) {
    }

    @FXML
    private void addDetailsClicked(MouseEvent event) throws IOException {
        FXRouter.when("DetailsView", "AddDetails.fxml");
        FXRouter.setRouteContainer("DetailsView", generalAnchorPane);
        FXRouter.goTo("DetailsView");
    }

}
