/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.information.tracker;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.users.CompanyAdmin;
import tn.esprit.overpowered.byusforus.entities.users.HRManager;
import tn.esprit.overpowered.byusforus.entities.users.ProjectManager;
import tn.esprit.overpowered.byusforus.services.users.CompanyAdminFacadeRemote;
import tn.esprit.overpowered.byusforus.services.users.HRManagerFacadeRemote;
import tn.esprit.overpowered.byusforus.services.users.ProjectManagerFacadeRemote;

/**
 *
 * @author pc
 */
public class InfoTracker {

    static String jndiName = "piJEE-ejb-1.0/UserFacade!tn.esprit.overpowered.byusforus.services.users.UserFacadeRemote";
    static String jndiName2 = "piJEE-ejb-1.0/CompanyAdminFacade!tn.esprit.overpowered.byusforus.services.users.CompanyAdminFacadeRemote";
    static String jndiName3 = "piJEE-ejb-1.0/HRManagerFacade!tn.esprit.overpowered.byusforus.services.users.HRManagerFacadeRemote";
    static String jndiName4 = "piJEE-ejb-1.0/ProjectManagerFacade!tn.esprit.overpowered.byusforus.services.users.ProjectManagerFacadeRemote";

    public static CompanyAdmin getAdminInformation(Long currentUserId) throws NamingException {
        Context context = new InitialContext();
        CompanyAdminFacadeRemote adminProxy = (CompanyAdminFacadeRemote) context.lookup(jndiName2);
        return adminProxy.find(currentUserId);
    }

    public static HRManager getHRInformation(Long currentUserId) throws NamingException {
        Context context = new InitialContext();
        HRManagerFacadeRemote hrProxy = (HRManagerFacadeRemote) context.lookup(jndiName3);
        return hrProxy.find(currentUserId);
    }

    public static ProjectManager getPMInformation(Long currentUserId) throws NamingException {
        Context context = new InitialContext();
        ProjectManagerFacadeRemote pmProxy = (ProjectManagerFacadeRemote) context.lookup(jndiName4);
        return pmProxy.find(currentUserId);
    }

}
