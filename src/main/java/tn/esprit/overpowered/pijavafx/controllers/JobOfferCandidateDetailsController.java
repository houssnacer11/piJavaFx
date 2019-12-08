/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.naming.Context;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.candidat.CandidateApplication;
import tn.esprit.overpowered.byusforus.entities.entrepriseprofile.JobOffer;
import tn.esprit.overpowered.byusforus.entities.users.Candidate;
import tn.esprit.overpowered.byusforus.services.candidat.CandidateApplicationFacadeRemote;
import tn.esprit.overpowered.byusforus.util.JobApplicationState;
import util.factories.CreateAlert;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class JobOfferCandidateDetailsController implements Initializable {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchorScrollPane;
    @FXML
    private GridPane candidatesGridPane;
    @FXML
    private Label titleLabel;
    @FXML
    private Text nameText;
    @FXML
    private Text fullProfleText;
    @FXML
    private Text motivationLetterText;
    @FXML
    private Text viewResumeText;
    @FXML
    private Text skillsText;
    @FXML
    private HBox buttonsHBox;
    @FXML
    private JFXButton refuseCandidacyBtn;
    @FXML
    private JFXButton inviteBtn;
    private Candidate candidate;
    private JobOffer jobOffer;
    private Context context;
    @FXML
    private Label appStatusLabel;
    @FXML
    private Label addInfoLabel;
    @FXML
    private JFXButton goBackBtn;
    private CandidateApplication cApp;
    @FXML
    private AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Map<Context, CandidateApplication> dataMap = (HashMap) FXRouter.getData();
        cApp = dataMap.values().stream().findFirst().get();
        context = dataMap.keySet().stream().findFirst().get();
        candidate = cApp.getCandidate();
        jobOffer = cApp.getJobOffer();
        if (cApp.getJobApplicationState() != JobApplicationState.PENDING) {
            refuseCandidacyBtn.setDisable(true);
            inviteBtn.setDisable(true);
        }
        appStatusLabel.setText(cApp.getJobApplicationState().name());
        addInfoLabel.setText(cApp.getAdditionalInfo());
        titleLabel.setText(candidate.getFirstName() + " " + candidate.getLastName() + " " + titleLabel.getText());
        nameText.setText(candidate.getFirstName() + " " + candidate.getLastName());
        scrollPane.setPrefHeight((double) FXRouter.scene.heightProperty().doubleValue());
        anchorScrollPane.setPrefHeight((double) FXRouter.scene.heightProperty().doubleValue());
        scrollPane.setPrefWidth((double) FXRouter.scene.widthProperty().doubleValue());
        anchorScrollPane.setPrefWidth((double) FXRouter.scene.widthProperty().doubleValue());
        double titleLabelX = (FXRouter.scene.widthProperty().doubleValue() / 2) - titleLabel.getWidth();
        titleLabel.setLayoutX(titleLabelX);
        double candidatesGridPaneX = (FXRouter.scene.widthProperty().doubleValue() / 2) - candidatesGridPane.getWidth();
        candidatesGridPane.setLayoutX(candidatesGridPaneX);
        double buttonsHBoxLayoutX2 = (FXRouter.scene.widthProperty().doubleValue() / 2) - buttonsHBox.getWidth() / 2;
        buttonsHBox.setLayoutX(buttonsHBoxLayoutX2);
        double goBackBtnX = (FXRouter.scene.widthProperty().doubleValue() / 2) - goBackBtn.getWidth() / 2;
        goBackBtn.setLayoutX(goBackBtnX);
        FXRouter.scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            scrollPane.setPrefWidth((double) newValue - 10);
            anchorScrollPane.setPrefWidth((double) newValue);
            double titleLabelLayoutX = (newValue.doubleValue() / 2) - titleLabel.getWidth() / 2;
            titleLabel.setLayoutX(titleLabelLayoutX);
            double gridPaneLayoutX = (newValue.doubleValue() / 2) - candidatesGridPane.getWidth() / 2;
            candidatesGridPane.setLayoutX(gridPaneLayoutX);
            double buttonsHBoxLayoutX = (newValue.doubleValue() / 2) - buttonsHBox.getWidth() / 2;
            buttonsHBox.setLayoutX(buttonsHBoxLayoutX);
            double goBackBtnX2 = (newValue.doubleValue() / 2) - goBackBtn.getWidth() / 2;
            goBackBtn.setLayoutX(goBackBtnX2);
        });
        FXRouter.scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            scrollPane.setPrefHeight((double) newValue - 20);
            anchorScrollPane.setPrefHeight((double) newValue - 10);
        });

        motivationLetterText.setText(cApp.getMotivationLetter());
        viewResumeText.setText(cApp.getResume());
        skillsText.setText("JAVA");
//        if (candidate.getSkills() != null) {
//            for (Skill skill : candidate.getSkills()) {
//                skillsText.setText(skillsText.getText() + " " + skill.name());
//            }
//        }
    }

    @FXML
    private void onRefuseCandidacyBtnClicked(ActionEvent event) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox(20);
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Insert reason for refusal here:"), 0, 0);
        JFXTextArea refusalTextArea = new JFXTextArea();
        gridPane.add(refusalTextArea, 1, 0);
        gridPane.setPadding(new Insets(20));
        JFXButton validateBtn = new JFXButton();
        validateBtn.setText("Submit");
        gridPane.setHgap(40);
        validateBtn.setStyle("-fx-background-color: white;");
        dialogVbox.setPadding(new Insets(20));
        dialogVbox.getChildren().add(gridPane);
        dialogVbox.getChildren().add(validateBtn);
        Scene dialogScene = new Scene(dialogVbox, 800, 200);
        dialog.setScene(dialogScene);
        dialog.show();
        validateBtn.setOnAction((validateBtnEvent) -> {
            if (refusalTextArea.getText().isEmpty()) {
                CreateAlert.CreateAlert(Alert.AlertType.ERROR, "Error encountered", "Incomplete info",
                        "You need to specifiy a reason for the refusal");
            } else {
                try {
                    String jndiName = "piJEE-ejb-1.0/CandidateApplicationFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateApplicationFacadeRemote";
                    CandidateApplicationFacadeRemote candidateApplicationFacade = (CandidateApplicationFacadeRemote) context.lookup(jndiName);
//                    CandidateApplication cApp = candidateApplicationFacade.getApplicationByCandidateId(candidate.getId(), jobOffer.getId());
                    System.out.println("hedha capp id li jebou context : " + cApp.getId());
                    cApp.setAdditionalInfo(refusalTextArea.getText());
                    cApp.setJobApplicationState(JobApplicationState.REFUSED);
                    candidateApplicationFacade.updateCandidateApplication(cApp.getId(), cApp.getAdditionalInfo(), cApp.getJobApplicationState());
                    inviteBtn.setDisable(true);
                    refuseCandidacyBtn.setDisable(true);
                    addInfoLabel.setText(refusalTextArea.getText());
                    appStatusLabel.setText(JobApplicationState.REFUSED.name());
                } catch (NamingException ex) {
                    Logger.getLogger(JobOfferCandidateDetailsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    @FXML
    private void onInviteBtnClicked(ActionEvent event) throws IOException, NamingException {
        Optional<ButtonType> alertResult = CreateAlert.CreateAlert(Alert.AlertType.CONFIRMATION, "Confirmation needed", "Please confirm this decision.", "You're inviting this applicant to take the job offer's quiz. Proceed?");
        if (alertResult.isPresent() && alertResult.get() == ButtonType.OK) {
            String jndiName = "piJEE-ejb-1.0/CandidateApplicationFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateApplicationFacadeRemote";
            CandidateApplicationFacadeRemote candidateApplicationFacade = (CandidateApplicationFacadeRemote) context.lookup(jndiName);
//            CandidateApplication cApp = candidateApplicationFacade.getApplicationByCandidateId(candidate.getId(), jobOffer.getId());
            System.out.println("hedha capp id li jebou el fxruter get data : " + cApp.getId());
            System.out.println("hedha job id mel invite quiz!! : " + jobOffer.getId());
            cApp.setAdditionalInfo("Quizzes passed: 0");
            cApp.setJobApplicationState(JobApplicationState.INVITED_FOR_QUIZ);
            candidateApplicationFacade.updateCandidateApplication(cApp.getId(), cApp.getAdditionalInfo(), cApp.getJobApplicationState());
            System.out.println("candidate email " + candidate.getEmail());
            candidateApplicationFacade.sendMail(candidate.getEmail(), "You're invited to pass a quiz!", "Good morning, "
                    + "you've been invited to pass the quiz for the job offer: " + jobOffer.getTitle() + ".\n"
                    + "Please login to your account and consult "
                    + "the My Job Applications window to start your quiz");
            Map<Context, JobOffer> dataMap = new HashMap<>();
            dataMap.put(context, jobOffer);
            FXRouter.when("ListJobOfferCandidates", "JobOfferCandidateDetails.fxml");
            FXRouter.setRouteContainer("ListJobOfferCandidates", anchorPane);
            FXRouter.goTo("ListJobOfferCandidates", dataMap);
        }
    }

    @FXML
    private void onGoBackBtn(ActionEvent event) throws IOException {
        Map<Context, JobOffer> dataMap = new HashMap<>();
        dataMap.put(context, jobOffer);
        FXRouter.goTo("ListJobOfferCandidates", dataMap);
    }

}
