/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.messsages;

import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.messaging.Message;
import tn.esprit.overpowered.byusforus.entities.users.User;
import tn.esprit.overpowered.byusforus.services.messaging.MessagingRemote;

/**
 *
 * @author aminos
 */
public class MessageCheckThread implements Runnable {

    User user;
    Message newestMessage;
    MessagingRemote msgService;
    private volatile  ArrayList<Message> retMsg;
    public MessageCheckThread(User u, Message m) throws NamingException {
        this.user = u;
        this.newestMessage = m;
    }
    @Override
    public void run() {
        // send request with that most recent message's time
       // retMsg = MessageDelegate.getMessages(user.getId(), newestMessage.getSentTime());
    }
    
    public ArrayList<Message> getNewestMessages() {
        return this.retMsg;
    }
    
}
