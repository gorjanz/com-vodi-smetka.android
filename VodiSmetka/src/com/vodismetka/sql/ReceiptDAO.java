package com.vodismetka.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ReceiptDAO {

	private SQLiteDatabase db;
	private ReceiptSQLiteHelper dbHelper;
	
	public ReceiptDAO(Context context){
		dbHelper = new ReceiptSQLiteHelper(context);
		db = dbHelper.getWritableDatabase();
	}
	
	/**
	 * Insert a new item into the database
	 * @param rPrice price of the receipt item
	 * @param rDate date of the receipt item
	 * @param rImgId image id of the receipt item
	 */
	public void insertNewItem(int rPrice, String rDate, String rImgId){
		ContentValues rowValues = new ContentValues();
		
		rowValues.put(ReceiptSQLiteHelper.COLUMN_PRICE, rPrice);
		rowValues.put(ReceiptSQLiteHelper.COLUMN_DATE, rDate);
		rowValues.put(ReceiptSQLiteHelper.COLUMN_IMG, rImgId);
		
		db.insert(ReceiptSQLiteHelper.TABLE_NAME, null, rowValues);
	}
	
}
