package com.afklm.spring.security.habile.proxy.controller;

import com.afklm.spring.security.habile.proxy.HabileSimulationGateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.springframework.http.HttpStatus.OK;

/**
 * LoginController
 * 
 * The purpose of this class is to provide a login page in case of a form authentication mode.
 * 
 * @author m408461
 *
 */
@RestController
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HabileSimulationGateway.class);

    private String authenticationUrl = "/login";

    private static String ERROR_TAG = "<div class=\"ping-messages\"><div class=\"ping-error error-displayed\">"
            + "Incorrect user ID or password. Please try again.</div></div>";

    /**
     * Just to avoid side effect in order to debug between 404 and 401 return codes because no error page
     * 
     * @param exchange
     * @return
     */
    @GetMapping(path = "/favicon.ico")
    public ResponseEntity<String> favicon(ServerWebExchange exchange) {
        return new ResponseEntity<>("ok", OK);
    }

    /**
     * \/login endpoint.<br/>
     * 
     * @param exchange
     * @return login page
     */
    /**
     * @param exchange
     * @return
     */
    @GetMapping(path = "/login")
    public ResponseEntity<String> login(ServerWebExchange exchange) {
        LOGGER.debug("/login invoked");

        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "  <head>\n"
                + "    <meta charset=\"utf-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n"
                + "    <meta name=\"description\" content=\"\">\n"
                + "    <meta name=\"author\" content=\"\">\n"
                + "    <title>Simulation - Please sign in</title>\n"
                + inlineStyles()
                + "  </head>\n");

        String contextPath = exchange.getRequest().getPath().contextPath().value();
        sb.append("<body style><muithemeprovider>");
        sb.append("<div id=\"content-wrapper\"\r\n" +
                "    class=\"display-flex mui-col-xl-offset-4 mui-col-xl-4 mui-col-lg-offset-3 mui-col-lg-6 mui-col-md-offset-2 mui-col-md-8 mui-col-sm-offset-2 mui-col-sm-8 mui-col-xs-12\">\r\n" +
                "    <div id=\"pageContentId\" class=\"width-100-percent\">\r\n" +
                "        <h3 class=\"habile-header-title\">\r\n" +
                "            Habile<span class=\"habile-header-subtitle\">Simulation</span>\r\n" +
                "        </h3>\r\n" +
                "        <div class=\"mui--clearfix clear-float\"></div>\r\n" +
                "        <div class=\"mui-panel hl-mfa-panel\">\r\n" +
                "            <form id=\"nuxForm\" role=\"form\" action=\"" + contextPath + authenticationUrl + "\" autocomplete=\"off\"\r\n" +
                "                method=\"post\" class=\"display-flex\">\r\n" +
                "                <div class=\"display-flex width-100-percent height-250\">\r\n" +
                "                    <div class=\"width-100-percent\"></div>\r\n" +
                "                    <div class=\"width-100-percent align-self-center\">\r\n" +
                "                       <h4>Username</h4>" +
                "                        <div class=\"mui-textfield mui-textfield--float-label\">\r\n" +
                "                            <input tabindex=\"0\" type=\"text\" name=\"username\" id=\"username\" value=\"\" autofocus=\"\"\r\n" +
                "                                class=\"mui--is-empty mui--is-pristine mui--is-touched\">\r\n" +
                // " <label>user ID or e-mail</label>\r\n" +
                "                        </div>\r\n" +
                "                       <h4>Password</h4>" +
                "                        <div class=\"mui-textfield mui-textfield--float-label\">\r\n" +
                "                            <input type=\"password\" autocorrect=\"off\" name=\"password\" value=\"\"\r\n" +
                "                                id=\"password\" autocapitalize=\"off\" \r\n" +
                "                                class=\"mui--is-empty mui--is-untouched mui--is-pristine\">\r\n" +
                // " <label for=\"inputPassName\">Enter your password</label>\r\n" +
                "                        </div>\r\n" +
                "                    </div>\r\n");
        sb.append(createError(exchange));
        sb.append(
            "                    <div class=\"width-100-percent display-flex--bottom\">\r\n" +
                    "                            <button tabindex=\"0\" type=\"submit\" id=\"submitInputImmNameId\" class=\"mui-col-xs-12 mui-col-sm-offset-12 mui-col-md-6 mui-btn mui-btn--primary mui--pull-right\">\r\n"
                    +
                    "                                    Submit\r\n" +
                    "                                </button>\r\n" +
                    "                    </div>\r\n" +
                    "                </div>\r\n" +
                    "            </form>\r\n" +
                    "        </div>\r\n" +
                    "    </div>\r\n" +
                    "</div>");
        sb.append("</muithemeprovider>");
        sb.append("</body></html>");

        return new ResponseEntity<>(sb.toString(), OK);
    }

    /**
     * Inject the two required css files as inline style.<br/>
     * I could have targeted live css files but this approach allow us to run
     * in a kinda airplane mode (ie. without any network connection).
     * 
     * @return
     */
    private String inlineStyles() {
        StringBuilder sb = new StringBuilder();
        sb.append("<style>\r\n");
        sb.append(inlineStyle("css/mui.css"));
        sb.append(inlineStyle("css/style.css"));
        sb.append("\r\n</style>");
        return sb.toString();
    }

    /**
     * Return the content of a file as a <CODE>String</CODE>.
     * 
     * @param fileName
     * @return
     */
    private String inlineStyle(String fileName) {
        StringBuilder resultBuilder = new StringBuilder("");
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                resultBuilder.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("Error injecting css for file '{}'", fileName, e);
            return "";
        }
        return resultBuilder.toString();
    }

    /**
     * Return <CODE>true</CODE> if the keyword <CODE>error</CODE> is present
     * in the request parameters.
     * 
     * @return
     */
    private boolean isErrorPage(ServerWebExchange exchange) {
        return exchange.getRequest().getQueryParams().containsKey("error");
    }

    /**
     * Inject the error HTML tag if the page requires it.
     * 
     * @param exchange
     * @return
     */
    private String createError(ServerWebExchange exchange) {
        return isErrorPage(exchange) ? ERROR_TAG : "";
    }

}
