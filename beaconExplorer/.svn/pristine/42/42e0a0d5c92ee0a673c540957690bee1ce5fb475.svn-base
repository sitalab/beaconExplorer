package aero.developer.beacons.beaconglass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import aero.developer.beacons.beaconglass.objects.ATIBeacon;
import aero.developer.beacons.beaconglass.objects.ATIDevice;
import aero.developer.beacons.beaconglass.objects.EntryItem;
import aero.developer.beacons.beaconglass.objects.Item;
import aero.developer.beacons.beaconglass.objects.User;
import aero.developer.beacons.beaconglass.tasks.GetMetaDataTask;
import aero.developer.beacons.beaconglass.utils.Helpers;
import aero.developer.beacons.beaconglass.utils.sharedPrefEditor;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings.Secure;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.LiveCard.PublishMode;

public class GeofenceScanService extends Service {
	private NotificationManager mNM;

	// Unique Identification Number for the Notification.
	// We use it on Notification start, and to cancel it.
	private int NOTIFICATION = 1515;

	private ArrayList<ATIDevice> allDevices = new ArrayList<ATIDevice>();
	private ArrayList<Item> near = new ArrayList<Item>();
	private Map<String, ATIBeacon> beacons;
	private EntryItem LastBeacon = null;
	private BluetoothAdapter mBluetoothAdapter;
	private Handler mHandler;
	private Runnable populate;
	private Runnable scanafter;
	private static final long SCAN_PERIOD = 60000;
	private LiveCard mLiveCard;
	private static final String LIVE_CARD_TAG = "LiveCard";
	private RemoteViews mLiveCardView;

	@Override
	public void onCreate() {
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// notifcation to show the process is runing.
		showNotification();
		mHandler = new Handler();
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();

		// Checks if Bluetooth is supported on the device.
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, R.string.error_bluetooth_not_supported,
					Toast.LENGTH_SHORT).show();
			stopSelf();
			List<ATIBeacon> beaconslist = Helpers.get_beacons(this);
			beacons = new HashMap<String, ATIBeacon>();
			for (ATIBeacon i : beaconslist)
				beacons.put(i.getMacAddress(), i);
			return;
		}
		scanLeDevice(true);
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		mNM.cancel(NOTIFICATION);
		scanLeDevice(false);
		mHandler.removeCallbacks(scanafter);
		mHandler.removeCallbacks(populate);
		mLiveCard.unpublish();

		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (mLiveCard == null) {
			mLiveCard = new LiveCard(this, LIVE_CARD_TAG);
			// Inflate a layout into a remote view
			mLiveCardView = new RemoteViews(getPackageName(), R.layout.main);
			mLiveCardView
					.setTextViewText(R.id.msgView, getString(R.id.loading));
			Intent menuIntent = new Intent(this, Splash.class);
			menuIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TASK);
			mLiveCard.setAction(PendingIntent.getActivity(this, 0, menuIntent,
					0));

			// Publish the live card
			if (mLiveCard.isPublished()) {
				mLiveCard.unpublish();
			}
			mLiveCard.publish(PublishMode.REVEAL);

			// Queue the update text runnable

		}

		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	private void showNotification() {
		CharSequence text = "Service Started";
		// Set the icon, scrolling text and timestamp
		Notification notification = new Notification(R.drawable.ic_launcher,
				text, System.currentTimeMillis());
		notification.flags |= Notification.FLAG_ONGOING_EVENT
				| Notification.FLAG_NO_CLEAR;

		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(this, "Scanning for beacons in region",
				text, null);

		// Send the notification.
		mNM.notify(NOTIFICATION, notification);
	}

	private void scanLeDevice(final boolean enable) {
		if (enable) {
			allDevices.clear();
			scanafter = new Runnable() {
				@Override
				public void run() {
					scanLeDevice(true);
				}
			};
			populate = new Runnable() {
				@Override
				public void run() {
					// clearing all near we found before.
					near.clear();
					// stopping scan stopLescan got call back to look into it

					mBluetoothAdapter.stopLeScan(mLeScanCallback);
					List<ATIBeacon> beaconslist = Helpers
							.get_beacons(GeofenceScanService.this);
					// getting unique beacons by MacAddress

					beacons = new HashMap<String, ATIBeacon>();
					for (ATIBeacon i : beaconslist)
						beacons.put(i.getMacAddress(), i);

					Map<String, ATIDevice> map = new HashMap<String, ATIDevice>();
					for (ATIDevice ays : allDevices) {
						map.put(ays.getDevice().getAddress(), ays);
					}
					// clearing and populating all devices

					allDevices.clear();
					allDevices.addAll(map.values());
					Collections.sort(allDevices);
					if (allDevices.size() > 0) {

						for (int i = 0; i < allDevices.size(); i++) {
							String MacAddres = allDevices.get(i).getDevice()
									.getAddress();
							if (beacons.containsKey(MacAddres)) {
								near.add(new EntryItem(beacons.get(MacAddres),
										allDevices.get(i).getRssi(), allDevices
												.get(i).getDevice(), true));
							}
							beacons.remove(MacAddres);
						}

						if (near.size() > 0) {
							EntryItem nearstitem = (EntryItem) near.get(0);
							if (nearstitem != null) {
								// checking if we have nearest item so we notify
								// the user that we hit a new beacon.
								CheckifLastChanged(nearstitem);
							}
							beacons.remove(nearstitem.item.getMacAddress());
							near.remove(0);

						}
					}
					// launching scan again after period.

					mHandler.postDelayed(scanafter, SCAN_PERIOD);

				}
			};
			// stopping scan after 1 second then populating

			mHandler.postDelayed(populate, 1000);

			mBluetoothAdapter.startLeScan(mLeScanCallback);
		} else {
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}
	}

	// Device scan callback.
	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

		@Override
		public void onLeScan(final BluetoothDevice device, final int rssi,
				final byte[] scanRecord) {
			// adding all devices discovered within 1 second before

			allDevices.add(new ATIDevice(device, rssi));
		}
	};

	private void CheckifLastChanged(EntryItem newNear) {
		sharedPrefEditor sharedpref = new sharedPrefEditor(
				GeofenceScanService.this);
		boolean CanNotify = sharedpref.getBoolean("notificationBeacon");

		if (newNear != null && newNear.item != null) {
			if (LastBeacon == null || LastBeacon.item == null) {
				if (CanNotify) {

					User user = Helpers.getUser(GeofenceScanService.this);
					ATIBeacon atiBeacon = newNear.item;
					String flight = "";
					String paxId = "";
					if (user != null) {
						flight = user.getAirline() + user.getFlight();
						paxId = user.getFirstName() + " " + user.getLastName();
					} else {
						flight = "XS0001";
						paxId = "Ahmad Hariss";
					}

					float rssi = (float) newNear.rssi;
					String deviceIdentifier = Secure.getString(
							getContentResolver(), Secure.ANDROID_ID);

					// Tracking user and notifying if notify is enabled
					GetMetaDataTask getmeta = new GetMetaDataTask(
							getApplicationContext(), atiBeacon, flight,
							new LocalDate(), paxId, rssi, deviceIdentifier,
							mLiveCard);
					getmeta.execute();
				}
				LastBeacon = newNear;
			} else if (!newNear.item.getMacAddress().equals(
					LastBeacon.item.getMacAddress())) {
				if (CanNotify) {
					User user = Helpers.getUser(GeofenceScanService.this);
					ATIBeacon atiBeacon = newNear.item;
					String flight = "";
					String paxId = "";
					if (user != null) {
						flight = user.getAirline() + user.getFlight();
						paxId = user.getFirstName() + " " + user.getLastName();
					} else {
						flight = "XS0001";
						paxId = "Ahmad Hariss";
					}

					float rssi = (float) newNear.rssi;
					String deviceIdentifier = Secure.getString(
							getContentResolver(), Secure.ANDROID_ID);

					// Tracking user and notifying if notify is enabled
					GetMetaDataTask getmeta = new GetMetaDataTask(
							getApplicationContext(), atiBeacon, flight,
							new LocalDate(), paxId, rssi, deviceIdentifier,
							mLiveCard);
					getmeta.execute();
				}
				LastBeacon = newNear;
			}
		}
	}

	public static ArrayList<Item> cloneList(List<Item> list) {
		ArrayList<Item> clone = new ArrayList<Item>(list.size());
		for (Item item : list)
			clone.add(item);
		return clone;
	}

}