package de.bscgmbh.nfc.content;

/**
 * the received nfc content is enclosed with tags for the different types
 * @author mg
 *
 */
public enum ContentTags 
{
	//Tags in Reihenfolge der Zeichenkette hier definieren
	NdefMessage_Description("<ndefmessage#description>", "</ndefmessage#description>"),
	NdefMessage_RecordLength("<ndefmessage#recordlength>", "</ndefmessage#recordlength>"),
	//es wird mehrere geben, Unterscheidung?
	NdefMessage_Record("<ndefmessage#record>", "</ndefmessage#record>"),
	
	//inner tags from ndefMessage_Record
	NdefRecord_id("<ndefrecord#id>", "</ndefrecord#id>"),
	NdefRecord_tnf("<ndefrecord#tnf>", "</ndefrecord#tnf>"),
	NdefRecord_type("<ndefrecord#type>", "</ndefrecord#type>"),
	


	;
	
	private String startTag, endTag;
	
	private ContentTags(String startTag, String endTag)
	{
		this.startTag = startTag;
		this.endTag = endTag;
	}

	public String getStartTag() {
		return startTag;
	}


	public String getEndTag() {
		return endTag;
	}
	
	
	public static String getRawContent(String enclosedContent)
	{
		
		//TODO check ob vorhanden
		
		//danach den Inhalt aus dem string herauslösen
		
		// und zurückgeben
		String cleanContent = "";
	
		return cleanContent;
	}
	
}
