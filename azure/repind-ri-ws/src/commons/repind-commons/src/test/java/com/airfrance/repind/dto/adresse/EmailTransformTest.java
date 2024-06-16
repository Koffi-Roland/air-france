package com.airfrance.repind.dto.adresse;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.adresse.Email;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Ghayth AYARI
 *
 */
public class EmailTransformTest {

	private static final String NO_EXCEPTION_TO_BE_THROWN = "No exception is supposed to be thrown!";


	/**
	 * Test method for {@link com.airfrance.repind.dto.adresse.EmailTransform#bo2DtoLight(java.util.Set)}.
	 */
	@Test
	public void testBo2DtoLightSetOfEmail() {

		// Initialize local variables
		Set<Email> emailSet = null;
		Set<EmailDTO> result = null;

		try {
			// Test with null parameter
			result = EmailTransform.bo2Dto(emailSet);
			assertNull(result);

			// Test with empty set
			emailSet = new HashSet<Email>();
			result = EmailTransform.bo2Dto(emailSet);
			assertNotNull(result);
			assertTrue(result.isEmpty());

			// Test with loaded set
			emailSet.add(new Email());
			result = EmailTransform.bo2Dto(emailSet);
			assertNotNull(result);
			assertTrue(result.size() == 1);
		} catch (JrafDomainException e) {
			fail(NO_EXCEPTION_TO_BE_THROWN);
		}
	}

}
