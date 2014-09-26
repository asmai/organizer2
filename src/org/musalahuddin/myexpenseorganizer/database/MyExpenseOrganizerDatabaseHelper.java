package org.musalahuddin.myexpenseorganizer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyExpenseOrganizerDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "myexpense.db";
	private static final int DATABASE_VERSION = 10;
	
	public MyExpenseOrganizerDatabaseHelper(Context context) {
		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		
		db.execSQL("PRAGMA foreign_keys=ON;");
		//super.onOpen(db);
		Log.i("onOpen","called");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		ExpenseParentCategory.onCreate(db);
		ExpenseChildCategory.onCreate(db);
		AccountCategoryTable.onCreate(db);
		TransactionCategoryTable.onCreate(db);
		AccountTable.onCreate(db);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		ExpenseParentCategory.onUpgrade(db, oldVersion, newVersion);
		ExpenseChildCategory.onUpgrade(db, oldVersion, newVersion);
		AccountCategoryTable.onUpgrade(db, oldVersion, newVersion);
		TransactionCategoryTable.onUpgrade(db, oldVersion, newVersion);
		AccountTable.onUpgrade(db, oldVersion, newVersion);
		
	}
	
}
