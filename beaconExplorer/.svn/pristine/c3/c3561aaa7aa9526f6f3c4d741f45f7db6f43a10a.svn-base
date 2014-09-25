package aero.developer.beacons.beaconglass.objects;

import java.io.Serializable;

public class Airline implements Comparable<Airline>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -594911641042962075L;
	private String Code;
	private String Name;
	private String Platenumber;

	public Airline() {
		super();
	}

	public Airline(String code, String name, String platenumber) {
		super();
		Code = code;
		Name = name;
		setPlatenumber(platenumber);
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	@Override
	public String toString() {
		return "Airline [Code=" + Code + ", Name=" + Name + ", Platenumber="
				+ Platenumber + "]";
	}

	@Override
	public int compareTo(Airline another) {
		return Name.compareTo(another.Name);
	}

	/**
	 * @return the platenumber
	 */
	public String getPlatenumber() {
		return Platenumber;
	}

	/**
	 * @param platenumber
	 *            the platenumber to set
	 */
	public void setPlatenumber(String platenumber) {
		Platenumber = platenumber;
	}

}
