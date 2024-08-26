package de.bscgmbh.nfc.content;

import java.util.List;

public class NdefRecord 
{
	//hex string after received message
	private String id ="";
	
	//short string after received message
	private String tnf ="";
	
	//hex string after received message
	private String typed ="";
	
	//hex string after received message
	private String payload ="";
	
	//clear string (utf-8) after received message
	private String mimeType ="";
	
	//if response was filled
	private String response_content = "";
	
	//if response thrown an error
	private String response_error = "";
	

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTnf(String tnf) {
		this.tnf = tnf;
	}

	public void setTyped(String typed) {
		this.typed = typed;
	}

	private void setResponseContent(String response_content) {
		this.response_content = response_content;
	}

	private void setResponseError(String response_error) {
		this.response_error = response_error;
	}
	
	public String getResponse_Content(){
		return this.response_content;
	}
	
	
	public String getResponse_Error(){
		return this.response_error;
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(" id: ");
		sb.append(id);
		sb.append(" tnf: ");
		sb.append(tnf);
		sb.append(" typed: ");
		sb.append(typed);
		sb.append(" mimeType: ");
		sb.append(mimeType);
	

		sb.append(" payload: ");
		if(mimeType.contains("text/plain"))
		{
			sb.append(ContentTags.hexStringToString(payload));
		}
		else
		{
			sb.append(payload);
		}
		
		sb.append(" content: ");
		if(isResponseContentFilled())
		{
			sb.append(response_content);
		}
		else
		{
			sb.append(response_error);
		}
		return sb.toString();
	}
	

	public boolean isResponseContentFilled() 
	{
		if(response_content != null && response_content.length() > 0)
			return true;
		return false;
	}

	public static NdefRecord createNdefRecordObject(List<GenericPairVO<ContentTags, String>> genpairList) 
	{
		NdefRecord ndefRecord = new NdefRecord();
		
		for(int i = 0; i < genpairList.size(); i++)
		{
			switch(genpairList.get(i).getLeft())
			{
				case NdefRecord_id:
					ndefRecord.setId(genpairList.get(i).getRight());
					break;
				case NdefRecord_tnf:
					ndefRecord.setTnf(genpairList.get(i).getRight());
					break;
				case NdefRecord_type:
					ndefRecord.setTyped(genpairList.get(i).getRight());
					break;
				case NdefRecord_payload:
					ndefRecord.setPayload(genpairList.get(i).getRight());
					break;
				case NdefRecord_mimeType:
					ndefRecord.setMimeType(genpairList.get(i).getRight());
					break;
				case NdefRecord_response_content:
					ndefRecord.setResponseContent(genpairList.get(i).getRight());
					break;
				case NdefRecord_respone_error:
					ndefRecord.setResponseError(genpairList.get(i).getRight());
					break;
			}
		}
		return ndefRecord;
	}


	public boolean isFilled() 
	{
		if(id.length() != 0 || tnf.length() != 0 || typed.length() != 0 || payload.length() != 0 || mimeType.length() != 0)
			return true;
		return false;
	}
	
	
	
	
	

}
