/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import tn.esprit.overpowered.byusforus.entities.users.CompanyAdmin;
import tn.esprit.overpowered.byusforus.entities.users.CompanyProfile;
import tn.esprit.overpowered.byusforus.services.users.CompanyProfileFacadeRemote;
import util.authentication.Authenticator;
import util.information.tracker.InfoTracker;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class EntrepriseProfileController implements Initializable {

    @FXML
    private AnchorPane generalAnchorPane;
    @FXML
    private AnchorPane rightMenuAnchorPane;
    @FXML
    private JFXButton messagesButton;
    @FXML
    private JFXButton notificationsButton;
    @FXML
    private Button homeButton;
    @FXML
    private Button subscribersButton;
    @FXML
    private Button jobOfferButton;
    @FXML
    private Button companyButton;
    @FXML
    private AnchorPane centralAnchorPane;
    @FXML
    private Button followButton;
    @FXML
    private Label nameLabel;
    @FXML
    private Label companySizeLabel;
    @FXML
    private Label creationDate;
    @FXML
    private Label sectorOfActivity;
    @FXML
    private Label summary;
    @FXML
    private Label website;
    @FXML
    private AnchorPane topMenuAnchorPane;
    @FXML
    private MenuBar topMenu;
    @FXML
    private Button editProfileButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        FXRouter.when("EventsView", "Events.fxml");
        FXRouter.setRouteContainer("EventsView", generalAnchorPane);
        FXRouter.when("OffersView", "Offers.fxml");
        FXRouter.setRouteContainer("OffersView", generalAnchorPane);
        FXRouter.when("BaseView", "Base.fxml");
        FXRouter.setRouteContainer("BaseView", generalAnchorPane);
        FXRouter.when("LoginView", "Login.fxml");
        FXRouter.setRouteContainer("LoginView", generalAnchorPane);
        FXRouter.when("CompanyAdminProfileView", "CompanyAdminProfile.fxml");
        FXRouter.setRouteContainer("CompanyAdminProfileView", generalAnchorPane);
        FXRouter.when("CreateEventView", "CreateEvent.fxml");
        FXRouter.setRouteContainer("CreateEventView", generalAnchorPane);
        FXRouter.when("EditEventView", "EditEvent.fxml");
        FXRouter.setRouteContainer("EditEventView", generalAnchorPane);
        FXRouter.when("EventDetailsView", "EventDetails.fxml");
        FXRouter.setRouteContainer("EventDetailsView", generalAnchorPane);
        FXRouter.when("EntrepriseProfileView", "EntrepriseProfile.fxml");
        FXRouter.setRouteContainer("EntrepriseProfileView", generalAnchorPane);
        FXRouter.when("SubscribersListView", "SubscribersList.fxml");
        FXRouter.setRouteContainer("SubscribersListView", generalAnchorPane);
        try {
            String type = Authenticator.currentUser.getDiscriminatorValue();
            Long currentUserId = Authenticator.currentUser.getId();
            CompanyAdmin compAdmin = InfoTracker.getAdminInformation(currentUserId);
            Long idComProf = compAdmin.getCompanyProfile().getId();
            String jndiName = "piJEE-ejb-1.0/CompanyProfileFacade!tn.esprit.overpowered.byusforus.services.users.CompanyProfileFacadeRemote";
            Context context = new InitialContext();
            CompanyProfileFacadeRemote compProfileProxy = (CompanyProfileFacadeRemote) context.lookup(jndiName);
            CompanyProfile compProf = compProfileProxy.find(idComProf);
            nameLabel.setText(compProf.getName());
            companySizeLabel.setText(compProf.getCompanySize().toString());
            //creationDate.setText(compProf.getDateOfCreation().toString();
            sectorOfActivity.setText(compProf.getSectorOfActivity());
            summary.setText(compProf.getSummary());
            website.setText(compProf.getWebsite());
            
            
        } catch (Exception e) {
        }
    }

    @FXML
    private void homeButtonClicked(MouseEvent event) throws IOException {
        FXRouter.goTo("BaseView");
    }

    @FXML
    private void subscribersButtonOnClicked(MouseEvent event) throws IOException {
        FXRouter.goTo("SubscribersListView");
    }

    @FXML
    private void jobOfferButtonClicked(MouseEvent event) throws IOException {
        FXRouter.goTo("OffersView");
    }

    @FXML
    private void companyButtonClicked(MouseEvent event) {
    }

    @FXML
    private void followButtonClicked(MouseEvent event) {
    }

    @FXML
    private void editprofileButtonClicked(MouseEvent event) {
    }

}
