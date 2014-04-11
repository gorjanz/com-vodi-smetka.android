package com.vodismetka.activities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.vodismetka.R;
import com.vodismetka.workers.ImageFactory;
import com.vodismetka.workers.TessExtractor;
import com.vodismetka.workers.TextAnalyzer;

public class LaunchActivity extends Activity {

	public static final int CAMERA_PHOTO_REQUEST = 1;
	public static final int PICK_FROM_FILE = 2;
	public static final int DIALOG_DOWNLOAD_PROGRESS = 3;
	public static final String TAG = "LaunchActivity";
	public static final String PRICE_KEY = "priceKey";
	public static final String DATE_KEY = "dateKey";
	public static final String IMG_KEY = "imgKey";

	private Button addPurchase;
	private Button seeAllExcpenses;
	private Button seeMonthlySpenditure;
	private ProgressDialog progressDialog;

	private ImageFactory imgFactory;
	// private TessExtractor tessExtractor;
	private TextAnalyzer textAnalyzer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// get buttons from views
		addPurchase = (Button) findViewById(R.id.addNewReceipt);
		seeAllExcpenses = (Button) findViewById(R.id.seeAllExspenses);
		seeMonthlySpenditure = (Button) findViewById(R.id.seeMonthly);

		// create a dialog to provide options for the user
		// use the camera to take a photo, or choose an already saved photo
		final String[] items = new String[] { "Искористи ја камерата",
				"Одбери веќе постоечка" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, items);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Избери начин");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) { // pick from
						
				// use the camera to take the photo
				if (item == 0) {

					// set-up an intent to use the device camera
					Intent grabPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					grabPhoto.putExtra("return-data", true);

					try {
						startActivityForResult(grabPhoto, CAMERA_PHOTO_REQUEST);
					} catch (ActivityNotFoundException e) {
						Toast.makeText(getApplicationContext(),
								"Проблеми со камерата...", Toast.LENGTH_SHORT)
								.show();
						Log.e(TAG, e.getMessage());
					}

				} else { // pick from file
					
					// set-up an intent to get the photo using an existing content provider
					Intent grabPhoto = new Intent();

					grabPhoto.setType("image/*");
					grabPhoto.setAction(Intent.ACTION_GET_CONTENT);

					try{
					startActivityForResult(Intent.createChooser(grabPhoto,
							"Изберете начин:"), PICK_FROM_FILE);
					} catch(Exception e){
						Toast.makeText(getApplicationContext(),
								"Се случи грешка при отворањето на сликата...", Toast.LENGTH_SHORT)
								.show();
						Log.e(TAG, e.getMessage());

					}
				}
			}
		});

		final AlertDialog dialog = builder.create();

		// set on-click listeners for the buttons
		addPurchase.setOnClickListener(new View.OnClickListener() {

			// displays the dialog which enables the user to choose how to load the photo
			@Override
			public void onClick(View v) {

				dialog.show();
				
			}

		});

		seeAllExcpenses.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent viewAll = new Intent(getApplicationContext(),
						ViewAllExspensesActivity.class);
				startActivity(viewAll);
			}

		});

		seeMonthlySpenditure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent viewMonthly = new Intent(getApplicationContext(),
						MonthlyViewActivity.class);
				startActivity(viewMonthly);
			}

		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		// if the user presses cancel, nothing is done
		if (resultCode == RESULT_CANCELED)
			return;
		
		// if the user takes a picture, the picture is analyzed
		if (resultCode == RESULT_OK && requestCode == CAMERA_PHOTO_REQUEST) {
			progressDialog = (ProgressDialog) onCreateDialog(DIALOG_DOWNLOAD_PROGRESS);
			progressDialog.show();

			Bundle returnData = data.getExtras();
			Bitmap photo = (Bitmap) returnData.get("data");
			
			analyzePhoto(photo);
		}
		
		// if the user chooses a picture, the picture is analyzed
		if (resultCode == RESULT_OK && requestCode == PICK_FROM_FILE) {
			progressDialog = (ProgressDialog) onCreateDialog(DIALOG_DOWNLOAD_PROGRESS);
			progressDialog.show();
			
			Uri imageUri = data.getData();
	        Bitmap photo = null;
	        try {
				photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if(photo != null){
	        	analyzePhoto(photo);
	        } else {
	        	Log.e(TAG, "Error in returning image from file picker..");
	        }
			
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
			// progressDialog.show();
			return progressDialog;
		default:
			return null;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private class AsyncExtractor extends
			AsyncTask<TessExtractor, Integer, String> {

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

	// the photo is analyzed and the addNewReceipt activity is called
	private void analyzePhoto(Bitmap photo){
		if (photo == null) {
			Log.e(TAG, "camera intent not returning any image...");
			progressDialog.dismiss();
			return;
		}
		// set-up the name of the image
		String imageID = "img" + Long.toString(System.currentTimeMillis())
				+ ".jpg";

		// set-up image editing factory
		imgFactory = new ImageFactory(photo, imageID);

		// get the image ready for text recognition
		photo = imgFactory.getImg();

		// get tessExtractor object
		TessExtractor[] tess = new TessExtractor[1];
		tess[0] = new TessExtractor(this, photo);

		// extract the text
		// String extractedText = tessExtractor.getText();

		// extracting the text in a background thread
		AsyncTask<TessExtractor, Integer, String> asyncExtractor = new AsyncExtractor()
				.execute(tess);
		String extractedText = "";

		try {
			extractedText = asyncExtractor.get();
		} catch (InterruptedException e) {
			Toast.makeText(getApplicationContext(),
					"Анализата на сметката беше неуспешна...",
					Toast.LENGTH_SHORT).show();
			Log.e(TAG, e.getMessage());
		} catch (ExecutionException e) {
			Toast.makeText(getApplicationContext(),
					"Анализата на сметката беше неуспешна...",
					Toast.LENGTH_SHORT).show();
			Log.e(TAG, e.getMessage());
		}

		// analyze the text
		textAnalyzer = new TextAnalyzer(extractedText);
		Log.i(TAG, "The extracted text is: " + extractedText);

		// get the analyzed data
		String purchaseDate = textAnalyzer.getDate();
		int purchaseCost = textAnalyzer.getCost();

		// launch the affirmation screen for adding a new receipt
		Intent addReceiptActivity = new Intent(getApplicationContext(),
				AddNewReceiptActivity.class);
		addReceiptActivity.putExtra(DATE_KEY, purchaseDate);
		addReceiptActivity.putExtra(PRICE_KEY, purchaseCost);
		addReceiptActivity.putExtra(IMG_KEY, imageID);
		startActivity(addReceiptActivity);

	}
	
	
}
