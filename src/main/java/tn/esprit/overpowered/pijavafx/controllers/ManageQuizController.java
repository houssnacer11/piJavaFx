/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javax.naming.Context;
import javax.naming.NamingException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tn.esprit.overpowered.byusforus.entities.entrepriseprofile.JobOffer;
import tn.esprit.overpowered.byusforus.entities.quiz.Quiz;
import tn.esprit.overpowered.byusforus.services.quiz.QuizFacadeRemote;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class ManageQuizController implements Initializable {

    @FXML
    private Label titleLabel;
    @FXML
    private GridPane quizGridPane;
    @FXML
    private TextArea quizDetailsTextField;
    @FXML
    private TextField quizNameTextField;
    @FXML
    private TextField minScoreTextField;
    @FXML
    private JFXButton nextBtn;
    @FXML
    private JFXButton createQuizBtn;
    @FXML
    private HBox noQuizExistsHBox;

    private Context context;
    private JobOffer jobOffer;
    @FXML
    private JFXButton goBackBtn;
    @FXML
    private AnchorPane centerAnchor;
    private String quizNameJ;
    private Quiz quizFromDB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Map<Context, JobOffer> dataMap = (HashMap) FXRouter.getData();
        context = dataMap.keySet().stream().findFirst().get();
        jobOffer = dataMap.values().stream().findFirst().get();
        titleLabel.setText(titleLabel.getText() + " " + jobOffer.getTitle());
        String jndiName = "piJEE-ejb-1.0/QuizFacade!tn.esprit.overpowered.byusforus.services.quiz.QuizFacadeRemote";
        QuizFacadeRemote quizFacade = null;
        try {
            quizFacade = (QuizFacadeRemote) context.lookup(jndiName);
        } catch (NamingException ex) {
            Logger.getLogger(ManageQuizController.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONParser jsonParser = new JSONParser();
        Boolean hasQuiz = false;
        try (FileReader reader = new FileReader("quiz_joboffer.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray quizJList = (JSONArray) obj;
            System.out.println(quizJList);
            quizJList.forEach((t) -> {

            });
            //Iterate over employee array
            for (Object quiz : quizJList) {
                JSONObject quizJson = (JSONObject) quiz;
                Long jobOfferId = (Long) quizJson.get("jobOfferId");
                if (jobOfferId == jobOffer.getId()) {
                    quizNameJ = (String) quizJson.get("quizName");
                    hasQuiz = true;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (hasQuiz) {
            quizFromDB = quizFacade.getQuizByName(quizNameJ);
            quizGridPane.setVisible(true);
            createQuizBtn.setVisible(false);
            noQuizExistsHBox.setVisible(false);
            minScoreTextField.setText(Float.toString(quizFromDB.getPercentageToPass()));
            quizNameTextField.setText(quizFromDB.getName());
            quizDetailsTextField.setText(quizFromDB.getDetails());
        } else {
            nextBtn.setVisible(false);
            quizGridPane.setVisible(false);
        }

    }

    @FXML
    private void onNextBtn(ActionEvent event) throws IOException {
        quizFromDB.setDetails(quizDetailsTextField.getText());
        quizFromDB.setName(quizNameTextField.getText());
        quizFromDB.setPercentageToPass(Float.parseFloat(minScoreTextField.getText()));
        Map<Context, Map<JobOffer, Quiz>> dataMap = new HashMap<>();
        Map<JobOffer, Quiz> secondMap = new HashMap<>();
        secondMap.put(jobOffer, quizFromDB);
        dataMap.put(context, secondMap);
        FXRouter.when("EditQuestions", "EditQuestions.fxml");
        FXRouter.setRouteContainer("EditQuestions", centerAnchor);
        FXRouter.goTo("EditQuestions", dataMap);
    }

    @FXML
    private void onCreateQuizBtnClicked(ActionEvent event) throws IOException {
        System.out.println("clicked!!");
        Map<Context, JobOffer> dataMap = new HashMap<>();
        dataMap.put(context, jobOffer);
        FXRouter.when("CreateQuiz2", "CreateQuiz.fxml");
        FXRouter.setRouteContainer("CreateQuiz2", centerAnchor);
        FXRouter.goTo("CreateQuiz2", dataMap);
    }

    @FXML
    private void onGoBackBtnClicked(ActionEvent event) throws IOException {
        FXRouter.goTo("BaseView");

    }

}
