package soapreceiver;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import soapsender.PDFManager;

@WebServlet("/receiver")
public class ReceiverServlet extends HttpServlet {
	// http://localhost:8080/MTOMmodule/receiver
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		writer.println("<h2>" + " [*] Waiting for messages. To exit press CTRL+C" + "</h2>");
		try {
		} finally {
			writer.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/xml;charset=UTF-8");
//		PrintWriter writer = response.getWriter();
		try {
			MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);// DYNAMIC_SOAP_PROTOCOL
																			// SOAPConstants.SOAP_1_1_PROTOCOL
			InputStream inStream = request.getInputStream();
			SOAPMessage soapMessage = messageFactory.createMessage(new MimeHeaders(), inStream);
			
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			soapMessage.writeTo(out);
//			String strMsg = new String(out.toByteArray());
//			System.out.println(strMsg);

//			try(FileWriter fw = new FileWriter("D:\\\\Code\\\\Java\\\\Eclipse\\\\MTOMmodule\\\\src\\\\soapsender\\\\ReceiverMessage.txt", false))
//	        {
//				ByteArrayOutputStream out = new ByteArrayOutputStream();
//				soapMessage.writeTo(out);
//				String strMsg = new String(out.toByteArray());
////				System.out.println("___________Module__________\n" + strMsg+"\n\n");
//	            fw.write(strMsg);
//	            fw.flush();
//	        }
//	        catch(IOException ex){
//	            System.out.println("Module: "+ex.getMessage());
//	        } 
			
			//System.out.println("1 message received");
			//AttachmentPart attachment = soapMessage.get
			Iterator iterator = soapMessage.getAttachments();
			System.out.println(soapMessage.countAttachments());
			while (iterator.hasNext()) {
				AttachmentPart attachment = (AttachmentPart) iterator.next();
				String id = attachment.getContentId();
				String type = attachment.getContentType();
				System.out.print("Attachment " + id + " has content type " + type);
				if (type.equals("text/plain")) {
					Object content = attachment.getContent();
				
//						PDFManager pdfm = new PDFManager();
//						String newPdfFilePath = "D:\\\\Code\\\\Java\\\\Eclipse\\\\MTOMmodule\\\\src\\\\soapsender\\\\ReceiverPDF.pdf";
//						pdfm.createPdfFromBase64Str(content.toString(), newPdfFilePath);
//						pdfm.openPDFFile(newPdfFilePath);
					// System.out.println("Attachment contains:\n" + content);

				}
			}
			// System.out.println("Received message:\n"+strMsg+"\n\n");
//			writer.println(strMsg);
		} catch (Exception e) {
			System.out.println("Receiver: " + e.getMessage());
		} finally {
//			writer.close();
		}

	}

}
