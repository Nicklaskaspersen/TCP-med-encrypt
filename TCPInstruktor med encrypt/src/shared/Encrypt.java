package shared;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Encrypt {

	public static void encrypt(OutputStream out, Object o) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		ObjectOutputStream oos = new ObjectOutputStream(baos);

		oos.writeObject(o);
		oos.close();
		
		byte ff = (byte) 0xff;
		byte[] b = baos.toByteArray();
		for (int i=0; i<b.length; i++) b[i] = (byte) (b[i] ^ ff);
		
		out.write(b);
		
	}
	
	
	public static Object decrypt(InputStream is) throws IOException, ClassNotFoundException{
		
		byte ff = (byte) 0xff;
		byte[] b = new byte [1024];
		int count = is.read(b);
	
		
		for (int i=0; i<b.length; i++) b[i] = (byte) (b[i] ^ ff);
		ByteArrayInputStream bios = new ByteArrayInputStream(b);
		ObjectInputStream ois = new ObjectInputStream(bios);
		
		
		Object cryptTrans = ois.readObject();

		return cryptTrans;
	}
	
}
