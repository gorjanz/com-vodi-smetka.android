package com.vodismetka.activities;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vodismetka.R;
import com.vodismetka.workers.ImageFactory;
import com.vodismetka.workers.TessExtractor;
import com.vodismetka.workers.TextAnalyzer;

public class LaunchActivity extends Activity {

	public static final int CAMERA_PHOTO_REQUEST = 1;
	public static final int DIALOG_DOWNLOAD_PROGRESS = 10;
	public static final String TAG = "LaunchActivity";
	public static final String PRICE_KEY = "priceKey";
	public static final String DATE_KEY = "dateKey";
	public static final String IMG_KEY = "imgKey";
	
	private Button addPurchase;
	private Button seeAllExcpenses;
	private Button seeMonthlySpenditure;
	private ProgressDialog progressDialog;
	
	private ImageFactory imgFactory;
	//private TessExtractor tessExtractor;
	private TextAnalyzer textAnalyzer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// get buttons from views
		addPurchase = (Button) findViewById(R.id.addNewReceipt);
		seeAllExcpenses = (Button) findViewById(R.id.seeAllExspenses);
		seeMonthlySpenditure = (Button) findViewById(R.id.seeMonthly);

		// set on-click listeners for the buttons
		addPurchase.setOnClickListener(new View.OnClickListener() {

			// launch phones native camera app to take a photo of the receipt
			@Override
			public void onClick(View v) {

				Intent grabPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				grabPhoto.putExtra("return-data", true);
				startActivityForResult(grabPhoto, CAMERA_PHOTO_REQUEST);
			}

		});

		seeAllExcpenses.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent viewAll = new Intent(getApplicationContext(), ViewAllExspensesActivity.class);
				startActivity(viewAll);
			}

		});

		seeMonthlySpenditure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent viewMonthly = new Intent(getApplicationContext(), MonthlyViewActivity.class);
				startActivity(viewMonthly);
			}

		});

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_CANCELED)
			return;
		if(resultCode==RESULT_OK && requestCode==CAMERA_PHOTO_REQUEST){
			progressDialog = (ProgressDialog) onCreateDialog(DIALOG_DOWNLOAD_PROGRESS);
			progressDialog.show();
			
			Bundle returnData = data.getExtras();
			Bitmap photo = (Bitmap) returnData.get("data");
			
			if(photo == null){
				Log.e(TAG, "camera intent not returning any image...");
				progressDialog.dismiss();
				return;
			}
			String imageID = "img" + Long.toString(System.currentTimeMillis()) + ".jpg";
			
			//set-up image editing factory
			imgFactory = new ImageFactory(photo, imageID);
			
			//get the image ready for text recognition
			photo = imgFactory.getImg();
			
			//get tessExtractor object
			TessExtractor[] tess = new TessExtractor[1];
			tess[0] = new TessExtractor(this, photo);
			
			//extract the text
			//String extractedText = tessExtractor.getText();
			
			//extracting the text in a background thread
			AsyncTask<TessExtractor, Integer, String> asyncExtractor = new AsyncExtractor().execute(tess);
			String extractedText = "";

			try {
				extractedText = asyncExtractor.get();
			} catch (InterruptedException e) {
				Toast.makeText(getApplicationContext(), "Анализата на сметката беше неуспешна...", Toast.LENGTH_SHORT).show();
				Log.e(TAG,e.getMessage());
			} catch (ExecutionException e) {
				Toast.makeText(getApplicationContext(), "Анализата на сметката беше неуспешна...", Toast.LENGTH_SHORT).show();
				Log.e(TAG,e.getMessage());
			}
			
			//analyze the text
			textAnalyzer = new TextAnalyzer(extractedText);
			Log.i(TAG, "The extracted text is: " + extractedText);
			
			//get the analyzed data
			String purchaseDate = textAnalyzer.getDate();
			int purchaseCost = textAnalyzer.getCost();
			
			//launch the affirmation screen for adding a new receipt
			Intent addReceiptActivity = new Intent(getApplicationContext(), AddNewReceiptActivity.class);
			addReceiptActivity.putExtra(DATE_KEY, purchaseDate);
			addReceiptActivity.putExtra(PRICE_KEY, purchaseCost);
			addReceiptActivity.putExtra(IMG_KEY, imageID);
			startActivity(addReceiptActivity);
			
		}
		
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case DIALOG_DOWNLOAD_PROGRESS:
	        progressDialog = new ProgressDialog(this);
	        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	        progressDialog.setCancelable(false);
	        progressDialog.setMessage("Сметката се анализира...");
	        progressDialog.setProgress(0);
	        //progressDialog.show();
	        return progressDialog;
	    default:
	    return null;
	    }
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	private class AsyncExtractor extends AsyncTask<TessExtractor, Integer, String> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}
		
		@Override
		protected String doInBackground(TessExtractor... params) {
			return params[0].getText();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			progressDialog.incrementProgressBy(values[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
		}

	}

}
