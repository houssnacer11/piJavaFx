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
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote;
import util.authentication.Authenticator;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 */
public class AddDetailsController implements Initializable {

    @FXML
    private AnchorPane parentAnchorPane;
    @FXML
    private AnchorPane rightMenuAnchorPane;
    @FXML
    private Button homeButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button contactButton;
    @FXML
    private Button jobOffer;
    @FXML
    private Button companyButton;
    @FXML
    private AnchorPane topMenuAnchorPane;
    @FXML
    private MenuBar topMenu;
    @FXML
    private JFXTextField positionText;
    @FXML
    private JFXTextField orginizationText;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private JFXTextField degreeText;
    @FXML
    private JFXTextField universityText;
    @FXML
    private DatePicker cursusStartDate;
    @FXML
    private DatePicker cursusEndDate;
    @FXML
    private Button addExperienceButton;
    @FXML
    private Button addCursusButton;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void homeButtonOnClicked(MouseEvent event) throws IOException {
        FXRouter.when("BaseView", "Base.fxml");
        FXRouter.setRouteContainer("BaseView", parentAnchorPane);
        FXRouter.goTo("BaseView");

    }

    @FXML
    private void profileButtonClicked(MouseEvent event) throws IOException {
        FXRouter.when("Profile", "Profile.fxml");
        FXRouter.setRouteContainer("Profile", parentAnchorPane);
        FXRouter.goTo("Profile");
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
    private void addExperienceButtonClicked(MouseEvent event) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote";
        Context context = new InitialContext();
        CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) context.lookup(jndiName);
        //Creating experience and affecting it to candidate
        Instant instant = Instant.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        Instant instant2 = Instant.from(endDate.getValue().atStartOfDay(ZoneId.systemDefault()));
        Date date2 = Date.from(instant2);
        addExperienceButton.setDisable(true);
        

    }

    @FXML
    private void addCursusButtonClicked(MouseEvent event) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote";
        Context context = new InitialContext();
        CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) context.lookup(jndiName);
        //Creating cursus and affecting to candidate
         Instant instant = Instant.from(cursusStartDate.getValue().atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        Instant instant2 = Instant.from(cursusEndDate.getValue().atStartOfDay(ZoneId.systemDefault()));
        Date date2 = Date.from(instant2);
        addCursusButton.setDisable(true);
        
    }

}
