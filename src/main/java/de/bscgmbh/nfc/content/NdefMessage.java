package de.bscgmbh.nfc.content;

import java.util.ArrayList;
import java.util.List;

public class NdefMessage 
{
	private int description;
	
	private List<NdefRecord> ndefRecordList = new ArrayList<NdefRecord>();
	
	public int getDescription() {
		return description;
	}

	public void setDescription(int description) {
		this.description = description;
	}

	public List<NdefRecord> getNdefRecordList() {
		return ndefRecordList;
	}

	public void setNdefRecordList(List<NdefRecord> ndefRecordList) {
		this.ndefRecordList = ndefRecordList;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("NdefMessage: ");
		sb.append("Description " + getDescription());
		sb.append(" ");
		sb.append("NdefRecordList size " + ndefRecordList.size());
		for(int i = 0; i < ndefRecordList.size(); i++)
		{
			sb.append(ndefRecordList.get(i).toString());
		}
		return sb.toString();
	}
	
	
	public static NdefMessage createNdefMessageObject(String receivedMessage)
	{
		return NdefMessage.createNdefMessageObject(NdefMessage.getGenPairListFromReceivedMessage(receivedMessage));
	}
	
	/**
	 * divide the tagged receivedMessage in key and content
	 * @param receivedMessage
	 * @return
	 */
	public static List<GenericPairVO<ContentTags, String>> getGenPairListFromReceivedMessage(String receivedMessage)
	{
		List<GenericPairVO<ContentTags, String>> genpairList = new ArrayList<GenericPairVO<ContentTags, String>>();
		
		for(int i = 0; i < ContentTags.values().length; i++)
		{
			boolean hasMore = true;
			
			do
			{

				int startIndex = receivedMessage.indexOf(ContentTags.values()[i].getStartTag());
				int endIndex = receivedMessage.indexOf(ContentTags.values()[i].getEndTag());
				
				if(startIndex == -1 || endIndex == -1)
				{
					hasMore = false;
					continue;
				}
				
				String contentOfTag = receivedMessage.substring(startIndex+ContentTags.values()[i].getStartTag().length(),
						endIndex);
				genpairList.add(new GenericPairVO<ContentTags, String>(ContentTags.values()[i], contentOfTag));
				
				receivedMessage = receivedMessage.substring(endIndex + ContentTags.values()[i].getEndTag().length());
				
				if(!receivedMessage.contains(ContentTags.values()[i].getStartTag()))
					hasMore = false;
				
			}
			while(hasMore);
			
		}
		return genpairList;
	}
	

	private void addNdefRecordToList(NdefRecord ndefRecord) 
	{
		ndefRecordList.add(ndefRecord);
	}
	
	public static NdefMessage createNdefMessageObject(List<GenericPairVO<ContentTags, String>> genpairList)
	{
		NdefMessage ndefMessage = null;
		List<NdefRecord> recordList;
		for(int i = 0; i < genpairList.size(); i++)
		{
			switch(genpairList.get(i).getLeft())
			{
				case NdefMessage_Description:
					ndefMessage = new NdefMessage();
					ndefMessage.setDescription(Integer.parseInt(genpairList.get(i).getRight()));
					break;
				case NdefMessage_RecordLength:
					//keine Ahnung ob benötigt
					break;
				case NdefMessage_Record:
					//split the values from the record
					List<GenericPairVO<ContentTags, String>> innerPairList = NdefMessage.getGenPairListFromReceivedMessage(genpairList.get(i).getRight());
					NdefRecord ndefRecord = NdefRecord.createNdefRecordObject(innerPairList);
					if(ndefRecord.isFilled())
					{
						ndefMessage.addNdefRecordToList(ndefRecord);
					}
					break;
			}
		}
		return ndefMessage;
	}


}
