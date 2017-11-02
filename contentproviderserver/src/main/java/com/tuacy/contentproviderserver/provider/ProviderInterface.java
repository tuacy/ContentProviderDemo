package com.tuacy.contentproviderserver.provider;


import android.net.Uri;

/**
 * 我们给ContentProvider提供两张表user_table， department_table
 */
public class ProviderInterface {

	/**
	 * authorities 一般设置为包名(applicationId)，用来区分不同的ContentProvider
	 */
	public static final String AUTHORITIES = "com.tuacy.contentproviderserver";

	/**
	 * user数据库信息
	 */
	/**
	 * 用来区分ContentProvider中不同的表
	 */
	public static final String PATH_USER_TABLE          = "user_table";
	public static final String USER_TABLE_ROW_ID        = "_ID";
	public static final String USER_TABLE_ROW_NODE_NAME = "name";
	public static final String USER_TABLE_ROW_NODE_AGE  = "age";
	public static final Uri    USER_CONTENT_URI         = Uri.parse("content://" + AUTHORITIES)
															 .buildUpon()
															 .appendPath(PATH_USER_TABLE)
															 .build();

	/**
	 * department数据库信息
	 */
	/**
	 * 用来区分ContentProvider中不同的表
	 */
	public static final String PATH_DEPARTMENT_TABLE            = "department_table";
	public static final String DEPARTMENT_TABLE_ROW_ID          = "_ID";
	public static final String DEPARTMENT_TABLE_ROW_NODE_NAME   = "name";
	public static final String DEPARTMENT_TABLE_ROW_NODE_PEOPLE = "people";
	public static final Uri    DEPARTMENT_CONTENT_URI           = Uri.parse("content://" + AUTHORITIES)
																	 .buildUpon()
																	 .appendPath(PATH_DEPARTMENT_TABLE)
																	 .build();

}
