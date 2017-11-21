/*===============================================================================================================================
        CLASS Name:    MainTriggerTestNG.java
        CREATED BY:    Koushik Kadimcherla
        DATE CREATED:  Nov 2017
        DESCRIPTION:   This java class is to programatically create testng.xml and trigger the automation scripts                    
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/

package OCTS_Automation_Main_Modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class TestNG_Invoke_Financial_WS {
	 static String testFile;
	 static String GIInterfaceFIle;
	 
	public void wstriggertestng(String testFile,String GIInterfaceFIle){
//	public static void main(String[] args) {
		 try {
		        // testFile = args[0];
		       //GIInterfaceFIle = args[1];
			// testFile = "C:\\Users\\koushik\\Documents\\Oracle Fusion\\ERP_InputDatasheet.XLSX";
			 //GIInterfaceFIle = "C:\\Users\\koushik\\Documents\\Oracle Fusion\\GlInterface.zip";
		    }
		    catch (ArrayIndexOutOfBoundsException e){
		        System.out.println("ArrayIndexOutOfBoundsException caught");
		    }
		
		ArrayList<String> testcase=new ArrayList<String>();
		testcase.add("OCTS_Automation_Main_Modules.ERP_Financial_Webservice_MainClass");
		  Integer numberOfHosts = 1;

		    // Creating a new Suite
		    XmlSuite suite = new XmlSuite();
		    suite.setName("ERP_OCTS_Automation");
		     
		    for (Integer i = 0; i < numberOfHosts; i++) {

		      // Creating a new Test
		      XmlTest test = new XmlTest(suite);
		      
		      // Set Test name
		      test.setName("WebService_Automation");

		      // New list for the parameters
		      Map<String, String> testParams = new HashMap<String, String>();

		      // Add parameter to the list
		      testParams.put("testFile", testFile);
		      testParams.put("GIInterfaceFile", GIInterfaceFIle);
		      // Add parameters to test
		      test.setParameters(testParams);

		      // New list for the classes
		      List<XmlClass> classes = new ArrayList<XmlClass>();

		      // Putting the classes to the list
		      classes.add(new XmlClass(testcase.get(i)));

		      // Add classes to test
		      test.setClasses(classes);

		    }

		    // New list for the Suites
		    List<XmlSuite> suites = new ArrayList<XmlSuite>();

		    // Add suite to the list
		    suites.add(suite);

		    // Creating the xml

		    TestNG tng = new TestNG();
		    tng.setXmlSuites(suites);

		    System.out.println(suite.toXml());

		    tng.run();
	}

}
