package soapreceiver;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiverThread implements Runnable {
	public void run() {
		//System.out.println("���������� �: "+Integer.toString(this.hashCode()).substring(0, 3)+" �������");
		receiveTask();
	}

	public synchronized void receiveTask() {
//		try {
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			System.out.println("IOException");
//		} catch (TimeoutException e) {
//			// TODO Auto-generated catch block
//			System.out.println("TimeoutException");
//		}	
//	}
	}
}
