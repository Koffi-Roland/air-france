package com.afklm.repind.msv.handicap.mashery.interceptor;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.json.JsonSanitizer;

/**
 * Utility class for parsing json input
 *
 * @author x074151 (Suresh van Rookhuizen)
 */

@Component
@Slf4j
public class JsonParserUtil {

	static final String X_MASHERY_OAUTH_USER_CONTEXT = "X-Mashery-Oauth-User-Context";

	private final JsonFactory jsonFactory = new JsonFactory();
	private final ObjectMapper mapper = new ObjectMapper();

	/**
	 * Parses the mashery user context header string to a MasheryUserContext object.
	 * 
	 * @param request
	 *            HttpServletRequest to get the mashery usercontext from the header.
	 * @return The header string parsed as MasheryUserContext object
	 */
	public MasheryUserContext getMasheryUserContext(final HttpServletRequest request) {
		final String masheryUserContext = request.getHeader(X_MASHERY_OAUTH_USER_CONTEXT);
		log.info("Mashery sent UserContext: " + masheryUserContext);
		if (isNotEmpty(masheryUserContext)) {
			final String sanatizedMasheryContext = JsonSanitizer.sanitize(masheryUserContext);
			try(final JsonParser parser = jsonFactory.createParser(sanatizedMasheryContext)) {
				parser.setCodec(mapper);
				final MasheryUserContext userContext = parser.readValueAs(MasheryUserContext.class);
				return userContext;
			} catch (final Exception e) {
				log.error(format("Problem reading header: %s, content: %s", X_MASHERY_OAUTH_USER_CONTEXT,
						masheryUserContext), e);
			}
		}
		return null;
	}
}
