/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitMenuButton;
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
import tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote;
import util.authentication.Authenticator;
import util.authentication.CandidateServices;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author EliteBook
 */
public class CandidateListController implements Initializable {

    @FXML
    private AnchorPane rightMenuAnchorPane;
    @FXML
    private Button profileButton;
    @FXML
    private AnchorPane topMenuAnchorPane;
    @FXML
    private MenuBar topMenu;
    @FXML
    private TableColumn<Candidate, String> name;
    @FXML
    private TableColumn<Candidate, String> lastname;
    @FXML
    private TableColumn<Candidate, String> type;
    @FXML
    private TableColumn<Candidate, String> recommendations;
    @FXML
    private TableColumn<Candidate, String> visits;
    @FXML
    private TableView<Candidate> candidateView;
    @FXML
    private AnchorPane parentAnchorPane;
    @FXML
    private TableColumn<Candidate, Long> id;
    @FXML
    private TextField searchText;
    @FXML
    private JFXRadioButton radioName;
    @FXML
    private JFXRadioButton lastNameRadio;
    @FXML
    private JFXRadioButton emailRadio;
    @FXML
    private Button homeButton;
    @FXML
    private Button contactButton;
    @FXML
    private Button jobOffer;
    @FXML
    private Button companyButton;
    @FXML
    private Button searchButton;
    @FXML
    private ToggleGroup searchOption;
    @FXML
    private JFXButton viewProfile;
    @FXML
    private Button friendsButton;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote";
            Context context = new InitialContext();
            CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) context.lookup(jndiName);
            List<Candidate> list = candidateProxy.findAllCandidate();
            System.out.println("THE NAME ISSSSSSSSSS: " + list.get(0).getUsername());
            ObservableList<Candidate> cdtObs = FXCollections.observableArrayList();
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

        } catch (NamingException ex) {
            Logger.getLogger(CandidateListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void viewProfileAction(MouseEvent event) throws IOException, NamingException {
        String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byus"
                + "forus.services.candidat.CandidateFacadeRemote";
        Context context = new InitialContext();
        CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) context.lookup(jndiName);
        Candidate cdt = candidateView.getSelectionModel().getSelectedItem();
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
    private void profileButtonClicked(MouseEvent event) throws IOException {
        FXRouter.when("Profile", "Profile.fxml");
        FXRouter.setRouteContainer("Profile", parentAnchorPane);
        FXRouter.goTo("Profile");
    }

    @FXML
    private void searchButtonClicked(MouseEvent event) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote";
        Context context = new InitialContext();
        CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) context.lookup(jndiName);
        if (radioName.isSelected()) {
            List<Candidate> list = candidateProxy.searchByName(searchText.getText());
            ObservableList<Candidate> cdtObs = FXCollections.observableArrayList();

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
            ObservableList<Candidate> cdtObs = FXCollections.observableArrayList();

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
            ObservableList<Candidate> cdtObs = FXCollections.observableArrayList();

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
        }
    }

    @FXML
    private void friendsButtonClicked(MouseEvent event) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote";
        Context context = new InitialContext();
        CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) context.lookup(jndiName);

        List<Candidate> cdtSet = candidateProxy.friendsList(Authenticator.currentUser.getId());
        ObservableList<Candidate> cdtObs = FXCollections.observableArrayList();

        for (Candidate c : cdtSet) {
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

    @FXML
    private void homeButtonOnClicked(MouseEvent event) throws IOException {
        FXRouter.when("BaseView", "Base.fxml");
        FXRouter.setRouteContainer("BaseView", parentAnchorPane);
        FXRouter.goTo("BaseView");

    }

    @FXML
    private void contactButtonClicked(MouseEvent event) throws IOException {
        FXRouter.when("CandidateListView", "CandidateList.fxml");
        FXRouter.setRouteContainer("CandidateListView", parentAnchorPane);
        FXRouter.goTo("CandidateListView");
    }

    @FXML
    private void jobOfferClicked(MouseEvent event) throws IOException {
        FXRouter.when("JobOfferView", "CandidateJobOfferList.fxml");
        FXRouter.setRouteContainer("JobOfferView", parentAnchorPane);
        FXRouter.goTo("JobOfferView");

    }

    @FXML
    private void companyButtonClicked(MouseEvent event) throws IOException {
        FXRouter.when("CompanyListView", "CompanyList.fxml");
        FXRouter.setRouteContainer("CompanyListView", parentAnchorPane);
        FXRouter.goTo("CompanyListView");

    }

    @FXML
    private void selected(MouseEvent event) {
    }

}
