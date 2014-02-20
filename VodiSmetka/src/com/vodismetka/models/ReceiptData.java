package com.vodismetka.models;

import java.util.Date;

/**
 * 
 * @author Gorjan
 * Model class for the information available for a particular receipt
 */
public class ReceiptData {
	
	private String receiptImageId;
	private int purchaseCost;
	private String purchaseAddress;
	private Date purchaseDate;

	public ReceiptData(int purchaseCost, String purchaseAddress, Date purchaseDate) {
		super();
		this.purchaseCost = purchaseCost;
		this.purchaseAddress = purchaseAddress;
		this.purchaseDate = purchaseDate;
		receiptImageId = generateImageId();
	}

	private String generateImageId(){
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toString(purchaseCost));
		sb.append("-");
		sb.append(Long.toString(purchaseDate.getTime()));
		return sb.toString();
	}
	
	public String getReceiptImageId() {
		return receiptImageId;
	}

	public int getPurchaseCost() {
		return purchaseCost;
	}

	public String getPurchaseAddress() {
		return purchaseAddress;
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
		return (receiptImageId.equals(cmp.receiptImageId)) &&
				(purchaseCost==cmp.purchaseCost) &&
				(purchaseAddress.equals(cmp.purchaseAddress)) &&
				(purchaseDate.equals(cmp.purchaseDate));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Датум: " + purchaseDate.toString() + "\n");
		sb.append("Адреса: " + purchaseAddress + "\n");
		sb.append("Потрошено: " + purchaseCost + "\n");
		return sb.toString();
	}
	
	

}
