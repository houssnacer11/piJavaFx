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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
import tn.esprit.overpowered.byusforus.entities.entrepriseprofile.JobOffer;
import tn.esprit.overpowered.byusforus.entities.users.CompanyProfile;
import tn.esprit.overpowered.byusforus.services.entrepriseprofile.JobOfferFacadeRemote;
import tn.esprit.overpowered.byusforus.services.users.CompanyAdminFacadeRemote;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author EliteBook
 */
public class CompanyListController implements Initializable {

    @FXML
    private AnchorPane rightMenuAnchorPane;
    @FXML
    private Button homeButton;
    @FXML
    private Button profileButton;
    @FXML
    private AnchorPane topMenuAnchorPane;
    @FXML
    private MenuBar topMenu;
    @FXML
    private Label status;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<CompanyProfile> companyView;
    @FXML
    private TableColumn<?, ?> name;
    @FXML
    private TableColumn<?, ?> sector;
    @FXML
    private TableColumn<?, ?> website;
    @FXML
    private JFXButton viewDetails;
    @FXML
    private AnchorPane parentAnchorPane;
    @FXML
    private TableColumn<?, ?> creationDate;
    @FXML
    private TextField searchText;
    @FXML
    private TableView<JobOffer> jobOfferView;
    @FXML
    private TableColumn<?, ?> jobTitle;
    @FXML
    private TableColumn<?, ?> jobDate;
    @FXML
    private TableColumn<?, ?> jobPositions;
    @FXML
    private Button jobOfferButton;
    @FXML
    private Button contactButton;
    @FXML
    private Button jobOffer;
    @FXML
    private Button companyButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      FXRouter.when("CreateJobOfferView", "CreateJobOffer.fxml", "JobOffer", 640, 425);
        FXRouter.setRouteContainer("CreateJobOfferView", parentAnchorPane);
        FXRouter.when("ProfileView", "Profile.fxml", "JobOffer", 640, 425);
        FXRouter.setRouteContainer("ProfileView", parentAnchorPane);
        FXRouter.when("CompanyHRProfileView", "CompanyHRProfile.fxml", "Profile", 600, 400);
        FXRouter.setRouteContainer("CompanyHRProfileView", parentAnchorPane);
        FXRouter.when("CompanyPMProfileView", "CompanyPMProfile.fxml", "Profile", 600, 400);
        FXRouter.setRouteContainer("CompanyPMProfileView", parentAnchorPane);
        FXRouter.when("BaseView", "Base.fxml", "HOME", 800, 600);
        FXRouter.setRouteContainer("BaseView", parentAnchorPane);
        try {
            String jndiName = "piJEE-ejb-1.0/CompanyAdminFacade!tn.esprit.overpowered.byusforus.services.users.CompanyAdminFacadeRemote";
            Context context = new InitialContext();
            CompanyAdminFacadeRemote companyProxy = (CompanyAdminFacadeRemote) context.lookup(jndiName);
            List<CompanyProfile> compList = companyProxy.findAllCompanies();
            if (compList.isEmpty()) {
                System.out.println("EMPTY");
            }
            //System.out.println("THE LOCATION ISSSSSSSSSS: " + list.get(0).getCity());
            ObservableList<CompanyProfile> compObs = FXCollections.observableArrayList();

            for (CompanyProfile c : compList) {
                compObs.add(c);
            }
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            creationDate.setCellValueFactory(new PropertyValueFactory<>("dateOfCreation"));
            sector.setCellValueFactory(new PropertyValueFactory<>("sectorOfActivity"));
            website.setCellValueFactory(new PropertyValueFactory<>("website"));
            System.out.println("Still working at this point");
            companyView.setItems(compObs);

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
        FXRouter.goTo("ProfileView");
    }

    @FXML
    private void selected(MouseEvent event) {
    }


    @FXML
    private void searchButtonClicked(MouseEvent event) {
         try {
            String jndiName = "piJEE-ejb-1.0/CompanyAdminFacade!tn.esprit.overpowered.byusforus.services.users.CompanyAdminFacadeRemote";
            Context context = new InitialContext();
            CompanyAdminFacadeRemote companyProxy = (CompanyAdminFacadeRemote) context.lookup(jndiName);
            List<CompanyProfile> compList = companyProxy.searchCompanyProfileByName(searchText.getText());
            if (compList.isEmpty()) {
                System.out.println("EMPTY");
            }
            //System.out.println("THE LOCATION ISSSSSSSSSS: " + list.get(0).getCity());
            ObservableList<CompanyProfile> compObs = FXCollections.observableArrayList();

            for (CompanyProfile c : compList) {
                compObs.add(c);
            }
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            creationDate.setCellValueFactory(new PropertyValueFactory<>("dateOfCreation"));
            sector.setCellValueFactory(new PropertyValueFactory<>("sectorOfActivity"));
            website.setCellValueFactory(new PropertyValueFactory<>("website"));
            System.out.println("Still working at this point");
            companyView.setItems(compObs);

        } catch (NamingException ex) {
            Logger.getLogger(CandidateListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void jobOfferButtonClicked(MouseEvent event) {
        CompanyProfile comp = companyView.getSelectionModel().getSelectedItem();
         try {
            String jndiName = "piJEE-ejb-1.0/CompanyAdminFacade!tn.esprit.overpowered.byusforus.services.users.CompanyAdminFacadeRemote";
            Context context = new InitialContext();
            CompanyAdminFacadeRemote companyProxy = (CompanyAdminFacadeRemote) context.lookup(jndiName);
            List<JobOffer> list = companyProxy.jobOffersByCompany(comp.getId());
            if (list.isEmpty()) {
                System.out.println("EMPTY");
            }
            //System.out.println("THE LOCATION ISSSSSSSSSS: " + list.get(0).getCity());
            ObservableList<JobOffer> offerObs = FXCollections.observableArrayList();

            for (JobOffer o : list) {
                offerObs.add(o);
            }
            jobTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            jobPositions.setCellValueFactory(new PropertyValueFactory<>("peopleNeeded"));
            jobDate.setCellValueFactory(new PropertyValueFactory<>("dateOfCreation"));
            System.out.println("Still working at this point");
            jobOfferView.setItems(offerObs);

        } catch (NamingException ex) {
            Logger.getLogger(CandidateListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void contactButtonClicked(MouseEvent event) {
    }

    @FXML
    private void jobOfferClicked(MouseEvent event) throws IOException {
        FXRouter.when("JobOfferView", "CandidateJobOfferList.fxml" );
        FXRouter.setRouteContainer("JobOfferView", parentAnchorPane);
        FXRouter.goTo("JobOfferView");
    }

    @FXML
    private void companyButtonClicked(MouseEvent event) throws IOException {
        FXRouter.when("CandidateListView", "CandidateList.fxml");
        FXRouter.setRouteContainer("CandidateListView", parentAnchorPane);
        FXRouter.goTo("CandidateListView");
    }

    @FXML
    private void viewDetailsAction(MouseEvent event) throws IOException {
        CompanyProfile comp = companyView.getSelectionModel().getSelectedItem();
         FXRouter.when("CompanyProfileView", "CompanyProfile.fxml");
        FXRouter.setRouteContainer("CompanyProfileView", parentAnchorPane);
        FXRouter.goTo("CompanyProfileView", comp);
    }
    
}
