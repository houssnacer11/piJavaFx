/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class CompanyAdminController implements Initializable {

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
    private Button eventsButton;
    @FXML
    private Button compProfileButton;
    @FXML
    private Button logoutButton;
    @FXML
    private AnchorPane centralAnchorPane;
    @FXML
    private Label visits;
    @FXML
    private Label recommendations;
    @FXML
    private AnchorPane topMenuAnchorPane;
    @FXML
    private MenuBar topMenu;
    @FXML
    private Label name;
    @FXML
    private Label lastname;
    @FXML
    private Label email;
    @FXML
    private Label username;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void homeButtonOnClicked(MouseEvent event) {
    }

    @FXML
    private void profileButtonOnClicked(MouseEvent event) {
    }

    @FXML
    private void jobOffersButtonOnClicked(MouseEvent event) {
    }

    @FXML
    private void eventsButtonOnClicked(MouseEvent event) {
    }

    @FXML
    private void compProfileButtonOnClicked(MouseEvent event) {
    }

    @FXML
    private void logoutButtonOnClicked(MouseEvent event) {
    }
    
}
