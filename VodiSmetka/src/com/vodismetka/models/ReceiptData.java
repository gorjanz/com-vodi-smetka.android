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
	private Date purchaseDate;

	public ReceiptData(int purchaseCost, String receiptImageId, Date purchaseDate) {
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


	public Date getPurchaseDate() {
		return purchaseDate;
	}

	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if (o.getClass() != this.getClass())
			return false;
		ReceiptData cmp = (ReceiptData) o;
		return this.itemId == cmp.itemId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Датум: " + purchaseDate.toString() + "\n");
		sb.append("Потрошено: " + purchaseCost + "\n");
		return sb.toString();
	}
	
	

}
