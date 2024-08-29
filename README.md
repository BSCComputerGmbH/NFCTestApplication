# NFCTestApplication
Gluon Mobile example application for a nfc communication (at the moment only for android devices).

#### Requirement to run the application
You need a built nfc module. The module can be found in a fork of the AttachExtended repository ( [https://github.com/BSCComputerGmbH/AttachExtended/tree/master](https://github.com/BSCComputerGmbH/AttachExtended/tree/master) ). To build the nfc module you use the shell script build.sh ( you find it in the root directory). In this build script you need to specify the environment variables of your installation.
After finishing the build process the jars are available in the maven repository in the directory com/gluonhq/attachextended/nfc.
For the next step you must change the pom.xml of the NFCTestApplication by adding the local repository location.
 ```
<repository>
	<id>local-repository</id>
	<name>repository</name>
	<!-- attach extended with the nfc as locally build -->
	<url>file:///home/mg/.m2/repository/</url>
</repository>
```
The app must be built with the known Gluon Mobile commands. After installation on the Android mobile phone you can test the application.  
#### Run the NFCTestApplication
After opening the application you will find two buttons. After pressing the first button the nfc process will be started and all received NdefMessages/NdefRecords will be shown on the app view.  
The second button is provided for a bidirectional communication between nfc module and nfc hardware. In our selected example we access the STM 550 Multisensor Module from the EnOcean company (Link: https://www.enocean.com/de/produkt/stm-550-multisensor-modul/?frequency=868-de ).
#### Background of the test application
Basically all data communication between nfc module and the test application works over one string.  
To make it possible to transmit different and structured queries/results, the content of the string is tagged. The tags are similar to the xml structure.  Consequently every tag has an opening and closing element. All currently available tags are viewable in the enum ContentTags (package de.bscgmbh.nfc.content).  

Results from the nfc module are stored in the ResultObject. The application receives all results from the nfc module via ChangeListener&lt;String>. For this reason a ChangeListener&lt;String> must first be created and added to this ResulObject as listener.

Example:

 ```
NfcService.create().ifPresent(service -> {  
	service.getResultObject().addListener(simpleRequestListener);  
}); 
```
In the change method of the ChangeListener is where the application specific logic is to be implemented. This change method will be triggered after a new result is available in the nfc module.
#### Connection establishment with the nfc module
##### SimpleRequestCall
The easiest way to communicate with the nfc module is the SimpleRequestCall. With this request, the NFC module is told that all messages are to be read and added to the ResultObject after the connection has been established. To start this process implement the following programm code in your application:  
 ```
NfcService.create().ifPresent(service -> {  
	service.doConnect(ContentTags.SimpleRequestCall.getStartTag() + 
			ContentTags.SimpleRequestCall.getEndTag());  
	});
}); 
```
Only the start and end tags of the enum SimpleRequestCall are passed as parameters to the service connect method.
##### SequenceRequestCall
Another possibilty is the connection establishment with a bidirectional communication. In this variant, the string parameter from the connect method is more complex.  
That’s why the enum SequenceRequestCall is used here as an input tag. Within this tag further parameter are set, depending on the nfc application.  
With the first inline parameter the nfc technology is set that will be used. The identifying enum here is IO_Operations and the associated content is from the enum TagTechnologyConstants.  

After setting the technology follows the actual sequence request that is carried out after a connection has been established. Every Query consists of the enums Request and Response. These enums are enclosed with the Sequence tag and a continuous number. The content of the Request and Response is application specific und will be set as a hex string. The expected result will be set to the response tag, if the result from the nfc hardware is to be checked. Only if the expected and the received result are the same the next sequence will be called.  
A response tag that contains an empty string, signals the nfc module that the received result needs to be forwarded to ResultObject.

You can find the example code in the ActionEvent of the sequenceButton in the class BasicView. There the connection to an EnOcean Multisensor module is established. The first sequence is for the authorization with a pin code. If the expected response matches the result obtained, a register on the Multisensor module is queried with the second sequence. The result will be sent to the application via the ResultObject.  
The complete request is completed with the end tag of SequenceRequestCall.
#### Showcase
The following link leads to a video that shows the TestNFCApplication in action:
https://www.youtube.com/watch?v=qI6vgbOD6B8
The second link shows a video about a real use case:
https://www.youtube.com/watch?v=QWiRFbUaCiU




