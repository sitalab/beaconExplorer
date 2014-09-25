package aero.developer.beacons.beaconglass.objects;

import android.bluetooth.BluetoothDevice;

public class ATIDevice  implements Comparable<ATIDevice>{

	private BluetoothDevice device;
	private int rssi;

	public ATIDevice(BluetoothDevice device, int rssi) {
		this.device = device;
		this.rssi = rssi;
	}

	public ATIDevice() {
	}

	@Override
	public String toString() {
		return "ATIDevice [device=" + device + ", rssi=" + rssi + "]";
	}

	public BluetoothDevice getDevice() {
		return device;
	}

	public void setDevice(BluetoothDevice device) {
		this.device = device;
	}

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	@Override
	public int compareTo(ATIDevice another) {
        return this.rssi < another.getRssi() ? 1 : (this.rssi > another.getRssi() ? -1 : 0);

	}
	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof ATIDevice)) {
	        return false;
	    }

	    ATIDevice that = (ATIDevice) other;
	    if(that.device==null)return false;

	    // Custom equality check here.
	    return this.device.getAddress().equals(that.device.getAddress());

	}
}
