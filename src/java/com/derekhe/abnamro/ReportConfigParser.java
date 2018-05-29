package com.derekhe.abnamro;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.xml.sax.helpers.DefaultHandler;

/**
 * This class is used to read and parse report_config.xml.
 * 
 * @author derekhe
 *
 */
class ReportConfigParser extends DefaultHandler {

	private static final String REPORT_CONFIG = "report_config.xml";

	/**
	 * Parse report_config.xml and convert values to a list of {@link Information}.
	 * 
	 * @return
	 * @throws IOException
	 * @throws XMLStreamException
	 */
	public List<Information> parse() throws IOException, XMLStreamException {
		String path = ClassLoader.getSystemResource(REPORT_CONFIG).getPath();
		List<Information> informations = new ArrayList<>();;
		Information information = null;
		Item item = null;
		String text = null;

		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(path));

		while (reader.hasNext()) {
			int Event = reader.next();

			switch (Event) {
			case XMLStreamConstants.START_ELEMENT: {
				if ("information".equals(reader.getLocalName())) {
					information = new Information();
					information.setType(reader.getAttributeValue(0));
					information.setMode(reader.getAttributeValue(1));
				}

				if ("item".equals(reader.getLocalName())) {
					item = new Item();
				}
				break;
			}
			case XMLStreamConstants.CHARACTERS: {
				text = reader.getText().trim();
				break;
			}
			case XMLStreamConstants.END_ELEMENT: {
				switch (reader.getLocalName()) {
				case "information": {
					informations.add(information);
					break;
				}
				case "item": {
					information.addItem(item);
					break;
				}
				case "name": {
					item.setName(text);
					break;
				}
				case "beginIndex": {
					item.setBeginIndex(Integer.parseInt(text));
					break;
				}
				case "endIndex": {
					item.setEndIndex(Integer.parseInt(text));
					break;
				}
				case "operator": {
					item.setOperator(text);
					break;
				}

				}
				break;
			}
			}
		}

		return informations;
	}
}