package com.vodismetka.workers;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vodismetka.R;
import com.vodismetka.activities.LaunchActivity;
import com.vodismetka.activities.ViewReceiptActivity;
import com.vodismetka.models.ReceiptData;

public class ReceiptsAdapter extends ArrayAdapter<ReceiptData>{

	private final Context context;
	private List<ReceiptData> items;
	private LayoutInflater inflater;
	
	public ReceiptsAdapter(Context context, List<ReceiptData> resource) {
		super(context, R.layout.receipt_list_view, resource);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		items = resource;
		this.context = context;
	}
	
	class ViewHolder {
		RelativeLayout layout;
		ImageView imgView;
		TextView dateText;
		TextView priceText;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		final ReceiptData rowItem = items.get(position);
		
		if(convertView == null){
			holder = new ViewHolder();
			holder.layout = (RelativeLayout) inflater.inflate(R.layout.list_row_item, null);
			holder.imgView = (ImageView) holder.layout.findViewById(R.id.icon);
			holder.dateText = (TextView) holder.layout.findViewById(R.id.rowDateLabel);
			holder.priceText = (TextView) holder.layout.findViewById(R.id.rowCostLabel);
			
			convertView = holder.layout;
			convertView.setTag(holder);
		}
		
		holder = (ViewHolder) convertView.getTag();
		
		holder.imgView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent viewReceipt = new Intent(context, ViewReceiptActivity.class);
				viewReceipt.putExtra(LaunchActivity.IMG_KEY, rowItem.getReceiptImageId());
				context.startActivity(viewReceipt);
			}
		});
		
		int spentAmount = rowItem.getPurchaseCost();
		
		//set spent amount
		holder.dateText.setText("Потрошено: " + Integer.toString(spentAmount) + "ден.");
		
		//set the background color according to the amount spent
		holder.layout.setBackgroundColor(context.getResources().getColor(R.color.red));
		if(spentAmount<1000){
			if(spentAmount<500){
				if(spentAmount<100){
					holder.layout.setBackgroundColor(context.getResources().getColor(R.color.green));
				} else {
					holder.layout.setBackgroundColor(context.getResources().getColor(R.color.yellow));
				}
			} else{
				holder.layout.setBackgroundColor(context.getResources().getColor(R.color.orange));
			}
		}
		
		//set date
		holder.priceText.setText("Датум: " + rowItem.getPurchaseDate());
		
		return convertView;
	}
	
}
