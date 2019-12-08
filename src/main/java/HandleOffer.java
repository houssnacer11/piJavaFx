/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilJoboffer;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.overpowered.byusforus.entities.entrepriseprofile.JobOffer;
import tn.esprit.overpowered.byusforus.entities.users.HRManager;
import tn.esprit.overpowered.byusforus.entities.util.ExpertiseLevel;
import tn.esprit.overpowered.byusforus.entities.util.OfferStatus;
import tn.esprit.overpowered.byusforus.entities.util.Skill;
import tn.esprit.overpowered.byusforus.services.entrepriseprofile.JobOfferFacadeRemote;
import tn.esprit.overpowered.byusforus.services.users.HRManagerFacadeRemote;
import tn.esprit.overpowered.byusforus.services.users.ProjectManagerFacadeRemote;
import util.authentication.Authenticator;

/**
 *
 * @author pc
 */
public class HandleOffer {

    public static void createJobOffer(String title,
            String location, ExpertiseLevel expertiseLevel,
            Set<Skill> skills, int nberOfpeopleNeeded, String description,
            LocalDate expirationDate) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/HRManagerFacade!tn.esprit.overpowered.byusforus.services.users.HRManagerFacadeRemote";
        Context context = new InitialContext();
        HRManagerFacadeRemote hrManagerProxy = (HRManagerFacadeRemote) context.lookup(jndiName);
        Instant instant = Instant.from(expirationDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        JobOffer jobOffer = new JobOffer();
        jobOffer.setTitle(title);
        jobOffer.setCity(location);
        jobOffer.setExpertiseLevel(expertiseLevel);
        jobOffer.setPeopleNeeded(nberOfpeopleNeeded);
        jobOffer.setSkills(skills);
        jobOffer.setOfferStatus(OfferStatus.AVAILABLE);
        jobOffer.setDescription(description);
        jobOffer.setDateOfArchive(date);
        Long hrId = Authenticator.currentUser.getId();
        hrManagerProxy.createOffer(hrId, jobOffer);
    }

    public static boolean requestJobOfferCreation(String title,
            String location, ExpertiseLevel expertiseLevel,
            Set<Skill> skills, int nberOfpeopleNeeded, String description,
            LocalDate expirationDate) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/ProjectManagerFacade!tn.esprit.overpowered.byusforus.services.users.ProjectManagerFacadeRemote";
        Context context = new InitialContext();
        ProjectManagerFacadeRemote pManagerProxy = (ProjectManagerFacadeRemote) context.lookup(jndiName);
        Instant instant = Instant.from(expirationDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        JobOffer jobOffer = new JobOffer();
        jobOffer.setTitle(title);
        jobOffer.setCity(location);
        jobOffer.setExpertiseLevel(expertiseLevel);
        jobOffer.setPeopleNeeded(nberOfpeopleNeeded);
        jobOffer.setSkills(skills);
        jobOffer.setDescription(description);
        jobOffer.setDateOfArchive(date);
        Long pmId = Authenticator.currentUser.getId();
        return pManagerProxy.createJobOfferRequest(jobOffer, pmId);
    }

    public static boolean approveOffer(String title) throws NamingException {

        String jndiName = "piJEE-ejb-1.0/HRManagerFacade!tn.esprit.overpowered.byusforus.services.users.HRManagerFacadeRemote";
        Context context = new InitialContext();
        HRManagerFacadeRemote hrManagerProxy = (HRManagerFacadeRemote) context.lookup(jndiName);

        return hrManagerProxy.approveJobOffer(title);
    }

    public static boolean declineOffer(String title, String motif) throws NamingException {

        String jndiName = "piJEE-ejb-1.0/HRManagerFacade!tn.esprit.overpowered.byusforus.services.users.HRManagerFacadeRemote";
        Context context = new InitialContext();
        HRManagerFacadeRemote hrManagerProxy = (HRManagerFacadeRemote) context.lookup(jndiName);

        return hrManagerProxy.declineJobOffer(title, motif);
    }

    public static List<JobOffer> searchByTitle(String title) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/JobOfferFacade!tn.esprit.overpowered.byusforus.services.entrepriseprofile.JobOfferFacadeRemote";
        Context context = new InitialContext();
        JobOfferFacadeRemote jobOfferProxy = (JobOfferFacadeRemote) context.lookup(jndiName);
        return jobOfferProxy.searchByTitle(title);

    }

    public static List<JobOffer> searchExpertise(ExpertiseLevel expertise) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/JobOfferFacade!tn.esprit.overpowered.byusforus.services.entrepriseprofile.JobOfferFacadeRemote";
        Context context = new InitialContext();
        JobOfferFacadeRemote jobOfferProxy = (JobOfferFacadeRemote) context.lookup(jndiName);
        return jobOfferProxy.searchByExpertise(expertise);

    }

    public static List<JobOffer> searchLocation(String location) throws NamingException {
        String jndiName = "piJEE-ejb-1.0/JobOfferFacade!tn.esprit.overpowered.byusforus.services.entrepriseprofile.JobOfferFacadeRemote";
        Context context = new InitialContext();
        JobOfferFacadeRemote jobOfferProxy = (JobOfferFacadeRemote) context.lookup(jndiName);
        return jobOfferProxy.searchByLocation(location);

    }
}
