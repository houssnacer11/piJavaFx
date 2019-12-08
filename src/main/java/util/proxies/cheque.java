/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.proxies.claim;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.Paiement.Cheque;
import tn.esprit.overpowered.byusforus.services.Paiement.PaimentRemote;

/**
 *
 * @author amine
 */
public class cheque {
     public static void ValiderCheque(Cheque r) throws NamingException {

        String jndiName = "piJEE-ejb-1.0/PaimentService!tn.esprit.overpowered.byusforus.services.Paiement.PaimentRemote";
        Context context = new InitialContext();
        PaimentRemote PaimentRemote = (PaimentRemote) context.lookup(jndiName);
        PaimentRemote.addCheque(r);
       
                
    }
}
