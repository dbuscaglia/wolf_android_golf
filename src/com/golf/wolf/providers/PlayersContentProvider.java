package com.golf.wolf.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;


/**
 * This content provider will handle the CRUD operations for a Player
 * Active Record.  This model leverages the activeandroid framework to
 * make sqllite access less messy and ORM.
 * 
 * @author dbuscaglia
 *
 */
public class PlayersContentProvider extends ContentProvider {
	/**
	 * publish the public Uri for our players
	 */
	public static final Uri CONTENT_URL =
			Uri.parse("content://com.golf.wolf.playersprovier/players");
	
	
	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_SCORE = "socre";
	public static final String KEY_RUNNING_TOTAL = "running_total";
	
	private SQLLightHelper players_db;
	
	private static final int ALLROWS    = 1;
	private static final int SINGLE_ROW = 2;
	
	private static final UriMatcher uriMatcher;
	
	// populate uri matcher object
	// uri ending in 'players' =>> all players
	// uri ending in 'players/#' =>> player of given id
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.golf.wolf.playersprovider", "players", ALLROWS);
		uriMatcher.addURI("com.golf.wolf.playersprovider", "players/#", SINGLE_ROW);
	}
	
	@Override
	public String getType(Uri uri) {
		// Return a string that indentifies the MIME type for content provider URI
		String mimeType;
		
		switch (uriMatcher.match(uri)) {
			case ALLROWS: 
				mimeType = "vnd.android.cursor.dir/vnd.golf.wolf.players";
				break;
			
			case SINGLE_ROW:
				mimeType = "vnd.android.cursor.item/vnd.golf.wolf.players";
				break;
			
			default:
				throw new IllegalArgumentException("Unsuported URI: " + uri);
			
		}
		return mimeType;
	}
	
	@Override
	public boolean onCreate() {
		// construct the db
		players_db = new SQLLightHelper(getContext(), 
											SQLLightHelper.DATABASE_NAME, 
											null, 
											SQLLightHelper.DATABASE_VERSION, null );
		return true;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		SQLiteDatabase db = players_db.getWritableDatabase();
		
		// place holders
		String groupBy = null;
		String having  = null;
		
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(SQLLightHelper.DATABASE_TABLE);
		
		// limit to a row if single row query
		switch (uriMatcher.match(uri)) {
		case SINGLE_ROW: 
			String rowID = uri.getPathSegments().get(1);
			selection = 
				KEY_ID + "=" + rowID 
				+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
		}
		
		Cursor cursor = queryBuilder.query(db, projection, selection, 
											selectionArgs, groupBy, having, sortOrder);
		return cursor;
	}
	
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		
		
		
		return 0;
	}


	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}





	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	private static class SQLLightHelper extends SQLiteOpenHelper {

		private static final String DATABASE_NAME     = "wolfGolfPlayers.db";
		private static final int    DATABASE_VERSION  = 1;
		private static final String DATABASE_TABLE    = "wolfGolfPlayersTable";
		
		// create db sql statement.
		private static final String DATABASE_CREATE = 
		"create table " + DATABASE_TABLE + 
		" (" + KEY_ID + " integer primary key autoincrement, " + 
		KEY_NAME + " text not null, " +
		KEY_SCORE + " int, " +
		KEY_RUNNING_TOTAL + " int);";
		
		public SQLLightHelper(Context context, String name, CursorFactory factory,
				int version, DatabaseErrorHandler errorHandler) {
			
			super(context, name, factory, version, errorHandler);
			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// log the create
			Log.w("PlayersDBAdapter", "Creating new Wolf Golf Game Players DB");
			
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// log
			Log.w("PlayersDBAdapter", "Upgrading from version " + 
					oldVersion + " to version: " + newVersion + " , destroying old data");
			
			//drop and recreate
			db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
			// call onCreate
			onCreate(db);
			
		}
		

	}
	
}
