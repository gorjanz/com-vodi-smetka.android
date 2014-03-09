package com.vodismetka.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.vodismetka.R;
import com.vodismetka.workers.ImageFactory;
import com.vodismetka.workers.TessExtractor;
import com.vodismetka.workers.TextAnalyzer;

public class LaunchActivity extends Activity {

	public static final int CAMERA_PHOTO_REQUEST = 1;
	public static final String TAG = "LaunchActivity";
	public static final String PRICE_KEY = "priceKey";
	public static final String DATE_KEY = "dateKey";
	public static final String IMG_KEY = "imgKey";
	
	private Button addPurchase;
	private Button seeWeeklySpenditure;
	private Button seeMonthlySpenditure;
	
	private ImageFactory imgFactory;
	private TessExtractor tessExtractor;
	private TextAnalyzer textAnalyzer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// get buttons from views
		addPurchase = (Button) findViewById(R.id.addNewReceipt);
		seeWeeklySpenditure = (Button) findViewById(R.id.seeWeekly);
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

		seeWeeklySpenditure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}

		});

		seeMonthlySpenditure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}

		});

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_CANCELED)
			return;
		if(resultCode==RESULT_OK && requestCode==CAMERA_PHOTO_REQUEST){
			Bundle returnData = data.getExtras();
			Bitmap photo = (Bitmap) returnData.get("data");
			
			if(photo == null){
				Log.e(TAG, "camera intent not returning any image...");
			}
			String imageID = "img" + Long.toString(System.currentTimeMillis()) + ".jpg";
			
			//set-up image editing factory
			imgFactory = new ImageFactory(photo, imageID);
			
			//get the image ready for text recognition
			photo = imgFactory.getImg();
			
			//get tessExtractor object
			tessExtractor = new TessExtractor(this, photo);
			
			//extract the text
			String extractedText = tessExtractor.getText();
			
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
	protected void onResume() {
		super.onResume();
	}

}
