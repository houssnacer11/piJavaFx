/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tn.esprit.overpowered.byusforus.entities.quiz.Choice;
import tn.esprit.overpowered.byusforus.entities.quiz.Question;
import tn.esprit.overpowered.byusforus.entities.quiz.QuestionType;
import tn.esprit.overpowered.byusforus.entities.quiz.Quiz;
import tn.esprit.overpowered.byusforus.services.quiz.ChoiceFacadeRemote;
import tn.esprit.overpowered.byusforus.services.quiz.QuestionFacadeRemote;
import tn.esprit.overpowered.byusforus.services.quiz.QuizFacadeRemote;
import util.factories.CreateAlert;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class CreateQuestionsController implements Initializable {

//    @FXML
//    private Accordion questionsAccordion;
    @FXML
    private AnchorPane mainAnchorPane;

    TextField questionTextField = new TextField();
    TextField pointsTextField;
    TextField choiceTextField;

    private Boolean questionValid;
    private Boolean choicesValid;
    ListView lv = new ListView();
    private Button submitBtn;
    private List<Question> questionsList;
    private List<Choice> choicesList;
    private QuizFacadeRemote quizFacadeProxy;
    private QuestionFacadeRemote questionFacadeProxy;
    private ChoiceFacadeRemote choiceFacadeProxy;
    @FXML
    private Button submitQuizBtn;
    private Context context;
    private Quiz quiz;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Map<Context, Quiz> dataMap = (HashMap) FXRouter.getData();
        context = dataMap.keySet().stream().findFirst().get();
        quiz = dataMap.values().stream().findFirst().get();
        choicesList = new ArrayList<>();
        questionsList = new ArrayList<>();
        pointsTextField = new TextField();
        choiceTextField = new TextField();
        String quizJndiName = "piJEE-ejb-1.0/QuizFacade!tn.esprit.overpowered.byusforus.services.quiz.QuizFacadeRemote";
        String questionJndiName = "piJEE-ejb-1.0/QuestionFacade!tn.esprit.overpowered.byusforus.services.quiz.QuestionFacadeRemote";
        String choiceJndiName = "piJEE-ejb-1.0/ChoiceFacade!tn.esprit.overpowered.byusforus.services.quiz.ChoiceFacadeRemote";
        quizFacadeProxy = null;
        questionFacadeProxy = null;
        choiceFacadeProxy = null;
        try {
            Context context = new InitialContext();
            quizFacadeProxy = (QuizFacadeRemote) context.lookup(quizJndiName);
            questionFacadeProxy = (QuestionFacadeRemote) context.lookup(questionJndiName);
            choiceFacadeProxy = (ChoiceFacadeRemote) context.lookup(choiceJndiName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        questionValid = false;
        choicesValid = false;
        submitBtn = new Button("Submit");
        submitBtn.setDisable(true);

        // Integer input control
        pointsTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    pointsTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        // Quiz name textfield length input control
        questionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 100) {
                CreateAlert.CreateAlert(Alert.AlertType.ERROR, "Error!", "Input Error!", "Length needs to be < 100 chars");
                questionTextField.setText(questionTextField.getText().substring(0, 100));
            }
            questionValid = true;
            updateBtnState(submitBtn);
        });

        fillGridPane();

    }

    public void fillGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.add(new Label("Question:"), 0, 0);
        gridPane.add(new Label("Points"), 0, 1);
        gridPane.add(new Label("Choices"), 0, 2);
        Button addChoiceBtn = new Button("Add");
        gridPane.setHalignment(addChoiceBtn, HPos.RIGHT);
        gridPane.add(questionTextField, 1, 0);
        gridPane.add(pointsTextField, 1, 1);
        gridPane.add(choiceTextField, 1, 2);
        gridPane.add(addChoiceBtn, 1, 2);

        addChoiceBtn.setOnAction((event) -> {
            HBox choiceHBox = new HBox();
            choiceHBox.getChildren().add(new Label(choiceTextField.getText()));

            Button deleteChoiceBtn = new Button("Delete");
            Button markAsCorrectChoiceBtn = new Button("Mark as correct answer");
            choiceHBox.getChildren().add(markAsCorrectChoiceBtn);
            choiceHBox.getChildren().add(deleteChoiceBtn);
            choiceHBox.setSpacing(10);
            Choice choice = new Choice();
            deleteChoiceBtn.setOnAction((deleteChoiceBtnClicked) -> {
                lv.getItems().remove(choiceHBox);
                updateBtnState(submitBtn);
                choicesList.remove(choice);
            });
            choice.setChoiceText(choiceTextField.getText());
            markAsCorrectChoiceBtn.setOnAction((correct) -> {
                choice.setIsCorrectChoice(!choice.getIsCorrectChoice());
                if (choice.getIsCorrectChoice()) {
                    markAsCorrectChoiceBtn.setText("Unmark");
                } else {
                    markAsCorrectChoiceBtn.setText("Mark as correct answer");

                }
            });
            lv.getItems().add(choiceHBox);
            choicesList.add(choice);
            updateBtnState(submitBtn);
            choiceTextField.setText("");
        });

        //choicesHbox.setSpacing(10);
        gridPane.add(lv, 1, 3);
        gridPane.add(submitBtn, 1, 4);
        gridPane.getColumnConstraints().add(new ColumnConstraints(74.0));
        gridPane.getColumnConstraints().add(new ColumnConstraints(378));
        gridPane.setVgap(5);

        submitBtn.setOnAction(
                (event) -> {
                    Question question = new Question();
                    question.setQuestionPoints(Integer.parseInt(pointsTextField.getText()));
                    question.setQuestionText(questionTextField.getText());
                    question.setQuestionType(QuestionType.MULTI_ANSWER);
                    question.setChoices(choicesList);
                    questionsList.add(question);
                    System.out.println("Question: " + questionTextField.getText());
                    CreateAlert.CreateAlert(Alert.AlertType.INFORMATION, "Success", "Successfull operation", "Question submitted successfully.");
                    questionTextField.setText("");
                    lv.getItems().removeAll(lv.getItems());
                    pointsTextField.setText("");
                    choiceTextField.setText("");
                    choicesList = new ArrayList<>();

                }
        );

        mainAnchorPane.getChildren().add(gridPane);

    }

    public void updateBtnState(Button submitBtn) {
        Boolean choicesValid = true;
        for (Choice c : choicesList) {
            if (c.getIsCorrectChoice() == true) {
                break;
            }
            choicesValid = false;
        }
        if (questionValid && !questionTextField.getText().isEmpty() && !pointsTextField.getText().isEmpty() && lv.getItems().size() > 1 && choicesValid) {
            submitBtn.setDisable(false);
        } else {
            submitBtn.setDisable(true);

        }
    }

    @FXML
    private void onSubmitQuizBtnClicked(ActionEvent event) {
        quiz.setQuestions(questionsList);
        String fileName = "quiz_joboffer";
        JSONParser jsonParser = new JSONParser();
        JSONArray quizJList = null;
        try (FileReader reader = new FileReader("quiz_joboffer.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            quizJList = (JSONArray) obj;
            System.out.println(quizJList);
            JSONObject quizAppJSON = new JSONObject();
            quizAppJSON.put("quizName", quiz.getName());
            quizAppJSON.put("jobOfferId", quiz.getJobOffer().getId());
            quizJList.add(quizAppJSON);

        } catch (FileNotFoundException e) {
        } catch (IOException | ParseException e) {
        }

        try (FileWriter file = new FileWriter(fileName + ".json")) {
            file.write(quizJList.toJSONString());
            file.flush();
        } catch (IOException e) {
        }
        quiz.setJobOffer(null);
        quizFacadeProxy.create(quiz);
    }

}
