/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers.messaging;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.controlsfx.control.textfield.TextFields;
import tn.esprit.overpowered.byusforus.entities.messaging.Message;
import tn.esprit.overpowered.byusforus.entities.users.Candidate;
import tn.esprit.overpowered.byusforus.entities.users.User;
import tn.esprit.overpowered.byusforus.services.messaging.MessagingRemote;
import util.authentication.Authenticator;
import util.cache.ContextCache;
import util.dataAbstraction.messaging.Conversation;
import util.dataAbstraction.messaging.ConversationCreator;
import util.messsages.MessageDelegate;
import util.messsages.MessageSender;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author aminos
 */
public class InboxController implements Initializable {

    @FXML
    private JFXButton newMessageButton;
    @FXML
    private VBox conversationList;
    @FXML
    private VBox messageList;
    @FXML
    private JFXTextArea enterMessageArea;
    @FXML
    private JFXButton sendButton;
    @FXML
    private JFXTextField userNameText;

    Context messageContext;
    HashMap<String, User> contacts;
    HashMap<String, MessageInfoViewController> conversationBarrier;
    ArrayList<Conversation> aC;
    User to;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conversationBarrier = new HashMap<>();
        aC = new ArrayList<>();
        to = (User) FXRouter.getData();
        if (to != null) {
            userNameText.setText(to.getFirstName() + " " + to.getLastName() + "@" + to.getUsername());
        }
        ArrayList<Message> myMessages = MessageDelegate.getMyMessages(Authenticator.currentUser.getId());
        try {
            updateMessageList(myMessages);
        } catch (IOException ex) {
            Logger.getLogger(InboxController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void updateMessageList(ArrayList<Message> myMessages) throws IOException {
        for (Conversation c : ConversationCreator.create(myMessages)) {      
            System.out.println(c.getNewestMessage().getSentTime() + " " + c.getMessages().get(c.getMessages().size() - 1).getSentTime());
                if (!conversationBarrier.containsKey(c.getUid())) {
                FXMLLoader loader = new FXMLLoader();

                Pane messagePane = (Pane) loader.load(
                        getClass().getResourceAsStream(
                                "/fxml/MessageInfoView.fxml"
                        ));
                MessageInfoViewController cn = loader.getController();
                cn.setConversation(c);
                conversationList.getChildren().add(messagePane);
                conversationBarrier.put(c.getUid(), cn);
                } else {
                    if (!conversationBarrier.get(c.getUid()).getConversation().getNewestMessage().getId().equals(c.getNewestMessage().getId()))
                        conversationBarrier.get(c.getUid()).setConversation(c);
            }

        }

    }

    @FXML
    private void newMessage(ActionEvent event) {
        messageList.getChildren().clear();
        userNameText.setStyle("-fx-text-outer-color: red;");

        if (Authenticator.currentUser.getDiscriminatorValue().equals("CANDIDATE")) {
            Candidate cnd = (Candidate) Authenticator.currentUser;
            System.out.println("********** " + cnd.getContacts().size());
            /* ArrayList<Candidate> ls = new ArrayList<Candidate>();
            ((Candidate) Authenticator.currentUser).getContacts().forEach((e) -> {
                System.out.println("****" + e.getUsername());
                contacts.put(e.getFirstName() + " " + e.getLastName() + "@" + e.getUsername(), e);
            });
             */
            TextFields.bindAutoCompletion(userNameText, contacts.keySet());
        }
    }

    @FXML
    private void sendMessage(ActionEvent event) throws NamingException, IOException {
        Message m = new Message();
        m.setText(enterMessageArea.getText());
        if (to != null) {
            MessageDelegate.sendMessage(m, Authenticator.currentUser, to);
            updateMessageList(MessageDelegate.getMyMessages(Authenticator.currentUser.getId()));

        }
    }

    @FXML
    private void sendRandomMessage(ActionEvent event) throws NamingException {
        MessageSender.send();
    }

}
