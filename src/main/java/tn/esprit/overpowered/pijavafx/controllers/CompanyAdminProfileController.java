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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.users.CompanyAdmin;
import tn.esprit.overpowered.byusforus.entities.users.HRManager;
import tn.esprit.overpowered.byusforus.entities.users.ProjectManager;
import util.authentication.Authenticator;
import util.information.tracker.InfoTracker;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class CompanyAdminProfileController implements Initializable {

    @FXML
    private AnchorPane parentAnchorPane;
    @FXML
    private AnchorPane rightMenuAnchorPane;
    @FXML
    private JFXButton messagesButton;
    @FXML
    private JFXButton messagesButton1;
    @FXML
    private JFXButton notificationsButton;
    @FXML
    private Button homeButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button jobOffersButton;
    @FXML
    private Button eventsButton;
    @FXML
    private Button compProfileButton;
    @FXML
    private Button logoutButton;
    @FXML
    private AnchorPane centralAnchorPane;
    @FXML
    private Label visits;
    @FXML
    private Label recommendations;
    @FXML
    private AnchorPane topMenuAnchorPane;
    @FXML
    private MenuBar topMenu;
    @FXML
    private Label name;
    @FXML
    private Label lastname;
    @FXML
    private Label email;
    @FXML
    private Label username;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         CompanyAdmin hrm = (CompanyAdmin) FXRouter.getData();
        name.setText(hrm.getFirstName());
        lastname.setText(hrm.getLastName());
        email.setText(hrm.getEmail());
        recommendations.setText(Integer.toString(hrm.getRecommendations()));
        visits.setText(Integer.toString(hrm.getVisits()));
        username.setText(hrm.getUsername());
        FXRouter.when("EventsView", "Events.fxml");
        FXRouter.setRouteContainer("EventsView", parentAnchorPane);
        FXRouter.when("OffersView", "Offers.fxml");
        FXRouter.setRouteContainer("OffersView", parentAnchorPane);
        FXRouter.when("BaseView", "Base.fxml");
        FXRouter.setRouteContainer("BaseView", parentAnchorPane);
        FXRouter.when("LoginView", "Login.fxml");
        FXRouter.setRouteContainer("LoginView", parentAnchorPane);
        FXRouter.when("CompanyAdminProfileView", "CompanyAdminProfile.fxml");
        FXRouter.setRouteContainer("CompanyAdminProfileView", parentAnchorPane);
        FXRouter.when("CreateEventView", "CreateEvent.fxml");
        FXRouter.setRouteContainer("CreateEventView", parentAnchorPane);
        FXRouter.when("EditEventView", "EditEvent.fxml");
        FXRouter.setRouteContainer("EditEventView", parentAnchorPane);
        FXRouter.when("EventDetailsView", "EventDetails.fxml");
        FXRouter.setRouteContainer("EventDetailsView", parentAnchorPane);
        FXRouter.when("EntrepriseProfileView", "EntrepriseProfile.fxml");
        FXRouter.setRouteContainer("EntrepriseProfileView", parentAnchorPane);
    }

    @FXML
    private void homeButtonOnClicked(MouseEvent event) throws IOException {
        FXRouter.goTo("BaseView");
    }

    @FXML
    private void profileButtonOnClicked(MouseEvent event) {

    }

    @FXML
    private void jobOffersButtonOnClicked(MouseEvent event) throws IOException {
        FXRouter.goTo("OffersView");
    }

    @FXML
    private void eventsButtonOnClicked(MouseEvent event) throws IOException {
        FXRouter.goTo("EventsView");
    }

    @FXML
    private void compProfileButtonOnClicked(MouseEvent event) throws NamingException, IOException {
        String type = Authenticator.currentUser.getDiscriminatorValue();
        Long currentUserId = Authenticator.currentUser.getId();
        switch (type) {
            case "COMPANY_ADMIN":
                CompanyAdmin compAdmin = InfoTracker.getAdminInformation(currentUserId);
                FXRouter.goTo("EntrepriseProfileView", compAdmin);
                break;
            case "HUMAN_RESOURCES_MANAGER":
                System.out.println("THIS IS UR IDDDDDDDDDDD  " + currentUserId);
                HRManager hrManager = InfoTracker.getHRInformation(currentUserId);
                FXRouter.goTo("EntrepriseProfileView", hrManager);
                break;
            case "PROJECT_MANAGER":
                ProjectManager pManager = InfoTracker.getPMInformation(currentUserId);
                FXRouter.goTo("EntrepriseProfileView", pManager);
                break;
            default:
                break;
        }
    }

    @FXML
    private void logoutButtonOnClicked(MouseEvent event) throws IOException {
        FXRouter.goTo("LoginView");
    }

}
