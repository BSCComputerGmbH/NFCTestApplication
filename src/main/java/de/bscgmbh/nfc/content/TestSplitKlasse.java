package de.bscgmbh.nfc.content;

import java.util.List;

/**
 * First try to split the received Message 
 * @author Matthias G.
 *
 */
public class TestSplitKlasse {

	private static boolean firstTry = true;
	
	
	
	public static void main(String[] args) 
	{
		if(firstTry)
		{
			buildExampleFirstTry();
		}
		else
		{
			buildSecondExmaple();
		}
		
		
		
		
		
	}
	
	private static void buildSecondExmaple() 
	{
		String completeString = getSTM550Response();
		//String completeString = getRealReceivedString();
		System.out.println(completeString);

		//extrahierte Werte die in der genpairList abgelegt werden.
		List<GenericPairVO<ContentTags, String>> genpairList = NdefMessage.getGenPairListFromReceivedMessage(completeString);
		
		//Danach hat er alle Objekte die zu NDef gehören
		for(int i = 0; i < genpairList.size(); i++)
		{
			System.out.println("genpairContent " + genpairList.get(i).toString());
		}
		
		//jetzt kann der Aufbau des Ndef Message objekt erfolgen
		
		NdefMessage ndefMessage = NdefMessage.createNdefMessageObject(genpairList);
		System.out.println("ndefMessage#description " + ndefMessage.getDescription());
		System.out.println("ndefMessage#ndefrecord  " + ndefMessage.getNdefRecordList().size());
		
		for(int i = 0; i < ndefMessage.getNdefRecordList().size(); i++)
		{
			System.out.println("ndefMessage#ndefRecord  " + ndefMessage.getNdefRecordList().get(i).toString());
		}
		
		
	}

	private static void buildExampleFirstTry() {

		String completeString = getCompleteString();
		//String completeString = getRealReceivedString();
		System.out.println(completeString);
		
		//extrahierte Werte die in der genpairList abgelegt werden.
		List<GenericPairVO<ContentTags, String>> genpairList = NdefMessage.getGenPairListFromReceivedMessage(completeString);
		
		for(int i = 0; i < genpairList.size(); i++)
		{
			System.out.println("genpairContent " + genpairList.get(i).toString());
		}
		
		
	}

	private static String getSTM550Response()
	{
		return "<ndefmessage#description>0</ndefmessage#description><ndefmessage#recordlength>2</ndefmessage#recordlength><ndefmessage#record><ndefrecord#id></ndefrecord#id><ndefrecord#tnf>1</ndefrecord#tnf><ndefrecord#type>54</ndefrecord#type><ndefrecord#payload>02656E3650454E4F2B3330533030303030343133384637412B31503030304230303030303034432B33305053363230312D4B3531362B3250444130342B31325A30343745384342413133363838302B334333312B313653303130303030323000000000</ndefrecord#payload><ndefrecord#mimeType>text/plain</ndefrecord#mimeType></ndefmessage#record><ndefmessage#record><ndefrecord#id></ndefrecord#id><ndefrecord#tnf>1</ndefrecord#tnf><ndefrecord#type>54</ndefrecord#type><ndefrecord#payload>02656E4D4F4E41000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000</ndefrecord#payload><ndefrecord#mimeType>text/plain</ndefrecord#mimeType></ndefmessage#record>";
		
	}

	private static String getCompleteString() {
		StringBuilder sb = new StringBuilder();
		sb.append(ContentTags.NdefMessage_Description.getStartTag());
		sb.append("0");
		sb.append(ContentTags.NdefMessage_Description.getEndTag());
		sb.append(ContentTags.NdefMessage_RecordLength.getStartTag());
		sb.append("3");
		sb.append(ContentTags.NdefMessage_RecordLength.getEndTag());
		
		for(int i = 0; i < 3; i++)
		{
			sb.append(ContentTags.NdefMessage_Record.getStartTag());
			sb.append("Test Content RecordSet " + i);
			sb.append(ContentTags.NdefMessage_Record.getEndTag());
		}
		return sb.toString();
	}

}
