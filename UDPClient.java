import java.net.*;
import java.io.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import java.lang.*;
import buffer.Buffer;
public class UDPClient{
    public static void main(String args[]){ 
		Random rand;
		rand = new Random(123456789L);
		// rand.setSeed(123321);

		// args give message contents and destination hostname
		DatagramSocket aSocket = null;
		try {
			int port = 10000;
			aSocket = new DatagramSocket(port);    

			InetAddress aHost = InetAddress.getByName(args[1]);
			int total = (int)Math.ceil(args[0].length()/12.0);
			List<Buffer> sends = new ArrayList<>();
			String[] a = splitInParts(args[0], 12);

			for (int i = 0; i < total; i++) {
				Buffer e = new Buffer(i+1, total, a[i]);
				sends.add(e);
			}
			Collections.shuffle(sends);

			int serverPort = 6789;	
			for (Buffer buff : sends) {
				
				DatagramPacket request = new DatagramPacket(buff.Tobytes(),  16, aHost, serverPort);
				System.out.print("Enviando msg...");	
				try{Thread.sleep(rand.nextInt(1000));}catch(InterruptedException e){System.out.println(e);}    
				System.out.println(buff.message);
				System.out.println(buff.Tobytes().length);
				double pob =  rand.nextInt(1000);
				while( pob < 500){
					pob = rand.nextInt(1000);
					try{Thread.sleep(rand.nextInt(1000));}catch(InterruptedException e){System.out.println(e);}    
					// Esperar até ter mais de 50% de chance de da certo
				}
				aSocket.send(request);			                        
				System.out.println("feito!");	
	
				byte[] buffer = new byte[16];
				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
				System.out.print("Recebendo msg...");	
				aSocket.receive(reply);
				System.out.println("feito!");	
				System.out.println("Reply: " + new String(reply.getData()));	
			}

		}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		}catch (IOException e){System.out.println("IO: " + e.getMessage());
		}finally {if(aSocket != null) aSocket.close();}
	}		      	

	public static String[] splitInParts(String s, int partLength){
    int len = s.length();

    // Number of parts
    int nparts = (len + partLength - 1) / partLength;
    String parts[] = new String[nparts];

    // Break into parts
    int offset= 0;
    int i = 0;
    while (i < nparts)
    {
        parts[i] = s.substring(offset, Math.min(offset + partLength, len));
		// System.out.println(parts[i]);;
        offset += partLength;
        i++;
    }

    return parts;
}
}
