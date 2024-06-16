package com.airfrance.repind.util.converters;

import org.dozer.DozerConverter;

import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarConverter extends DozerConverter<XMLGregorianCalendar, XMLGregorianCalendar> {

	public XMLGregorianCalendarConverter() {
		super(XMLGregorianCalendar.class, XMLGregorianCalendar.class);
	}

	@Override
	public XMLGregorianCalendar convertFrom(XMLGregorianCalendar src, XMLGregorianCalendar dest) {
		return src;
	}

	@Override
	public XMLGregorianCalendar convertTo(XMLGregorianCalendar src, XMLGregorianCalendar dest) {
		return dest;
	}

}
