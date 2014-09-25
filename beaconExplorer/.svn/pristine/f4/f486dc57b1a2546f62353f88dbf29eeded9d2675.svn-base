package aero.developer.beaconExplorer.database;

import java.util.LinkedList;
import java.util.Queue;

import aero.developer.beaconExplorer.utils.sharedPrefEditor;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseOps {
	private static Context ctx;
	private DatabaseHelper DbHelper;
	private static SQLiteDatabase dbcon;
	public static Queue<Integer> activeOps;
	private static boolean ready = false;

	public DatabaseOps(Context ctx) {
		if (ctx != null) {
			DatabaseOps.ctx = ctx;
		}
		if (DatabaseOps.ctx != null) {
			if (activeOps == null) {
				activeOps = new LinkedList<Integer>();
			}
			DbHelper = DatabaseHelper.getInstance(DatabaseOps.ctx);
			sharedPrefEditor sp = new sharedPrefEditor(DatabaseOps.ctx);
			String b = sp.getString("DatabaseIndicator");
			PushDb_IfNot_Exist(b, DbHelper, DatabaseOps.ctx);

			ready = true;
		} else {
			ready = false;
		}
	}

	/*
	 * void PushDb_IfNot_Exist()
	 * 
	 * Checks if database exists, if not this function pushes the local
	 * Profile.db database file in assests.
	 * 
	 * This is called just in one case: "Application's First Run" which is
	 * indicated by Shared Preference "DatabaseIndicator"==""
	 */
	public static void PushDb_IfNot_Exist(String DatabaseIndicator,
			DatabaseHelper dbCon, Context ctx) {
		sharedPrefEditor _sp = new sharedPrefEditor(ctx);
		try {
			if (DatabaseIndicator == null
					|| DatabaseIndicator.equalsIgnoreCase("")) {
				// This is first run
				dbCon.DeleteAllPreviousDatabases();
				_sp.addString("DatabaseIndicator",
						"ALL_DELETED_ON_UPDATE_OR_FIRSTRUN");
			}
			dbCon.createDataBase();
		} catch (Exception e) {
			if (e != null) {
				e.printStackTrace();
			}
		}
	}

	public static void closeConnectionPool(Context ctx) {
		DatabaseHelper.getInstance(ctx).close();
	}

	public static boolean isReady() {
		return ready;
	}

	public static void setReady(boolean ready) {
		DatabaseOps.ready = ready;
	}

	public SQLiteDatabase open(int OpNumber) {
		activeOps.add(OpNumber);
		return DbHelper.getReadableDatabase();
	}

	public boolean close(SQLiteDatabase dbcon) {
		int closedFor = activeOps.remove();
		dbcon.close();
		return true;
	}

	public void EmptyTable(String tableName) {
		if (DbHelper != null) {
			try {
				dbcon = open(1);
				dbcon.execSQL("DELETE FROM " + tableName);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(dbcon);
			}
		}
	}

	/*
	 * Function: InsertInto Parameters: -tableName: Table in which you want to
	 * insert into. -colName_1,colName_2: name of table columns to insert data
	 * into. -colValue_1,colValue_2: values of to be inserted. Where colValue_1
	 * is related to colName_1 and is of Type "String". Where as colValue_2 is
	 * related to colName_2 and is generic of Type "byte[]". Role: cache
	 * serialized data in the database with it's pair.
	 */
	public boolean InsertIntoDb(String tableName, String colName_1,
			String colValue_1, String colName_2, byte[] colValue_2) {
		boolean retvalue = false;
		if (DbHelper != null && activeOps.size() == 0 && colValue_2 != null
				&& colValue_1 != null && colName_2 != null && colName_1 != null) {

			ContentValues values = new ContentValues();
			values.put(colName_1, colValue_1);
			values.put(colName_2, colValue_2);

			try {
				dbcon = open(1);
				long result = dbcon.insertWithOnConflict(tableName, null,
						values, SQLiteDatabase.CONFLICT_REPLACE);
				if (result == -1) {
					retvalue = false;
				} else {
					retvalue = true;
				}
			} catch (SQLException e) {
				retvalue = false;
				e.printStackTrace();
			} finally {
				close(dbcon);
			}

			return retvalue;
		}
		return retvalue;
	}

	public void DeleteObjectFromDb(String tableName, String whereColName,
			String whereObjectName) {
		if (DbHelper != null && activeOps.size() == 0) {
			try {
				dbcon = open(1);
				String[] where = new String[1];
				where[0] = whereObjectName;
				dbcon.delete(tableName, whereColName + "=" + whereObjectName,
						null);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(dbcon);
			}
		}

	}

	public byte[] getObjectFromDb(String tableName, String selectColName,
			String whereColName, String whereObjectName) {
		Cursor mCursor = null;
		byte[] objectBytes = null;
		if (DbHelper != null && activeOps.size() == 0) {
			try {
				String sql = "SELECT " + selectColName + " FROM " + tableName
						+ " WHERE  LOWER(" + whereColName + ") =  LOWER('"
						+ whereObjectName + "')";
				dbcon = open(1);
				mCursor = dbcon.rawQuery(sql, null);
				if (mCursor != null) {
					if (mCursor.moveToFirst()) {
						do {
							objectBytes = mCursor.getBlob(mCursor
									.getColumnIndex(selectColName));

						} while (mCursor.moveToNext());
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (mCursor != null) {
					mCursor.deactivate();
					mCursor.close();
					mCursor = null;
				}
				close(dbcon);
			}
		}

		return objectBytes;
	}

	public long InsertNotificationIntoDb(String tableName, String colName_0,
			String colValue_0, String colName_1, String colValue_1,
			String colName_2, byte[] colValue_2) {
		long retvalue = 0;
		if (DbHelper != null && activeOps.size() == 0 && colValue_2 != null
				&& colValue_1 != null && colName_2 != null && colName_1 != null
				&& colName_0 != null) {

			ContentValues values = new ContentValues();
			if (colValue_0 != null) {
				values.put(colName_0, colValue_0);
			}
			values.put(colName_1, colValue_1);
			values.put(colName_2, colValue_2);

			try {
				dbcon = open(1);
				long result = dbcon.insertWithOnConflict(tableName, null,
						values, SQLiteDatabase.CONFLICT_REPLACE);
				if (result == -1) {
					retvalue = 0;
				} else {
					retvalue = result;
				}
			} catch (SQLException e) {
				retvalue = 0;
				e.printStackTrace();
			} finally {
				close(dbcon);
			}

			return retvalue;
		}
		return retvalue;
	}

	public String getNotificationStringObjectFromDb(String tableName,
			String selectColName, String whereColName, String whereObjectName) {
		Cursor mCursor = null;
		String objectBytes = null;
		if (DbHelper != null && activeOps.size() == 0) {
			try {
				String sql = "SELECT " + selectColName + " FROM " + tableName
						+ " WHERE  LOWER(" + whereColName + ") =  LOWER('"
						+ whereObjectName + "')";
				dbcon = open(1);
				mCursor = dbcon.rawQuery(sql, null);
				if (mCursor != null) {
					if (mCursor.moveToFirst()) {
						do {
							objectBytes = mCursor.getString(mCursor
									.getColumnIndex(selectColName));

						} while (mCursor.moveToNext());
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (mCursor != null) {
					mCursor.deactivate();
					mCursor.close();
					mCursor = null;
				}
				close(dbcon);
			}
		}

		return objectBytes;
	}

	public void DeleteNotificationStringObjectFromDb(String tableName,
			String whereColName, String whereObjectName) {
		if (DbHelper != null && activeOps.size() == 0) {
			try {
				dbcon = open(1);
				String[] where = new String[1];
				where[0] = whereObjectName;
				dbcon.delete(tableName, whereColName + "=" + whereObjectName,
						null);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(dbcon);
			}
		}

	}

	public String getNotificationBlobObjectFromDb(String tableName,
			String selectColName, String whereColName, String whereObjectName) {
		Cursor mCursor = null;
		byte[] objectBytes = null;
		if (DbHelper != null && activeOps.size() == 0) {
			try {
				String sql = "SELECT " + selectColName + " FROM " + tableName
						+ " WHERE  LOWER(" + whereColName + ") =  LOWER('"
						+ whereObjectName + "')";
				dbcon = open(1);
				mCursor = dbcon.rawQuery(sql, null);
				if (mCursor != null) {
					if (mCursor.moveToFirst()) {
						do {
							objectBytes = mCursor.getBlob(mCursor
									.getColumnIndex(selectColName));

						} while (mCursor.moveToNext());
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (mCursor != null) {
					mCursor.deactivate();
					mCursor.close();
					mCursor = null;
				}
				close(dbcon);
			}
		}

		return new String(objectBytes);
	}
}
