/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers.messaging;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import util.dataAbstraction.messaging.Conversation;

/**
 * FXML Controller class
 *
 * @author aminos
 */
public class MessageInfoViewController implements Initializable {

    @FXML
    private Text messageSource;
    @FXML
    private Hyperlink userName;
    @FXML
    private Circle seenStatus;
    @FXML
    private Text messageExrept;
    @FXML
    private Hyperlink deleteLink;
    
    Conversation c;

    public Conversation getConversation() {
        return c;
    }

    public void setConversation(Conversation c) {
        this.c = c;
        userName.setText(this.c.getNewestMessage().getFrom().getUsername());
        messageExrept.setText(c.getMessages().get(c.getMessages().size() - 1).getText().substring(0,c.getMessages().get(c.getMessages().size() - 1).getText().length() / 2 + 1 ));
    }

    
   
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setUsername(String username) {
        this.userName.setText(username);
    }
    
}
