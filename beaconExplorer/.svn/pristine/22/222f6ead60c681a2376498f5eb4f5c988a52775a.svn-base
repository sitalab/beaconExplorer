package aero.developer.beacons.beaconglass.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import android.util.Log;

public class Serializer {

	public Serializer() {
	}

	/*
	 * Function: serializeObject Parameters: Object Return: Array of bytes role:
	 * takes any type of object, that implements serializable and serializes it
	 */
	public static byte[] serializeObject(Object o) {
		if (o != null) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				ObjectOutput out = new ObjectOutputStream(bos);
				out.writeObject(o);
				out.close();

				// Get the bytes of the serialized object
				byte[] buf = bos.toByteArray();
				return buf;
			} catch (IOException ioe) {
				if (ioe != null) {
					ioe.printStackTrace();
					Log.e("serializeObject", "error: " + ioe.getMessage());
				}
			}
		} else {
			Log.e("serializeObject", "Object: has a null value");
		}
		return null;
	}

	public static byte[] serializeObject(String Type, Object o) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ObjectOutput out = new ObjectOutputStream(bos);
			out.writeObject(o);
			out.close();

			// Get the bytes of the serialized object
			byte[] buf = bos.toByteArray();
			// saveToDatabase(Type, buf);
			return buf;
		} catch (IOException ioe) {
			if (ioe != null) {
				Log.e("serializeObject", "error: " + ioe.getMessage());
			} else {
				Log.e("serializeObject", "error IOE");
			}
			return null;
		}
	}

	public static Object deserializeObject(byte[] b) {
		if (b != null && b.length > 0) {
			try {

				ObjectInputStream in = new ObjectInputStream(
						new ByteArrayInputStream(b));
				Object object = in.readObject();
				in.close();

				return object;
			} catch (ClassNotFoundException cnfe) {
				if (cnfe != null)
					Log.e("deserializeObject",
							"class not found error: " + cnfe.getMessage());
				return null;
			} catch (IOException ioe) {
				if (ioe != null)
					Log.e("deserializeObject", "io error: " + ioe.getMessage());
				return null;
			}
		} else {
			Log.e("deserializeObject", "byte[] b has a Null Value");
		}
		return null;
	}

}
