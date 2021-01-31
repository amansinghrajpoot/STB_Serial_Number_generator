package serialnumber;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

    final class AddSerialNumber implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serial_number;
	private String prefix;
	private long number;
	
	private transient File objfile;
	private transient FileInputStream fileis;
	private transient ObjectInputStream objis;
	private transient FileOutputStream fileos;
	private transient ObjectOutputStream objos;
	
	
	public AddSerialNumber(String prefix, long startNumber, String newfile) throws IOException {
		
		this.number = startNumber;
		this.prefix = prefix;
		this.serial_number = this.prefix + this.number;
		
		this.objfile = new File(newfile);
		
		this.fileos = new FileOutputStream(this.objfile);
		this.objos = new ObjectOutputStream(this.fileos);
		
		
		this.objos.writeObject(this);
		this.objos.flush();
		
		this.fileos.close();
		this.objos.close();
		
		
	}
	
	public AddSerialNumber(String existingfile) throws IOException, ClassNotFoundException {
		
		this.objfile = new File(existingfile);
		
		this.fileis = new FileInputStream(this.objfile);
		this.objis = new ObjectInputStream(this.fileis);
		
		AddSerialNumber temp = (AddSerialNumber) this.objis.readObject();
		
		this.prefix = temp.prefix;
		this.number = temp.number;
		this.serial_number = temp.serial_number;
		
		this.fileis.close();
		this.objis.close();
		
		
	}
	
	void increment(int steps) throws ClassNotFoundException, IOException {
		
		this.fileis = new FileInputStream(this.objfile);
		this.objis = new ObjectInputStream(this.fileis);
		
		AddSerialNumber temp = (AddSerialNumber) this.objis.readObject();
				
		this.prefix = temp.prefix;
		this.number = temp.number;
		this.serial_number = temp.serial_number;
		
		this.fileis.close();
		this.objis.close();
		
		for( int i = 0; i < steps; i ++) this.number++;
		
		this.serial_number = this.prefix + this.number;
		
		this.fileos = new FileOutputStream(this.objfile);
		this.objos = new ObjectOutputStream(this.fileos);
		
		this.objos.writeObject(this);
		this.objos.flush();	
		
		
		this.fileos.close();
		this.objos.close();
	}


	 static long getSerialversionuid() {
		return serialVersionUID;
	}

	 String getSerial_number() {
		return this.serial_number;
	}

	 String getPrefix() {
		return this.prefix;
	}

	 long getNumber() {
		return this.number;
	}
	
	
	

}
