/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jfoenix.controls.JFXButton;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
import tn.esprit.overpowered.byusforus.util.JobApplicationState;
import util.authentication.Authenticator;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class JobApplicationController implements Initializable {

    @FXML
    private Label jobAppTitle;
    @FXML
    private TextArea motivationLetter;
    @FXML
    private Label name;
    @FXML
    private Label email;
    @FXML
    private Label resume;
    private Context context;
    @FXML
    private JFXButton goBack;
    @FXML
    private JFXButton submitApp;
    private JobOffer jobOffer;
    private Candidate cdt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Map<Context, JobOffer> dataMap = (HashMap) FXRouter.getData();
        context = dataMap.keySet().stream().findFirst().get();
        jobOffer = dataMap.values().stream().findFirst().get();
        cdt = (Candidate) Authenticator.currentUser;
        name.setText(cdt.getFirstName() + " " + cdt.getLastName());
        email.setText(cdt.getEmail());
        resume.setText(cdt.getCurriculumVitaes());
        jobAppTitle.setText(jobOffer.getTitle() + " " + jobAppTitle.getText());

    }

    @FXML
    private void onGoBackBtnClicked(ActionEvent event) {
    }

    @FXML
    private void onSubmitAppBtnClicked(ActionEvent event) throws IOException, NamingException {
        CandidateApplication cApp = new CandidateApplication();
        cApp.setJobApplicationState(JobApplicationState.PENDING);
        cApp.setMotivationLetter(motivationLetter.getText());
        cApp.setCandidate(null);
        cApp.setJobOffer(null);
        Candidate cand = (Candidate) Authenticator.currentUser;
        cApp.setResume(cand.getCurriculumVitaes());
        cApp.setQuizzesTaken(null);
//        mapper.writeValue(new File(fileName + ".json"), cApp);
//        CandidateApplication cApp2 = mapper.readValue(new File(fileName + ".json"), CandidateApplication.class);
//        System.out.println("this is quiz try from json : \n " + cApp2);
        String fileName = "candidate_apps";
        JSONParser jsonParser = new JSONParser();
        JSONArray quizJList = null;
        try (FileReader reader = new FileReader("candidate_apps.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            quizJList = (JSONArray) obj;
            System.out.println(quizJList);
            JSONObject quizAppJSON = new JSONObject();
            quizAppJSON.put("candidateId", Authenticator.currentUser.getId());
            quizAppJSON.put("jobOfferId", jobOffer.getId());
            quizAppJSON.put("cAppMotivationLetter", motivationLetter.getText());
            quizJList.add(quizAppJSON);

        } catch (FileNotFoundException e) {
        } catch (IOException | ParseException e) {
        }
        try (FileWriter file = new FileWriter(fileName + ".json")) {
            file.write(quizJList.toJSONString());
            file.flush();
        } catch (IOException e) {
        }

        String jndiName = "piJEE-ejb-1.0/CandidateApplicationFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateApplicationFacadeRemote";
        CandidateApplicationFacadeRemote candidateApplicationFacade = (CandidateApplicationFacadeRemote) context.lookup(jndiName);
        cApp.setCandidate(null);
        candidateApplicationFacade.create(cApp);
    }

}
