package com.airfrance.repind.util;

import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.message.HeaderList;
import com.sun.xml.ws.developer.JAXWSProperties;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.NodeList;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

public class ReadSoaHeaderHelper {

	private ReadSoaHeaderHelper() {
		
	}
	
	/**
	 * Get a header from the web service context.
	 * @param context
	 * @param headerName
	 * @return
	 * @throws XMLStreamException
	 */
	public static Header getHeaderFromContext(WebServiceContext context, String headerName) {
		
		if (context != null) { 
			MessageContext mContext = context.getMessageContext();
			if (StringUtils.isBlank(headerName) || mContext == null) {
				return null;
			}
	
			// Get All headers from context
			HeaderList headers = (HeaderList) context.getMessageContext().get(JAXWSProperties.INBOUND_HEADER_LIST_PROPERTY);
			
			for (Header header : headers) {
				// If header (which is a balise) has the same name than the headerName, return the header
				if (headerName.equalsIgnoreCase(header.getLocalPart())) {
					return header;
				}
			}
		}

		return null;
	}

	/**
	 * Get a child from the header using the childName
	 * @param header
	 * @param childrenName
	 * @return
	 * @throws SOAPException
	 */
	public static String getHeaderChildren(Header header, String childrenName) throws SOAPException {
		if (header == null) {
			return null;
		}
		
		SOAPMessage message = MessageFactory.newInstance().createMessage();
		header.writeTo(message);
		SOAPHeader head = message.getSOAPHeader();
		NodeList consumer = head.getElementsByTagNameNS("*", childrenName);
		
		if (consumer != null && consumer.getLength() > 0
				&& consumer.item(0) != null 
				&& consumer.item(0).getChildNodes() != null && consumer.item(0).getChildNodes().getLength() > 0
				&& consumer.item(0).getChildNodes().item(0) != null) {
			return consumer.item(0).getChildNodes().item(0).getNodeValue();
		}
		
		return null;
		
	}
}
