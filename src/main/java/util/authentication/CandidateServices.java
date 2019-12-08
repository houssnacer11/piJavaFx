/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.authentication;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.users.Candidate;
import tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote;

/**
 *
 * @author EliteBook
 */
public class CandidateServices {
    
    public static Candidate findCandidate(Long cdtId) throws NamingException
    {
     String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote";
            Context context = new InitialContext();
            CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) context.lookup(jndiName);   
           return candidateProxy.find(cdtId);
    }
}
