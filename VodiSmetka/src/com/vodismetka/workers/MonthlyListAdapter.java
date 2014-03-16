package com.vodismetka.workers;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vodismetka.R;
import com.vodismetka.activities.LaunchActivity;
import com.vodismetka.activities.ViewReceiptActivity;
import com.vodismetka.models.ReceiptData;

public class MonthlyListAdapter extends ArrayAdapter<ReceiptData> {

	public static final String TAG = "MonthlyListAdapter";
	
	private final Context context;
	private List<ReceiptData> items;
	
	public MonthlyListAdapter(Context context, List<ReceiptData> resource) {
		super(context, R.layout.monthly_view, resource);

		items = resource;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.list_row_item, null);
		final ReceiptData rowItem = items.get(position);
		
		//TO-DO get all items for month = position + 1, sum the prices and set the text fields accordingly
		
		ImageView photoView = (ImageView) rowView.findViewById(R.id.icon);
		photoView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent viewReceipt = new Intent(context, ViewReceiptActivity.class);
				viewReceipt.putExtra(LaunchActivity.IMG_KEY, rowItem.getReceiptImageId());
				context.startActivity(viewReceipt);
			}
		});
		
		//TO-DO sort by month and display receipts by for month
		
		TextView monthId = (TextView) rowView.findViewById(R.id.rowDateLabel);
		//monthId.setText(Integer.toString(rowItem.getId()));
		monthId.setText("Потрошено: " + Integer.toString(rowItem.getPurchaseCost()));
		
		TextView monthlySpent = (TextView) rowView.findViewById(R.id.rowCostLabel);
		//monthlySpent.setText(Integer.toString(rowItem.getId()));
		//Log.i(TAG,"Потрошено: " + Integer.toString(rowItem.getPurchaseCost()));
		monthlySpent.setText("Датум: " + rowItem.getPurchaseDate());
		
		return rowView;
	}

}
