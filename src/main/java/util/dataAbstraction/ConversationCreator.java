/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dataAbstraction.messaging;

import java.util.ArrayList;
import tn.esprit.overpowered.byusforus.entities.messaging.Message;

/**
 *
 * @author aminos
 */
public class ConversationCreator {

    public static ArrayList<Conversation> create(ArrayList<Message> messages) {
        ArrayList<Conversation> res = new ArrayList<>();
        Conversation cc;
        for (Message m : messages) {
            boolean added = false;
            for (Conversation c : res) {
                if (c.isPartOf(m)) {
                    c.addMessage(m);
                    added = true;
                }
            }
            if (!added) {
                cc = new Conversation(m.getFrom(), m.getTo());
                cc.addMessage(m);
                res.add(cc);
            }

        }

        return res;
    }
}
