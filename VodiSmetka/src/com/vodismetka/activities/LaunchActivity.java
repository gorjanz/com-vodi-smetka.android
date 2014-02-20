package com.vodismetka.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.vodismetka.R;

public class LaunchActivity extends Activity {

	public static int CAMERA_PHOTO_REQUEST = 1;

	private Button addPurchase;
	private Button seeWeeklySpenditure;
	private Button seeMonthlySpenditure;

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
	protected void onResume() {
		super.onResume();
	}

}
