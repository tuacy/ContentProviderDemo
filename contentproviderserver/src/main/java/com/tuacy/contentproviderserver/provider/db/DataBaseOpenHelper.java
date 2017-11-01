package com.tuacy.contentproviderserver.provider.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tuacy.contentproviderserver.provider.ProviderInterface;


public class DataBaseOpenHelper extends SQLiteOpenHelper {

	public static final  String DATABASE_NAME    = "contentprovider.db";
	private static final int    DATABASE_VERSION = 1;

	private Context mContext;

	public DataBaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createUserTable(db);
		createDepartmentTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		mContext.deleteDatabase(DATABASE_NAME);
	}

	private static void createUserTable(SQLiteDatabase db) {
		String sql = "create table if not exists " + ProviderInterface.PATH_USER_TABLE + "(" + ProviderInterface.USER_TABLE_ROW_ID +
					 " integer primary key autoincrement, " + ProviderInterface.USER_TABLE_ROW_NODE_NAME + " text, " +
					 ProviderInterface.USER_TABLE_ROW_NODE_AGE + " int default (0) " + ")";
		db.execSQL(sql);
	}

	private static void createDepartmentTable(SQLiteDatabase db) {
		String sql = "create table if not exists " + ProviderInterface.PATH_DEPARTMENT_TABLE + "(" +
					 ProviderInterface.DEPARTMENT_TABLE_ROW_ID + " integer primary key autoincrement, " +
					 ProviderInterface.DEPARTMENT_TABLE_ROW_NODE_NAME + " text, " + ProviderInterface.DEPARTMENT_TABLE_ROW_NODE_PEOPLE +
					 " int default (0) " + ")";
		db.execSQL(sql);
	}
}
