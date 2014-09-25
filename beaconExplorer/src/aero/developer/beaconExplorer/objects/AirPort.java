package aero.developer.beaconExplorer.objects;

import java.io.Serializable;

public class AirPort implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2916710153267225955L;
	private String airportName;
	private String airportCity;
	private String airportCountry;
	private String airportCode;
	private String airportLongtitude;
	private String airportLatitdude;

	public AirPort() {
	}

	public AirPort(String airportName, String airportCity,
			String airportCountry, String airportCode,
			String airportLongtitude, String airportLatitdude) {
		this.airportName = airportName;
		this.airportCity = airportCity;
		this.airportCountry = airportCountry;
		this.airportCode = airportCode;
		this.airportLongtitude = airportLongtitude;
		this.airportLatitdude = airportLatitdude;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public String getAirportCity() {
		return airportCity;
	}

	public void setAirportCity(String airportCity) {
		this.airportCity = airportCity;
	}

	public String getAirportCountry() {
		return airportCountry;
	}

	public void setAirportCountry(String airportCountry) {
		this.airportCountry = airportCountry;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getAirportLongtitude() {
		return airportLongtitude;
	}

	public void setAirportLongtitude(String airportLongtitude) {
		this.airportLongtitude = airportLongtitude;
	}

	public String getAirportLatitdude() {
		return airportLatitdude;
	}

	public void setAirportLatitdude(String airportLatitdude) {
		this.airportLatitdude = airportLatitdude;
	}

	@Override
	public String toString() {
		return "AirPorts [airportName=" + airportName + ", airportCity="
				+ airportCity + ", airportCountry=" + airportCountry
				+ ", airportCode=" + airportCode + ", airportLongtitude="
				+ airportLongtitude + ", airportLatitdude=" + airportLatitdude
				+ "]";
	}

}
