/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.candidat.CandidateApplication;
import tn.esprit.overpowered.byusforus.entities.entrepriseprofile.JobOffer;
import tn.esprit.overpowered.byusforus.entities.quiz.Quiz;
import tn.esprit.overpowered.byusforus.entities.users.Candidate;
import tn.esprit.overpowered.byusforus.entities.users.CompanyAdmin;
import tn.esprit.overpowered.byusforus.entities.users.CompanyProfile;
import tn.esprit.overpowered.byusforus.entities.users.HRManager;
import tn.esprit.overpowered.byusforus.entities.users.ProjectManager;
import tn.esprit.overpowered.byusforus.entities.users.User;
import tn.esprit.overpowered.byusforus.entities.util.ExpertiseLevel;
import tn.esprit.overpowered.byusforus.entities.util.Skill;
import tn.esprit.overpowered.byusforus.services.candidat.CandidateApplicationFacadeRemote;
import tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote;
import tn.esprit.overpowered.byusforus.services.entrepriseprofile.JobOfferFacadeRemote;
import tn.esprit.overpowered.byusforus.services.quiz.QuizFacadeRemote;
import util.authentication.Authenticator;
import util.authentication.CandidateServices;
import util.exceptions.InvalidArgumentException;
import util.exceptions.WidgetNotFoundException;
import util.factories.ChangeDimensions;
import util.factories.ChangeDimensionsFactory;
import util.information.tracker.InfoTracker;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 */
public class BaseController implements Initializable {

    @FXML
    private AnchorPane generalAnchorPane;
//    @FXML
//    private AnchorPane rightMenuAnchorPane;
    @FXML
    private AnchorPane centralAnchorPane;
    @FXML
    private AnchorPane topMenuAnchorPane;
    @FXML
    private MenuBar topMenu;
    @FXML
    private AnchorPane rightMenuAnchorPane;
    @FXML
    private Button profileButton;
    @FXML
    private JFXButton messagesButton;
    @FXML
    private JFXButton notificationsButton;
    @FXML
    private JFXButton createQuizBtn;
    @FXML
    private JFXButton manageCandidacyBtn;

    private Context context;

    private JobOfferFacadeRemote jobOfferFacade;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            context = new InitialContext();
        } catch (NamingException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // First register a new RouteScene
        // Then bind that RouteScene to its container
        FXRouter.when("CreateQuiz", "CreateQuiz.fxml");
        FXRouter.when("CreateQuestions", "CreateQuestions.fxml");
        FXRouter.when("QuizInfo", "QuizInfo.fxml");
        FXRouter.when("QuizResults", "QuizResults.fxml");
        FXRouter.when("ListJobOfferCandidates", "ListJobOfferCandidates.fxml");
        FXRouter.when("JobOfferCandidateDetails", "JobOfferCandidateDetails.fxml");
        FXRouter.when("ProfileView", "Profile.fxml");
        FXRouter.when("CompanyAdminProfileView", "CompanyAdminProfile.fxml");
        FXRouter.when("CompanyHRProfileView", "CompanyHRProfile.fxml");
        FXRouter.when("CompanyPMProfileView", "CompanyPMProfile.fxml");
        FXRouter.setRouteContainer("CompanyAdminProfileView", generalAnchorPane);
        FXRouter.setRouteContainer("CompanyHRProfileView", generalAnchorPane);
        FXRouter.setRouteContainer("CompanyPMProfileView", generalAnchorPane);
        FXRouter.setRouteContainer("ProfileView", generalAnchorPane);
        FXRouter.setRouteContainer("QuizInfo", centralAnchorPane);
        FXRouter.setRouteContainer("QuizResults", centralAnchorPane);
        FXRouter.setRouteContainer("CreateQuiz", centralAnchorPane);
        FXRouter.setRouteContainer("CreateQuestions", centralAnchorPane);

        FXRouter.setRouteContainer("ListJobOfferCandidates", centralAnchorPane);
        FXRouter.setRouteContainer("JobOfferCandidateDetails", centralAnchorPane);
        // registering listeners for resizehttps://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
        ChangeDimensionsFactory cFactory = new ChangeDimensionsFactory();
        ChangeListener<Number> sideMenuChangeListener;
        Scene s = FXRouter.scene;
       
        try {
            Authenticator.currentUser = CandidateServices.findCandidate(1L);
        } catch (NamingException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void onCreateQuizBtnClicked(ActionEvent event) throws IOException, NamingException {
        String jndiName = "piJEE-ejb-1.0/QuizFacade!tn.esprit.overpowered.byusforus.services.quiz.QuizFacadeRemote";
        QuizFacadeRemote quizFacadeProxy = (QuizFacadeRemote) context.lookup(jndiName);
        Map<Context, Quiz> dataMap = new HashMap<>();
        dataMap.put(context, quizFacadeProxy.findAll().get(0));
        FXRouter.goTo("QuizInfo", dataMap);
    }

    public AnchorPane getCentralAnchorPane() {
        return centralAnchorPane;
    }

    public void setCentralAnchorPane(AnchorPane centralAnchorPane) {
        this.centralAnchorPane = centralAnchorPane;

    }

    @FXML

    private void goToInbox(ActionEvent event) throws IOException {
        FXRouter.when("inboxView", "Inbox.fxml");
        FXRouter.setRouteContainer("inboxView", centralAnchorPane);
        FXRouter.goTo("inboxView");
    }

    @FXML
    private void onManageCandidacyBtnClicked(ActionEvent event) throws NamingException, IOException, NamingException, NoSuchAlgorithmException {
        String jndiName = "piJEE-ejb-1.0/JobOfferFacade!tn.esprit.overpowered.byusforus.services."
                + "entrepriseprofile.JobOfferFacadeRemote";
        String cAppJndiName = "piJEE-ejb-1.0/CandidateApplicationFacade!tn.esprit.overpowered."
                + "byusforus.services.candidat.CandidateApplicationFacadeRemote";
        JobOfferFacadeRemote jobOfferFacade = (JobOfferFacadeRemote) context.lookup(jndiName);
        CandidateApplicationFacadeRemote candidateApplicationFacade = (CandidateApplicationFacadeRemote) context.lookup(cAppJndiName);

        CompanyProfile company = new CompanyProfile();
        company.setName("Google");
        List<Candidate> registeredCandidates = new ArrayList<>();
        Candidate c = new Candidate();
        c.setFirstName("Ahmed");
        c.setLastName("Bacha");
        c.setSkills(new HashSet<Skill>(Arrays.asList(Skill.PYTHON)));
        c.setPassword("1234".getBytes());
        c.setUsername("ahmedbacha");
        c.setEmail("yessin.amor@gmail.tn");
        registeredCandidates.add(c);
        registeredCandidates.add(c);
        JobOffer jobOffer = new JobOffer();
        jobOffer.setTitle("Developpeur FULLSTACK JS");
        Set<Skill> skillSet = new HashSet<>();
        skillSet.add(Skill.JAVA);
        skillSet.add(Skill.PYTHON);
        jobOffer.setSkills(skillSet);
        jobOffer.setPeopleNeeded(3);
        jobOffer.setExpertiseLevel(ExpertiseLevel.EXPERT);
        jobOffer.setDescription("The candidate will help us work on Fullstack JS projects.");
        jobOffer.setCity("Tunis");
        jobOffer.setCompany(company);
        jobOffer.setRegisteredCandidates(registeredCandidates);
//        jobOfferFacade.create(jobOffer);
//        CandidateApplication candidateApplication = new CandidateApplication("motiv", "resume.pdf", jobOffer);
//        candidateApplication.setCandidate(c);
//        candidateApplicationFacade.create(candidateApplication);
        Map<Context, JobOffer> dataMap = new HashMap<>();
        dataMap.put(context, jobOfferFacade.findAll().get(0));
        FXRouter.goTo("ListJobOfferCandidates", dataMap);
    }

    private void contactsButtonClicked(MouseEvent event) throws IOException {
        FXRouter.when("CandidateListView", "CandidateList.fxml", "Candidate List", 889, 543);
        FXRouter.setRouteContainer("CandidateListView", generalAnchorPane);
        FXRouter.goTo("CandidateListView");
    }

    @FXML

    private void profileButtonClicked(MouseEvent event) throws IOException, NamingException {
        String type = Authenticator.currentUser.getDiscriminatorValue();
        Long currentUserId = Authenticator.currentUser.getId();
        switch (type) {
            case "CANDIDATE":
                String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote";
                Context context;
                try {
                    context = new InitialContext();
                    CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) context.lookup(jndiName);
                    Candidate cdt = new Candidate();
                    cdt = candidateProxy.find(Authenticator.currentUser.getId());
                    FXRouter.goTo("ProfileView", cdt);
                } catch (NamingException ex) {
                    Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //FXRouter.goTo("ProfileView");
                break;
            case "COMPANY_ADMIN":
                CompanyAdmin compAdmin = InfoTracker.getAdminInformation(currentUserId);
                FXRouter.goTo("CompanyAdminProfileView", compAdmin);
                break;
            case "HUMAN_RESOURCES_MANAGER":
                System.out.println("THIS IS UR IDDDDDDDDDDD  " + currentUserId);
                HRManager hrManager = InfoTracker.getHRInformation(currentUserId);
                FXRouter.goTo("CompanyHRProfileView", hrManager);
                break;
            case "PROJECT_MANAGER":
                ProjectManager pManager = InfoTracker.getPMInformation(currentUserId);
                FXRouter.goTo("CompanyPMProfileView", pManager);
                break;
            default:
                break;
        }

    }

    @FXML
    private void onApplyToJobBtnClicked(ActionEvent event) throws IOException, NamingException {
        FXRouter.when("JobApplication", "JobApplication.fxml");
        FXRouter.setRouteContainer("JobApplication", centralAnchorPane);
        String jndiName = "piJEE-ejb-1.0/JobOfferFacade!tn.esprit.overpowered.byusforus.services."
                + "entrepriseprofile.JobOfferFacadeRemote";
        jobOfferFacade = (JobOfferFacadeRemote) context.lookup(jndiName);
        Map<Context, JobOffer> dataMap = new HashMap<>();
        JobOffer j = jobOfferFacade.findAll().get(0);
        dataMap.put(context, j);
        FXRouter.goTo("JobApplication", dataMap);
    }

    @FXML
    private void onMyJobApplications(ActionEvent event) throws IOException {
        FXRouter.when("CandidateAppliedJobs", "CandidateAppliedJobs.fxml");
        FXRouter.setRouteContainer("CandidateAppliedJobs", centralAnchorPane);
        FXRouter.goTo("CandidateAppliedJobs");
    }

}
