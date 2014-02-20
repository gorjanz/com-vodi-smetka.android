package com.vodismetka.activities;

import com.vodismetka.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button addPurchase;
	private Button seeWeeklySpenditure;
	private Button seeMonthlySpenditure;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		addPurchase = (Button) findViewById(R.id.addNewReceipt);
		seeWeeklySpenditure = (Button) findViewById(R.id.seeWeekly);
		seeMonthlySpenditure = (Button) findViewById(R.id.seeMonthly);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
}
