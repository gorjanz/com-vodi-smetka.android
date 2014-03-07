package com.vodismetka.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vodismetka.R;
import com.vodismetka.sql.ReceiptDAO;
import com.vodismetka.workers.MonthlyListAdapter;

public class MonthlyViewActivity extends ListActivity {

	private ReceiptDAO dataAccessObject;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.monthly_view);
		
		//ListView list = (ListView) findViewById(R.id.list);
		// create the data access object
		dataAccessObject = new ReceiptDAO(this);
		
		//set the list adapter and get the items
		//list.setAdapter(new ListAdapter(this, dataAccessObject.getItems()));
		setListAdapter(new MonthlyListAdapter(this, dataAccessObject.getItems()));
		
		Button backButton = (Button)findViewById(R.id.back);
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent back = new Intent(getApplicationContext(), LaunchActivity.class);
				startActivity(back);
				finish();
			}
		});
	}
	
//	@Override
//	protected void onListItemClick(ListView l, View v, int position, long id) {
//		
//		Model m = (Model) getListView().getItemAtPosition(position);
//		Toast.makeText(this, m.toString(), Toast.LENGTH_SHORT).show();
//		
//	}
	

}
