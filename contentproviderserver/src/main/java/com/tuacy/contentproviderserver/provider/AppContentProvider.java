package com.tuacy.contentproviderserver.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tuacy.contentproviderserver.provider.db.DataBaseOpenHelper;

import java.io.FileNotFoundException;

import static com.tuacy.contentproviderserver.provider.ProviderInterface.AUTHORITIES;
import static com.tuacy.contentproviderserver.provider.ProviderInterface.PATH_DEPARTMENT_TABLE;
import static com.tuacy.contentproviderserver.provider.ProviderInterface.PATH_USER_TABLE;


public class AppContentProvider extends ContentProvider {

	private DataBaseOpenHelper mDataBaseManager;
	private Context mContext;

	@Override
	public boolean onCreate() {
		if (getContext() == null) {
			throw new NullPointerException("AppContentProvider getContext() null");
		}
		mContext = getContext();
		mDataBaseManager = new DataBaseOpenHelper(mContext);
		return true;
	}

	/**
	 * 共享文件
	 */
	@Nullable
	@Override
	public ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String mode) throws FileNotFoundException {
		return super.openFile(uri, mode);
	}

	@Nullable
	@Override
	public String[] getStreamTypes(@NonNull Uri uri, @NonNull String mimeTypeFilter) {
		return super.getStreamTypes(uri, mimeTypeFilter);
	}

	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri,
						@Nullable String[] projection,
						@Nullable String selection,
						@Nullable String[] selectionArgs,
						@Nullable String sortOrder) {
		final SQLiteDatabase db = mDataBaseManager.getReadableDatabase();

		Cursor cursor = null;
		switch (buildUriMatcher().match(uri)) {
			case PATH_USER:
				cursor = db.query(PATH_USER_TABLE, projection, selection, selectionArgs, sortOrder, null, null);
				cursor.setNotificationUri(mContext.getContentResolver(), uri);
				break;
			case PATH_DEPARTMENT:
				cursor = db.query(PATH_DEPARTMENT_TABLE, projection, selection, selectionArgs, sortOrder, null, null);
				cursor.setNotificationUri(mContext.getContentResolver(), uri);
				break;
		}

		return cursor;
	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		String type = null;
		switch (buildUriMatcher().match(uri)) {
			case PATH_USER:
			case PATH_DEPARTMENT:
				type = "vnd.android.cursor.dir/multi";
				break;
		}
		return type;
	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
		final SQLiteDatabase db = mDataBaseManager.getWritableDatabase();
		Uri returnUri;
		long _id;
		switch (buildUriMatcher().match(uri)) {
			case PATH_USER:
				_id = db.insert(PATH_USER_TABLE, null, values);
				if (_id > 0) {
					returnUri = ContentUris.withAppendedId(ProviderInterface.USER_CONTENT_URI, _id);
					mContext.getContentResolver().notifyChange(uri, null);
				} else {
					throw new android.database.SQLException("Failed to user insert row into " + uri);
				}
				break;
			case PATH_DEPARTMENT:
				_id = db.insert(PATH_DEPARTMENT_TABLE, null, values);
				if (_id > 0) {
					returnUri = ContentUris.withAppendedId(ProviderInterface.DEPARTMENT_CONTENT_URI, _id);
					mContext.getContentResolver().notifyChange(uri, null);
				} else {
					throw new android.database.SQLException("Failed to department insert row into " + uri);
				}
				break;
			default:
				throw new android.database.SQLException("insert Unknown uri: " + uri);
		}
		return returnUri;
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
		final SQLiteDatabase db = mDataBaseManager.getReadableDatabase();

		int deleteId = -1;
		switch (buildUriMatcher().match(uri)) {
			case PATH_USER:
				deleteId = db.delete(PATH_USER_TABLE, selection, selectionArgs);
				mContext.getContentResolver().notifyChange(uri, null);
				break;
			case PATH_DEPARTMENT:
				deleteId = db.delete(PATH_DEPARTMENT_TABLE, selection, selectionArgs);
				mContext.getContentResolver().notifyChange(uri, null);
				break;
			default:
				throw new android.database.SQLException("delete Unknown uri: " + uri);
		}

		return deleteId;
	}

	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		final SQLiteDatabase db = mDataBaseManager.getReadableDatabase();

		int updateId = -1;
		switch (buildUriMatcher().match(uri)) {
			case PATH_USER:
				updateId = db.update(PATH_USER_TABLE, values, selection, selectionArgs);
				mContext.getContentResolver().notifyChange(uri, null);
				break;
			case PATH_DEPARTMENT:
				updateId = db.update(PATH_DEPARTMENT_TABLE, values, selection, selectionArgs);
				mContext.getContentResolver().notifyChange(uri, null);
				break;
			default:
				throw new android.database.SQLException("update Unknown uri: " + uri);
		}

		return updateId;
	}


	public static final int PATH_USER       = 0x100;
	public static final int PATH_DEPARTMENT = 0x200;

	static UriMatcher buildUriMatcher() {
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		matcher.addURI(AUTHORITIES, PATH_USER_TABLE, PATH_USER);
		matcher.addURI(AUTHORITIES, PATH_DEPARTMENT_TABLE, PATH_DEPARTMENT);
		//		matcher.addURI(AUTHORITY, "user", 1);// 配置表
		//		matcher.addURI(AUTHORITY, "user/#", 2);// 匹配任何数字
		//		result = db.delete(DBHelper.USERTABLE, "_id=?", new String[] {ContentUris.parseId(uri) + "" });
		//		matcher.addURI(AUTHORITY, "user/*", 3);// 匹配任何文本
		//		result = db.delete(DBHelper.USERTABLE, "USERNAME=?", new String[] { uri.getPathSegments().get(1) });

		return matcher;
	}
}
