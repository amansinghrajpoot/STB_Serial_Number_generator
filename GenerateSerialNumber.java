package serialnumber;

import java.io.File;
import java.util.ArrayList;


public final class GenerateSerialNumber {

	public static ArrayList<String> generate(String filename, String sheetname, int incrementsteps, int noofserialnumbers, boolean readfromexcelfile) throws Exception {
		
		ArrayList<String> updatedrows = new ArrayList<>();
		ArrayList<String> efile = null;
		int efilesize = 0;
		File wb = new File(filename);
		String headers = null;
		String[] header = null;
		
		int boxtypeidx = -1;
		int snidx = -1;
		int macidx = -1;
		int nagraididx = -1;
		int lgididx = -1;
		int productclassidx = -1;
		int ouiidx = -1;
		int lgcpeididx = -1;
		int esnidx = -1;
		
		String boxtype = null;
		String serialnumber = null;
		String macaddress = null;
		String nagraid = null;
		String lgid = null;
		String productclass = null;
		String oui = null;
		String lgcpeid = null;
		String esn = null;
		
		String frow = null;
		
		String[] sn = null;
		String[] ma = null;
		String[] lg = null;
		String[] es = null;
		
		
		String row = null;
		String[] cell = new String[9];		
		
		
		efile = ReadWriteFile.readExcel(wb, sheetname);
		
		efilesize = efile.size();
		
	    headers = efile.get(0);
	    header = headers.split(",");
	    
	    
		row = efile.get(efilesize - 1);
		
		String temp[] =  row.split(",");
		
	    for( int i = 0; i < temp.length; i++) {
	    	
	    	cell[i] = temp[i];
	    }
	    
	    	    
	    for(int i = 0; i < header.length; i ++) {
	    	
	        if (header[i].toLowerCase().equals("serial_number")) snidx = i;
	    	else if (header[i].toLowerCase().equals("mac_address")) macidx = i;
	    	else if (header[i].toLowerCase().equals("nagra_id")) nagraididx = i;
	    	else if (header[i].toLowerCase().equals("lg_id")) lgididx = i;
	    	else if (header[i].toLowerCase().equals("product_class")) productclassidx = i;
	    	else if (header[i].toLowerCase().equals("oui")) ouiidx = i;
	    	else if (header[i].toLowerCase().equals("lg_cpe_id")) lgcpeididx = i;
	    	else if (header[i].toLowerCase().equals("esn")) esnidx = i;
	    	else if (header[i].toLowerCase().equals("box_type")) boxtypeidx = i;

	    }

	    if(boxtypeidx != -1) boxtype = cell[boxtypeidx];
	    else throw new Exception("Box_type not found in file");

	    if(snidx != -1) serialnumber = cell[snidx];
	    else throw new Exception("Serial_Number not found in file");
	    
	    if(macidx != -1) macaddress = cell[macidx];
	    else throw new Exception("Mac_Address not found in file");
	    
	    if(nagraididx != -1) nagraid = cell[nagraididx];
	    else throw new Exception("Nagra_ID not found in file");
	    
	    if(lgididx != -1) lgid = cell[lgididx];
	    else throw new Exception("LG_ID not found in file");
	    
	    if(productclassidx != -1) productclass = cell[productclassidx];
	    else throw new Exception("Product_Class not found in file");
	    
	    if(ouiidx != -1) oui = cell[ouiidx];
	    else throw new Exception("OUI not found in file");
	    
	    if(lgcpeididx != -1) lgcpeid = cell[lgcpeididx];
	    else throw new Exception("LG_CPE_ID not found in file");
	    
	    if(esnidx != -1) esn = cell[esnidx];
	    else throw new Exception("ESN not found in file");	
	    
	   
	    	    
	    if(serialnumber == null) throw new Exception("Missing Serial_Number value in file, column "+ snidx);
	    else sn = serialnumber.split("(?<=\\D)(?=\\d)");
	    
	    if( macaddress == null) throw new Exception("Missing Mac_Address value in file, column "+ macidx);
	    else ma = macaddress.split("(?<=\\D)(?=\\d)");
	    
	    if( lgcpeid == null) throw new Exception("Missing LG_CPE_ID value in file, column "+ lgcpeididx);
	    else lg = lgcpeid.split("-");
	    
	    if( esn == null) throw new Exception("Missing ESN value in file, column "+ esnidx);
	    else es = esn.split("LGNLEE3ON1000000000000000000000000");
	    
	    File savedsn = new File(sn[0]);
	    File savedma = new File(ma[0]);
	    File savedng = new File("ngid");
	    File savedlg = new File("lgid");
	    File savedlgcpeid = new File(lg[0] +"-"+ lg[1]);
	    File savedesn = new File("LGNLEE3ON");
	    
	    AddSerialNumber addsn = null;
	    AddSerialNumber addma = null;
	    AddSerialNumber addng = null;
	    AddSerialNumber addlg = null;
	    AddSerialNumber addlgcpeid = null;
	    AddSerialNumber addesn = null;
	    
		
	    		
		if(savedsn.exists() && readfromexcelfile == false) {
			addsn = new AddSerialNumber(sn[0]);	    
	    }
	    else addsn = new AddSerialNumber(sn[0], Integer.parseInt(sn[1]), sn[0]);	
	    
	    if(savedma.exists() && readfromexcelfile == false) {
	    	 addma = new AddSerialNumber(ma[0]);	    
	    }
	    else  addma = new AddSerialNumber(ma[0], Integer.parseInt(ma[1]), ma[0]);
	    
	    if(savedng.exists() && readfromexcelfile == false) {
	    	 addng = new AddSerialNumber("ngid");	    
	    }
	    else  addng = new AddSerialNumber("ngid", Integer.parseInt(nagraid),"ngid");
	    
	    if(savedlg.exists() && readfromexcelfile == false) {
	    	 addlg = new AddSerialNumber("lgid");	    
	    }
	    else  addlg = new AddSerialNumber("lgid", Integer.parseInt(lgid),"lgid");
	    
	    if(savedlgcpeid.exists() && readfromexcelfile == false) {
	    	 addlgcpeid = new AddSerialNumber(lg[0] +"-"+ lg[1]);	    
	    }
	    else  addlgcpeid = new AddSerialNumber(lg[0] +"-"+ lg[1], Integer.parseInt(lg[2]), lg[0] +"-"+ lg[1]);
	    

	    if(savedesn.exists() && readfromexcelfile == false) {
	    	 addesn = new AddSerialNumber("LGNLEE3ON1000000000000000000000000");	    
	    }
	    else  addesn = new AddSerialNumber("LGNLEE3ON1000000000000000000000000", Long.parseLong(es[1]), "LGNLEE3ON1000000000000000000000000");
	    
	    
        
	    for (int i = 0; i < noofserialnumbers; i++) {
	    	
	    addsn.increment(incrementsteps);
	    addma.increment(incrementsteps);	    	    
	    addng.increment(incrementsteps);
	    addlg.increment(incrementsteps);
	    addlgcpeid.increment(incrementsteps);	    
	    addesn.increment(incrementsteps);
	    
	    frow = boxtype+","+addsn.getSerial_number()+","+addma.getSerial_number()+"," + addng.getNumber() +","+ String.format("%012d", addlg.getNumber())+"," + productclass + ","+ oui +","+ lg[0] +"-"+ lg[1] + "-" + String.format("%012d", addlgcpeid.getNumber())+","+ addesn.getSerial_number();
	    
	    updatedrows.add(frow);
	    
	   
	    
	    }
	    	    	    
	    ReadWriteFile.writeExcel(wb, sheetname, frow);
	    
	    return updatedrows;
	}

}
