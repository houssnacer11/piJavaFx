/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.candidat.CandidateApplication;
import tn.esprit.overpowered.byusforus.entities.entrepriseprofile.JobOffer;
import tn.esprit.overpowered.byusforus.entities.quiz.Quiz;
import tn.esprit.overpowered.byusforus.entities.users.Candidate;
import tn.esprit.overpowered.byusforus.services.candidat.CandidateApplicationFacadeRemote;
import tn.esprit.overpowered.byusforus.services.quiz.QuizFacadeRemote;
import tn.esprit.overpowered.byusforus.util.JobApplicationState;
import util.authentication.Authenticator;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class CandidateAppliedJobsController implements Initializable {

    @FXML
    private GridPane jobsGridPane;
    @FXML
    private JFXButton goBackBtn;
    private JobOffer jobOffer;
    private QuizFacadeRemote quizFacadeProxy;
    private Context context = null;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public QuizFacadeRemote getQuizFacadeProxy() {
        return quizFacadeProxy;
    }

    public void setQuizFacadeProxy(QuizFacadeRemote quizFacadeProxy) {
        this.quizFacadeProxy = quizFacadeProxy;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int index = 0;
        int colIndex = 0;
        try {
            context = new InitialContext();
        } catch (NamingException ex) {
            Logger.getLogger(CandidateAppliedJobsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String cAppJndiName = "piJEE-ejb-1.0/CandidateApplicationFacade!tn.esprit.overpowered."
                + "byusforus.services.candidat.CandidateApplicationFacadeRemote";
        CandidateApplicationFacadeRemote cAppFacade = null;
        try {
            cAppFacade = (CandidateApplicationFacadeRemote) context.lookup(cAppJndiName);
        } catch (NamingException ex) {
            Logger.getLogger(ListJobOfferCandidatesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<CandidateApplication> cAppList = cAppFacade.getCandidateApplicationByCdtId(Authenticator.currentUser.getId());
        for (CandidateApplication candidateApp : cAppList) {
            jobOffer = candidateApp.getJobOffer();
            Candidate candidate = candidateApp.getCandidate();
            VBox candidateHBox = new VBox();
            HBox btnHBox = new HBox();
            Label nameLabel = new Label("Job title: " + candidateApp.getJobOffer().getTitle());
            Label stateLabel = new Label("Status: " + candidateApp.getJobApplicationState().name());
            JFXButton takeQuizBtn = new JFXButton("Take Quiz");
            takeQuizBtn.setVisible(false);
            takeQuizBtn.setDisable(true);
            if (candidateApp.getJobApplicationState() == JobApplicationState.INVITED_FOR_QUIZ) {
                takeQuizBtn.setVisible(true);
                takeQuizBtn.setDisable(false);
            }
            takeQuizBtn.setStyle("-fx-background-color: white;");
            nameLabel.setFont(Font.font("System", 17));
            stateLabel.setFont(Font.font("System", 17));
            takeQuizBtn.setFont(Font.font("System", 10));
            candidateHBox.getChildren().add(nameLabel);
            candidateHBox.getChildren().add(stateLabel);
            btnHBox.getChildren().add(takeQuizBtn);
            btnHBox.setAlignment(Pos.CENTER_RIGHT);
            candidateHBox.getChildren().add(btnHBox);
            if (colIndex % 2 == 0) {
                jobsGridPane.add(candidateHBox, 0, index);
                colIndex = 1;
            } else {
                jobsGridPane.add(candidateHBox, 1, index);
                colIndex = 0;
                index++;
            }
            String jndiName = "piJEE-ejb-1.0/QuizFacade!tn.esprit.overpowered.byusforus.services.quiz.QuizFacadeRemote";
            try {
                setQuizFacadeProxy((QuizFacadeRemote) context.lookup(jndiName));
            } catch (NamingException ex) {
                Logger.getLogger(CandidateAppliedJobsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            takeQuizBtn.setOnAction((event) -> {
                try {
                    Map<Context, Quiz> dataMap = new HashMap<>();
                    dataMap.put(getContext(), getQuizFacadeProxy().getQuizByJobOfferId(jobOffer.getId()));
                    FXRouter.goTo("QuizInfo", dataMap);
                } catch (IOException ex) {
                    Logger.getLogger(ListJobOfferCandidatesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    @FXML
    private void onGoBackBtn(ActionEvent event) {
    }

}
