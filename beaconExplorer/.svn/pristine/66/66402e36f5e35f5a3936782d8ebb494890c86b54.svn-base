package aero.developer.beaconExplorer.objects;

import android.bluetooth.BluetoothDevice;

public class EntryItem  implements Item{
	public ATIBeacon item;
	public int rssi;
	public BluetoothDevice bluetoothItem;
	public boolean isOnline=false;

	public EntryItem(ATIBeacon item, int rssi,BluetoothDevice bluetoothItem,boolean isOnline) {
		this.item = item;
		this.rssi=rssi;
		this.bluetoothItem=bluetoothItem;
		this.isOnline=isOnline;
		
	}

	@Override
	public String toString() {
		return "EntryItem [item=" + item + ", rssi=" + rssi + "]";
	}

	@Override
	public boolean isSection() {
		return false;
	}

}
