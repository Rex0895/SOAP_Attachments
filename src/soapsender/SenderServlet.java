package soapsender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/sender")
public class SenderServlet extends HttpServlet {
	//http://localhost:8080/MTOMmodule/sender
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");//Windows-1251
        PrintWriter writer = response.getWriter();
        PDFManager pdf = new PDFManager();
        writer.println("<h2>Reading and converting pdf to byte array</h2>");
        long startTime = System.currentTimeMillis();
//        String pdfStr = Arrays.toString(pdf.pdfToByteArray("D:\\\\Code\\\\Java\\\\Eclipse\\\\MTOMmodule\\\\src\\\\soapsender\\\\File183MB.pdf"));
       String pdfStr = pdf.pdfToBase64Str("D:\\\\Code\\\\Java\\\\Eclipse\\\\MTOMmodule\\\\src\\\\soapsender\\\\File330MB.pdf");
        
        try(FileWriter fw = new FileWriter("D:\\\\Code\\\\Java\\\\Eclipse\\\\MTOMmodule\\\\src\\\\soapsender\\\\senderMessage.txt", false))
        {
            fw.write(pdfStr);
            fw.flush();
        }
        catch(IOException ex){
             
            System.out.println(ex.getMessage());
        } 
        
//      pdf.createPdfFromBase64Str(pdfStr, "D:\\\\Code\\\\Java\\\\Eclipse\\\\MTOMmodule\\\\src\\\\soapsender\\\\File.pdf");
//       pdf.openPDFFile("D:\\\\Code\\\\Java\\\\Eclipse\\\\MTOMmodule\\\\src\\\\soapsender\\\\File.pdf");
       long endTime = System.currentTimeMillis();
        //System.out.println(pdfStr);
      
        System.out.println("Total execution time: " + (endTime-startTime) + "ms");
        
        writer.println("<h2>Sending SOAP msg with byte array in body tagy</h2>");
		try {
			for (int i = 1; i <=1; i++) {
				Thread t = new Thread(new SOAPSenderThread(i,1,3000,pdfStr));
				t.start();
			}
		}
		catch(Exception e){
			System.out.println("Sender: "+e.getMessage());
		}
		finally {
			  writer.println("<h2>Senders are started</h2>");
			writer.close();
		}
	}
}
