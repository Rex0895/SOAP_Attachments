package module_servlet;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


@WebServlet("/module")
public class ModuleServlet extends HttpServlet {
	// http://localhost:8080/MTOMmodule/module
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		writer.println("<h2>" + " [*] Waiting for messages. To exit press CTRL+C" + "</h2>");
		try {
		} finally {
			writer.close();
		}
	}

	/** Receiver */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/soap+xml;charset=UTF-8");//application/soap+xml   text/xml
//		PrintWriter writer = response.getWriter();
		try {
			MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);//DYNAMIC_SOAP_PROTOCOL
			InputStream inStream = request.getInputStream();
			SOAPMessage soapMessage = messageFactory.createMessage(new MimeHeaders(), inStream);
			String base64Str = getBase64StringFromSOAPBody(soapMessage, "file");
			//______________________-TestFileWriter_______________________
//			try(FileWriter fw = new FileWriter("D:\\\\Code\\\\Java\\\\Eclipse\\\\MTOMmodule\\\\src\\\\soapsender\\\\module.txt", false))
//	        {
//	            fw.write(base64Str);
//	            fw.flush();
//	        }
//	        catch(IOException ex){
//	             
//	            System.out.println(ex.getMessage());
//	        } 
//___________________________TestPdfFromBase64___________________			
//			PDFManager pdfm = new PDFManager();
//			String newPdfFilePath = "D:\\\\Code\\\\Java\\\\Eclipse\\\\MTOMmodule\\\\src\\\\soapsender\\\\FileFromAtt.pdf";
//	        pdfm.createPdfFromBase64Str(base64Str, newPdfFilePath);
//	        pdfm.openPDFFile(newPdfFilePath);
//			System.out.println("Module receives,processes and send message:");
			addToAttachmentsAndSend(soapMessage, base64Str, "http://localhost:8080/MTOMmodule/receiver");
			
//			writer.println(strMsg);
		} catch (Exception e) {
			System.out.println("Module: " +e.getMessage());
		} finally {
//			writer.close();
		}

	}

	private String getBase64StringFromSOAPBody(SOAPMessage msg, String fileTag) throws SOAPException {
		SOAPBody soapBody = msg.getSOAPBody();
		NodeList nodes = soapBody.getElementsByTagName(fileTag);
		String someMsgContent = null;
		Node node = nodes.item(0);
		someMsgContent = node != null ? node.getTextContent() : "";
		node.setTextContent("");
		return someMsgContent;
		// System.out.println("____________fileBegin______________\n"+someMsgContent+"\n____________fileEnd______________");
	}

	private void addToAttachmentsAndSend(SOAPMessage message, String base64Str, String destination)
			throws SOAPException, ParserConfigurationException, SAXException, IOException {
		SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection connection = soapConnFactory.createConnection();
	
		   
		AttachmentPart attachment = message.createAttachmentPart( );
		attachment.setContent("sfsdfsdfsdfsdf", "text/plain");
	//	attachment.set
//		attachment.setContent(base64Str, "text/plain");
		attachment.setContentId("FileInBase64");// update_address
		
		
//		attachment.setContent(base64Str,"application/octet-stream");
//		attachment.setMimeHeader("Content-Transfer-Encoding", "binary");
		
		
		
		message.addAttachmentPart(attachment);
		message.saveChanges();

		try(FileWriter fw = new FileWriter("D:\\\\Code\\\\Java\\\\Eclipse\\\\MTOMmodule\\\\src\\\\soapsender\\\\moduleMessage.txt", false))
        {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			message.writeTo(out);
			String strMsg = new String(out.toByteArray());
//			System.out.println("___________Module__________\n" + strMsg+"\n\n");
            fw.write(strMsg);
            fw.flush();
        }
        catch(IOException ex){
            System.out.println("Module: "+ex.getMessage());
        } 
		
		
		connection.call(message, destination);//SOAPMessage reply =
		connection.close();
	}

//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		response.setContentType("text/html;charset=UTF-8");
//		System.out.println("Module waits for messages.");
//		PrintWriter writer = response.getWriter();
//		writer.println("<h2>" + "Module waits for messages" + "</h2>");
//		try {
//			SOAPEnvelope env = reply.getSOAPPart().getEnvelope();
//		    SOAPBody sb = env.getBody();
//		    Name ElName = env.createName("XElement");
//		    Iterator it = sb.getChildElements(ElName);
//		    SOAPBodyElement sbe = (SOAPBodyElement) it.next();
//		    MyValue =   sbe.getValue();
//		    
//		    
//		} finally {
//			writer.close();
//		}
//	}

}
