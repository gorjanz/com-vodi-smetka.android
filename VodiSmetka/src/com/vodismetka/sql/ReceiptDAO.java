package com.vodismetka.sql;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ListView;

import com.vodismetka.models.ReceiptData;

public class ReceiptDAO {
	
	public static final String TAG = "ReceiptDAO";

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
	public void insertNewItem(int rPrice, String rDate, String rImgId, int rMonth){
		ContentValues rowValues = new ContentValues();
		
		rowValues.put(ReceiptSQLiteHelper.COLUMN_PRICE, rPrice);
		rowValues.put(ReceiptSQLiteHelper.COLUMN_DATE, rDate);
		rowValues.put(ReceiptSQLiteHelper.COLUMN_IMG, rImgId);
		rowValues.put(ReceiptSQLiteHelper.COLUMN_MONTH, rMonth);
		
		db.insert(ReceiptSQLiteHelper.TABLE_NAME, null, rowValues);
		Log.i(TAG, "inserting: " + rowValues.toString());
	}
	
	/**
	 * Get all the items from the database
	 * @return list of items to populate the {@link ListView}
	 */
	public List<ReceiptData> getItems(){
		
		List<ReceiptData> items = new ArrayList<ReceiptData>();
		
		String[] tableColumns = {
				ReceiptSQLiteHelper.COLUMN_ID,
				ReceiptSQLiteHelper.COLUMN_PRICE,
				ReceiptSQLiteHelper.COLUMN_DATE,
				ReceiptSQLiteHelper.COLUMN_IMG,
				ReceiptSQLiteHelper.COLUMN_MONTH
		};
		
		Cursor cursor = db.query(ReceiptSQLiteHelper.TABLE_NAME, tableColumns, null, null, null, null, null);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			//create new receipt object
			ReceiptData receipt = new ReceiptData();
			
			//fill the object with receipt data
			receipt.setItemId(cursor.getInt(0));			//receipt id
			receipt.setPurchaseCost(cursor.getInt(1));		//receipt price
			receipt.setPurchaseDate(cursor.getString(2));	//receipt date
			receipt.setReceiptImageId(cursor.getString(3)); //receipt imgId
			receipt.setMonth(cursor.getInt(4));	 			//receipt month
			
			//add the new receipt to the item list
			items.add(receipt);
			
			//move to next result of the query
			cursor.moveToNext();
		}
		
		return items;
	}
	
	/**
	 * Get all the items from the database for a particular month
	 * @param month the month in which the purchase was made {1,...,12}
	 * @return list of items to populate the {@link ListView}
	 */
	public List<ReceiptData> getItemsForMonth(int month){
		
		List<ReceiptData> items = new ArrayList<ReceiptData>();
		
		String[] tableColumns = {
				ReceiptSQLiteHelper.COLUMN_ID,
				ReceiptSQLiteHelper.COLUMN_PRICE,
				ReceiptSQLiteHelper.COLUMN_DATE,
				ReceiptSQLiteHelper.COLUMN_IMG,
				ReceiptSQLiteHelper.COLUMN_MONTH
		};
		
		//Cursor cursor = db.rawQuery("SELECT * FROM " + ReceiptSQLiteHelper.TABLE_NAME + " WHERE ( " + ReceiptSQLiteHelper.COLUMN_MONTH + " = ? ) ",new String[] {Integer.toString(month)});
		Cursor cursor = db.query(ReceiptSQLiteHelper.TABLE_NAME, tableColumns, ReceiptSQLiteHelper.COLUMN_MONTH + " = ?", new String[] {Integer.toString(month)},null,null,null);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			//create new receipt object
			ReceiptData receipt = new ReceiptData();
			
			//fill the object with receipt data
			receipt.setItemId(cursor.getInt(0));			//receipt id
			receipt.setPurchaseCost(cursor.getInt(1));		//receipt price
			receipt.setPurchaseDate(cursor.getString(2));	//receipt date
			receipt.setReceiptImageId(cursor.getString(3)); //receipt imgId
			receipt.setMonth(cursor.getInt(4));	 			//receipt month
			
			//add the new receipt to the item list
			items.add(receipt);
			
			//move to next result of the query
			cursor.moveToNext();
		}
		
		return items;
	}
	
	//close the database
	public void close(){
		db.close();
	}
	
}
