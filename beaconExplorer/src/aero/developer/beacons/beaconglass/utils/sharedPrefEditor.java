package aero.developer.beacons.beaconglass.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class sharedPrefEditor {
	Context _context = null;

	/*
	 * sharedPrefEditor Class
	 * 
	 * Used to saved String/Int/Long variables to the SharedPreferences
	 */
	/*
	 * Class Constructor
	 */
	public sharedPrefEditor(Context context) {
		_context = context;
	}

	/*
	 * void ClearAll()
	 * 
	 * Actions: cleans up the sharedPreference
	 * 
	 * Params: non
	 */
	public void ClearAll() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(_context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear();
		editor.commit();
	}

	/*
	 * void addString(String PrefName, String PrefValue)
	 * 
	 * Actions: saves a String value in the sharedPreference
	 * 
	 * Params: String PrefName : name of the variable to save String PrefValue:
	 * value of the variable to save
	 */
	public void addString(String PrefName, String PrefValue) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(_context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(PrefName, PrefValue);
		editor.commit();
	}
	
	
	/*
	 * void addBoolean(String PrefName, boolean PrefValue)
	 * 
	 * Actions: saves a boolean value in the sharedPreference
	 * 
	 * Params: String PrefName : name of the variable to save boolean PrefValue:
	 * value of the variable to save
	 */
	public void addBoolean(String PrefName, boolean PrefValue) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(_context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(PrefName, PrefValue);
		editor.commit();
	}

	/*
	 * void addString(String PrefName, String PrefValue)
	 * 
	 * Actions: saves a Int value in the sharedPreference
	 * 
	 * Params: String PrefName : name of the variable to save Int PrefValue:
	 * value of the variable to save
	 */
	public void addInt(String PrefName, int PrefValue) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(_context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(PrefName, PrefValue);
		editor.commit();
	}

	/*
	 * void addString(String PrefName, String PrefValue)
	 * 
	 * Actions: saves a Long value in the sharedPreference
	 * 
	 * Params: String PrefName : name of the variable to save Long PrefValue:
	 * value of the variable to save
	 */
	public void addLong(String PrefName, long PrefValue) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(_context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putLong(PrefName, PrefValue);
		editor.commit();
	}

	/*
	 * String getString(String StringName)
	 * 
	 * Param: String StringName
	 * 
	 * Returns/Retrieves String value of the Input Param.
	 */
	public String getString(String StringName) {
		String retValue = "";
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(_context);
		retValue = preferences.getString(StringName, "");

		return retValue;
	}

	/*
	 * String getString(String intName)
	 * 
	 * Param: String intName
	 * 
	 * Returns/Retrieves Int value of the Input Param.
	 */
	public int getInt(String intName) {
		int retValue = 0;
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(_context);
		retValue = preferences.getInt(intName, 0);

		return retValue;
	}

	/*
	 * Long getLong(String longName)
	 * 
	 * Param: String longName
	 * 
	 * Returns/Retrieves Long value of the Input Param.
	 */
	public Long getLong(String longName) {
		Long retValue = (long) 0;
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(_context);
		retValue = preferences.getLong(longName, 0);

		return retValue;
	}
	
	/*
	 * Boolean getBoolean(String longName)
	 * 
	 * Param: String longName
	 * 
	 * Returns/Retrieves Long value of the Input Param.
	 */
	public boolean getBoolean(String longName) {
		boolean retValue = false;
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(_context);
		retValue = preferences.getBoolean(longName, true);

		return retValue;
	}

}
