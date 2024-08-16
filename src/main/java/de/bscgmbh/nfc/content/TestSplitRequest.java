package de.bscgmbh.nfc.content;

import java.util.Arrays;
import java.util.List;



public class TestSplitRequest 
{
	public static void main(String[] args) 
	{
		String exampleRequest = getExampleRequest();
		System.out.println("completeRequest " + exampleRequest);
		
		List<GenericPairVO<? extends ARequest, ? extends AResponse>> genericPairList = RequestResponseDivier.getGenericPairList(exampleRequest);
		
		for(int i = 0; i < genericPairList.size(); i++)
		{
			System.out.println("left  " + ((ByteArrayRequest)genericPairList.get(i).getLeft()).getRequest().length);
			System.out.println("right " + ((ByteArrayResponse)genericPairList.get(i).getRight()).getResponse().length);
		}
		
		byte[] responseFromSensor = new byte[] {0x00, 0x01};
		
		boolean testVergleich = ((ByteArrayResponse)genericPairList.get(0).getRight()).isExpectedResponse(ByteArrayResponse.toObjectArray(responseFromSensor));
		System.out.println("expectedResponse " + testVergleich);
		
		
	}
	
	

	/**
	 * build example 
	 * @return
	 */
	private static String getExampleRequest() {
		StringBuilder sequenceRequest = new StringBuilder();
		sequenceRequest.append(ContentTags.SequenceRequestCall.getStartTag());
		sequenceRequest.append(ContentTags.getSequenceStartTag(0));
		sequenceRequest.append(ContentTags.Request.getStartTag());
		//wass gesendet wird
		sequenceRequest.append("0x1B0000E500");
		sequenceRequest.append(ContentTags.Request.getEndTag());
		sequenceRequest.append(ContentTags.Response.getStartTag());
		//was erwartet wird
		sequenceRequest.append("0x0000");
		sequenceRequest.append(ContentTags.Response.getEndTag());
		sequenceRequest.append(ContentTags.getSequenceEndTag(0));
		
		
		//TODO vielleicht anstatt start tag doch eher gleich auf sequence mit einer fortlaufenden ID
		sequenceRequest.append(ContentTags.getSequenceStartTag(1));
		sequenceRequest.append(ContentTags.Request.getStartTag());
		sequenceRequest.append("0x3049");
		sequenceRequest.append(ContentTags.Request.getEndTag());
		
		sequenceRequest.append(ContentTags.Response.getStartTag());
		//empty alles was kommt wird entgegen genommen.
		sequenceRequest.append("");
		sequenceRequest.append(ContentTags.Response.getEndTag());
		
		sequenceRequest.append(ContentTags.getSequenceEndTag(1));
		
		sequenceRequest.append(ContentTags.SequenceRequestCall.getEndTag());
		
		
		return sequenceRequest.toString();
	}

}
