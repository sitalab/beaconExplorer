package aero.developer.beaconExplorer.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import aero.developer.beaconExplorer.objects.AirPort;

public class AirportsParser {

	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	Map<String, AirPort> AirlineMap = new HashMap<String, AirPort>();

	public AirportsParser() {
		this.AirlineMap = new HashMap<String, AirPort>();
	}

	private String getNodeValue(NamedNodeMap map, String key) {
		String nodeValue = null;
		Node node = map.getNamedItem(key);
		if (node != null) {
			nodeValue = node.getNodeValue();
		}
		return nodeValue;
	}

	public Map<String, AirPort> getList() {
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

			NodeList AirlineList = doc.getElementsByTagName("airport");
			final int length = AirlineList.getLength();
			for (int i = 0; i < length; i++) {
				final NamedNodeMap attr = AirlineList.item(i).getAttributes();
				final String airportName = getNodeValue(attr, "airportName");
				final String airportCity = getNodeValue(attr, "airportCity");
				final String airportCountry = getNodeValue(attr,
						"airportCountry");
				final String airportCode = getNodeValue(attr, "airportCode");
				final String airportLongtitude = getNodeValue(attr,
						"Longtitude");
				final String airportLatitdude = getNodeValue(attr, "Latitude");
				AirPort airport = new AirPort(airportName, airportCity,
						airportCountry, airportCode, airportLongtitude,
						airportLatitdude);

				// Add to list
				AirlineMap.put(airportCode, airport);

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