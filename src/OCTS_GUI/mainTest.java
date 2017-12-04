package OCTS_GUI;

import Common_Utility.CompareExcel;

public class mainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CompareExcel ce = new CompareExcel();
		String cmpxls = ce.readXLSXFile();
        System.out.println(cmpxls);
	}

}
