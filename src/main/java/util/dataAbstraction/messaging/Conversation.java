/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dataAbstraction.messaging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;
import tn.esprit.overpowered.byusforus.entities.messaging.Message;
import tn.esprit.overpowered.byusforus.entities.users.User;

/**
 *
 * @author aminos
 */
public class Conversation {

    private ArrayList<Message> messages;
    private User u1;
    private User u2;
    private String uid;

    public User getU1() {
        return u1;
    }

    public User getU2() {
        return u2;
    }
    
    public Conversation(User u1, User u2) {
        this.u1 = u1;
        this.u2 = u2;
        uid = u1.getId().toString() + ";" + u2.getId().toString();
        this.messages = new ArrayList<>();
    }

    public void addMessage(Message m) {
        this.messages.add(m);
        this.orderBySentDate();
    }

    public void addAll(Collection<Message> msgC) {
        this.messages.addAll(msgC);
        this.orderBySentDate();
    }

    public void sort(Comparator<Message> c) {
        messages.sort(c);
    }

    public void orderBySentDate() {
        sort((Message m1, Message m2) -> m1.getSentTime().compareTo(m2.getSentTime()));
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
        this.orderBySentDate();
    }

    public Message getNewestMessage() {
        return messages.get(messages.size() - 1);
    }

    public boolean isPartOf(Message m) {
        if (m.getFrom().getId().equals(u1.getId())) {
            return true;
        }
        if (m.getFrom().getId().equals(u2.getId())) {
            return true;
        }
        if (m.getTo().getId().equals(u1.getId())) {
            return true;
        }
        if (m.getTo().getId().equals(u2.getId())) {
            return true;
        }
        return false;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Conversation other = (Conversation) obj;
        if (!this.uid.equals(other.uid)) {
            return false;
        }
        return true;
    }
}
