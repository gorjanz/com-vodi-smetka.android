package com.vodismetka.workers;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.vodismetka.exceptions.FolderNotCreatedException;
import com.vodismetka.exceptions.TesseractNotInitializedException;


public class TessExtractor {
	
	public static final String TAG = "TessExtractor";

	private Bitmap photo;
	private TessFactory tessFactory;
	private TessBaseAPI baseApi;
	private String extractedText;
	private Context activityContext;
	
	public TessExtractor(Context c, Bitmap photo) {
		this.photo = photo;
		extractedText = "";
		tessFactory = new TessFactory(c);
		activityContext = c;
	}
	
	public String getText(){
		if(extractedText.equals(""))
			return extractText();
		return extractedText;
	}
	
	private String extractText(){
		
		
		try{
			baseApi = tessFactory.getTess();
		} catch (TesseractNotInitializedException e){
			Log.e(TAG, e.getMessage());
			if(baseApi == null){
				Log.e(TAG, "TessBaseAPI is null... TessFactory not returning tess object...");
			}
		} catch (FolderNotCreatedException ee){
			Log.e(TAG, ee.getMessage());
		}
		
		System.out.println("in ocr function");
		baseApi.init(TessFactory.DATA_PATH, TessFactory.lang);
		System.out.println("training file loaded");
		//photo = photo.copy(Bitmap.Config.ARGB_8888, true);
		baseApi.setImage(photo);
		try{
			extractedText = baseApi.getUTF8Text();
		} catch(Exception e){
			Log.i(TAG, "Error in recognizing text...");
		}
		baseApi.end();
		return extractedText;
	}
	
}
