package com.afklm.spring.security.habile.proxy.controller;

import com.afklm.soa.stubs.common.systemfault.v1.ObjectFactory;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.common.systemfault.v1.SystemFault;
import com.afklm.soa.stubs.w000479.v1.HblWsBusinessException;
import com.afklm.soa.stubs.w000479.v1.fault.HblWsBusinessFault;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRQ;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRS;
import com.afklm.spring.security.habile.proxy.HabileSimulationGateway;
import com.afklm.spring.security.habile.proxy.ws.ProvideUserRightsAccessV10Impl;
import com.afklm.spring.security.habile.proxy.ws.ProvideUserRightsAccessV20Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.ByteArrayOutputStream;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

/**
 * WebServiceController
 * 
 * The purpose of this class is to provide a fake WebService suitable for
 * webflux integration jaxws implementation relies on servlet implementation and
 * this is not possible with reactive environment
 * 
 * @author m405991
 *
 */
@RestController
public class WebServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HabileSimulationGateway.class);

    private static final String START_NODE = "<userId>";
    private static final String END_NODE = "</userId>";

    private static String soapEnvelope = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
            + "<soapenv:Header /><soapenv:Body>%s</soapenv:Body></soapenv:Envelope>";

    private static String soapFault = "<soapenv:Fault >" + "<faultcode>soapenv:Server</faultcode>"
            + "<faultstring>%s</faultstring>" + "	<detail>%s</detail>" + "</soapenv:Fault>";

    @Autowired
    private ProvideUserRightsAccessV10Impl provideUserRightsAccessV10Impl;

    @Autowired
    private ProvideUserRightsAccessV20Impl provideUserRightsAccessV20Impl;

    private JAXBContext jaxbCtxV10Response;

    private JAXBContext jaxbCtxV10Fault;

    private JAXBContext jaxbCtxSystemFault;

    private ObjectFactory objectFactorySystemFault;

    private JAXBContext jaxbCtxV20Response;

    private JAXBContext jaxbCtxV20Fault;

    /**
     * Constructor
     * 
     * @throws JAXBException
     */
    public WebServiceController() throws JAXBException {
        jaxbCtxV10Response = JAXBContext.newInstance(ProvideUserRightsAccessRS.class);
        jaxbCtxV10Fault = JAXBContext.newInstance(HblWsBusinessFault.class);
        jaxbCtxSystemFault = JAXBContext.newInstance(SystemFault.class);
        objectFactorySystemFault = new ObjectFactory();

        jaxbCtxV20Response = JAXBContext
            .newInstance(com.afklm.soa.stubs.w000479.v2.provideuserrightsaccessv2.ProvideUserRightsAccessRS.class);
        jaxbCtxV20Fault = JAXBContext.newInstance(com.afklm.soa.stubs.w000479.v2.hblwsfault.HblWsBusinessFault.class);

    }

    /**
     * w000479v01 endpoint
     * 
     * @param request request
     * @return fake SOAP XML response
     * @throws JAXBException if an error coccurs
     */
    @PostMapping(path = "/mock/ws/w000479v01", produces = "text/xml")
    public ResponseEntity<String> w000479v01(@RequestBody String request) throws JAXBException {
        LOGGER.debug("w000479v01 invoked with '{}'", request);
        ProvideUserRightsAccessRS response;
        String userId = "N/A";
        try {
            ProvideUserRightsAccessRQ requestWS = new ProvideUserRightsAccessRQ();
            userId = extractUserId(request);
            LOGGER.debug("w000479v01 invoked for user '{}'", userId);
            requestWS.setUserId(userId);
            response = provideUserRightsAccessV10Impl.provideUserRightsAccess(requestWS);
            return new ResponseEntity<>(marshall(jaxbCtxV10Response.createMarshaller(), response), OK);
        } catch (HblWsBusinessException e) {
            LOGGER.error("Cannot retrieve user '{0}'", userId, e);
            return new ResponseEntity<>(marshallFault(jaxbCtxV10Fault.createMarshaller(), e.getFaultInfo(), e.getMessage()),
                INTERNAL_SERVER_ERROR);
        } catch (SystemException e) {
            LOGGER.error("Cannot retrieve user '{0}'", userId, e);
            return new ResponseEntity<>(marshallFault(jaxbCtxSystemFault.createMarshaller(),
                objectFactorySystemFault.createSystemFault(e.getFaultInfo()),
                e.getMessage()),
                INTERNAL_SERVER_ERROR);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("Cannot retrieve user '{0}'", request, e);
            SystemFault fault = new SystemFault();
            fault.setFaultDescription("Unmashalling via substring");
            fault.setErrorCode("-999");
            fault.setOriginatingError("extractUserId");
            return new ResponseEntity<>(marshallFault(jaxbCtxSystemFault.createMarshaller(), fault, e.getMessage()),
                INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * w000479v02 endpoint
     * 
     * @param request request
     * @return fake SOAP XML response
     * @throws JAXBException if an error coccurs
     */
    @PostMapping(path = "/mock/ws/w000479v02", produces = "text/xml")
    public ResponseEntity<String> w000479v02(@RequestBody String request) throws JAXBException {
        LOGGER.debug("w000479v01 invoked with '{}'", request);
        com.afklm.soa.stubs.w000479.v2.provideuserrightsaccessv2.ProvideUserRightsAccessRS response;
        String userId = "N/A";
        try {
            com.afklm.soa.stubs.w000479.v2.provideuserrightsaccessv2.ProvideUserRightsAccessRQ requestWS = new com.afklm.soa.stubs.w000479.v2.provideuserrightsaccessv2.ProvideUserRightsAccessRQ();
            userId = extractUserId(request);
            LOGGER.debug("w000479v01 invoked for user '{}'", userId);
            requestWS.setUserId(userId);
            response = provideUserRightsAccessV20Impl.provideUserRightsAccess(requestWS);
            return new ResponseEntity<>(marshall(jaxbCtxV20Response.createMarshaller(), response), OK);
        } catch (com.afklm.soa.stubs.w000479.v2.HblWsBusinessException e) {
            LOGGER.error("Cannot retrieve user '{0}'", userId, e);
            return new ResponseEntity<>(marshallFault(jaxbCtxV20Fault.createMarshaller(), e.getFaultInfo(), e.getMessage()),
                INTERNAL_SERVER_ERROR);
        } catch (SystemException e) {
            LOGGER.error("Cannot retrieve user '{0}'", userId, e);
            return new ResponseEntity<>(marshallFault(jaxbCtxSystemFault.createMarshaller(),
                objectFactorySystemFault.createSystemFault(e.getFaultInfo()),
                e.getMessage()),
                INTERNAL_SERVER_ERROR);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("Cannot retrieve user '{0}'", request, e);
            SystemFault fault = new SystemFault();
            fault.setFaultDescription("Unmashalling via substring");
            fault.setErrorCode("-999");
            fault.setOriginatingError("extractUserId");
            return new ResponseEntity<>(marshallFault(jaxbCtxSystemFault.createMarshaller(), fault, e.getMessage()),
                INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Basic method to extract the value of the <b>userId</b> node using substrings.
     * 
     * @param request
     * @return
     */
    private String extractUserId(String request) {
        return request.substring(request.indexOf(START_NODE) + START_NODE.length(), request.indexOf(END_NODE)).trim();
    }

    private String marshall(Marshaller marshaller, Object object) throws JAXBException {
        marshaller.setProperty("jaxb.fragment", Boolean.TRUE); // required to stop <?xml ... being added ?>
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marshaller.marshal(object, outputStream);
        String payload = new String(outputStream.toByteArray());
        String output = String.format(soapEnvelope, payload);
        LOGGER.debug("Marshalling with jaxb produced '{}'", output);
        return output;
    }

    private String marshallFault(Marshaller marshaller, Object object, String reason) throws JAXBException {
        marshaller.setProperty("jaxb.fragment", Boolean.TRUE); // required to stop <?xml ... being added ?>
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marshaller.marshal(object, outputStream);
        String payload = new String(outputStream.toByteArray());
        String output = String.format(soapEnvelope, String.format(soapFault, reason, payload));
        LOGGER.debug("Marshalling with jaxb produced '{}'", output);
        return output;
    }
}
