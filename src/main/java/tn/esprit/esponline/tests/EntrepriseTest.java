package tn.esprit.esponline.tests;

import java.util.ArrayList;
import static org.junit.Assert.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.esponline.entity.*;
import tn.esprit.esponline.repository.*;
import tn.esprit.esponline.service.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseTest {
	
	@Autowired
	IEntrepriseService service;
	
	@Autowired
	EntrepriseRepository rep;
	
	@Autowired
	DepartementRepository drep;
	
	private static final Logger l = LogManager.getLogger(EntrepriseTest.class);	
	
	
	@Test(timeout = 2000)
	public void testAjouterEntreprise()
	{
		try {
			l.info("In testAjouterEntreprise():");
			Entreprise entreprise=new Entreprise();
			ArrayList <Entreprise> liste1 =(ArrayList<Entreprise>) rep.findAll();
			int size1=liste1.size();
			l.info(()->"nombre d'entreprises avant l'ajout: " +size1);
			l.info("Je vais ajouter une entreprise.");
			int id=service.ajouterEntreprise(entreprise);
			ArrayList <Entreprise> liste2 =(ArrayList<Entreprise>) rep.findAll();
			int size2=liste2.size();
			l.info(()->"nombre d'entreprises apres l'ajout: " +size2);
			l.info("comparaison size avant et apres.");
			assertTrue(size2==size1+1);
			service.deleteEntrepriseById(id);
			l.info("je supprime l'entreprise.");
			l.info("Out testAjouterEntreprise() sans erreurs.");
		}
		catch (Exception e)
		{ l.error(()->"Erreur dans testAjouterEntreprise() : " + e); }
	}
	
	//"testAjout", "testAjout"
	
	@Test(timeout = 2000)
	public void testAjouterDepartement()
	{
		try {
			l.info("In testAjouterDepartement():");
			Departement departement=new Departement("Ressources Humaines.");
			departement.setEntreprise(null);
			l.info("Je vais creer un departement.");
			int id=service.ajouterDepartement(departement);
			l.info("Je vais ajouter un departement.");
			l.info(()->"Id du departement que je viens d'ajouter: "+id);
			l.info("je teste si l'id du departement est bien different de 0.");
			assertTrue(id!=0);
			l.info("je supprime le departement.");
			service.deleteDepartementById(id);
			l.info("Out testAjouterDepartement() sans erreurs.");
		}catch (Exception e) { l.error(()->"Erreur dans testAjouterDepartement() : " + e); }
	}
	


	@Test(timeout = 2000)
	public void testAffecterDepartement()
	{
		try {
			l.info("In testAjouterDepartement():");
			l.info("Je vais creer une entreprise.");
			Entreprise entreprise=new Entreprise("testAjoutt", "testAjoutt");
			l.info("Je vais creer un departementt.");
			Departement departement=new Departement("Ressources Humaines");
			l.info("Je vais ajouter l'entreprise.");
			int ajouterentreprise=service.ajouterEntreprise(entreprise);
			l.info("Je vais ajouter le departement.");
			int d=service.ajouterDepartement(departement);
			l.info("Je vais affecter le departement a l'entreprise.");
			service.affecterDepartementAEntreprise(d, ajouterentreprise);
			l.info("Je vais reprendre le departement depuis la base de donnée.");
			Departement findbyid=drep.findById(d).orElseThrow(RuntimeException::new);
			l.info("Je vais tester si l'entreprise_id du departement est égale a l'id de l'entreprise auquel je l'ai affecté.");
			assertTrue(findbyid.getEntreprise().getId()==ajouterentreprise);
			l.info("Je vais supprimer le departement.");
			service.deleteDepartementById(d);
			l.info("Je vais supprimer l'entreprise.");
			service.deleteEntrepriseById(ajouterentreprise);
			l.info("Out testAffecterDepartement() sans erreurs.");
		}catch (Exception e) { l.error(()-> "Erreur dans testAffecterDepartement() : " + e); }
	}
	
	
	@Test(timeout = 2000)
	public void testSupprimerEntreprise()
	{
		try{
			l.info("In testSupprimerEntreprise():");
			l.info("Je vais creer une entreprise.");
			Entreprise entreprise=new Entreprise("testAjouttt", "testAjouttt");
			l.info("Je vais ajouter l'entreprise.");
			int ajouterentreprise=service.ajouterEntreprise(entreprise);
			l.info("Je vais supprimer l'entreprise.");
			service.deleteEntrepriseById(ajouterentreprise);
			l.info("Je vais m'assurer que la methode getEntrepriseById() retourne null.");
			assertNull(service.getEntrepriseById(ajouterentreprise));
			l.info("Out testSupprimerEntreprise() sans erreurs.");
		}catch (Exception e) { l.error(()->"Erreur dans testSupprimerEntreprise() : " + e); }
	}
	
	
	
	@Test(timeout = 2000)
	public void testSupprimerDepartement()
	{
		try{
			l.info("In testSupprimerEntreprise():");
			l.info("Je vais creer un departement.");
			Departement departement=new Departement("Ressources Humaines");
			departement.setEntreprise(null);
			l.info("Je vais ajouter un departement.");
			int d=service.ajouterDepartement(departement);
			l.info("Je vais supprimer le departement.");
			service.deleteDepartementById(d);
			l.info("Je vais m'assurer que la methode findDepartementById() retourne null.");
			assertNull(drep.findById(d).orElse(null));
		}catch (Exception e) { l.error(()->"Erreur dans testSupprimerDepartement() : " + e); }
	}
}
