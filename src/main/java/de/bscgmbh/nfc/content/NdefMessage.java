package de.bscgmbh.nfc.content;

import java.util.List;

public class NdefMessage 
{
	private int description;
	
	private List<NdefRecord> ndefRecordList;
	
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
					
					break;
			
			
			}
			
			
			
			
		}
		
		
		
		
		return ndefMessage;
	}

}
