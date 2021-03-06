package com.vodismetka.activities;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vodismetka.R;
import com.vodismetka.models.ReceiptData;
import com.vodismetka.sql.ReceiptDAO;
import com.vodismetka.workers.ReceiptsAdapter;

public class ViewAllExspensesActivity extends ListActivity {

	private ReceiptDAO dataAccessObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receipt_list_view);

		// set list header info
		((TextView) findViewById(R.id.listLabel)).setText("Сите трошоци");

		// ListView list = (ListView) findViewById(R.id.list);
		// create the data access object
		dataAccessObject = new ReceiptDAO(this);

		//get all items
		List<ReceiptData> items = dataAccessObject.getItems();
		
		// set the list adapter and get the items
		// list.setAdapter(new ListAdapter(this, dataAccessObject.getItems()));
		setListAdapter(new ReceiptsAdapter(this, items));
		
		((TextView)findViewById(R.id.listViewFooterText)).setText("Преглед на " + items.size() + " сметки" );

		Button backButton = (Button) findViewById(R.id.back);
		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent back = new Intent(getApplicationContext(),
						LaunchActivity.class);
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
