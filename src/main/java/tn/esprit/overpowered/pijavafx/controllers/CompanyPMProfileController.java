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
import tn.esprit.overpowered.byusforus.entities.users.HRManager;
import tn.esprit.overpowered.byusforus.entities.users.ProjectManager;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class CompanyPMProfileController implements Initializable {

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
    private AnchorPane centralAnchorPane;
    @FXML
    private Label name;
    @FXML
    private Label lastname;
    @FXML
    private Label email;
    @FXML
    private Label username;
    @FXML
    private Label visits;
    @FXML
    private Label recommendations;
    @FXML
    private AnchorPane topMenuAnchorPane;
    @FXML
    private MenuBar topMenu;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        profileButton.setDisable(true);
        ProjectManager pm = (ProjectManager) FXRouter.getData();
        name.setText(pm.getFirstName());
        lastname.setText(pm.getLastName());
        email.setText(pm.getEmail());
        recommendations.setText(Integer.toString(pm.getRecommendations()));
        visits.setText(Integer.toString(pm.getVisits()));
        username.setText(pm.getUsername());
        FXRouter.when("OffersView", "Offers.fxml", 889, 543);
        FXRouter.setRouteContainer("OffersView", parentAnchorPane);
        FXRouter.when("BaseView", "Base.fxml");
        FXRouter.setRouteContainer("BaseView", parentAnchorPane);
    }

    @FXML
    private void homeButtonOnClicked(MouseEvent event) throws IOException {
        ProjectManager pm = (ProjectManager) FXRouter.getData();
        FXRouter.goTo("BaseView", pm);
    }

    @FXML
    private void profileButtonClicked(ActionEvent event) {
        
    }

    @FXML
    private void jobOffersButtonOnClicked(MouseEvent event) throws IOException {
        ProjectManager pm = (ProjectManager) FXRouter.getData();
        FXRouter.goTo("OffersView", pm);
    }

}
