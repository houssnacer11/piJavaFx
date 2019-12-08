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
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.entrepriseprofile.JobOffer;
import util.routers.FXRouter;
import utilJoboffer.HandleOffer;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class MotifDeclineOfferController implements Initializable {

    @FXML
    private Button confirmMotifButton;
    @FXML
    private Button cancelMotifButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextArea motif;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        FXRouter.when("MotifDeclineOfferView", "MotifDeclineOffer.fxml");
        FXRouter.setRouteContainer("MotifDeclineOfferView", anchorPane);
        FXRouter.when("CompanyViewOfferDetailsView", "CompanyViewOfferDetails.fxml",800,600);
        FXRouter.setRouteContainer("CompanyViewOfferDetailsView", anchorPane);
    }

    @FXML
    private void confirmMotifButtonOnClicked(MouseEvent event) throws NamingException, IOException {
        JobOffer jobOffer = (JobOffer) FXRouter.getData();
        HandleOffer.declineOffer(jobOffer.getTitle(), motif.getText());
        FXRouter.goTo("CompanyViewOfferDetailsView",jobOffer);
    }

    @FXML
    private void cancelMotifButtonOnClicked(MouseEvent event) throws IOException {
        JobOffer jobOffer = (JobOffer) FXRouter.getData();
        FXRouter.goTo("CompanyViewOfferDetailsView",jobOffer);
    }

}
