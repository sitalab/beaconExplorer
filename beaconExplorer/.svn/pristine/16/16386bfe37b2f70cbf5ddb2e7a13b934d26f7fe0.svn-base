package aero.developer.beaconExplorer;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import aero.developer.beaconExplorer.httpRequests.HttpData;
import aero.developer.beaconExplorer.httpRequests.HttpRequest;
import aero.developer.beaconExplorer.objects.ATIBeacon;
import aero.developer.beaconExplorer.objects.AirPort;
import aero.developer.beaconExplorer.objects.TaskStatus;
import aero.developer.beaconExplorer.utils.AppStatics;
import aero.developer.beaconExplorer.utils.Helpers;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;

import com.google.glass.widget.SliderView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Splash extends Activity {
	protected PowerManager.WakeLock mWakeLock;
	private SliderView mIndeterm;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);
		context = this;
		mIndeterm = (SliderView) findViewById(R.id.loading);

		mIndeterm.startIndeterminate();

		final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK,
				"Beacon");
		this.mWakeLock.acquire();
		new initSplash().execute();
	}

	@Override
	public void onDestroy() {
		this.mWakeLock.release();
		super.onDestroy();
	}

	class initSplash extends AsyncTask<Void, Void, Void> {
		TaskStatus status;

		@Override
		protected Void doInBackground(Void... params) {

			// deleteing previous saved info
			Helpers.delete_nearestAirport(context);
			Helpers.delete_beacons(context);

			Helpers.addAirports(context);

			if (HttpRequest.isConnected()) {
				HttpData data = null;
				HttpRequest request = new HttpRequest();

				new AppStatics(getApplicationContext());

				// getting last good coordinates
				Location location = Helpers.getLocation(context);

				if (location != null) {
					// retriving nearest airport.
					String sUrl = AppStatics.AirportURL
							+ location.getLatitude() + "/"
							+ location.getLongitude() + "?" + "app_id="
							+ AppStatics.appId + "&app_key="
							+ AppStatics.api_KEY_VALUE;
					data = request.get(sUrl, getApplicationContext());
					String jsonStr = data.content;
					if (jsonStr != null) {
						try {

							JSONArray jsonarray = new JSONArray(jsonStr);

							JSONObject item = jsonarray.getJSONObject(0);

							if (item != null) {
								AirPort nearestAirport = new AirPort();
								nearestAirport.setAirportCity(item
										.getString("city"));
								nearestAirport.setAirportCode(item
										.getString("code"));
								nearestAirport.setAirportCountry(item
										.getString("country"));
								nearestAirport.setAirportName(item
										.getString("name"));
								nearestAirport.setAirportLatitdude(item
										.getString("latitude"));
								nearestAirport.setAirportLongtitude(item
										.getString("longitude"));
								Helpers.Save_nearestAirport(context,
										nearestAirport);

								// when success if the airport exist, then we
								// should get the beacons registered.

								String beaconUrl = AppStatics.beaconsUrl
										+ "?UserName=ziad@itx.net&airportCode="
										+ nearestAirport.getAirportCode()
										+ "&app_id=" + AppStatics.appId
										+ "&app_key="
										+ AppStatics.api_KEY_VALUE;
								data = request.get(beaconUrl,
										getApplicationContext());

								Type listType = new TypeToken<List<ATIBeacon>>() {
								}.getType();
								List<ATIBeacon> beacons = new Gson().fromJson(
										data.content, listType);

								Helpers.Save_beacons(context, beacons);

								this.status = TaskStatus.SUCCESS;

							}

						} catch (JSONException e) {
							this.status = TaskStatus.FAIL;
							e.printStackTrace();
						}
					}// end handle of airport json response.
				} else {
					this.status = TaskStatus.NOCOORDINATES;
				}

			} else {
				this.status = TaskStatus.NO_INTERNET;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			Intent i = new Intent(Splash.this, MainActivity.class);
			startActivity(i);
			finish();

		}

	}

}
