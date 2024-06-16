package com.afklm.spring.security.habile.web;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Refresh Controller
 *
 * @author m408461
 */
@RestController("refreshController")
public class RefreshController {

    @Autowired
    @Qualifier("messageResourceSS4H")
    private MessageSource messageSource;

    /**
     * Returns the refresh information page either in French or English (default)
     *
     * @return id of the connected user
     */
    @GetMapping("/check-session")
    public String checkSession(Locale loc) {
        StringBuilder sb = new StringBuilder()
                .append("<html><head>" +
                        "<style type=\"text/css\">\r\n" +
                        ".reconnect-container { display: flex; flex-direction: column; align-items: center; justify-content: center; height: 100vh; }\r\n" +
                        ".reconnect-icon { width: 10rem; height: 10rem; color: #21a62e; }\r\n" +
                        ".reconnect-title { margin: 1rem 0; font-size: 2rem; } " +
                        "</style>" +
                        "</head>")
                .append("<body>" +
                        "<div class='reconnect-container'>" +
                        "<svg class=\"reconnect-icon\" fill=\"none\" stroke=\"currentColor\" viewBox=\"0 0 24 24\" xmlns=\"http://www.w3.org/2000/svg\">" +
                        "<path stroke-linecap=\"round\" stroke-linejoin=\"round\" stroke-width=\"2\" " +
                        "d=\"M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z\"" +
                        "></path>" +
                        "</svg>" +
                        "<span class=\"reconnect-title\">")
                .append(messageSource.getMessage("congrats", null, loc))
                .append("</span><p>")
                .append(messageSource.getMessage("refresh", null, loc))
                .append("</p></div></body></html>");
        return sb.toString();
    }
}
