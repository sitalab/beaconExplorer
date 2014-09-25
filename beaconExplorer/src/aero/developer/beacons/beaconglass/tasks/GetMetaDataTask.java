package aero.developer.beacons.beaconglass.tasks;

import org.joda.time.LocalDate;

import aero.developer.beacons.beaconglass.R;
import aero.developer.beacons.beaconglass.Splash;
import aero.developer.beacons.beaconglass.httpRequests.HttpData;
import aero.developer.beacons.beaconglass.httpRequests.HttpRequest;
import aero.developer.beacons.beaconglass.objects.ATIBeacon;
import aero.developer.beacons.beaconglass.objects.AirPort;
import aero.developer.beacons.beaconglass.objects.TaskStatus;
import aero.developer.beacons.beaconglass.utils.AppStatics;
import aero.developer.beacons.beaconglass.utils.Helpers;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.timeline.LiveCard;
import com.google.gson.Gson;

/**
 * Get meta data about beacons.
 * 
 * @author kevinosullivan
 * 
 */
public class GetMetaDataTask extends AsyncTask<Void, Void, ATIBeacon> {

	// private SplashScreen splashScreen;
	private ATIBeacon atiBeacon;
	String jsonResponse;
	private String flight;
	private Object paxId;
	private String deviceIdentifier;
	private Float rssi;
	private final Handler mHandler = new Handler();
	private LocalDate flightDate;
	private Activity activity = null;
	private Context context = null;
	private TaskStatus status;
	private static final int NOTIFICATION_ID = 1;
	private NotificationManager notificationManager;
	private TextView messageView;
	private AudioManager mAudioManager;
	private LiveCard mLiveCard;
	private static final String LIVE_CARD_TAG = "LiveCard";
	private RemoteViews mLiveCardView;
	private String msg;

	/**
    * 
    */
	public GetMetaDataTask(Activity activity, ATIBeacon atiBeacon) {
		this.atiBeacon = atiBeacon;
		this.activity = activity;

	}

	/**
    * 
    */
	public GetMetaDataTask(Activity activity, ATIBeacon atiBeacon,
			String flight, LocalDate flightDate, String paxId, Float rssi,
			String deviceIdentifier, TextView messageView) {
		this.atiBeacon = atiBeacon;
		this.flight = flight;
		this.flightDate = flightDate;
		this.paxId = paxId;
		this.rssi = rssi;
		this.deviceIdentifier = deviceIdentifier;
		this.activity = activity;
		this.notificationManager = (NotificationManager) activity
				.getSystemService(activity.NOTIFICATION_SERVICE);
		this.messageView = messageView;
		mAudioManager = (AudioManager) activity
				.getSystemService(Context.AUDIO_SERVICE);

	}

	/**
	    * 
	    */
	public GetMetaDataTask(Context context, ATIBeacon atiBeacon, String flight,
			LocalDate flightDate, String paxId, Float rssi,
			String deviceIdentifier, LiveCard livecard) {
		this.atiBeacon = atiBeacon;
		this.flight = flight;
		this.flightDate = flightDate;
		this.paxId = paxId;
		this.rssi = rssi;
		this.context = context;
		this.deviceIdentifier = deviceIdentifier;
		this.notificationManager = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);
		this.mLiveCard = livecard;
		mAudioManager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);

	}

	@Override
	protected ATIBeacon doInBackground(Void... params) {
		if (HttpRequest.isConnected()) {
			HttpData data = null;
			HttpRequest request = new HttpRequest();
			if (context == null && activity != null) {
				context = activity.getApplicationContext();
			}
			new AppStatics(context);
			Log.i("GetMetaDataTask",
					"Loading meta-data for  " + atiBeacon.getUuid());
			String url = AppStatics.BeaconDetails + atiBeacon.getLocation()
					+ "/" + atiBeacon.getUuid() + "/" + atiBeacon.getMajorId()
					+ "/" + atiBeacon.getMinorId() + "?ignoreMe";
			url += "&app_id=" + AppStatics.appId + "&app_key="
					+ AppStatics.api_KEY_VALUE;

			if (flight != null)
				url += "&flightNumber=" + flight;
			if (flightDate != null)
				url += "&flightDate=" + flightDate.toString();
			if (paxId != null)
				url += "&passengerIdentifier=" + paxId;
			if (rssi != null)
				url += "&measuredRSSI=" + rssi;
			if (deviceIdentifier != null)
				url += "&deviceIdentifier=" + deviceIdentifier;

			data = request.get(url, context);

			if (data.content != null) {
				Gson gson = new Gson();
				ATIBeacon atiBeacon = gson.fromJson(data.content,
						ATIBeacon.class);
				this.status = TaskStatus.SUCCESS;
				return atiBeacon;
			}

		} else {
			this.status = TaskStatus.NO_INTERNET;
		}
		this.status = TaskStatus.FAIL;

		return null;
	}

	@Override
	protected void onPostExecute(ATIBeacon atiBeacon) {

		if (this.status == TaskStatus.SUCCESS) {
			Intent intent = new Intent(context, Splash.class);
			PendingIntent pIntent = PendingIntent.getActivity(context, 0,
					intent, 0);

			msg = null;

			if (atiBeacon != null && atiBeacon.getMetaData() != null
					&& atiBeacon.getMetaData().getDescriptiveText() != null) {
				msg = atiBeacon.getMetaData().getDescriptiveText();
			}

			if (msg == null || msg.trim().length() == 0) {
				AirPort location = Helpers.get_nearestAirport(context);
				msg = String.format(context.getString(R.string.noDescriptive),
						atiBeacon.getName(), location.getAirportCode());
			}

			// Helpers.ShowMsgDialog(this.activity, null, msg, false);
			if (messageView != null) {
				messageView.setText(msg);
			} else {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						mLiveCardView = new RemoteViews(context
								.getPackageName(), R.layout.main);
						mLiveCardView.setTextViewText(R.id.msgView,
								String.valueOf(msg));
						mLiveCard.setViews(mLiveCardView);

					}
				});

			}
			mAudioManager.playSoundEffect(Sounds.SUCCESS);
		}
	}

}