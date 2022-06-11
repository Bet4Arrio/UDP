import java.net.*;
import java.io.*;
import buffer.Buffer;
import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor.STRING;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import java.util.Collections;
public class UDPServer{
    public static void main(String args[]){ 
    	DatagramSocket aSocket = null;

		try{
		    aSocket = new DatagramSocket(6789);
			// create socket at agreed port
			System.out.println("Iniciando server...");
			List<Buffer> pkgs = new ArrayList<>();
 			while(true){
				byte[] buffer = new byte[16];
 				DatagramPacket request = new DatagramPacket(buffer, 0, buffer.length);
				
  				aSocket.receive(request);     
				Buffer b = new Buffer(request.getData());
				pkgs.add(b);

				DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), 
				                                          request.getAddress(), request.getPort());
				System.out.println("PKG:  "+b.message);
				if(pkgs.size() == b.length){

					Collections.sort(pkgs, (o1, o2) -> o1.offset - o2.offset);
					System.out.println("========================================== ");
					System.out.println("Message: ");
					for (Buffer c : pkgs) {
						System.out.print(c.message);
						
					}
					System.out.println("========================================== ");
					pkgs.clear();
				}
				aSocket.send(reply);
			}
		} catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {System.out.println("IO: " + e.getMessage());
		} finally {if(aSocket != null) aSocket.close();}
    }
}
