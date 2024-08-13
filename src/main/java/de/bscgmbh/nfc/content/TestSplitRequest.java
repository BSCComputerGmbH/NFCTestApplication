package de.bscgmbh.nfc.content;

import java.util.ArrayList;
import java.util.List;

public class TestSplitRequest 
{
	public static void main(String[] args) 
	{
		List<GenericPairVO<? extends ARequest, ? extends AResponse>> genericPairList = new ArrayList<GenericPairVO<? extends ARequest, ? extends AResponse>> ();
		
		
		
		String sampleRequest = getExampleRequest();
		System.out.println("completeRequest " + sampleRequest);
		
		//jetzt die Testzerlegung
		
		//1. entfernen des SequenceReqeustCall
		int startIndex = sampleRequest.indexOf(ContentTags.SequenceRequestCall.getStartTag());
		int endIndex = sampleRequest.indexOf(ContentTags.SequenceRequestCall.getEndTag());
		String contentOfTag = sampleRequest.substring(ContentTags.SequenceRequestCall.getStartTag().length(), endIndex);
		System.out.println("contentOfTag " + contentOfTag);
		
		//2. alle Sequenzen in einer einer Liste packen GenericPair<Request, Response>
		int sequenceIndex = 0;
		boolean hasMore = true;
		do
		{
			startIndex = contentOfTag.indexOf(ContentTags.getSequenceStartTag(sequenceIndex));
			endIndex = contentOfTag.indexOf(ContentTags.getSequenceEndTag(sequenceIndex));
			if(startIndex == -1 || endIndex == -1)
			{
				hasMore = false;
				break;
			}
			
			//request/response content
			String contentOfSequence = contentOfTag.substring(startIndex + ContentTags.getSequenceStartTag(sequenceIndex).length(), endIndex);
			
			//substring contentOfSequence in request and response
			int startIndexReq = contentOfSequence.indexOf(ContentTags.Request.getStartTag());
			int endIndexReq = contentOfSequence.indexOf(ContentTags.Request.getEndTag());
			
			int startIndexResp = contentOfSequence.indexOf(ContentTags.Response.getStartTag());
			int endIndexResp = contentOfSequence.indexOf(ContentTags.Response.getEndTag());
			
			String contentOfRequest = contentOfSequence.substring(startIndexReq + ContentTags.Request.getStartTag().length(), endIndexReq);
			System.out.println("contentOfRequest " + contentOfRequest);
			
			String contentOfResponse = contentOfSequence.substring(startIndexResp + ContentTags.Response.getStartTag().length(), endIndexResp);
			System.out.println("contentOfResponse " + contentOfResponse);
			
			genericPairList.add(createGenericPair(contentOfRequest, contentOfResponse));
			
			sequenceIndex++;
		}
		while(hasMore);
		
		for(int i = 0; i < genericPairList.size(); i++)
		{
			System.out.println("left  " + ((ByteArrayRequest)genericPairList.get(i).getLeft()).getRequest().length);
			System.out.println("right " + ((ByteArrayResponse)genericPairList.get(i).getRight()).getResponse().length);
		}
		
		
		//3. Abarbeitung aller Sequenzen
		
		
		//4. Alles zurückgeben was empfangen wurde
		//List<GenericPairVO<?, ?>> requestList = 
		
		
		
		
		
		
		
		
		
		
		
		
	}

	private static GenericPairVO<? extends ARequest, ? extends AResponse> createGenericPair(String contentOfRequest, String contentOfResponse) 
	{
		if(contentOfRequest.startsWith("0x"))
		{
			return new GenericPairVO<ByteArrayRequest, ByteArrayResponse>(new ByteArrayRequest(contentOfRequest), new ByteArrayResponse(contentOfResponse));
		}
		return null;
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
