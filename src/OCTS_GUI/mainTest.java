package OCTS_GUI;

import Common_Utility.XmlToExcelConverter;
import OCTS_Financial_Webservice.ERP_executeXMLQuery;
import OCTS_Financial_Webservice.ERP_getSessionID;

public class mainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ERP_getSessionID sessID = new ERP_getSessionID();
    	ERP_executeXMLQuery getXML = new ERP_executeXMLQuery();
    	XmlToExcelConverter conv = new XmlToExcelConverter();
    	
    	try {
    		String sessionID = sessID.getSessionID("237917", "Fusion321");
    		String xml = getXML.getXMLQuery(sessionID);
    		xml = xml.substring(0, xml.length()-11);
			conv.getAndReadXml("C:\\Automation_OCTS\\Data",xml);
    		//System.out.println(xml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
