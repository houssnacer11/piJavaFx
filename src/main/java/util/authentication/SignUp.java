/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.authentication;

import LicenceManager.LicenceFacadeRemote;
import Licenses.Licence;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.users.Candidate;
import tn.esprit.overpowered.byusforus.entities.users.CompanyAdmin;
import tn.esprit.overpowered.byusforus.entities.users.CompanyProfile;
import tn.esprit.overpowered.byusforus.entities.users.HRManager;
import tn.esprit.overpowered.byusforus.entities.users.ProjectManager;
import tn.esprit.overpowered.byusforus.entities.users.User;
import tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote;
import tn.esprit.overpowered.byusforus.services.users.CompanyAdminFacadeRemote;
import tn.esprit.overpowered.byusforus.services.users.HRManagerFacadeRemote;
import tn.esprit.overpowered.byusforus.services.users.ProjectManagerFacadeRemote;
import tn.esprit.overpowered.byusforus.services.users.UserFacadeRemote;
import tn.esprit.overpowered.byusforus.util.Role;
import util.routers.FXRouter;

/**
 *
 * @author EliteBook
 */
public class SignUp {

    public static String CheckSignUp(String email, String username) throws NoSuchAlgorithmException, NamingException {
        String jndiName = "piJEE-ejb-1.0/UserFacade!tn.esprit.overpowered.byusforus.services.users.UserFacadeRemote";
        Context context = new InitialContext();
        UserFacadeRemote userProxy = (UserFacadeRemote) context.lookup(jndiName);
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        return userProxy.checkExistence(email, username);
    }

    public static String ContinueAsCandidate(String email) throws NamingException, NoSuchAlgorithmException {
        String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote";
        Context context = new InitialContext();
        CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) context.lookup(jndiName);
        return candidateProxy.accountCreationConfirmation(email);
    }
    //THIS FUNCTION IS TO CONFIRM CREATION OF A COMPANY RESPONSIBLE BY MAIL

    public static String ContinueAsCompanyAdmin(String email) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote";
        Context context = new InitialContext();
        CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) context.lookup(jndiName);
        return candidateProxy.accountCreationConfirmation(email);
    }

    public static String ContinueAsHRManager(String email) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote";
        Context context = new InitialContext();
        CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) context.lookup(jndiName);
        return candidateProxy.accountCreationConfirmation(email);
    }

    public static String ContinueAsPManager(String email) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote";
        Context context = new InitialContext();
        CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) context.lookup(jndiName);
        return candidateProxy.accountCreationConfirmation(email);
    }

    public static String ContinueAsEmployee(String email) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote";
        Context context = new InitialContext();
        CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) context.lookup(jndiName);
        return candidateProxy.accountCreationConfirmation(email);
    }

    public static boolean isAlphanumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isDigit(c) && !Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    //TERMINATING ACCOUNT CREATION
    //FINALIZING CANDIDATE ACCOUNT CREATION
    public static void finishCreation(Candidate candidate) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/CandidateFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateFacadeRemote";
        Context context = new InitialContext();
        CandidateFacadeRemote candidateProxy = (CandidateFacadeRemote) context.lookup(jndiName);
        candidateProxy.createCandidate(candidate);
    }

    //FINALIZING ENTREPRISE ADMIN CREATION
    public static void finishAdminCreation(CompanyAdmin companyAdmin) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/CompanyAdminFacade!tn.esprit.overpowered.byusforus.services.users.CompanyAdminFacadeRemote";
        Context context = new InitialContext();
        CompanyAdminFacadeRemote companyAdminProxy = (CompanyAdminFacadeRemote) context.lookup(jndiName);
        // The company name is stored temporarly in CurriculumVitae
        String compName = companyAdmin.getCurriculumVitaes();
        CompanyProfile comp = new CompanyProfile(compName);
        companyAdmin.setCurriculumVitaes("");
        companyAdmin.setIntroduction("");
        Long adminId = companyAdminProxy.addCompanyAdmin(companyAdmin);
        Long compId = companyAdminProxy.createCompanyProfile(comp);
        companyAdminProxy.bindCompanyAdminToCompanyProfile(adminId, compId);

    }

    //FINALIZING ENTREPRISE HR MANAGER CREATION
    public static void finishHRManagerCreation(HRManager hrManager) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/HRManagerFacade!tn.esprit.overpowered.byusforus.services.users.HRManagerFacadeRemote";
        Context context = new InitialContext();
        //This is the company name set in the curriculumVitae
        String compName = hrManager.getCurriculumVitaes();
        hrManager.setIntroduction("");
        hrManager.setCurriculumVitaes("");
        HRManagerFacadeRemote hrManagerProxy = (HRManagerFacadeRemote) context.lookup(jndiName);
        Long hrmId = hrManagerProxy.createHRManager(hrManager);
        SignUp.bindHRtoCompany(compName, hrmId);

    }

    //FINALIZING ENTREPRISE PROJECT MANAGER CREATION
    public static void finishPManagerCreation(ProjectManager pManager) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/ProjectManagerFacade!tn.esprit.overpowered.byusforus.services.users.ProjectManagerFacadeRemote";
        Context context = new InitialContext();
        //This is the company name set in the curriculumVitae
        String compName = pManager.getCurriculumVitaes();
        pManager.setIntroduction("");
        pManager.setCurriculumVitaes("");
        ProjectManagerFacadeRemote pManagerProxy = (ProjectManagerFacadeRemote) context.lookup(jndiName);
        Long pmId = pManagerProxy.createPManager(pManager);
        SignUp.bindPMtoCompany(compName, pmId);

    }

    public static boolean emailInputSanitization(String email) {
        String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!"
                + "#$%&'*+/=?^_`{|}~-]+)*"
                + "|\"(?:[\\x01-\\x08\\x0b\\x0c\\x"
                + "0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x0"
                + "1-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0"
                + "-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z"
                + "0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]"
                + "|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]"
                + "|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01"
                + "-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7"
                + "f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        boolean matches = Pattern.matches(regex, email);
        return matches;
    }

    public static boolean verifyCompUserInfo(String licenceID, String companyName,
            String userPass, String userRole) throws NamingException {
        Licence licence = new Licence();
        licence.setCompanyLicenceId(licenceID);
        licence.setCompanyName(companyName);
        licence.setUserPass(userPass);
        licence.setUserRole(Role.valueOf(userRole));
        String jndiName = "piJEE-ejb-1.0/LicenceFacade!LicenceManager.LicenceFacadeRemote";
        Context context = new InitialContext();
        LicenceFacadeRemote licenceProxy = (LicenceFacadeRemote) context.lookup(jndiName);
        return licenceProxy.verifyLicenceInfo(licence);

    }

    //INFORMATION COLLECTORS
    //COLLECTIONG SIGNUP INFORMATION AS CANDIDATE
    public static void collectInfoAscandidate(String username, String email,
            String password, String fisrtname, String lastname) throws NoSuchAlgorithmException {
        /*
        String myData = SignUp.ContinueAsCandidate(email.getText());
                            Candidate cdt = new Candidate();
                            cdt.setUsername(username.getText());
                            cdt.setFirstName(firstname.getText());
                            cdt.setLastName(lastname.getText());
                            cdt.setPassword(password.getText().getBytes());
                            cdt.setEmail(email.getText());
                            //!!!!!!!!!WATCH OUT!!!!!!!!!!!
                            //!!!!!THIS IS A DANGEROUS TRICK TO COLLECT CONFIRMATION CODE SENT TO THE CANDIDATE!!!!
                            cdt.setIntroduction(myData);
                            FXRouter.goTo("EmailConfirmView", cdt);
         */
    }

    //COLLECTING SINGUP INFORMATION AS COMPANYADMIN
    public static CompanyAdmin collectInfoAsCompanyAdmin(String username, String email,
            String password, String fisrtname, String lastname) throws NoSuchAlgorithmException {

        CompanyAdmin companyAdmin = new CompanyAdmin();
        companyAdmin.setUsername(username);
        companyAdmin.setEmail(email);
        companyAdmin.setFirstName(fisrtname);
        companyAdmin.setLastName(lastname);
        companyAdmin.setPassword(password.getBytes());
        return companyAdmin;

    }

    public static HRManager collectInfoHRManager(String username, String email,
            String password, String fisrtname, String lastname) throws NoSuchAlgorithmException {

        HRManager hrManager = new HRManager();
        hrManager.setUsername(username);
        hrManager.setEmail(email);
        hrManager.setFirstName(fisrtname);
        hrManager.setLastName(lastname);
        hrManager.setPassword(password.getBytes());
        return hrManager;

    }

    public static ProjectManager collectInfoPManager(String username, String email,
            String password, String fisrtname, String lastname) throws NoSuchAlgorithmException {

        ProjectManager pManager = new ProjectManager();
        pManager.setUsername(username);
        pManager.setEmail(email);
        pManager.setFirstName(fisrtname);
        pManager.setLastName(lastname);
        pManager.setPassword(password.getBytes());
        return pManager;

    }

    public static boolean bindHRtoCompany(String compName, Long hrmId) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/HRManagerFacade!tn.esprit.overpowered.byusforus.services.users.HRManagerFacadeRemote";
        Context context = new InitialContext();
        HRManagerFacadeRemote hrManagerProxy = (HRManagerFacadeRemote) context.lookup(jndiName);
        return hrManagerProxy.affecterHRtoCompany(hrmId, compName);
    }

    public static boolean bindPMtoCompany(String compName, Long hrmId) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/ProjectManagerFacade!tn.esprit.overpowered.byusforus.services.users.ProjectManagerFacadeRemote";
        Context context = new InitialContext();
        ProjectManagerFacadeRemote pManagerProxy = (ProjectManagerFacadeRemote) context.lookup(jndiName);
        return pManagerProxy.affecterPMtoCompany(hrmId, compName);
    }
}
