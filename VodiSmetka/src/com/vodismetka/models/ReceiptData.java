package com.vodismetka.models;

import java.util.Date;

/**
 * 
 * @author Gorjan
 * Model class for the information available for a particular receipt
 */
public class ReceiptData {
	
	private int itemId;
	private String receiptImageId;
	private int purchaseCost;
	private String purchaseDate;

	public ReceiptData(int purchaseCost, String receiptImageId, String purchaseDate) {
		super();
		this.purchaseCost = purchaseCost;
		this.purchaseDate = purchaseDate;
		this.receiptImageId = receiptImageId;
	}
	
	public String getReceiptImageId() {
		return receiptImageId;
	}

	public int getPurchaseCost() {
		return purchaseCost;
	}


	public String getPurchaseDate() {
		return purchaseDate;
	}
	
	public void setId(int id){
		itemId = id;
	}

	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if (o.getClass() != this.getClass())
			return false;
		ReceiptData cmp = (ReceiptData) o;
		return (receiptImageId.equals(cmp.receiptImageId)) &&
				(purchaseCost==cmp.purchaseCost) &&
				(purchaseDate.equals(cmp.purchaseDate));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Датум: " + purchaseDate + "\n");
		sb.append("Потрошено: " + purchaseCost + "\n");
		return sb.toString();
	}
	
	

}
