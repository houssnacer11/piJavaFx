/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.entrepriseprofile.Event;
import tn.esprit.overpowered.byusforus.entities.users.CompanyAdmin;
import tn.esprit.overpowered.byusforus.entities.users.CompanyProfile;
import tn.esprit.overpowered.byusforus.entities.users.HRManager;
import tn.esprit.overpowered.byusforus.entities.users.ProjectManager;
import tn.esprit.overpowered.byusforus.services.entrepriseprofile.EventFacadeRemote;
import util.authentication.Authenticator;
import util.information.tracker.InfoTracker;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class EditEventController implements Initializable {

    @FXML
    private AnchorPane parentAnchorPane;
    @FXML
    private AnchorPane rightMenuAnchorPane;
    @FXML
    private Button homePageButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button EventsButton;
    @FXML
    private Button logoutButton;
    @FXML
    private AnchorPane centralAnchorPane;
    @FXML
    private TextArea eventdescription;
    @FXML
    private JFXTextField name;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private JFXTextField location;
    @FXML
    private JFXTextField compName;
    @FXML
    private AnchorPane topMenuAnchorPane;
    @FXML
    private MenuBar topMenu;
    @FXML
    private Button updateEventButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        Event currentEvent = (Event) FXRouter.getData();
        LocalDate date = currentEvent.getStartDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate date2 = currentEvent.getEndDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        eventdescription.setText(currentEvent.getDescription());
        name.setText(currentEvent.getName());
        location.setText(currentEvent.getLocation());
        startDate.setValue(date);
        endDate.setValue(date2);
        compName.setText(currentEvent.getCompany().getName());
        FXRouter.when("EventsView", "Events.fxml");
        FXRouter.setRouteContainer("EventsView", parentAnchorPane);
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
    }

    @FXML
    private void homePageButton(MouseEvent event) throws IOException {
        FXRouter.goTo("BaseView");
    }

    @FXML
    private void profileButton(MouseEvent event) throws NamingException, IOException {
        String type = Authenticator.currentUser.getDiscriminatorValue();
        Long currentUserId = Authenticator.currentUser.getId();
        switch (type) {
            case "COMPANY_ADMIN":
                CompanyAdmin compAdmin = InfoTracker.getAdminInformation(currentUserId);
                FXRouter.goTo("CompanyAdminProfileView", compAdmin);
                break;
            case "HUMAN_RESOURCES_MANAGER":
                System.out.println("THIS IS UR IDDDDDDDDDDD  " + currentUserId);
                HRManager hrManager = InfoTracker.getHRInformation(currentUserId);
                FXRouter.goTo("CompanyHRProfileView", hrManager);
                break;
            case "PROJECT_MANAGER":
                ProjectManager pManager = InfoTracker.getPMInformation(currentUserId);
                FXRouter.goTo("CompanyPMProfileView", pManager);
                break;
            default:
                break;
        }
    }

    @FXML
    private void eventsButtonOnClicked(MouseEvent event) throws IOException {
        FXRouter.goTo("EventsView");
    }

    @FXML
    private void logoutButtonOnClicked(MouseEvent event) throws IOException {
        FXRouter.goTo("LoginView");
    }


    @FXML
    private void updateEventButtonOnClicked(MouseEvent event) throws NamingException, IOException {
                Long currentUserId = Authenticator.currentUser.getId();
        CompanyAdmin compAdmin = InfoTracker.getAdminInformation(currentUserId);
        CompanyProfile compProf = compAdmin.getCompany();
                String jndiName = "piJEE-ejb-1.0/EventFacade!tn.esprit.overpowered.byusforus.services.entrepriseprofile.EventFacadeRemote";
        Context context = new InitialContext();
        EventFacadeRemote eventProxy = (EventFacadeRemote) context.lookup(jndiName);
        Instant instant = Instant.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()));
        Instant instant2 = Instant.from(endDate.getValue().atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        Date date2 = Date.from(instant2);
        Event newEvent = new Event();
        newEvent.setName(name.getText());
        newEvent.setDescription(eventdescription.getText());
        newEvent.setStartDate(date);
        newEvent.setEndDate(date2);
        newEvent.setCompany(compProf);
        newEvent.setLocation(location.getText());
        updateEventButton.setDisable(true);
        eventProxy.editEvent(newEvent);
        
        FXRouter.goTo("EventsView");
    }

}
