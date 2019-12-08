/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.github.sarxos.webcam.Webcam;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tn.esprit.overpowered.byusforus.entities.entrepriseprofile.JobOffer;
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
public class QuizInfoController implements Initializable {

    @FXML
    private Button startQuizBtn;
    @FXML
    private Label quizName;
    @FXML
    private Label numberOfQuestions;
    @FXML
    private Label minScore;
    @FXML
    private TextArea quizDetails;

    private List<Question> questions;
    private Quiz quizO;
    @FXML
    private AnchorPane anchorPane;
    private JobOffer jobOffer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            jobOffer = (JobOffer) FXRouter.getData();
            System.out.println("hedha job offer id mel quiz info conte" + jobOffer.getId());
            FXRouter.when("TryQuiz", "TryQuiz.fxml");
            FXRouter.setRouteContainer("TryQuiz", anchorPane);
            String jndiName = "piJEE-ejb-1.0/QuizFacade!tn.esprit.overpowered.byusforus.services.quiz.QuizFacadeRemote";
            Context context = new InitialContext();
            QuizFacadeRemote quizFacadeProxy = (QuizFacadeRemote) context.lookup(jndiName);
            JSONParser jsonParser = new JSONParser();
            try (FileReader reader = new FileReader("quiz_joboffer.json")) {
                //Read JSON file
                Object obj = jsonParser.parse(reader);
                JSONArray quizJList = (JSONArray) obj;
                System.out.println(quizJList);
                for (Object quiz : quizJList) {
                    JSONObject quizJson = (JSONObject) quiz;
                    Long jobOfferId = (Long) quizJson.get("jobOfferId");
                    String quizNamesss = (String) quizJson.get("quizName");
                    if (jobOfferId == jobOffer.getId()) {
                        quizO = quizFacadeProxy.getQuizByName(quizNamesss);
                    }
                }

            } catch (FileNotFoundException e) {
            } catch (IOException | ParseException e) {
            }

//            quiz = quizFacadeProxy.findAll().get(0);
            questions = quizO.getQuestions();
            quizName.setText(quizO.getName());
            quizDetails.setText(quizO.getDetails());
            numberOfQuestions.setText(Integer.toString(questions.size()));
            minScore.setText(Float.toString(quizO.getPercentageToPass()));
        } catch (NamingException ex) {
            Logger.getLogger(QuizInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onStartQuizBtnClicked(ActionEvent event) throws IOException {
        if (Webcam.getWebcams().isEmpty()) {
            CreateAlert.CreateAlert(Alert.AlertType.ERROR, "Error!", "You need to have a webcam to continue.",
                    "We we weren't able to detect a webcam on your system. Please add a webcam then retry.");
        } else {
            Optional<ButtonType> alertResult = CreateAlert.CreateAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "We need your permission.", "In order to take the quiz, we need access to your webcam feed. Do you accept?");
            if (alertResult.isPresent() && alertResult.get() == ButtonType.OK) {
                quizO.setJobOffer(jobOffer);
                FXRouter.goTo("TryQuiz", quizO);
            }
        }
    }

}
