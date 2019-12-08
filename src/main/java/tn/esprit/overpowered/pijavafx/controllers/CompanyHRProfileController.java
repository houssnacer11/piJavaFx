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
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class CompanyHRProfileController implements Initializable {

    @FXML
    private AnchorPane rightMenuAnchorPane;
    @FXML
    private JFXButton messagesButton;
    @FXML
    private JFXButton messagesButton1;
    @FXML
    private JFXButton notificationsButton;
    @FXML
    private Button profileButton;
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
    @FXML
    private Button homeButton;
    @FXML
    private Button jobOffersButton;
    @FXML
    private AnchorPane parentAnchorPane;
    @FXML
    private Button eventsButton;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        HRManager hrm = (HRManager) FXRouter.getData();
        name.setText(hrm.getFirstName());
        lastname.setText(hrm.getLastName());
        email.setText(hrm.getEmail());
        recommendations.setText(Integer.toString(hrm.getRecommendations()));
        visits.setText(Integer.toString(hrm.getVisits()));
        username.setText(hrm.getUsername());
        FXRouter.when("OffersView", "Offers.fxml", 889, 543);
        FXRouter.setRouteContainer("OffersView", parentAnchorPane);
        FXRouter.when("BaseView", "Base.fxml");
        FXRouter.setRouteContainer("BaseView", parentAnchorPane);
        FXRouter.when("EventsView", "Events.fxml");
        FXRouter.setRouteContainer("EventsView", parentAnchorPane);

    }

    @FXML
    private void profileButtonClicked(ActionEvent event) {
    }

    @FXML
    private void homeButtonOnClicked(MouseEvent event) throws IOException {
        HRManager hrm = (HRManager) FXRouter.getData();
        FXRouter.goTo("BaseView", hrm);
    }

    @FXML
    private void jobOffersButtonOnClicked(MouseEvent event) throws IOException {
        HRManager hrm = (HRManager) FXRouter.getData();
        FXRouter.goTo("OffersView", hrm);
    }

    @FXML
    private void eventsButtonOnClicked(MouseEvent event) throws IOException {
        FXRouter.goTo("EventsView");
    }

}
