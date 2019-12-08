/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.jfoenix.controls.JFXButton;
import java.awt.Checkbox;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.entrepriseprofile.Event;
import tn.esprit.overpowered.byusforus.entities.entrepriseprofile.JobOffer;
import tn.esprit.overpowered.byusforus.entities.users.CompanyAdmin;
import tn.esprit.overpowered.byusforus.entities.users.HRManager;
import tn.esprit.overpowered.byusforus.entities.users.ProjectManager;
import tn.esprit.overpowered.byusforus.services.entrepriseprofile.EventFacadeRemote;
import tn.esprit.overpowered.byusforus.services.entrepriseprofile.JobOfferFacadeRemote;
import util.authentication.Authenticator;
import util.information.tracker.InfoTracker;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class EventsController implements Initializable {

    @FXML
    private AnchorPane parentAnchorPane;
    @FXML
    private AnchorPane rightMenuAnchorPane;
    @FXML
    private Button homeButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button logoutButton;
    @FXML
    private AnchorPane topMenuAnchorPane;
    @FXML
    private MenuBar topMenu;
    @FXML
    private AnchorPane centerAnchorPane;
    @FXML
    private TableView<Event> eventsView;
    @FXML
    private Button viewEventButton;
    @FXML
    private Button editEventButton;
    @FXML
    private Button deleteEventButton;
    @FXML
    private Label status;
    @FXML
    private TextField searchElement;
    @FXML
    private Button searchButton;
    @FXML
    private ComboBox<?> searchOptionCombo;
    @FXML
    private Button newEventButton;
    @FXML
    private TableColumn<?, ?> name;
    @FXML
    private TableColumn<?, ?> location;
    @FXML
    private TableColumn<?, ?> startTime;
    @FXML
    private TableColumn<?, ?> endTime;
    @FXML
    private TableColumn<?, ?> compName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
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

        String jndiName = "piJEE-ejb-1.0/EventFacade!tn.esprit.overpowered.byusforus.services.entrepriseprofile.EventFacadeRemote";
        Context context;
        try {
            context = new InitialContext();

            EventFacadeRemote eventProxy = (EventFacadeRemote) context.lookup(jndiName);
            List<Event> list = eventProxy.viewAllEvent();

            //System.out.println("THE LOCATION ISSSSSSSSSS: " + list.get(0).getCity());
            ObservableList<Event> events = FXCollections.observableArrayList();

            for (Event e : list) {
                events.add(e);
            }
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            location.setCellValueFactory(new PropertyValueFactory<>("location"));
            startTime.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            endTime.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            //

            eventsView.setItems(events);
        } catch (NamingException ex) {
            Logger.getLogger(EventsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void homeButtonOnClicked(MouseEvent event) throws IOException {
        FXRouter.goTo("BaseView");
    }

    @FXML
    private void profileButtonOnClicked(MouseEvent event) throws IOException, NamingException {
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
    private void logoutButtonOnClicked(MouseEvent event) throws IOException {
        FXRouter.goTo("LoginView");
    }

    @FXML
    private void selected(MouseEvent event
    ) {
    }

    @FXML
    private void viewEventButtonOnClicked(MouseEvent event
    ) throws IOException {
        Event e = eventsView.getSelectionModel().getSelectedItem();
        FXRouter.goTo("EventDetailsView", e);

    }

    @FXML
    private void editEventButtonOnClicked(MouseEvent event
    ) throws IOException {
        Event e = eventsView.getSelectionModel().getSelectedItem();
        FXRouter.goTo("EditEventView", e);
    }

    @FXML
    private void deleteEventButtonOnClicked(MouseEvent event
    ) {
        String jndiName = "piJEE-ejb-1.0/EventFacade!tn.esprit.overpowered.byusforus.services.entrepriseprofile.EventFacadeRemote";
        Context context;
        try {
            context = new InitialContext();

            EventFacadeRemote eventProxy = (EventFacadeRemote) context.lookup(jndiName);
            Event e = eventsView.getSelectionModel().getSelectedItem();
            eventProxy.deleteEvent(e);
            eventsView.getItems().removeAll(eventsView.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
        }

    }

    @FXML
    private void searchButtonClicked(MouseEvent event
    ) {
    }

    @FXML
    private void newEventButtonOnclicked(MouseEvent event
    ) throws IOException {
        FXRouter.goTo("CreateEventView");
    }

}
