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
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ArrayList<ATIDevice> allDevices = new ArrayList<ATIDevice>();
	private ArrayList<Item> near = new ArrayList<Item>();
	private Map<String, ATIBeacon> beacons;
	private EntryItem LastBeacon = null;
	private TextView msgView;

	/*
	 * bluetooth variables
	 */
	private BluetoothAdapter mBluetoothAdapter;
	private Handler mHandler;
	private Runnable populate;
	private Runnable scanafter;

	private static final int REQUEST_ENABLE_BT = 1;
	// Stops scanning after 10 seconds.
	private static final long SCAN_PERIOD = 5000;
	private Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main);

		mHandler = new Handler();

		activity = this;
		msgView = (TextView) findViewById(R.id.msgView);
		final BluetoothManager bluetoothManager = (BluetoothManager) activity
				.getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();

		// Checks if Bluetooth is supported on the device.
		if (mBluetoothAdapter == null) {
			Toast.makeText(activity, R.string.error_bluetooth_not_supported,
					Toast.LENGTH_SHORT).show();
			activity.finish();
			return;
		}

		// putting unique Ledevices into by MacAdderss
		List<ATIBeacon> beaconslist = Helpers.get_beacons(activity);
		beacons = new HashMap<String, ATIBeacon>();
		for (ATIBeacon i : beaconslist)
			beacons.put(i.getMacAddress(), i);

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		if (!mBluetoothAdapter.isEnabled()) {
			if (!mBluetoothAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			}
		}
		scanLeDevice(true);
		stopService(new Intent(this, GeofenceScanService.class));

		super.onResume();
	}

	@Override
	public void onPause() {
		scanLeDevice(false);
		mHandler.removeCallbacks(scanafter);
		mHandler.removeCallbacks(populate);
		super.onPause();

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// User chose not to enable Bluetooth.
		if (requestCode == REQUEST_ENABLE_BT
				&& resultCode == Activity.RESULT_CANCELED) {
			this.finish();
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void scanLeDevice(final boolean enable) {
		if (enable) {
			allDevices.clear();

			// creating runnable to open leadapter and scan for beacons

			scanafter = new Runnable() {
				@Override
				public void run() {
					scanLeDevice(true);
				}
			};
			// creating runnable to manipulate and populate beacons after
			// filtering them
			populate = new Runnable() {
				@Override
				public void run() {

					// stopping scan stopLescan got call back to look into it
					near.clear();
					mBluetoothAdapter.stopLeScan(mLeScanCallback);
					// getting unique beacons by MacAddress
					List<ATIBeacon> beaconslist = Helpers.get_beacons(activity);
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

						// Generating array to be given to the adapter to be
						// displayed on:
						// Nearest, near, not in range and known
						// checking 1st near

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
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// adding all devices discovered within 1 second before

					allDevices.add(new ATIDevice(device, rssi));
				}
			});
		}
	};

	private void CheckifLastChanged(EntryItem newNear) {
		sharedPrefEditor sharedpref = new sharedPrefEditor(activity);
		boolean CanNotify = sharedpref.getBoolean("notificationBeacon");

		if (newNear != null && newNear.item != null) {
			User user = Helpers.getUser(activity);
			String flight = "";
			String paxId = "";

			if (user != null) {
				flight = user.getAirline() + user.getFlight();
				paxId = user.getFirstName() + " " + user.getLastName();
			} else {
				flight = "XS0001";
				paxId = "Google Glass";
			}
			if (LastBeacon == null || LastBeacon.item == null) {
				if (CanNotify) {

					ATIBeacon atiBeacon = newNear.item;

					float rssi = (float) newNear.rssi;
					String deviceIdentifier = Secure.getString(
							activity.getContentResolver(), Secure.ANDROID_ID);
					// Tracking user and notifying if notify is enabled
					GetMetaDataTask getmeta = new GetMetaDataTask(activity,
							atiBeacon, flight, new LocalDate(), paxId, rssi,
							deviceIdentifier, msgView);
					getmeta.execute();
				}
				LastBeacon = newNear;
			} else if (!newNear.item.getMacAddress().equals(
					LastBeacon.item.getMacAddress())) {
				if (CanNotify) {
					ATIBeacon atiBeacon = newNear.item;
					float rssi = (float) newNear.rssi;
					String deviceIdentifier = Secure.getString(
							activity.getContentResolver(), Secure.ANDROID_ID);
					// Tracking user and notifying if notify is enabled
					GetMetaDataTask getmeta = new GetMetaDataTask(activity,
							atiBeacon, flight, new LocalDate(), paxId, rssi,
							deviceIdentifier, msgView);
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

	@Override
	protected void onStop() {
		Intent i = new Intent(this, GeofenceScanService.class);
		// potentially add data to the intent
		startService(i);
		super.onStop();
	}
}
