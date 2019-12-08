/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.messsages;

import java.util.ArrayList;
import java.util.Date;
import tn.esprit.overpowered.byusforus.entities.messaging.Message;
import tn.esprit.overpowered.byusforus.entities.users.User;
import tn.esprit.overpowered.byusforus.services.messaging.MessagingRemote;
import util.cache.ContextCache;

/**
 *
 * @author aminos
 */
public class MessageDelegate {

    public static MessagingRemote getRemote() {
        return (MessagingRemote) ContextCache
                .getInstance()
                .getProxy("piJEE-ejb-1.0/Messaging!tn.esprit.overpowered.byusforus.services.messaging.MessagingRemote");
    }
    
    public static void sendMessage(Message m, User from, User to) {
        getRemote().sendMessage(m, from.getId(), to.getId());
    }
    
    public static void seen(Message m, User u) {
        getRemote().seeMessage(u.getId(), m.getId());
    }
    
    public static void delete(Message m, User u) {
         getRemote().hideMessage(u.getId(), m.getId());
    }

    public static ArrayList<Message> getMyMessages(Long id) {
        return getRemote().getMyMessages(id);
    }
    
        public ArrayList<Message> getMessages(Long userId, Date t) {
            return getRemote().getMessages(userId, t);
        }

}
