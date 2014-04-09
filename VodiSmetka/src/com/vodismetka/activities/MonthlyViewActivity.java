package com.vodismetka.activities;

import java.util.Calendar;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vodismetka.R;
import com.vodismetka.models.ReceiptData;
import com.vodismetka.sql.ReceiptDAO;
import com.vodismetka.workers.ReceiptsAdapter;

public class MonthlyViewActivity extends ListActivity {

	private ReceiptDAO dataAccessObject;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receipt_list_view);
		
		//ListView list = (ListView) findViewById(R.id.list);
		// create the data access object
		dataAccessObject = new ReceiptDAO(this);
		
		//get the current month
		Calendar current = Calendar.getInstance();
		int currentMonth = current.get(Calendar.MONTH) + 1;
		
		//get this months' receipts and calculate total spending
		List<ReceiptData> thisMonthsReceipts = dataAccessObject.getItemsForMonth(currentMonth);
		int totalSpending = 0;
		for (ReceiptData receiptData : thisMonthsReceipts) {
			totalSpending += receiptData.getPurchaseCost();
		}
		
		//set the total spending
		((TextView)findViewById(R.id.listViewFooterText)).setText("Вкупно потрошенo месецов " + totalSpending + " ден." );
		
		//set the list adapter and get the items
		//list.setAdapter(new ListAdapter(this, dataAccessObject.getItems()));
		setListAdapter(new ReceiptsAdapter(this, thisMonthsReceipts));
		
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
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		ReceiptData m = (ReceiptData) getListView().getItemAtPosition(position);
		Toast.makeText(this, m.toString(), Toast.LENGTH_SHORT).show();
		
	}
	

}
