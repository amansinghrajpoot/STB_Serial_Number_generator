package serialnumber;

import java.util.ArrayList;

public class Driver {

	public static void main(String[] args) throws Exception {
		
     ArrayList<String> updatedrows = GenerateSerialNumber.generate("STB.xlsx", "Sheet1", 1, 3, false);
     
     updatedrows.forEach(i -> System.out.println(i));

	}

}
