package de.bscgmbh.nfc.content;

import java.util.ArrayList;
import java.util.List;

public class TestSplitKlasse {

	public static void main(String[] args) 
	{
		String completeString = getCompleteString();
		System.out.println(completeString);
		
		//extrahierte Werte die in der genpairList abgelegt werden.
		List<GenericPairVO<ContentTags, String>> genpairList = new ArrayList<GenericPairVO<ContentTags, String>>();
		
		
		for(int i = 0; i < ContentTags.values().length; i++)
		{
			boolean hasMore = true;
			
			do
			{

				int startIndex = completeString.indexOf(ContentTags.values()[i].getStartTag());
				int endIndex = completeString.indexOf(ContentTags.values()[i].getEndTag());
				
				String contentOfTag = completeString.substring(startIndex+ContentTags.values()[i].getStartTag().length(),
						endIndex);
				genpairList.add(new GenericPairVO<ContentTags, String>(ContentTags.values()[i], contentOfTag));
				
				completeString = completeString.substring(endIndex + ContentTags.values()[i].getEndTag().length());
				
				if(!completeString.contains(ContentTags.values()[i].getStartTag()))
					hasMore = false;
				
			}
			while(hasMore);
			
		}
		
		for(int i = 0; i < genpairList.size(); i++)
		{
			System.out.println("genpairContent " + genpairList.get(i).toString());
			
			
			
			
			
		}
		
		
		
		
		
		
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
