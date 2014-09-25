package aero.developer.beaconExplorer.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import aero.developer.beaconExplorer.objects.Airline;



public class AirlineParser {

	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	List<Airline> AirlineMap = new ArrayList<Airline>();

	public AirlineParser() {
		this.AirlineMap = new ArrayList<Airline>();
	}

	private String getNodeValue(NamedNodeMap map, String key) {
		String nodeValue = null;
		Node node = map.getNamedItem(key);
		if (node != null) {
			nodeValue = node.getNodeValue();
		}
		return nodeValue;
	}

	public List<Airline> getList() {
		Collections.sort(AirlineMap);
		return this.AirlineMap;
	}

	/**
	 * Parse XML file containing body part X/Y/Description
	 * 
	 * @param inStream
	 */
	public void parse(InputStream inStream) {
		try {
			this.factory = DocumentBuilderFactory.newInstance();
			this.builder = this.factory.newDocumentBuilder();
			this.builder.isValidating();
			Document doc = this.builder.parse(inStream, null);

			doc.getDocumentElement().normalize();

			NodeList AirlineList = doc.getElementsByTagName("Airline");
			final int length = AirlineList.getLength();

			for (int i = 0; i < length; i++) {
				final NamedNodeMap attr = AirlineList.item(i).getAttributes();
				final String Name = getNodeValue(attr, "Name");
				final String Code = getNodeValue(attr, "Code");
				final String platenumber = getNodeValue(attr, "platenumber");

				Airline Airline = new Airline(Code, Name, platenumber);

				// Add to list
				AirlineMap.add(Airline);

			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
}