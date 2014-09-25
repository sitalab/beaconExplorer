package aero.developer.beaconExplorer.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * create custom DatabaseHelper class that extends SQLiteOpenHelper
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	private static DatabaseHelper mInstance = null;
	public static final String DATABASE_NAME = "BeaconTrac.jet";
	public static final String DB_PATH_1 = "/data/data/";
	public static final String DB_PATH_2 = "/databases/";
	/************* Cache_Table Table ********************/
	public static final String TABLE_USER = "Cache_Table";
	public static final String COL_USER_ITEMNAME = "t_CacheObject_Name";
	public static final String COL_USER_ITEMVALUE = "b_CacheObjectByte_Value";
	public static final String DATABASE_CREATE_TABLE_CACHE = "create table IF NOT EXISTS "
			+ TABLE_USER
			+ " ("
			+ COL_USER_ITEMNAME
			+ " text primary key,"
			+ COL_USER_ITEMVALUE + " BLOB);";

	private static final int DATABASE_VERSION = 1;

	private static Context mCtx;

	public static DatabaseHelper getInstance(Context ctx) {
		/**
		 * use the application context as suggested by CommonsWare. this will
		 * ensure that you dont accidentally leak an Activitys context (see this
		 * article for more information:
		 * http://developer.android.com/resources/articles
		 * /avoiding-memory-leaks.html /avoiding-memory-leaks.html)
		 */
		if (ctx != null) {
			mCtx = ctx;
		}
		if (mInstance == null && mCtx != null) {
			mInstance = new DatabaseHelper(mCtx.getApplicationContext());
		}

		return mInstance;
	}

	/**
	 * constructor should be private to prevent direct instantiation. make call
	 * to static factory method "getInstance()" instead.
	 */
	public DatabaseHelper(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		DatabaseHelper.mCtx = ctx;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE_TABLE_CACHE);
		// db.execSQL(DATABASE_CREATE_TABLE_USER);
		// db.execSQL(DATABASE_CREATE_NOTIFY_TABLE);


	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.e("Database versions", "============Database versions  "
				+ oldVersion + " , " + newVersion);

	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() { // throws IOException

		String packageName = mCtx.getApplicationContext().getPackageName();
		String path = DB_PATH_1 + packageName + DB_PATH_2;
		File dbFolderDir = new File(path + DATABASE_NAME);
		boolean dbExist;
		if (dbFolderDir.exists()) {
			dbExist = true;
		} else {
			dbExist = false;
		}

		if (dbExist) {
			// do nothing - database already exist
		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			try {
				this.getReadableDatabase();
			} catch (Exception e) {
				e.printStackTrace();
			}

			/*
			 * try { copyDataBase(); } catch (IOException e) { throw new
			 * Error("Error copying database"); }
			 */
		}

	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = DatabaseHelper.mCtx.getAssets().open(
				DATABASE_NAME);

		String packageName = DatabaseHelper.mCtx.getApplicationContext()
				.getPackageName();
		String path = DB_PATH_1 + packageName + DB_PATH_2;

		// Path to the just created empty db
		String outFileName = path + DATABASE_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024]; // 1024
		int length;
		try {
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}

			// Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();
		} catch (FileNotFoundException e) {
			if (e != null) {
				Log.e("Copying DB", e.getMessage());
			}
		} catch (IOException e) {
			if (e != null) {
				Log.e("Copying DB", e.getMessage());
			}
		} catch (Exception e) {
			if (e != null) {
				Log.e("Copying DB", e.getMessage());
			}
		}
	}

	// public void openDataBase() {
	// // Open the database
	// String myPath = DB_PATH + DATABASE_NAME;
	//
	// try {
	// mInstance = SQLiteDatabase.openDatabase(myPath, null,
	// SQLiteDatabase.OPEN_READONLY);
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * Delete Database Files if they already exist on the first run
	 * **/
	public void DeleteAllPreviousDatabases() {
		String packageName = DatabaseHelper.mCtx.getApplicationContext()
				.getPackageName();
		String path = DB_PATH_1 + packageName + DB_PATH_2;

		File dbFolderDir = new File(path);

		if (dbFolderDir.exists()) {
			String[] children = dbFolderDir.list();

			for (String s : children) {
				if (s.contains(".jet")) {
					File file = new File(path + s);
					boolean deleted = file.delete();
					Log.e("Deleting Database Log", "Delete: " + deleted
							+ " => " + path + s);
				}
			}

		}
	}
}