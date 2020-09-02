package soapsender;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPMessage;
public class SOAPSenderThread implements Runnable {
	private int id, interval, msgCount;
	private String pdfStr;

	public SOAPSenderThread(int id, int msgCount, int interval,String pdfStr) {
		this.id = id;
		this.msgCount = msgCount;
		this.interval = interval;
		this.pdfStr=pdfStr;
	}

	@Override
	public void run() {
		try {
			// Cоздание соединение
			SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = soapConnFactory.createConnection();

			
			for (int i = 1; i <= msgCount; i++) {
				String mess = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
						+ "<soapenv:Header/>" + "<soapenv:Body>" + createSoapBody(id, i,pdfStr) + "</soapenv:Body>"
						+ "</soapenv:Envelope>";

				InputStream is = new ByteArrayInputStream(mess.getBytes());
				SOAPMessage message = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL).createMessage(null, is);//DYNAMIC_SOAP_PROTOCOL
				message.saveChanges();
				// Адресат
				String destination = "http://localhost:8080/MTOMmodule/module";//"http://localhost:9090/MTOMmodule/ws/mtomservice";
				// Отправка
				connection.call(message, destination);
//				System.out.println("Sender #"+id+" Message #"+i+"is sended");
				Thread.sleep(interval);
			}
			// Закрываем соединение
			connection.close();
		} catch (Exception e) {
			System.out.println("Sender thread: "+e.getMessage());
		}
	}

	private String createSoapBody(int id, int i,String pdfStr) {
		
//		String pdfStr = Arrays.toString(pdf.generatePdf());
		
		String bodyXML = "<tagA>\r\n" + "	<sender>" + id + "</sender>\r\n" + "	<msgid>" + i + "</msgid>\r\n"
				+ "	<description>\r\n" + "	Sender #" + id + ", message #" + i + "\r\n" + "	</description>\r\n"
				+ "</tagA >\r\n" + "<file>"+pdfStr + "</file>\r\n" + "<tagB>\r\n" + "	<sender>"
				+ id + "</sender>\r\n" + "	<msgid>" + i + "</msgid>\r\n" + "	<description>\r\n" + "	Sender #" + id
				+ ", message #" + i + "\r\n" + "	</description>\r\n" + "</tagB >";
		// String finalStr="xmlPart1+pdfStr+xmlPart2";
		return bodyXML;

	}
}
//String xmlPart1= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
//		"<soapmsg>\r\n" + 
//		"<tag1>\r\n" + 
//		"    <sender>"+id+"</sender>\r\n" + 
//		"    <msgid>"+i+"</msgid>\r\n" + 
//		"    <description>\r\n" + 
//		"   Sender #"+id+", message #"+i+"\r\n" + 
//		"   </description>\r\n" + 
//		"</tag1>\r\n" + 
//		"<tag2>\r\n" + 
//		"    <sender>"+id+"</sender>\r\n" + 
//		"    <msgid>"+i+"</msgid>\r\n" + 
//		"    <description>\r\n" + 
//		"   Sender #"+id+", message #"+i+"\r\n" + 
//		"   </description>\r\n" + 
//		"</tag2>\r\n" +  
//		"<fileBase64>\r\n"; 
//String xmlPart2= 
//		"</fileBase64>\r\n" + 
//		"<tag3>\r\n" + 
//		"    <sender>"+id+"</sender>\r\n" + 
//		"    <msgid>"+i+"</msgid>\r\n" + 
//		"    <description>\r\n" + 
//		"   Sender #"+id+", message #"+i+"\r\n" + 
//		"   </description>\r\n" + 
//		"</tag3>\r\n"+
//		"</soapmsg>";

//private String toXmlString(Document document) throws TransformerException {
//TransformerFactory transformerFactory = TransformerFactory.newInstance();
//Transformer transformer = transformerFactory.newTransformer();
//DOMSource source = new DOMSource(document);
//StringWriter strWriter = new StringWriter();
//StreamResult result = new StreamResult(strWriter);
//transformer.transform(source, result);
//return strWriter.getBuffer().toString();
//}

//private SOAPMessage soapMessage(String soapAction)throws SOAPException{
////создание SOAP сообщения
//SOAPMessage messageSOAP = MessageFactory.newInstance().createMessage();
//
//MimeHeaders headers = messageSOAP.getMimeHeaders();
//headers.addHeader("SOAPAction", soapAction);
//
//SOAPPart part = messageSOAP.getSOAPPart();
//SOAPEnvelope envelope = part.getEnvelope();
//
//SOAPBody soapBody = envelope.getBody();
//SOAPElement soapBodyElem = soapBody.addChildElement("fileData");
//soapBodyElem.addTextNode(contentBase64);
//
//messageSOAP.saveChanges();
//return messageSOAP;
//}
///**
//* @param задать soap action 
//*/
//public void callSoapWebService(String soapAction) {
//try {
//	// Create SOAP Connection
//	SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
//	SOAPConnection soapConnection = soapConnectionFactory.createConnection();
//
//	// Send SOAP Message to SOAP Server
//	SOAPMessage soapResponse = soapConnection.call(soapMessage(soapAction), webServiceURL);
//
//	// Print the SOAP Response
//	System.out.println("Response SOAP Message:");
//	soapResponse.writeTo(System.out);
//	System.out.println();
//
//	soapConnection.close();
//} catch (Exception e) {
//	System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
//	//e.printStackTrace();
//}
//}
