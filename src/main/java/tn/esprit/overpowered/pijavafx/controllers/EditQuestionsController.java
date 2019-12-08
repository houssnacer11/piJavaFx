/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.naming.Context;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.entrepriseprofile.JobOffer;
import tn.esprit.overpowered.byusforus.entities.quiz.Choice;
import tn.esprit.overpowered.byusforus.entities.quiz.Question;
import tn.esprit.overpowered.byusforus.entities.quiz.Quiz;
import tn.esprit.overpowered.byusforus.services.quiz.QuizFacadeRemote;
import util.factories.CreateAlert;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class EditQuestionsController implements Initializable {

    @FXML
    private JFXButton goBackBtn;
    @FXML
    private JFXButton submitChangesBtn;
    @FXML
    private Label titleLabel;
    @FXML
    private GridPane gridPane;
    @FXML
    private JFXButton addQuestionBtn;
    private Quiz quiz;
    private Context context;
    private JobOffer jobOffer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Map<Context, Map<JobOffer, Quiz>> dataMap = (HashMap) FXRouter.getData();
        context = dataMap.keySet().stream().findFirst().get();
        Map<JobOffer, Quiz> secondMap = dataMap.values().stream().findFirst().get();
        quiz = secondMap.values().stream().findFirst().get();
        System.out.println("this is my quiz id from dbb " + quiz.getId());
        jobOffer = secondMap.keySet().stream().findFirst().get();
        titleLabel.setText(titleLabel.getText() + " " + quiz.getName());
        int index = 0;
        int colIndex = 0;
        for (Question questionInList : quiz.getQuestions()) {
            Label questionTextLabel = new Label(questionInList.getQuestionText());
            gridPane.add(questionTextLabel, 0, index);
            VBox choicesVBox = new VBox();

            for (Choice choiceInList : questionInList.getChoices()) {
                HBox choiceHBox = new HBox();
                JFXTextField choiceNameJFXTextField = new JFXTextField(choiceInList.getChoiceText());
                JFXButton correctOrNotBtn = new JFXButton();
                if (choiceInList.getIsCorrectChoice()) {
                    correctOrNotBtn.setText("Unmark as correct");
                } else {
                    correctOrNotBtn.setText("Mark as correct");

                }
                JFXButton deleteChoiceJFXButton = new JFXButton("Delete choice");

                choiceHBox.getChildren().add(choiceNameJFXTextField);
                choiceHBox.getChildren().add(correctOrNotBtn);
                choiceHBox.getChildren().add(deleteChoiceJFXButton);
                if (questionInList.getChoices().size() > 2) {
                    deleteChoiceJFXButton.setVisible(false);
                }
                choicesVBox.getChildren().add(choiceHBox);
                deleteChoiceJFXButton.setOnAction((event) -> {
                    Optional<ButtonType> alertResult = CreateAlert.CreateAlert(Alert.AlertType.CONFIRMATION, "Confirmation",
                            "Confirmation required", "Are you sure you want to delete this choice?");
                    if (alertResult.isPresent() && alertResult.get() == ButtonType.OK) {
                        questionInList.getChoices().remove(choiceInList);
                        choicesVBox.getChildren().remove(choiceHBox);
                    }
                });
            }
            gridPane.add(choicesVBox, 1, index);

            index++;
        }
    }

    @FXML
    private void onGoBackBtnClicked(ActionEvent event) {
    }

    @FXML
    private void onSubmitChangesBtn(ActionEvent event) {
        System.out.println("choices for first question in quiz " + quiz.getQuestions().get(0).getChoices());
        String jndiName = "piJEE-ejb-1.0/QuizFacade!tn.esprit.overpowered.byusforus.services.quiz.QuizFacadeRemote";
        QuizFacadeRemote quizFacade = null;
        try {
            quizFacade = (QuizFacadeRemote) context.lookup(jndiName);
        } catch (NamingException ex) {
            Logger.getLogger(ManageQuizController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void onAddQuestionBtnClicked(ActionEvent event) {
    }

}
