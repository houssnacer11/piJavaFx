/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import tn.esprit.overpowered.byusforus.entities.quiz.QuizTry;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class QuizResultsController implements Initializable {

    @FXML
    private Label resultLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView confettiImageView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        double resultLabelLayoutX1 = (FXRouter.scene.widthProperty().doubleValue() / 2) - resultLabel.getWidth() / 2;
        resultLabel.setLayoutX(resultLabelLayoutX1);
        double infoLabelLayoutX1 = (FXRouter.scene.widthProperty().doubleValue() / 2) - infoLabel.getWidth() / 2;
        infoLabel.setLayoutX(infoLabelLayoutX1);
        confettiImageView.setPreserveRatio(true);
        confettiImageView.fitWidthProperty().bind(FXRouter.scene.widthProperty());
        confettiImageView.fitHeightProperty().bind(FXRouter.scene.heightProperty());
        FXRouter.scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            double resultLabelLayoutX = (newValue.doubleValue() / 2) - resultLabel.getWidth() / 2;
            resultLabel.setLayoutX(resultLabelLayoutX);
            double infoLabelLayoutX = (newValue.doubleValue() / 2) - infoLabel.getWidth() / 2;
            infoLabel.setLayoutX(infoLabelLayoutX);
            confettiImageView.fitWidthProperty().bind(FXRouter.scene.widthProperty());
        });
        FXRouter.scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            confettiImageView.fitHeightProperty().bind(FXRouter.scene.heightProperty());
        });
        QuizTry quizTry = (QuizTry) FXRouter.getData();
        float score = quizTry.getPercentage();
        float percentageToPass = quizTry.getQuiz().getPercentageToPass();
        System.out.println("Score user: " + score);
        System.out.println("score to pass : " + percentageToPass);
        if (score >= percentageToPass) {
            resultLabel.setText("Congratulations!");
            infoLabel.setText("You passed this quiz with a " + score + "% score ");
        } else {
            confettiImageView.setVisible(false);
            resultLabel.setText("You didn't pass.");
            infoLabel.setText("Sorry to inform you that your score " + score + "% is less than the "
                    + "required score to pass this quiz. ");

        }
    }

}
