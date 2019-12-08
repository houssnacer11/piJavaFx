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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.users.CompanyProfile;
import tn.esprit.overpowered.byusforus.services.users.CompanyAdminFacadeRemote;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author EliteBook
 */
public class CompanyProfileController implements Initializable {

    @FXML
    private AnchorPane rightMenuAnchorPane;
    @FXML
    private JFXButton messagesButton;
    @FXML
    private JFXButton notificationsButton;
    @FXML
    private Button homeButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button contactsButtons;
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
    private AnchorPane generalAnchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
            CompanyProfile comp = (CompanyProfile) FXRouter.getData();
            nameLabel.setText(comp.getName());
            companySizeLabel.setText(comp.getCompanySize());
            sectorOfActivity.setText(comp.getSectorOfActivity());
            summary.setText(comp.getSummary());
            website.setText(comp.getWebsite());

      
    }    

    @FXML
    private void homeButtonClicked(MouseEvent event) throws IOException {
        FXRouter.when("BaseView", "Base.fxml" );
        FXRouter.setRouteContainer("BaseView", generalAnchorPane);
        FXRouter.goTo("BaseView");

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
    private void jobOfferButtonClicked(MouseEvent event) throws IOException {
        FXRouter.when("JobOfferView", "CandidateJobOfferList.fxml" );
        FXRouter.setRouteContainer("JobOfferView", generalAnchorPane);
        FXRouter.goTo("JobOfferView");

    }

    @FXML
    private void companyButtonClicked(MouseEvent event) throws IOException {
        FXRouter.when("CompanyListView", "CompanyList.fxml" );
        FXRouter.setRouteContainer("CompanyListView", generalAnchorPane);
        FXRouter.goTo("CompanyListView");

    }

    @FXML
    private void followButtonClicked(MouseEvent event) {
        followButton.setDisable(true);
    }
    
}
