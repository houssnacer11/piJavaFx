/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.authentication;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.users.CompanyProfile;
import tn.esprit.overpowered.byusforus.entities.users.HRManager;
import tn.esprit.overpowered.byusforus.entities.users.User;
import tn.esprit.overpowered.byusforus.services.users.UserFacadeRemote;
import util.cache.ContextCache;

/**
 *
 * @author aminos
 */
public class UserCreator {

    public static void main(String[] args) throws NamingException, NoSuchAlgorithmException {
        User u = new User();
        CompanyProfile cp = new CompanyProfile();
        cp.setName("azerty");
        HRManager hrm = new HRManager();
        hrm.setEmail("yessin.amor@gmail.com");
        hrm.setPassword("1234@".getBytes(StandardCharsets.UTF_8));
        hrm.setUsername("wxc");
        hrm.setCompany(cp);
        hrm.setCompanyProfile(cp);
        getRemote().create(hrm);
        System.out.println("Done");
    }

    public static UserFacadeRemote getRemote() {
        return (UserFacadeRemote) ContextCache
                .getInstance()
                .getProxy("piJEE-ejb-1.0/UserFacade!tn.esprit.overpowered.byusforus.services.users.UserFacadeRemote");
    }
}
