package com.airfrance.repind.fonetik;

import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.entity.individu.Individu;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class PhonetiseTest { // extends AbstractTransactionalSpringContextTests {

    /** logger */
    private static final Log logger = LogFactory.getLog(PhonetiseTest.class);
    
    @Autowired
    private IndividuRepository individuRepository;
    
    /**
     * Retourne le chemin du fichier de configuration JRAF
     * 
     * @return chemin du fichier de configuration JRAF
     * @throws IOException
     */
    // specifies the Spring configuration to load for this test fixture
/*    protected String[] getConfigLocations() {
        return new String[] { "classpath:config/application-context-spring-test.xml" };
    }
*/
    
    /**
     * Constructor for IndividuDSTest.
     * @param arg0 argument
     */
/*    public PhonetiseTest(String arg0) {
        super(arg0);
        setDefaultRollback(false);
    }
*/
    @Test
    public void testPhonetiseMot() {    	
    	try {
			List<Individu> listIndividu = individuRepository.findIndividuByExample(50, 75);
			// On test 50 individu
			for (Individu individu : listIndividu) {
		    	
				// On cree la fonction de phonetisation
				PhonetikInput input = new PhonetikInput();
				
				// NOM et PRENOM  
		    	input.setIdent(individu.getNom() + " " + individu.getPrenom());
		    	PhEntree.Fonetik_Entree(input);
		    	if (!individu.getIndicNomPrenom().equals(input.getIndict())) {
		    		Assert.fail();
		    		logger.error(individu.getSgin()+ ":" + individu.getNom() + " " + individu.getPrenom()  
							 + " -> " + individu.getIndicNomPrenom() + "<>" + input.getIndict());
		    	}
		    	if (!individu.getIndcons().equals(input.getIndCons())) {
		    		Assert.fail();
		    		logger.error(individu.getSgin()+ ":" + individu.getNom() + " " + individu.getPrenom()  
							 + " -> " + individu.getIndcons() + "<>" + input.getIndCons() + "("+ input.getIndict() + ")");
		    	}

		    	// NOM
		    	input = new PhonetikInput();
		    	input.setIdent(individu.getNom());
		    	PhEntree.Fonetik_Entree(input);
		    	if (!individu.getIndicNom().equals(input.getIndict())) {
		    		Assert.fail();
		    		logger.error(individu.getSgin()+ ":" + individu.getNom()  
							 + " -> " + individu.getIndicNom() + "<>" + input.getIndict());
		    	}
			}
		} catch (DataAccessException e) {
			// e.printStackTrace();
			logger.fatal(e);
		}
//    	PhonetikInput input = new PhonetikInput();
//    	input.setIdent("CZCZODROWSKI PHILIPPE");
//    	PhEntree.Fonetik_Entree(input);
    	
    }
    
    /**
     * methode main
     * @param args arguments
     */
/*    public static void main(String[] args) {
        junit.textui.TestRunner.run(PhonetiseTest.class);
    }

*/
}
