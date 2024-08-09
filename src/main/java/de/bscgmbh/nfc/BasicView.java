package de.bscgmbh.nfc;


import com.gluonhq.attachextended.nfc.NfcService;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.Icon;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;

import de.bscgmbh.nfc.content.ContentTags;
import de.bscgmbh.nfc.content.NdefMessage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BasicView extends View {

	/**
	 * simple flag to switch the request call at the nfc sensor
	 */
	private static boolean isSimpleRequestCall = true;
	
    public BasicView() {
        
    	
    	
    	
        Label label = new Label("Hello JavaFX World!");

        Button button = new Button("Change the World!");
        button.setGraphic(new Icon(MaterialDesignIcon.LANGUAGE));
        button.setOnAction(e -> {
        	
        	
        	StringBuilder sb = new StringBuilder();
        	sb.append("NFCService   isEmpty? " + NfcService.create().isEmpty());
        	sb.append("\n");
        	label.setText(sb.toString());
        	
        	//TODO anstatt testConnectToSensor ist dann das zu setzen was abgearbeitet werden soll.
        	
        	//Soll eine Sequenz abgearbeitet werden
        	
        	//Oder ein simpler Zugriff erfolgen einfach alles NDF Record infos weitergeben
        	
        	
        	NfcService.create().ifPresent(service -> {
        		
        		if(isSimpleRequestCall)
        		{
        			service.doConnect(ContentTags.SimpleRequestCall.getStartTag() + ContentTags.SimpleRequestCall.getEndTag());
        		}
        		else
        		{
        			//TODO 
        			
        			StringBuilder sequenceRequest = new StringBuilder();
        			sequenceRequest.append(ContentTags.SequenceRequestCall.getStartTag());
        			sequenceRequest.append(ContentTags.SequenceStart.getStartTag());
        			sequenceRequest.append(ContentTags.Request.getStartTag());
        			
        			sequenceRequest.append(ContentTags.Request.getEndTag());
        			sequenceRequest.append(ContentTags.Response.getStartTag());
        			
        			sequenceRequest.append(ContentTags.Response.getEndTag());
        			sequenceRequest.append(ContentTags.SequenceStart.getEndTag());
        			sequenceRequest.append(ContentTags.SequenceRequestCall.getEndTag());
        			
        			
        			
        			service.doConnect(sequenceRequest.toString());
        			
        		}
        		
        		
        		
        		
        		service.getResultObject().addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
					{
						//got the new result
						if(newValue != null && !newValue.equals(""))
						{
							//label.setText(newValue);
							//newValue ist tagged
							
							//divide the tags
							NdefMessage ndefMessage = NdefMessage.createNdefMessageObject(newValue);
							if(ndefMessage != null)
							{
								label.setText(ndefMessage.toString());
								
							}
							
							
							
							
							//reset the result object 
							service.getResultObject().set("");
						}
						
						
						
					}
        			
        		});
        	});
        
        
        
        
        });
        
        VBox controls = new VBox(15.0, label, button);
        controls.setAlignment(Pos.CENTER);
        
        setCenter(controls);
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> System.out.println("Menu")));
        appBar.setTitleText("Basic View");
        appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> System.out.println("Search")));
    }
    
}
