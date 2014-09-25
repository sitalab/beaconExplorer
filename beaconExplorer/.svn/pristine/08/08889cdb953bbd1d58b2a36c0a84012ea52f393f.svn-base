package aero.developer.beacons.beaconglass;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * This Broadcast receiver is used to start the scan service once the glass
 * rebooted or started.
 * */
public class BootCompleteReceiver extends WakefulBroadcastReceiver {
	private Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		Intent i = new Intent(this.context, GeofenceScanService.class);
		this.context.startService(i);
	}
}