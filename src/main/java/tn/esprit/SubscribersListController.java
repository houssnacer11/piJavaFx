/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
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
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.users.Candidate;
import tn.esprit.overpowered.byusforus.entities.users.CompanyAdmin;
import tn.esprit.overpowered.byusforus.entities.users.CompanyProfile;
import tn.esprit.overpowered.byusforus.entities.users.Professional;
import tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote;
import tn.esprit.overpowered.byusforus.services.users.CompanyProfileFacadeRemote;
import util.authentication.Authenticator;
import util.information.tracker.InfoTracker;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class SubscribersListController implements Initializable {

    @FXML
    private AnchorPane parentAnchorPane;
    @FXML
    private AnchorPane rightMenuAnchorPane;
    @FXML
    private Button homeButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button jobOffer;
    @FXML
    private Button logoutButton;
    @FXML
    private AnchorPane topMenuAnchorPane;
    @FXML
    private MenuBar topMenu;
    @FXML
    private TableView<Professional> candidateView;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> name;
    @FXML
    private TableColumn<?, ?> lastname;
    @FXML
    private TableColumn<?, ?> type;
    @FXML
    private TableColumn<?, ?> recommendations;
    @FXML
    private TableColumn<?, ?> visits;
    @FXML
    private TextField searchText;
    @FXML
    private Button searchButton;
    @FXML
    private JFXRadioButton radioName;
    @FXML
    private ToggleGroup searchOption;
    @FXML
    private JFXRadioButton lastNameRadio;
    @FXML
    private JFXRadioButton emailRadio;
    @FXML
    private JFXButton viewProfile;
    @FXML
    private Button friendsButton;

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
        try {
            String type = Authenticator.currentUser.getDiscriminatorValue();
            Long currentUserId = Authenticator.currentUser.getId();
            String jndiName = "piJEE-ejb-1.0/CompanyProfileFacade!tn.esprit.overpowered.byusforus.services.users.CompanyProfileFacadeRemote";
            Context context = new InitialContext();
            CompanyProfileFacadeRemote compProfileProxy = (CompanyProfileFacadeRemote) context.lookup(jndiName);
            List<Professional> list = compProfileProxy.getSubscribersList(currentUserId);
            System.out.println("THE NAME ISSSSSSSSSS: " + list.get(0).getUsername());
            ObservableList<Professional> cdtObs = FXCollections.observableArrayList();
            for (Professional p : list) {
                cdtObs.add(p);
            }
            id.setCellValueFactory(new PropertyValueFactory<>("Id"));
            name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastname.setCellValueFactory(new PropertyValueFactory<>("LastName"));
            recommendations.setCellValueFactory(new PropertyValueFactory<>("Recommendations"));
            visits.setCellValueFactory(new PropertyValueFactory<>("Visits"));
            System.out.println("Still working at this point");
            candidateView.setItems(cdtObs);

        } catch (NamingException ex) {
            Logger.getLogger(CandidateListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void homeButtonOnClicked(MouseEvent event) throws IOException {
        FXRouter.goTo("BaseView");
    }

    @FXML
    private void profileButtonClicked(MouseEvent event) throws IOException {
        try {
            String type = Authenticator.currentUser.getDiscriminatorValue();
            Long currentUserId = Authenticator.currentUser.getId();
            CompanyAdmin compAdmin = InfoTracker.getAdminInformation(currentUserId);
            Long idComProf = compAdmin.getCompanyProfile().getId();
            String jndiName = "piJEE-ejb-1.0/CompanyProfileFacade!tn.esprit.overpowered.byusforus.services.users.CompanyProfileFacadeRemote";
            Context context = new InitialContext();
            CompanyProfileFacadeRemote compProfileProxy = (CompanyProfileFacadeRemote) context.lookup(jndiName);
            CompanyProfile compProf = compProfileProxy.find(idComProf);
              FXRouter.goTo("EntrepriseProfileView",compProf);
        } catch (Exception e) {
        }

    }

    @FXML
    private void jobOfferClicked(MouseEvent event
    ) throws IOException {
        FXRouter.goTo("OffersView");
    }

    @FXML
    private void logoutButtonClicked(MouseEvent event) throws IOException {
        FXRouter.goTo("LoginView");
    }

    @FXML
    private void selected(MouseEvent event
    ) {
    }

    @FXML
    private void searchButtonClicked(MouseEvent event
    ) throws NamingException {
                String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote";
        Context context = new InitialContext();
        CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) context.lookup(jndiName);
        if (radioName.isSelected()) {
            List<Candidate> list = candidateProxy.searchByName(searchText.getText());
            ObservableList<Professional> cdtObs = FXCollections.observableArrayList();

            for (Candidate c : list) {
                cdtObs.add(c);
            }
            id.setCellValueFactory(new PropertyValueFactory<>("Id"));
            name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastname.setCellValueFactory(new PropertyValueFactory<>("LastName"));
            type.setCellValueFactory(new PropertyValueFactory<>("Type"));
            recommendations.setCellValueFactory(new PropertyValueFactory<>("Recommendations"));
            visits.setCellValueFactory(new PropertyValueFactory<>("Visits"));
            System.out.println("Still working at this point");
            candidateView.setItems(cdtObs);
        } else if (lastNameRadio.isSelected()) {
            List<Candidate> list = candidateProxy.searchByLastname(searchText.getText());
            ObservableList<Professional> cdtObs = FXCollections.observableArrayList();

            for (Candidate c : list) {
                cdtObs.add(c);
            }
            id.setCellValueFactory(new PropertyValueFactory<>("Id"));
            name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastname.setCellValueFactory(new PropertyValueFactory<>("LastName"));
            type.setCellValueFactory(new PropertyValueFactory<>("Type"));
            recommendations.setCellValueFactory(new PropertyValueFactory<>("Recommendations"));
            visits.setCellValueFactory(new PropertyValueFactory<>("Visits"));
            System.out.println("Still working at this point");
            candidateView.setItems(cdtObs);
        } else if (emailRadio.isSelected()) {
            List<Candidate> list = candidateProxy.searchByEmail(searchText.getText());
            ObservableList<Professional> cdtObs = FXCollections.observableArrayList();

            for (Professional c : list) {
                cdtObs.add(c);
            }
            id.setCellValueFactory(new PropertyValueFactory<>("Id"));
            name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastname.setCellValueFactory(new PropertyValueFactory<>("LastName"));
            type.setCellValueFactory(new PropertyValueFactory<>("Type"));
            recommendations.setCellValueFactory(new PropertyValueFactory<>("Recommendations"));
            visits.setCellValueFactory(new PropertyValueFactory<>("Visits"));
            System.out.println("Still working at this point");
            candidateView.setItems(cdtObs);
        }
    }

    @FXML
    private void viewProfileAction(MouseEvent event
    ) throws NamingException, IOException {
                String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byus"
                + "forus.services.candidat.CandidateFacadeRemote";
        Context context = new InitialContext();
        CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) context.lookup(jndiName);
        Professional cdt = candidateView.getSelectionModel().getSelectedItem();
        cdt.setVisits(candidateProxy.incrementVisits(cdt.getId()));
        Candidate test = (Candidate) Authenticator.currentUser;
        List<Candidate> cdtList = cdt.getContacts();
        /*        if(candidateProxy.checkContacts(cdt.getId(), test))
        {
            cdt.setCurriculumVitaes("notexists");
        }
        else 
            cdt.setCurriculumVitaes("exists");
         */
        FXRouter.when("ProfileView", "ContactProfile.fxml", "Profile", 672, 417);
        FXRouter.setRouteContainer("ProfileView", parentAnchorPane);
        FXRouter.goTo("ProfileView", cdt);
    }

    @FXML
    private void friendsButtonClicked(MouseEvent event
    ) {
    }

}
