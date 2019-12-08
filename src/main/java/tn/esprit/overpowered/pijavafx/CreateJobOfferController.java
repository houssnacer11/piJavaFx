/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.users.CompanyAdmin;
import tn.esprit.overpowered.byusforus.entities.users.HRManager;
import tn.esprit.overpowered.byusforus.entities.users.ProjectManager;
import tn.esprit.overpowered.byusforus.entities.util.ExpertiseLevel;
import tn.esprit.overpowered.byusforus.entities.util.Skill;
import util.authentication.Authenticator;
import util.information.tracker.InfoTracker;
import util.routers.FXRouter;
import utilJoboffer.HandleOffer;
import utilJoboffer.SkillView;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class CreateJobOfferController implements Initializable {

    ObservableList<String> expertiseLevels = FXCollections.
            observableArrayList("JUNIOR", "ASSOCIATE", "EXPERT");
    ObservableList<SkillView> skills = FXCollections.
            observableArrayList(new SkillView("JAVA"),
                    new SkillView("C"),
                    new SkillView("SOC"), new SkillView("MICROSOFT"), new SkillView("NETWORKING"));
    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField location;
    @FXML
    private JFXComboBox<String> expertiseLevelComboBox;
    @FXML
    private JFXTextField neededCandidates;
    @FXML
    private Button createOfferButton;
    @FXML
    private TableView<SkillView> skillsTable;
    @FXML
    private AnchorPane rightMenuAnchorPane;
    @FXML
    private AnchorPane centralAnchorPane;
    @FXML
    private AnchorPane topMenuAnchorPane;
    @FXML
    private MenuBar topMenu;
    @FXML
    private Button homePageButton;
    @FXML
    private TextArea description;
    @FXML
    private Button myprofileButton;
    @FXML
    private Button offersButton;
    @FXML
    private Button logoutButton;
    @FXML
    private AnchorPane parentAnchorPane;
    @FXML
    private DatePicker expirationDate;
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        FXRouter.when("LoginView", "Login.fxml", "Login", 600, 400);
        FXRouter.setRouteContainer("LoginView", parentAnchorPane);
        FXRouter.when("OffersView", "Offers.fxml", 889, 543);
        FXRouter.setRouteContainer("OffersView", parentAnchorPane);
        expertiseLevelComboBox.setValue("JUNIOR");
        expertiseLevelComboBox.setItems(expertiseLevels);
        TableColumn skillColumn = new TableColumn("SKILLS");
        TableColumn selectColumn = new TableColumn("SELECT");
        skillsTable.getColumns().addAll(skillColumn, selectColumn);

        skillColumn.setCellValueFactory(
                new PropertyValueFactory<SkillView, String>("skill"));
        selectColumn.setCellValueFactory(
                new PropertyValueFactory<SkillView, String>("select"));
        skillsTable.setItems(skills);

    }

    @FXML
    private void createOfferButtonOnClicked(MouseEvent event) throws NamingException, IOException {
        String type = Authenticator.currentUser.getDiscriminatorValue();
        Long currentUserId = Authenticator.currentUser.getId();
        Set<Skill> listofSkills = new HashSet<>();
        skills.stream().filter((skil) -> (skil.getSelect().isSelected())).forEachOrdered((SkillView skil) -> {
            listofSkills.add(Skill.valueOf(skil.getSkill()));
        });
        switch (type) {
            case "COMPANY_ADMIN":
                CompanyAdmin compAdmin = InfoTracker.getAdminInformation(currentUserId);
                FXRouter.goTo("OffersView", compAdmin);
                HandleOffer.createJobOffer(title.getText(), location.getText(),
                        ExpertiseLevel.valueOf(expertiseLevelComboBox.getValue()), listofSkills,
                        Integer.parseInt(neededCandidates.getText()), description.getText(),
                        expirationDate.getValue());
                break;
            case "HUMAN_RESOURCES_MANAGER":
                System.out.println("THIS IS UR IDDDDDDDDDDD  " + currentUserId);
                HandleOffer.createJobOffer(title.getText(), location.getText(),
                        ExpertiseLevel.valueOf(expertiseLevelComboBox.getValue()), listofSkills,
                        Integer.parseInt(neededCandidates.getText()), description.getText(),
                        expirationDate.getValue());
                HRManager hrManager = InfoTracker.getHRInformation(currentUserId);
                FXRouter.goTo("OffersView", hrManager);
                break;
            case "PROJECT_MANAGER":
                ProjectManager pManager = InfoTracker.getPMInformation(currentUserId);
                HandleOffer.requestJobOfferCreation(title.getText(), location.getText(),
                        ExpertiseLevel.valueOf(expertiseLevelComboBox.getValue()), listofSkills,
                        Integer.parseInt(neededCandidates.getText()), description.getText(),
                        expirationDate.getValue());
                FXRouter.goTo("OffersView", pManager);
                break;
            default:
                break;

        }
    }

    @FXML
    private void homePageButton(MouseEvent event
    ) throws IOException {
        FXRouter.when("BaseView", "Base.fxml");
        FXRouter.setRouteContainer("BaseView", parentAnchorPane);
        FXRouter.goTo("BaseView");
    }

    @FXML
    private void myprofileButton(MouseEvent event
    ) throws NamingException, IOException {
        String type = Authenticator.currentUser.getDiscriminatorValue();
        Long currentUserId = Authenticator.currentUser.getId();
        switch (type) {
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
    private void offersButtonOnClicked(MouseEvent event
    ) throws IOException {
        FXRouter.goTo("OffersView");
    }

    @FXML
    private void logoutButtonOnClicked(MouseEvent event) throws IOException {
        FXRouter.goTo("LoginView");
    }

}
