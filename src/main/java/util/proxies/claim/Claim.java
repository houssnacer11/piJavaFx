/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.proxies.claim;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.reclamation.Reclamation;
import tn.esprit.overpowered.byusforus.services.Reclamation.ReclamationLocal;
import tn.esprit.overpowered.byusforus.services.Reclamation.ReclamationRemote;
import util.authentication.Authenticator;

/**
 *
 * @author amine
 */
public class Claim {

    public static void createClaim(Reclamation r) throws NamingException {

        String jndiName = "piJEE-ejb-1.0/ReclamationService!tn.esprit.overpowered.byusforus.services.Reclamation.ReclamationRemote";
        Context context = new InitialContext();
        ReclamationRemote reclamationRemote = (ReclamationRemote) context.lookup(jndiName);
        reclamationRemote.addReclamation(r);
       
                
    }


}
