package com.airfrance.repind.util.ut;

import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.*;
import com.airfrance.repind.util.RankComputer;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author t444761 (Ghayth AYARI)
 *
 */
public class RankComputerTest {

	/**
	 * Test method for {@link com.airfrance.repind.util.RankComputer#computeRankIndividu(com.airfrance.repind.dto.individu.searchindividualbymulticriteria.SearchIndividualByMulticriteriaRequestDTO, com.airfrance.repind.dto.individu.searchindividualbymulticriteria.IndividualMulticriteriaDTO)}.
	 */
	@Test
	public void testComputeRankIndividuSearchIndividualByMulticriteriaRequestDTOIndividualMulticriteriaDTO() {
		
		// Initialize test objects
		// Individual ...
		IndividualMulticriteriaDTO individuHomonym = new IndividualMulticriteriaDTO();
		
		Date dateNaissance = null;
		try {
			dateNaissance = new SimpleDateFormat("dd-MM-yyy").parse("28-05-1989");
		} catch (ParseException e) {
			fail("No exception is supposed to be thrown");
		}
		
		TelecomsDTO telecom = new TelecomsDTO();
		telecom.setSnorm_inter_country_code("33");
		telecom.setSnorm_inter_phone_number("+33695056227");
		telecom.setSnorm_nat_phone_number_clean("0695056227");
		
		Set<TelecomsDTO> telecoms = new HashSet<TelecomsDTO>();
		telecoms.add(telecom);
		
		EmailDTO email = new EmailDTO();
		email.setEmail("ghayth.ayari@hotmail.com");
		
		Set<EmailDTO> emails = new HashSet<EmailDTO>();
		emails.add(email);
		
		PostalAddressDTO adresse = new PostalAddressDTO();
		adresse.setSno_et_rue("95 avenue Henri Dunant");
		adresse.setSville("Nice");
		adresse.setScode_postal("06100");
		adresse.setScode_pays("FR");
		adresse.setSstatut_medium("V");
		
		List<PostalAddressDTO> adresses = new ArrayList<PostalAddressDTO>();
		adresses.add(adresse);
		
		IndividuDTO individu = new IndividuDTO();
		individu.setNomSC("Ayari");
		individu.setPrenomSC("Ghayth");
		individu.setDateNaissance(dateNaissance);
		individu.setTelecoms(telecoms);
		individu.setEmaildto(emails);
		individu.setPostaladdressdto(adresses);
		
		individuHomonym.setIndividu(individu);
		
		// Request ...
		IdentityDTO identity1 = new IdentityDTO();
		identity1.setFirstName("Ghayth");
		identity1.setLastName("AYARI");
		identity1.setBirthday(dateNaissance);

		IdentityDTO identity2 = new IdentityDTO();
		identity2.setFirstName("Ghayt");
		identity2.setLastName("AYARI");
		identity2.setBirthday(dateNaissance);

		IdentityDTO identity3 = new IdentityDTO();
		identity3.setFirstName("Ghayth");
		identity3.setLastName("AYAR");
		identity3.setBirthday(dateNaissance);

		IdentityDTO identity4 = new IdentityDTO();
		identity4.setFirstName("Ghayth");
		identity4.setLastName("AYARI");

		IdentityDTO identity5 = new IdentityDTO();
		identity5.setFirstName("Ghayt");
		identity5.setLastName("AYARI");

		IdentityDTO identity6 = new IdentityDTO();
		identity6.setFirstName("Ghayth");
		identity6.setLastName("AYAR");

		IdentityDTO identity7 = new IdentityDTO();
		identity7.setFirstName("Ghayt");
		identity7.setLastName("AYAR");
		

		PostalAddressBlocDTO addressContent1 = new PostalAddressBlocDTO();
		addressContent1.setNumberAndStreet("95 avenue Henri Dunant");
		addressContent1.setCity("Nice");
		addressContent1.setZipCode("06100");
		addressContent1.setCountryCode("FR");
		
		PostalAddressBlocDTO addressContent2 = new PostalAddressBlocDTO();
		addressContent2.setNumberAndStreet("95 avenue henri dunant");
		addressContent2.setCity("Nice");
		addressContent2.setCountryCode("FR");

		PostalAddressBlocDTO addressContent3 = new PostalAddressBlocDTO();
		addressContent3.setCity("Nice");
		addressContent3.setZipCode("06100");
		addressContent3.setCountryCode("FR");

		PostalAddressBlocDTO addressContent4 = new PostalAddressBlocDTO();
		addressContent4.setNumberAndStreet("95 avenue Henri Dunant");
		addressContent4.setCity("Nic");
		addressContent4.setCountryCode("FR");

		PostalAddressBlocDTO addressContent5 = new PostalAddressBlocDTO();
		addressContent5.setZipCode("006100");
		addressContent5.setCountryCode("FR");

		PostalAddressBlocDTO addressContent6 = new PostalAddressBlocDTO();
		addressContent6.setNumberAndStreet("95 avenue henri Dunant");
		addressContent6.setCity("Nice");
		addressContent6.setCountryCode("FR");

		PostalAddressBlocDTO addressContent7 = new PostalAddressBlocDTO();
		addressContent7.setCity("Nice");
		addressContent7.setZipCode("06100");
		addressContent7.setCountryCode("FR");

		PostalAddressBlocDTO addressContent8 = new PostalAddressBlocDTO();
		addressContent8.setCity("Nic");
		addressContent8.setCountryCode("FR");

		PostalAddressBlocDTO addressContent9 = new PostalAddressBlocDTO();
		addressContent9.setZipCode("006100");
		addressContent9.setCountryCode("FR");

		PostalAddressBlocDTO addressContent10 = new PostalAddressBlocDTO();
		addressContent10.setCountryCode("FR");

		PostalAddressBlocDTO addressContent11 = new PostalAddressBlocDTO();
		addressContent11.setCountryCode("DE");
		
		
		ContactDTO contactEmail = new ContactDTO();
		contactEmail.setEmail("ghayth.ayari@hotmail.com");
		
		ContactDTO contactPhone = new ContactDTO();
		contactPhone.setPhoneNumber("0695056227");
		contactPhone.setCountryCode("33");

		ContactDTO contact1 = new ContactDTO();
		contact1.setPostalAddressBloc(addressContent1);

		ContactDTO contact2 = new ContactDTO();
		contact2.setPostalAddressBloc(addressContent2);

		ContactDTO contact3 = new ContactDTO();
		contact3.setPostalAddressBloc(addressContent3);

		ContactDTO contact4 = new ContactDTO();
		contact4.setPostalAddressBloc(addressContent4);

		ContactDTO contact5 = new ContactDTO();
		contact5.setPostalAddressBloc(addressContent5);

		ContactDTO contact6 = new ContactDTO();
		contact6.setPostalAddressBloc(addressContent6);

		ContactDTO contact7 = new ContactDTO();
		contact7.setPostalAddressBloc(addressContent7);

		ContactDTO contact8 = new ContactDTO();
		contact8.setPostalAddressBloc(addressContent8);

		ContactDTO contact9 = new ContactDTO();
		contact9.setPostalAddressBloc(addressContent9);

		ContactDTO contact10 = new ContactDTO();
		contact10.setPostalAddressBloc(addressContent10);

		ContactDTO contact11 = new ContactDTO();
		contact11.setPostalAddressBloc(addressContent11);

		
		// ************ TESTS ************
		// ************ NO NAMES CASES ************
		// email OK
		SearchIndividualByMulticriteriaRequestDTO request = new SearchIndividualByMulticriteriaRequestDTO();
		request.setContact(contactEmail);
		assertEquals(70, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// phone OK
		request.setContact(contactPhone);
		assertEquals(70, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// address OK
		request.setContact(contact1);
		assertEquals(60, RankComputer.computeRankIndividu(request, individuHomonym));
		
		
		// ************ STRICT NAMES CASES ************
		// email OK / No birthdate
		request.setContact(contactEmail);
		request.setIdentity(identity4);
		assertEquals(100, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// email KO / No birthdate
		request.setIdentity(identity4);
		request.setContact(null);
		// 40 since REPIND-1675... 
		assertEquals(40, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// Phone OK / No birthdate
		request.setIdentity(identity4);
		request.setContact(contactPhone);
		assertEquals(100, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// address OK / birthdate OK
		request.setIdentity(identity1);
		request.setContact(contact1);
		assertEquals(100, RankComputer.computeRankIndividu(request, individuHomonym));
		
		
		// address OK / No birthdate
		request.setIdentity(identity4);
		request.setContact(contact1);
		assertEquals(90, RankComputer.computeRankIndividu(request, individuHomonym));
		
		
		// address: Str OK / City OK / Country OK / birthdate OK
		request.setIdentity(identity1);
		request.setContact(contact2);
		assertEquals(90, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// address: City OK / Zip OK / Country OK / birthdate OK
		request.setIdentity(identity1);
		request.setContact(contact3);
		assertEquals(90, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// address: City like OK / Country OK / birthdate OK
		request.setIdentity(identity1);
		request.setContact(contact8);
		assertEquals(80, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// address: Zip like OK / Country OK / birthdate OK
		request.setIdentity(identity1);
		request.setContact(contact9);
		assertEquals(80, RankComputer.computeRankIndividu(request, individuHomonym));
		
		
		// address: Country OK / birthdate OK
		request.setIdentity(identity1);
		request.setContact(contact10);
		assertEquals(70, RankComputer.computeRankIndividu(request, individuHomonym));
		
		
		// address: No address, phone, email / birthdate OK
		request.setIdentity(identity1);
		request.setContact(null);
		assertEquals(65, RankComputer.computeRankIndividu(request, individuHomonym));
		
		
		// address: City like OK / Country OK / no birthdate
		request.setIdentity(identity4);
		request.setContact(contact8);
		assertEquals(60, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// address: Zip like OK / Country OK / no birthdate
		request.setIdentity(identity4);
		request.setContact(contact9);
		assertEquals(60, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// address: Country OK / no birthdate
		request.setIdentity(identity4);
		request.setContact(contact10);
		assertEquals(50, RankComputer.computeRankIndividu(request, individuHomonym));
		

		// ************ STRICT LAST NAME/LIKE FIRST NAME CASES ************
		// email OK / no birthdate
		request.setContact(contactEmail);
		request.setIdentity(identity5);
		assertEquals(90, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// email KO / no birthdate
		request.setIdentity(identity5);
		request.setContact(null);
		// 35 since REPIND-1675
		assertEquals(35, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// Phone OK / no birthdate
		request.setIdentity(identity5);
		request.setContact(contactPhone);
		assertEquals(90, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// address OK / birthdate OK
		request.setIdentity(identity2);
		request.setContact(contact1);
		assertEquals(90, RankComputer.computeRankIndividu(request, individuHomonym));
		
		
		// address: Str OK / City OK / Country OK / birthdate OK
		request.setIdentity(identity2);
		request.setContact(contact2);
		assertEquals(80, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// address: City OK / Zip OK / Country OK / birthdate OK
		request.setIdentity(identity2);
		request.setContact(contact3);
		assertEquals(80, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// address: City like OK / Country OK / birthdate OK
		request.setIdentity(identity2);
		request.setContact(contact8);
		assertEquals(70, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// address: Zip like OK / Country OK / birthdate OK
		request.setIdentity(identity2);
		request.setContact(contact9);
		assertEquals(70, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// address: Country OK / no birthdate
		request.setIdentity(identity5);
		request.setContact(contact10);
		assertEquals(40, RankComputer.computeRankIndividu(request, individuHomonym));
		

		// ************ LIKE LAST NAME/STRICT FIRST NAME CASES ************
		// email OK / no birthdate
		request.setContact(contactEmail);
		request.setIdentity(identity6);
		assertEquals(90, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// email KO / no birthdate
		request.setIdentity(identity6);
		request.setContact(null);
		// 30 since REPIND-1675
		assertEquals(30, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// Phone OK / no birthdate
		request.setIdentity(identity6);
		request.setContact(contactPhone);
		assertEquals(90, RankComputer.computeRankIndividu(request, individuHomonym));
		
		// address OK / birthdate OK
		request.setIdentity(identity3);
		request.setContact(contact1);
		assertEquals(90, RankComputer.computeRankIndividu(request, individuHomonym));
		

		// ************ LIKE LAST NAME/LIKE FIRST NAME CASES ************
		// address: Country OK / no birthdate
		request.setIdentity(identity7);
		request.setContact(contact10);
		assertEquals(30, RankComputer.computeRankIndividu(request, individuHomonym));
		
		request.setIdentity(identity7);
		request.setContact(contact1);
		assertEquals(30, RankComputer.computeRankIndividu(request, individuHomonym));
		
		request.setIdentity(identity7);
		request.setContact(contact11);
		assertEquals(0, RankComputer.computeRankIndividu(request, individuHomonym));
	}

}
