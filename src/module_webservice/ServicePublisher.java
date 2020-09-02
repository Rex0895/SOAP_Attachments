package module_webservice;

import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.MTOM;

public class ServicePublisher {
	public static void main(String[] args) {
		try {
		Endpoint ep = Endpoint.create(new ServiceImpl());
		ep.publish("http://localhost:9090/MTOMmodule/ws/mtomservice");
		System.out.println("Server is published!");
		Thread.sleep(30000);
		ep.stop();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}
