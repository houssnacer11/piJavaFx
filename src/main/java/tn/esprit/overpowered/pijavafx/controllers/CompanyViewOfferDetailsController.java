/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.entrepriseprofile.JobOffer;
import tn.esprit.overpowered.byusforus.entities.users.CompanyAdmin;
import tn.esprit.overpowered.byusforus.entities.users.HRManager;
import tn.esprit.overpowered.byusforus.entities.users.ProjectManager;
import tn.esprit.overpowered.byusforus.services.entrepriseprofile.JobOfferFacadeRemote;
import util.authentication.Authenticator;
import util.information.tracker.InfoTracker;
import util.routers.FXRouter;
import utilJoboffer.HandleOffer;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class CompanyViewOfferDetailsController implements Initializable {

    @FXML
    private AnchorPane generalAnchorPane;
    @FXML
    private AnchorPane rightMenuAnchorPane;
    @FXML
    private Button homeButtton;
    @FXML
    private Button profileButton;
    @FXML
    private Button jobOffers;
    @FXML
    private Button newOfferButton;
    @FXML
    private Button contactButton;
    @FXML
    private AnchorPane centralAnchorPane;
    @FXML
    private Label expertiseLevel;
    @FXML
    private Label title;
    @FXML
    private Label createdOn;
    @FXML
    private Label compName;
    @FXML
    private Label location;
    @FXML
    private Label archiveOn;
    @FXML
    private Label peopleNeeded;
    @FXML
    private Button approveOfferButton;
    @FXML
    private Button declineOfferButton;
    @FXML
    private Label about;
    @FXML
    private Label jobDescription;
    @FXML
    private AnchorPane topMenuAnchorPane;
    @FXML
    private MenuBar topMenu;
    @FXML
    private Label idLabel;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String type = Authenticator.currentUser.getDiscriminatorValue();
        Long currentUserId = Authenticator.currentUser.getId();

        if ("HUMAN_RESOURCES_MANAGER".equals(type)) {
            approveOfferButton.setVisible(true);
            declineOfferButton.setVisible(true);
            approveOfferButton.setDisable(false);
            declineOfferButton.setDisable(false);
        } else {
            approveOfferButton.setVisible(false);
            declineOfferButton.setVisible(false);
            approveOfferButton.setDisable(true);
            declineOfferButton.setDisable(true);
        }
        JobOffer jobOffer = (JobOffer) FXRouter.getData();
        title.setText(jobOffer.getTitle());
       createdOn.setText(jobOffer.getDateOfCreation().toString());
       location.setText(jobOffer.getCity());
       compName.setText(jobOffer.getCompany().getName());
       archiveOn.setText(jobOffer.getDateOfCreation().toString());
       expertiseLevel.setText(jobOffer.getExpertiseLevel().toString());
       peopleNeeded.setText(jobOffer.getPeopleNeeded().toString());
       jobDescription.setText(jobOffer.getDescription());
       FXRouter.when("JobOfferDetailView", "CompanyViewOfferDetails.fxml");
        FXRouter.setRouteContainer("JobOfferDetailView", generalAnchorPane);
        FXRouter.when("CompanyAdminProfileView", "CompanyAdminProfile.fxml");
        FXRouter.setRouteContainer("CompanyAdminProfileView", generalAnchorPane);
        FXRouter.when("CompanyHRProfileView", "CompanyHRProfile.fxml");
        FXRouter.setRouteContainer("CompanyHRProfileView", generalAnchorPane);
        FXRouter.when("CompanyPMProfileView", "CompanyPMProfile.fxml");
        FXRouter.setRouteContainer("CompanyPMProfileView", generalAnchorPane);
        FXRouter.when("OffersView", "Offers.fxml");
        FXRouter.setRouteContainer("OffersView", generalAnchorPane);
        FXRouter.when("BaseView", "Base.fxml");
        FXRouter.setRouteContainer("BaseView", generalAnchorPane);
        FXRouter.when("CreateJobOfferView", "CreateJobOffer.fxml");
        FXRouter.setRouteContainer("CreateJobOfferView", generalAnchorPane);
        FXRouter.when("MotifDeclineOfferView", "MotifDeclineOffer.fxml",420,256);
        FXRouter.setRouteContainer("MotifDeclineOfferView", generalAnchorPane);
    }

    @FXML
    private void homeButttonOnClicked(MouseEvent event) throws IOException {
        FXRouter.goTo("BaseView");
    }

    @FXML
    private void profileButtonOnClicked(MouseEvent event) throws NamingException, IOException {
        String type = Authenticator.currentUser.getDiscriminatorValue();
        Long currentUserId = Authenticator.currentUser.getId();
        switch (type) {

            case "COMPANY_ADMIN":
                CompanyAdmin companyAdmin = InfoTracker.getAdminInformation(currentUserId);
                FXRouter.goTo("CompanyAdminProfileView", companyAdmin);
                break;
            case "HUMAN_RESSOURCE_MANAGER":
                HRManager hrm = InfoTracker.getHRInformation(currentUserId);
                FXRouter.goTo("CompanyHRProfileView", hrm);
                break;
            case "PROJECT_MANAGER":
                ProjectManager pm = InfoTracker.getPMInformation(currentUserId);
                FXRouter.goTo("CompanyPMProfileView", pm);
                break;
            default:
                break;

        }
    }

    @FXML
    private void jobOffersOnClicked(MouseEvent event) throws IOException {
        FXRouter.goTo("OffersView");
    }

    @FXML
    private void newOfferButtonOnClicked(MouseEvent event) throws IOException {
        FXRouter.goTo("CreateJobOfferView");

    }

    @FXML
    private void contactButtonClicked(MouseEvent event) {
    }

    @FXML
    private void approveOfferButtonOnClicked(MouseEvent event) throws NamingException {
        HandleOffer.approveOffer(title.getText());
    }

    @FXML
    private void declineOfferButtonOnClicked(MouseEvent event) throws IOException, NamingException {
        String jndiName = "piJEE-ejb-1.0/JobOfferFacade!tn.esprit.overpowered.byusforus.services.entrepriseprofile.JobOfferFacadeRemote";
        Context context = new InitialContext();
        JobOfferFacadeRemote jobOfferProxy = (JobOfferFacadeRemote) context.lookup(jndiName);
        JobOffer jobOffer = jobOfferProxy.searchJobOfferByTitle(title.getText());
        FXRouter.goTo("MotifDeclineOfferView", jobOffer);
    }

}
