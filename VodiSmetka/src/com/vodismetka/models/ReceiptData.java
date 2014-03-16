package com.vodismetka.models;

import java.util.Date;

/**
 * 
 * @author Gorjan
 * Model class for the information available for a particular receipt
 */
public class ReceiptData implements Comparable<ReceiptData> {

	private int itemId;
	private String receiptImageId;
	private int purchaseCost;
	private String purchaseDate;
	private int month;
	
	public ReceiptData(){
	}

	public ReceiptData(int purchaseCost, String receiptImageId, String purchaseDate, int month) {
		this.purchaseCost = purchaseCost;
		this.purchaseDate = purchaseDate;
		this.receiptImageId = receiptImageId;
		this.month = month;
	}
	
	@Override
	public int compareTo(ReceiptData arg0) {
		String[] date1 = purchaseDate.split("(\\W{1})");
		String[] date2 = arg0.getPurchaseDate().split("(\\W{1})");
		Date d1 = new Date(Integer.parseInt(date1[2]), Integer.parseInt(date1[1]), Integer.parseInt(date1[0]));
		Date d2 = new Date(Integer.parseInt(date2[2]), Integer.parseInt(date2[1]), Integer.parseInt(date2[0]));
		return d1.compareTo(d2);
	}
	
	public String getReceiptImageId() {
		return receiptImageId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public void setReceiptImageId(String receiptImageId) {
		this.receiptImageId = receiptImageId;
	}

	public void setPurchaseCost(int purchaseCost) {
		this.purchaseCost = purchaseCost;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public int getPurchaseCost() {
		return purchaseCost;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
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
		sb.append("Потрошено: " + purchaseCost + "ден.\n");
		return sb.toString();
	}

}
