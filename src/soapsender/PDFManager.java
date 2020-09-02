package soapsender;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFManager {
	//private static final int BUFFER_SIZE =600000;
	public String pdfToBase64Str(String path) {
		StringBuilder result = new StringBuilder();
		try {
			File file= new File(path);
			InputStream input =new FileInputStream(file);
			long length = file.length();
		    if (length > Integer.MAX_VALUE) {
		        System.out.println("length > Integer.MAX_VALUE");
		    }
//			BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE); 
//		    Base64.Encoder encoder = Base64.getEncoder();//.getEncoder();  .getMimeEncoder(64,new byte[] { 13, 10});
//		    byte[] chunk = new byte[BUFFER_SIZE];
//		    int len = 0;
//		    while ( (len = in.read(chunk)) == BUFFER_SIZE ) {
//		         result.append( encoder.encodeToString(chunk) );
//		    }
//		    if ( len > 0 ) {
//		         chunk = Arrays.copyOf(chunk,len);
//		         result.append( encoder.encodeToString(chunk) );
//		    }
		   
		}
		catch(Exception e) {
			System.out.println("PDF" +e.getMessage());
		}
		 return result.toString();
	}
//		try {
//			File file = new File(path);
//			PDDocument doc = PDDocument.load(file, MemoryUsageSetting.setupTempFileOnly());
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			doc.save(baos);
//			String base64String = Base64.getEncoder().encodeToString(baos.toByteArray());
//			doc.close();
//			return base64String;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//		try (ByteArrayOutputStream os = java.util.Base64.getEncoder().wrap(new String(os.toByteArray()));
//				  FileInputStream fis = new FileInputStream(path)) {
//				    byte[] bytes = new byte[1024];
//				    int read;
//				    while ((read = fis.read(bytes)) > -1) {
//				        os.write(bytes, 0, read);
//				    }
//				}
	

	public void createPdfFromBase64Str(String base64Str, String resultPDFFile) {
		File file = new File(resultPDFFile);// "./test.pdf"
		try (FileOutputStream fos = new FileOutputStream(file);) {
			long startTime = System.currentTimeMillis();
			// To be short I use a corrupted PDF string, so make sure to use a valid one if
			// you want to preview the PDF file
			// String b64 =
			// "JVBERi0xLjUKJYCBgoMKMSAwIG9iago8PC9GaWx0ZXIvRmxhdGVEZWNvZGUvRmlyc3QgMTQxL04gMjAvTGVuZ3==";
			byte[] decoder = Base64.getDecoder().decode(base64Str);
			fos.write(decoder);
			long endTime = System.currentTimeMillis();
			System.out.println("PDF File Saved for " + (endTime - startTime) + "ms");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void openPDFFile(String path) {
		try {
			if ((new File(path)).exists()) {
				Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+path);
				p.waitFor();
			} else {
				System.out.println("File is not exists");
			}
			System.out.println("Done");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
