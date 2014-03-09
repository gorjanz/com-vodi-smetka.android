package com.vodismetka.activities;

import com.vodismetka.R;
import com.vodismetka.workers.ImageFactory;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class ViewReceiptActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_receipt);
		
		ImageView rView = (ImageView) findViewById(R.id.fullImgView);
		String imgId = getIntent().getExtras().getString(LaunchActivity.IMG_KEY);
		rView.setImageBitmap(ImageFactory.loadImage(imgId));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

}
