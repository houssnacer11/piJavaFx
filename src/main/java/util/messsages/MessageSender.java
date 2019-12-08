/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.messsages;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import tn.esprit.overpowered.byusforus.entities.messaging.Message;
import tn.esprit.overpowered.byusforus.services.messaging.Messaging;
import tn.esprit.overpowered.byusforus.services.messaging.MessagingRemote;
import util.authentication.Authenticator;
import util.cache.ContextCache;

/**
 *
 * @author aminos
 */
public class MessageSender {
    public static void send() throws NamingException {
        Message m = new Message();
        m.setText("hi");
        MessageDelegate.sendMessage(m, Authenticator.currentUser, Authenticator.currentUser);
    }
}
