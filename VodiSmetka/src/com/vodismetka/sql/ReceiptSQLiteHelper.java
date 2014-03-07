package com.vodismetka.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReceiptSQLiteHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "receipts_db";
	public static final String TABLE_NAME = "receipts";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_PRICE = "price";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_IMG = "receiptImg";
	public static final String COLUMN_MONTH = "month";

	public ReceiptSQLiteHelper(Context context) {
		//create database "receipts_db" : version 1
		super(context, ReceiptSQLiteHelper.DATABASE_NAME, null, 1);
	}

	/**
	 * Creates a simple table
	 * table name: "receipts" 
	 * columns: id (primary key) INTEGER
	 * 			date TEXT
	 * 			price INTEGER 
	 * 			receiptImg TEXT
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		//execute create table sql statement
		db.execSQL("CREATE TABLE "
				+ ReceiptSQLiteHelper.TABLE_NAME + " (" 
				+ ReceiptSQLiteHelper.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ ReceiptSQLiteHelper.COLUMN_PRICE + " INTEGER NOT NULL,"
				+ ReceiptSQLiteHelper.COLUMN_DATE + " TEXT NOT NULL,"
				+ ReceiptSQLiteHelper.COLUMN_IMG + " TEXT NOT NULL,"
				+ ReceiptSQLiteHelper.COLUMN_MONTH + " INTEGER NOT NULL);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//drop existing table
		db.execSQL("DROP TABLE IF EXISTS " + ReceiptSQLiteHelper.TABLE_NAME);
		//recreate the table
		onCreate(db);
	}

}
