package com.vodismetka.workers;

import java.util.Date;

public class TextAnalyzer {

	private String extText;
	private int extractedPrice;
	private String extractedAddress;
	private Date extractedDate;
	
	public TextAnalyzer(String extractedText) {
		extText = extractedText;
	}
	
	private void extract(){
		// fill extractedPrice, extracted Address, extractedDate from extText
	}
	
	public int getCost(){
		//TO-DO implement price of receipt extraction
		return 0;
	}
	
	public String getAddress(){
		//TO-DO implement markets address extraction
		return "";
	}
	
	public Date getDate(){
		//TO-DO implement date of purchase extraction
		return null;
	}
	
}
