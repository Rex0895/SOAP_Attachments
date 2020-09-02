package module_webservice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.soap.MTOM;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMText;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@WebService
@MTOM
public class ServiceImpl {
	@POST
	@Consumes( "text/xml" )
	@Produces( "text/xml" )
//	public SOAPMessage post(SOAPMessage soapMessage) throws SOAPException, IOException {
//			MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			getBin64FileFromSOAP(soapMessage, "file");
//			soapMessage.writeTo(out);
//			String strMsg = new String(out.toByteArray());
//			System.out.println("_______________________________\n" + strMsg);
//		return "Ответ"+strMsg;
//	}

	private String getBin64FileFromSOAP(SOAPMessage msg, String fileTag) throws SOAPException {
//		 MimeHeaders header = new MimeHeaders();     
//	     header.addHeader("Content-Type", "text/xml");
		SOAPBody soapBody = msg.getSOAPBody();
		NodeList nodes = soapBody.getElementsByTagName(fileTag);
		String someMsgContent = null;
		Node node = nodes.item(0);
		someMsgContent = node != null ? node.getTextContent() : "";
		node.setTextContent("");
		return someMsgContent;
		// System.out.println("____________fileBegin______________\n"+someMsgContent+"\n____________fileEnd______________");
	}
	private void makeMsgWithMTOM(SOAPMessage msg) throws SOAPException {
		 OMFactory fac = OMAbstractFactory.getOMFactory();
		 OMText binaryNode =  fac.createOMText(getBin64FileFromSOAP(msg,"file"));
		 
		 
	}
}
