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
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tn.esprit.overpowered.byusforus.entities.candidat.CandidateApplication;
import tn.esprit.overpowered.byusforus.entities.entrepriseprofile.JobOffer;
import tn.esprit.overpowered.byusforus.services.candidat.CandidateApplicationFacadeRemote;
import tn.esprit.overpowered.byusforus.services.entrepriseprofile.JobOfferFacadeRemote;
import tn.esprit.overpowered.byusforus.services.quiz.QuizFacadeRemote;
import tn.esprit.overpowered.byusforus.util.JobApplicationState;
import util.authentication.Authenticator;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author EliteBook
 */
public class CandidateJobOfferListController implements Initializable {

    @FXML
    private AnchorPane rightMenuAnchorPane;
    @FXML
    private Button homeButton;
    @FXML
    private Button profileButton;
    @FXML
    private AnchorPane topMenuAnchorPane;
    @FXML
    private MenuBar topMenu;
    @FXML
    private TableView<JobOffer> jobsView;
    @FXML
    private TableColumn<?, ?> title;
    @FXML
    private TableColumn<?, ?> offerStatus;
    @FXML
    private TableColumn<?, ?> dateOfCreation;
    @FXML
    private TableColumn<?, ?> city;
    @FXML
    private TableColumn<?, ?> dateOfArchive;
    @FXML
    private TableColumn<?, ?> peopleNeeded;
    @FXML
    private Label status;
    @FXML
    private Button searchButton;
    @FXML
    private AnchorPane parentAnchorPane;
    @FXML
    private Button contactButton;
    @FXML
    private Button jobOffer;
    @FXML
    private Button companyButton;
    @FXML
    private TableColumn<JobOffer, String> jobAppTC;

    private JobOffer jobOfferEnt;

    private String cAppMotivationLetter;
    @FXML
    private AnchorPane centralAnchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FXRouter.when("CreateJobOfferView", "CreateJobOffer.fxml", "JobOffer", 640, 425);
        FXRouter.setRouteContainer("CreateJobOfferView", parentAnchorPane);
        FXRouter.when("CompanyHRProfileView", "CompanyHRProfile.fxml", "Profile", 600, 400);
        FXRouter.setRouteContainer("CompanyHRProfileView", parentAnchorPane);
        FXRouter.when("CompanyPMProfileView", "CompanyPMProfile.fxml", "Profile", 600, 400);
        FXRouter.setRouteContainer("CompanyPMProfileView", parentAnchorPane);
        FXRouter.when("BaseView", "Base.fxml", "HOME", 800, 600);
        FXRouter.setRouteContainer("BaseView", parentAnchorPane);
        try {
            String jndiName = "piJEE-ejb-1.0/JobOfferFacade!tn.esprit.overpowered.byusforus.services.entrepriseprofile.JobOfferFacadeRemote";
            Context context = new InitialContext();
            JobOfferFacadeRemote jobOfferProxy = (JobOfferFacadeRemote) context.lookup(jndiName);
            List<JobOffer> list = jobOfferProxy.viewAllOffers();
            if (list.isEmpty()) {
                System.out.println("EMPTY");
            }
            //System.out.println("THE LOCATION ISSSSSSSSSS: " + list.get(0).getCity());
            ObservableList<JobOffer> offerObs = FXCollections.observableArrayList();

            for (JobOffer o : list) {
                offerObs.add(o);
            }
            title.setCellValueFactory(new PropertyValueFactory<>("Title"));
            offerStatus.setCellValueFactory(new PropertyValueFactory<>("offerStatus"));
            dateOfCreation.setCellValueFactory(new PropertyValueFactory<>("dateOfCreation"));
            city.setCellValueFactory(new PropertyValueFactory<>("city"));
            dateOfArchive.setCellValueFactory(new PropertyValueFactory<>("dateOfArchive"));
            peopleNeeded.setCellValueFactory(new PropertyValueFactory<>("peopleNeeded"));
            Callback<TableColumn<JobOffer, String>, TableCell<JobOffer, String>> cellFactory
                    = //
                    new Callback<TableColumn<JobOffer, String>, TableCell<JobOffer, String>>() {
                @Override
                public TableCell call(final TableColumn<JobOffer, String> param) {
                    final TableCell<JobOffer, String> cell = new TableCell<JobOffer, String>() {

                        final JFXButton btn = new JFXButton("");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            Boolean isApplied = false;
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                JSONParser jsonParser = new JSONParser();
                                try (FileReader reader = new FileReader("candidate_apps.json")) {
                                    //Read JSON file
                                    Object obj = jsonParser.parse(reader);
                                    jobOfferEnt = getTableView().getItems().get(getIndex());

                                    JSONArray quizJList = (JSONArray) obj;
                                    System.out.println(quizJList);
                                    quizJList.forEach((t) -> {

                                    });
                                    //Iterate over employee array
                                    for (Object quiz : quizJList) {
                                        JSONObject quizJson = (JSONObject) quiz;
                                        Long jobOfferId = (Long) quizJson.get("jobOfferId");
                                        Long candidateId = (Long) quizJson.get("candidateId");
                                        if (jobOfferId == jobOfferEnt.getId() && candidateId == Authenticator.currentUser.getId()) {
                                            cAppMotivationLetter = (String) quizJson.get("cAppMotivationLetter");
                                            isApplied = true;
                                        }
                                    }
                                    if (isApplied) {
                                        String jndiName = "piJEE-ejb-1.0/CandidateApplicationFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateApplicationFacadeRemote";
                                        CandidateApplicationFacadeRemote candidateApplicationFacade = (CandidateApplicationFacadeRemote) context.lookup(jndiName);
                                        CandidateApplication cApp = candidateApplicationFacade.getCAppByMotivLetter(cAppMotivationLetter);
                                        JobApplicationState jAppState = cApp.getJobApplicationState();
                                        btn.setText(jAppState.name());
                                        if (jAppState == JobApplicationState.INVITED_FOR_QUIZ) {
                                            btn.setText("Take Quiz");
                                            btn.setId("a");
                                            btn.setStyle("-fx-background-color: green;");
                                            btn.setTextFill(Paint.valueOf("white"));
                                        } else {
                                            btn.setDisable(true);
                                        }
                                    } else {
                                        btn.setText("Postulate");
                                    }

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                } catch (NamingException ex) {
                                    Logger.getLogger(CandidateJobOfferListController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                btn.setOnAction(event -> {
                                    if (btn.getText().equals("Take Quiz")) {
                                        try {
                                            FXRouter.when("TakeQuiz5", "QuizInfo.fxml");
                                            FXRouter.setRouteContainer("TakeQuiz5", centralAnchorPane);
                                            FXRouter.goTo("TakeQuiz5", getTableView().getItems().get(getIndex()));
                                        } catch (IOException ex) {
                                            Logger.getLogger(CandidateJobOfferListController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } else {
                                        System.out.println("application btn clicked for job offer" + jobOfferEnt.getTitle());
                                        Map<Context, JobOffer> dataMap = new HashMap<>();
                                        dataMap.put(context, getTableView().getItems().get(getIndex()));
                                        try {
                                            FXRouter.when("JobApp", "JobApplication.fxml");
                                            FXRouter.setRouteContainer("JobApp", centralAnchorPane);
                                            FXRouter.goTo("JobApp", dataMap);
                                        } catch (IOException ex) {
                                            Logger.getLogger(OffersController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                });
                                setGraphic(btn);
                                setText(null);
                            }
                        }
                    };
                    return cell;
                }
            };
            System.out.println("Still working at this point");
            jobAppTC.setCellFactory(cellFactory);
            System.out.println("Still working at this point");

            jobsView.setItems(offerObs);

        } catch (NamingException ex) {
            Logger.getLogger(CandidateListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void homeButtonOnClicked(MouseEvent event) throws IOException {
        FXRouter.when("BaseView", "Base.fxml");
        FXRouter.setRouteContainer("BaseView", parentAnchorPane);
        FXRouter.goTo("BaseView");
    }

    @FXML
    private void profileButtonClicked(MouseEvent event) throws IOException {
        FXRouter.when("ProfileViews", "Profile.fxml");
        FXRouter.setRouteContainer("ProfileView", parentAnchorPane);
        FXRouter.goTo("ProfileView");
    }

    @FXML
    private void selected(MouseEvent event) {
    }

    private void viewProfileAction(MouseEvent event) throws IOException {
        FXRouter.when("Profile", "Profile.fxml");
        FXRouter.setRouteContainer("Profile", parentAnchorPane);
        FXRouter.goTo("Profile");
    }

    @FXML
    private void searchButtonClicked(MouseEvent event) {
    }

    @FXML
    private void contactButtonClicked(MouseEvent event) throws IOException {
        FXRouter.when("CandidateListView", "CandidateList.fxml");
        FXRouter.setRouteContainer("CandidateListView", parentAnchorPane);
        FXRouter.goTo("CandidateListView");
    }

    @FXML
    private void jobOfferClicked(MouseEvent event) throws IOException {
        FXRouter.when("JobOfferView", "CandidateJobOfferList.fxml");
        FXRouter.setRouteContainer("JobOfferView", parentAnchorPane);
        FXRouter.goTo("JobOfferView");
    }

    @FXML
    private void companyButtonClicked(MouseEvent event) throws IOException {
        FXRouter.when("CompanyListView", "CompanyList.fxml");
        FXRouter.setRouteContainer("CompanyListView", parentAnchorPane);
        FXRouter.goTo("CompanyListView");
    }

}
