package de.bscgmbh.nfc.content;

public abstract class AResponse 
{

	protected String expectedResponseString;

	
	public AResponse(String expectedResponseString) {
		this.expectedResponseString = expectedResponseString;
	}

}
