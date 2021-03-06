package com.henrywarhurst.facerecog;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlHelper extends SQLiteOpenHelper {
	
	public static final String TAG					= "SqlHelper::";
	public static final String TABLE_PEOPLE 		= "people";
	public static final String COLUMN_ID 			= "_id";
	public static final String COLUMN_FIRST_NAME 	= "firstname";
	public static final String COLUMN_LAST_NAME 	= "lastname";
	
	private static final String DATABASE_NAME 		= "people.db";
	private static final int DATABASE_VERSION 		= 2;
	
	// Create database
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_PEOPLE + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_FIRST_NAME
			+ " text not null, " + COLUMN_LAST_NAME + " text not null);";
	
	public SqlHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SqlHelper.class.getName(),
				"Upgrading database from versoin "	+ oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEOPLE);
		onCreate(db);
	}
	
}
