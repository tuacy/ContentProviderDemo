package com.tuacy.contentproviderserver;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tuacy.contentproviderserver.provider.ProviderInterface;


public class UserActivity extends AppCompatActivity {

	private Button mButtonInsert;
	private Button mButtonQuery;
	private Button mButtonUpdate;
	private Button mButtonDelete;
	private String mNameTemp;
	private int    mAgeTemp;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		mButtonInsert = findViewById(R.id.button_insert);
		mButtonQuery = findViewById(R.id.button_query);
		mButtonUpdate = findViewById(R.id.button_update);
		mButtonDelete = findViewById(R.id.button_delete);
	}

	private void initEvent() {
		mButtonInsert.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				insert();
			}
		});

		mButtonQuery.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				query();
			}
		});

		mButtonUpdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				update();
			}
		});

		mButtonDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				delete();
			}
		});
	}

	private void initData() {
		mNameTemp = "tuacy";
		mAgeTemp = 0;
	}

	private void insert() {
		mAgeTemp++;
		ContentValues contentValues = new ContentValues();
		contentValues.put(ProviderInterface.USER_TABLE_ROW_NODE_NAME, mNameTemp + mAgeTemp);
		contentValues.put(ProviderInterface.USER_TABLE_ROW_NODE_AGE, mAgeTemp);
		Uri uri = getContentResolver().insert(ProviderInterface.USER_CONTENT_URI, contentValues);
		if (uri != null) {
			Log.d("tuacy", uri.toString());
		}
	}

	private void query() {
		Cursor cursor = getContentResolver().query(ProviderInterface.USER_CONTENT_URI, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				Log.d("tuacy", "id = " + cursor.getLong(cursor.getColumnIndex(ProviderInterface.USER_TABLE_ROW_ID)) + " name = " +
							   cursor.getString(cursor.getColumnIndex(ProviderInterface.USER_TABLE_ROW_NODE_NAME)) + " age = " +
							   cursor.getInt(cursor.getColumnIndex(ProviderInterface.USER_TABLE_ROW_NODE_AGE)));
			}
			cursor.close();
		}
	}

	private void update() {
		ContentValues contentValues = new ContentValues();
		mAgeTemp++;
		contentValues.put(ProviderInterface.USER_TABLE_ROW_NODE_NAME, mNameTemp + mAgeTemp);
		contentValues.put(ProviderInterface.USER_TABLE_ROW_NODE_AGE, mAgeTemp);
		int updateId = getContentResolver().update(ProviderInterface.USER_CONTENT_URI, contentValues,
												   ProviderInterface.USER_TABLE_ROW_ID + "=?", new String[]{1 + ""});
		Log.d("tuacy", "update id = " + updateId);
	}

	private void delete() {
		int deleteId = getContentResolver().delete(ProviderInterface.USER_CONTENT_URI, ProviderInterface.USER_TABLE_ROW_ID + "=?", new String[]{2 + ""});
		Log.d("tuacy", "delete id = " + deleteId);
	}
}
