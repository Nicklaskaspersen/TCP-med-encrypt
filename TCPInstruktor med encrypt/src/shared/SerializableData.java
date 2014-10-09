package shared;

import java.io.Serializable;

public class SerializableData implements Serializable {

	private static final long serialVersionUID = 1L;

	public String username;
	
	public double amount;
	
	public String toString(){
		return "\nusername: " + username + "\namount: " + amount;
	}
	
}
