package aero.developer.beaconExplorer.utils;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import aero.developer.beaconExplorer.R;
import aero.developer.beaconExplorer.database.DatabaseOps;
import aero.developer.beaconExplorer.objects.ATIBeacon;
import aero.developer.beaconExplorer.objects.AirPort;
import aero.developer.beaconExplorer.objects.Airline;
import aero.developer.beaconExplorer.objects.User;
import aero.developer.beaconExplorer.parsers.AirlineParser;
import aero.developer.beaconExplorer.parsers.AirportsParser;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;

public class Helpers {

	public Helpers() {
	}

	/**
	 * function to add User into database [current loaded user]
	 * 
	 * @param context
	 * @param user
	 *            Object of type {@link User}
	 * 
	 * */

	public static void AddUser(Context context, User user) {
		DatabaseOps ops = new DatabaseOps(context);
		ops.InsertIntoDb("Cache_Table", "t_CacheObject_Name", "User",
				"b_CacheObjectByte_Value", Serializer.serializeObject(user));
	}

	public static void deleteUser(Context context) {
		DatabaseOps ops = new DatabaseOps(context);
		ops.DeleteObjectFromDb("Cache_Table", "t_CacheObject_Name", "'User'");

	}

	/**
	 * function to save the nearest airport retrieved from splash by coordinates
	 * 
	 * @param context
	 * @param airport
	 * 
	 * */

	public static void Save_nearestAirport(Context context, AirPort airport) {
		DatabaseOps ops = new DatabaseOps(context);
		ops.InsertIntoDb("Cache_Table", "t_CacheObject_Name", "nearestAirport",
				"b_CacheObjectByte_Value", Serializer.serializeObject(airport));
	}

	public static void delete_nearestAirport(Context context) {
		DatabaseOps ops = new DatabaseOps(context);
		ops.DeleteObjectFromDb("Cache_Table", "t_CacheObject_Name",
				"'nearestAirport'");

	}

	/**
	 * function to get saved nearest airport from the database.
	 * 
	 * @param context
	 * @return {@link AirPort}
	 * */

	public static AirPort get_nearestAirport(Context context) {

		DatabaseOps ops = new DatabaseOps(context);
		byte[] b = ops.getObjectFromDb("Cache_Table",
				"b_CacheObjectByte_Value", "t_CacheObject_Name",
				"nearestAirport");
		AirPort airport = (AirPort) Serializer.deserializeObject(b);

		return airport;

	}

	/**
	 * function to save the registered beacons
	 * 
	 * @param context
	 * @param beacons
	 *            list of beacons
	 * 
	 * */

	public static void Save_beacons(Context context, List<ATIBeacon> beacons) {
		DatabaseOps ops = new DatabaseOps(context);
		ops.InsertIntoDb("Cache_Table", "t_CacheObject_Name", "beacons",
				"b_CacheObjectByte_Value", Serializer.serializeObject(beacons));
	}

	/**
	 * function to delete the registered beacons
	 * 
	 * @param context
	 * 
	 * */

	public static void delete_beacons(Context context) {
		DatabaseOps ops = new DatabaseOps(context);
		ops.DeleteObjectFromDb("Cache_Table", "t_CacheObject_Name", "'beacons'");

	}

	public static List<ATIBeacon> get_beacons(Context context) {

		DatabaseOps ops = new DatabaseOps(context);
		byte[] b = ops.getObjectFromDb("Cache_Table",
				"b_CacheObjectByte_Value", "t_CacheObject_Name", "beacons");
		List<ATIBeacon> beacons = (List<ATIBeacon>) Serializer
				.deserializeObject(b);

		return beacons;

	}

	/**
	 * function to get stored user card in the database[current loaded user]
	 * 
	 * @param context
	 * 
	 * @return {@link User}
	 * 
	 *         Object of type{@link User}
	 * 
	 * */

	public static User getUser(Context context) {

		DatabaseOps ops = new DatabaseOps(context);
		byte[] b = ops.getObjectFromDb("Cache_Table",
				"b_CacheObjectByte_Value", "t_CacheObject_Name", "User");
		User user = (User) Serializer.deserializeObject(b);

		return user;

	}

	public static String GetTranslation(Context context, String Field) {
		if (Field.trim().length() > 0)
			return context.getString(get_resID(context, Field));
		return context.getString(get_resID(context, "None"));
	}

	public static int get_resID(Context context, String Field) {
		String packageName = context.getPackageName();
		return context.getResources().getIdentifier(Field, "string",
				packageName);

	}

	/**
	 * function to fetch list of airlines from a file
	 * 
	 * @param context
	 *            activity or fragment context
	 * @return list of airlines
	 * 
	 * */
	public static List<Airline> getAirlinesList(Context context) {
		AirlineParser airlineParser = new AirlineParser();
		InputStream inputStream = context.getResources().openRawResource(
				R.raw.airlinecodes);

		airlineParser.parse(inputStream);
		List<Airline> countries = airlineParser.getList();
		return countries;
	}

	public static Airline getAirline(List<Airline> airlines, String AirlineCode) {
		for (int i = 0; i < airlines.size(); i++) {
			Airline item = airlines.get(i);
			if (item.getCode().equalsIgnoreCase(AirlineCode))
				return item;
		}

		return null;

	}

	public static void addAirports(Context context) {
		DatabaseOps ops = new DatabaseOps(context);
		Map<String, AirPort> items = getAirports(context);
		items = null;
		if (items == null) {
			AirportsParser parser = new AirportsParser();
			InputStream inputStream = context.getResources().openRawResource(
					R.raw.airports);

			parser.parse(inputStream);
			Map<String, AirPort> airports = parser.getList();

			ops.InsertIntoDb("Cache_Table", "t_CacheObject_Name", "Airports",
					"b_CacheObjectByte_Value",
					Serializer.serializeObject(airports));

		}
	}

	public static Map<String, AirPort> getAirports(Context context) {

		DatabaseOps ops = new DatabaseOps(context);
		byte[] b = ops.getObjectFromDb("Cache_Table",
				"b_CacheObjectByte_Value", "t_CacheObject_Name", "Airports");
		Map<String, AirPort> airports = (Map<String, AirPort>) Serializer
				.deserializeObject(b);

		return airports;
	}

	/**
	 * Get Last Known Location
	 * 
	 * @param ctx
	 *            Context, can not be null
	 * @return Location Object or null if could not get last Known Location
	 */
	public static Location getLocation(Context ctx) {
		LocationManager lm = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = lm.getProviders(true);

		/*
		 * Loop over the array backwards, and if you get an accurate location,
		 * then break out the loop
		 */
		Location l = null;

		for (int i = providers.size() - 1; i >= 0; i--) {
			l = lm.getLastKnownLocation(providers.get(i));
			if (l != null)
				break;
		}
		return l;
	}

	public static String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		} else {
			return capitalize(manufacturer) + " " + model;
		}
	}

	private static String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	}

}
