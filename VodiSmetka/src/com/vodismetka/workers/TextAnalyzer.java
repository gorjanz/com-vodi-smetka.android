package com.vodismetka.workers;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

import com.vodismetka.exceptions.UnrecognizableDateException;
import com.vodismetka.exceptions.UnrecognizablePriceException;

public class TextAnalyzer {

	public static final String TAG = "TextAnalyzer";
	
	private String extText;
	private int extractedPrice;
	private String extractedDate;

	public TextAnalyzer(String extractedText) {
		extText = extractedText;
		extract();
	}

	// fill extractedPrice and extractedDate from extText
	private void extract() {
		String fixed = TextFactory.fixString(extText);
		Log.i(TAG, extText + "\n -------------------------------- \n" + fixed);
		extractedPrice = getReceiptPrice(fixed);
		extractedDate = getReceiptDate(fixed);
		return;
	}
	
	private int getReceiptPrice(String text){
		int price = -1;
		try {
			int voGotovo = TextFactory.findVoGotovo(text);
			int vkPromet = TextFactory.findVkupenPromet(text);
			if(voGotovo == vkPromet)
				price = voGotovo;
			if(voGotovo == 0)
				price = vkPromet;
			if(vkPromet == 0)
				price = voGotovo;
			if(price == 0)
				price = -1;
		} catch (UnrecognizablePriceException e) {
			// TODO Auto-generated catch block
			Log.i(TAG, e.getMessage());
		}
		return price;
	}

	private String getReceiptDate(String text) {
		String date = "";
	    try{
	    	String extDate = TextFactory.findDatum(text);
	    	Log.i(TAG, extDate);
			String[] parts = extDate.split("datum\\W{1}");
	    	Log.i(TAG, parts[1]);
	    	date = parts[1];
	    }catch(Exception e){
	    	Log.i(TAG, e.getMessage());
	    	Calendar current = Calendar.getInstance();
			int month = current.get(Calendar.MONTH);
			int day = current.get(Calendar.DAY_OF_MONTH);
			int year = current.get(Calendar.YEAR);
			date = String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year);
	    }
	    Log.i(TAG, date);
		return date;
	}


	public int getCost() {
		return extractedPrice;
	}

	public String getDate() {
		return extractedDate;
	}

}
