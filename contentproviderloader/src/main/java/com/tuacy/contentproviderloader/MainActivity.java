package com.tuacy.contentproviderloader;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

	private Button               mButtonInsert;
	private ListView             mListDisplay;
	private ArrayAdapter<String> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		mButtonInsert = findViewById(R.id.button_insert);
		mListDisplay = findViewById(R.id.list_display);
	}

	private void initEvent() {
		mButtonInsert.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				insert();
			}
		});
	}

	private void insert() {
		ContentValues contentValues = new ContentValues();
		contentValues.put(ProviderInterface.USER_TABLE_ROW_NODE_NAME, "tuacy");
		contentValues.put(ProviderInterface.USER_TABLE_ROW_NODE_AGE, 28);
		Uri uri = getContentResolver().insert(ProviderInterface.USER_CONTENT_URI, contentValues);
		if (uri != null) {
			Log.d("tuacy", uri.toString());
		} else {
			Log.d("tuacy", "null");
		}
	}

	private void initData() {
		getLoaderManager().initLoader(0, null, this);
		mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1);
		mListDisplay.setAdapter(mAdapter);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader loader = new CursorLoader(this);
		loader.setUri(ProviderInterface.USER_CONTENT_URI);
		loader.setProjection(new String[]{ProviderInterface.USER_TABLE_ROW_NODE_NAME});
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		List<String> list = new ArrayList<>();
		if (data != null) {
			while (data.moveToNext()) {
				String name = data.getString(data.getColumnIndex(ProviderInterface.USER_TABLE_ROW_NODE_NAME));
				list.add(name);
			}
			mAdapter.clear();
			mAdapter.addAll(list);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.clear();
	}
}
