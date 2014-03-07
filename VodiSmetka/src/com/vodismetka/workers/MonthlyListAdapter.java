package com.vodismetka.workers;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vodismetka.R;
import com.vodismetka.models.ReceiptData;

public class MonthlyListAdapter extends ArrayAdapter<ReceiptData> {

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
		
		View rowView = inflater.inflate(R.layout.monthly_list_row_item, null);
		ReceiptData rowItem = items.get(position);
		
		//TO-DO get all items for month = position + 1, sum the prices and set the text fields accordingly
		
		TextView monthId = (TextView) rowView.findViewById(R.id.monthId);
		//monthId.setText(Integer.toString(rowItem.getId()));
		
		TextView monthlySpent = (TextView) rowView.findViewById(R.id.monthlySpent);
		//monthlySpent.setText(Integer.toString(rowItem.getId()));
		
		
		return rowView;
	}

	

	
}
