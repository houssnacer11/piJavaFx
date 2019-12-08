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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javax.naming.Context;
import javax.naming.NamingException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tn.esprit.overpowered.byusforus.entities.candidat.CandidateApplication;
import tn.esprit.overpowered.byusforus.entities.entrepriseprofile.JobOffer;
import tn.esprit.overpowered.byusforus.entities.users.Candidate;
import tn.esprit.overpowered.byusforus.entities.util.Skill;
import tn.esprit.overpowered.byusforus.services.candidat.CandidateApplicationFacadeRemote;
import tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class ListJobOfferCandidatesController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private GridPane candidatesGridPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchorScrollPane;
    @FXML
    private Label titleLabel;
    private Context context;
    private JobOffer jobOffer;
    private String cAppMotivationLetter;
    private List<CandidateApplication> listApplied = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Map<Context, JobOffer> dataMap = (HashMap) FXRouter.getData();
        jobOffer = dataMap.values().stream().findFirst().get();
        context = dataMap.keySet().stream().findFirst().get();
        titleLabel.setText(titleLabel.getText() + jobOffer.getTitle());
        scrollPane.setPrefHeight((double) FXRouter.scene.heightProperty().doubleValue() - 20);
        anchorScrollPane.setPrefHeight((double) FXRouter.scene.heightProperty().doubleValue() - 10);
        scrollPane.setPrefWidth((double) FXRouter.scene.widthProperty().doubleValue() - 55);
        anchorScrollPane.setPrefWidth((double) FXRouter.scene.widthProperty().doubleValue());
        FXRouter.scene.widthProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("scene width changed: " + newValue);
            scrollPane.setPrefWidth((double) newValue - 55);
            anchorScrollPane.setPrefWidth((double) newValue);
            double titleLabelLayoutX = (newValue.doubleValue() / 2) - titleLabel.getWidth() / 2;
            titleLabel.setLayoutX(titleLabelLayoutX);
            double gridPaneLayoutX = (newValue.doubleValue() / 2) - candidatesGridPane.getWidth() / 2;
            candidatesGridPane.setLayoutX(gridPaneLayoutX);
        });
        FXRouter.scene.heightProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("scene height changed: " + newValue);
            scrollPane.setPrefHeight((double) newValue - 20);
            anchorScrollPane.setPrefHeight((double) newValue - 10);
        });
        int index = 0;
        int colIndex = 0;
        System.out.println("job offer id " + jobOffer.getId());
        String cAppJndiName = "piJEE-ejb-1.0/CandidateApplicationFacade!tn.esprit.overpowered."
                + "byusforus.services.candidat.CandidateApplicationFacadeRemote";
        CandidateApplicationFacadeRemote cAppFacade = null;
        try {
            cAppFacade = (CandidateApplicationFacadeRemote) context.lookup(cAppJndiName);
        } catch (NamingException ex) {
            Logger.getLogger(ListJobOfferCandidatesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Boolean isApplied = false;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("candidate_apps.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray quizJList = (JSONArray) obj;
            System.out.println(quizJList);
            for (Object quiz : quizJList) {
                isApplied = false;
                JSONObject quizJson = (JSONObject) quiz;
                Long jobOfferId = (Long) quizJson.get("jobOfferId");
                Long candidateId = (Long) quizJson.get("candidateId");
                if (jobOfferId == jobOffer.getId()) {
                    cAppMotivationLetter = (String) quizJson.get("cAppMotivationLetter");
                    isApplied = true;
                }
                if (isApplied) {
                    String jndiName = "piJEE-ejb-1.0/CandidateApplicationFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateApplicationFacadeRemote";
                    String cjndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote";
                    CandidateApplicationFacadeRemote candidateApplicationFacade = (CandidateApplicationFacadeRemote) context.lookup(jndiName);
                    CandidateFacadeRemote candidateFacade = (CandidateFacadeRemote) context.lookup(cjndiName);
                    CandidateApplication cApp = candidateApplicationFacade.getCAppByMotivLetter(cAppMotivationLetter);
                    cApp.setCandidate(candidateFacade.find(candidateId));
                    listApplied.add(cApp);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ListJobOfferCandidatesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException | NamingException ex) {
            Logger.getLogger(ListJobOfferCandidatesController.class.getName()).log(Level.SEVERE, null, ex);
        }

//            List<CandidateApplication> cAppList = cAppFacade.getCandidateApplicationByJobOFfer(jobOffer.getId());
        for (CandidateApplication candidateApp : listApplied) {
            Candidate candidate = candidateApp.getCandidate();
            VBox candidateHBox = new VBox();
            HBox btnHBox = new HBox();
            Label nameLabel = new Label("Name: " + candidate.getFirstName() + " " + candidate.getLastName());
            double skillsScore = 0;
            Set<Skill> jobSkillSet = new HashSet<>();
            jobSkillSet.add(Skill.C);
            jobSkillSet.add(Skill.JAVA);
            Set<Skill> candidateSkillSet = new HashSet<>();
            jobSkillSet.add(Skill.JAVA);
            jobOffer.setSkills(jobSkillSet);
            candidate.setSkills(candidateSkillSet);
            if (candidate.getSkills() == null || jobOffer.getSkills() == null) {
                System.out.println("wahda menhom null");
                skillsScore = 0;
            } else {
                skillsScore = calculateCandidateMatchPercentage(candidate.getSkills(), jobOffer.getSkills());
            }
            Label skillsLabel = new Label("Skills match: " + 100 * skillsScore + "%");
            Label stateLabel = new Label("Status: " + candidateApp.getJobApplicationState().name());
            JFXButton detailsBtn = new JFXButton("See More Details");
            detailsBtn.setStyle("-fx-background-color: white;");
            nameLabel.setFont(Font.font("System", 17));
            skillsLabel.setFont(Font.font("System", 17));
            stateLabel.setFont(Font.font("System", 17));
            detailsBtn.setFont(Font.font("System", 10));
            candidateHBox.getChildren().add(nameLabel);
            candidateHBox.getChildren().add(skillsLabel);
            candidateHBox.getChildren().add(stateLabel);
            btnHBox.getChildren().add(detailsBtn);
//            btnHBox.setAlignment(Pos.CENTER_RIGHT);
            candidateHBox.getChildren().add(btnHBox);
            if (colIndex % 2 == 0) {
                candidatesGridPane.add(candidateHBox, 0, index);
                colIndex = 1;
            } else {
                candidatesGridPane.add(candidateHBox, 1, index);
                colIndex = 0;
                index++;
            }
            detailsBtn.setOnAction((event) -> {
                try {
                    Map<Context, CandidateApplication> dataMap2 = new HashMap<>();
                    dataMap2.put(dataMap.keySet().stream().findFirst().get(), candidateApp);
                    FXRouter.when("JobOfferCandidateDetails", "JobOfferCandidateDetails.fxml");
                    FXRouter.setRouteContainer("JobOfferCandidateDetails", anchorPane);
                    FXRouter.goTo("JobOfferCandidateDetails", dataMap2);
                } catch (IOException ex) {
                    Logger.getLogger(ListJobOfferCandidatesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        // anchorPane.getChildren().add(gridPane);
    }

    public double calculateCandidateMatchPercentage(Set<Skill> candidateSkillSet, Set<Skill> requiredSkillSet) {
        if (candidateSkillSet == null) {
            return 0d;
        }
        int requiredSkillsNumber = requiredSkillSet.size();
        int candidateMatchedSkills = 0;
        for (Skill candidateSkill : candidateSkillSet) {
            for (Skill requiredSkill : requiredSkillSet) {
                if (candidateSkill.toString().equals(requiredSkill.toString())) {
                    candidateMatchedSkills++;
                }
            }
        }
        return (double) candidateMatchedSkills / requiredSkillsNumber;
    }

}
