/*===============================================================================================================================
        CLASS Name:    ERP_Webservice_MainDriver_Script
        CREATED BY:    Koushik Kadimcherla
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Main Driver script                    
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/

package OCTS_Automation_Main_Modules;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import OCTS_Financial_Webservice.ERP_downloadESSJobExecutionDetails;
import OCTS_Financial_Webservice.ERP_getESSJobStatus;
import OCTS_Financial_Webservice.ERP_submitESSJobRequest_Loader;
import OCTS_Financial_Webservice.ERP_submitESSJobRequest_RequestImport;
import OCTS_Financial_Webservice.ERP_uploadFileToUcm;
import Common_Utility.CommonUtilFunctions;
import Common_Utility.Details;
import Common_Utility.ERP_utilDecodeBase;
import Common_Utility.ReporterBaseTest;


public class ERP_Financial_Webservice_MainClass extends ReporterBaseTest {
	FileInputStream file = null;
	String resultuploadFile;
	String auth;
	String resultsubmitESSJobRequest_Loader;
	String resultgetESSJobStatus_Loader="WAIT";
	String resultsubmitESSJobRequest_ReqImport;
	String resultgetESSJobStatus_ReqImport="WAIT";
	String resultDownloadESSJobExecutionDetails;
	String encStr;
	 List<Details> all;
	 ArrayList<Cell> cells_uUserName;
	 String cellvalue_uUserName;
	 ArrayList<Cell> cells_uPassword;
	 String cellvalue_uPassword;
	 ArrayList<Cell> cells_uFileName;
	 String cellvalue_uFileName;
	 ArrayList<Cell> cells_uContentType;
	 String cellvalue_uContentType;
	 ArrayList<Cell> cells_uDocumenTtitle;
	 String cellvalue_uDocumentTitle;
	 ArrayList<Cell> cells_uDocumentAuthor;
	 String cellvalue_uDocumentAuthor;
	 ArrayList<Cell> cells_uDocumentAccount;
	 String cellvalue_uDocumentAccount;
	 ArrayList<Cell> cells_uDocumentSecurityGroup;
	 String cellvalue_uDocumentSecurityGroup;
	 ArrayList<Cell> cells_uDocumentName;
	 String cellvalue_uDocumentName;
	 ArrayList<Cell> cells_uDocumentID;
	 String cellvalue_uDocumentID;
	 ArrayList<Cell> cells_ujobPackageName;
	 String cellvalue_ujobPackageName;
	 ArrayList<Cell> cells_ujobDefinitionName;
	 String cellvalue_ujobDefinitionName;
	 ArrayList<Cell> cells_uLoaderParamList1;
	 String cellvalue_uLoaderParamList1;
	 ArrayList<Cell> cells_uLoaderParamList2;
	 String cellvalue_uLoaderParamList2;
	 ArrayList<Cell> cells_uLoaderParamList3;
	 String cellvalue_uLoaderParamList3;
	 ArrayList<Cell> cells_uImportjobPackageName;
	 String cellvalue_uImportjobPackageName;
	 ArrayList<Cell> cells_uImportjobDefinitionName;
	 String cellvalue_uImportjobDefinitionName;
	 ArrayList<Cell> cells_uImportParamList1;
	 String cellvalue_uImportParamList1;
	 ArrayList<Cell> cells_uImportParamList2;
	 String cellvalue_uImportParamList2;
	 ArrayList<Cell> cells_uImportParamList3;
	 String cellvalue_uImportParamList3;
	 ArrayList<Cell> cells_uImportParamList4;
	 String cellvalue_uImportParamList4;
	 ArrayList<Cell> cells_uImportParamList5;
	 String cellvalue_uImportParamList5;
	 ArrayList<Cell> cells_uImportParamList6;
	 String cellvalue_uImportParamList6;
	 ArrayList<Cell> cells_uImportParamList7;
	 String cellvalue_uImportParamList7;
	 XSSFWorkbook workbook;
	 int resultsubmitESSJobRequest_ReqImport_New;
	
	  
	  
	 @Parameters({ "testFile", "GIInterfaceFile" })
	 @Test	 
	public  void OracleCloud_Financial_Webservices(String inputfile_fp,String GIfile_fp) throws Exception {
		try{
		 boolean booleanstatus=true;
		 System.out.println(inputfile_fp);
		 System.out.println(GIfile_fp);
		/*
         * Code to read input data file 
         */
		 test = extent.createTest("Testing Journal Entry Import");
		 System.out.println("after create test");

           try {
				     file = new FileInputStream(
				     new File(inputfile_fp));
				     test.log(Status.INFO, "File :" + inputfile_fp);		    
				  } 
           catch (Exception e1) {
				     Details d = new Details();
				     d.setErrormessage("File is not in valid format.Please select correct file");
				     test.log(Status.FAIL, "Step 1: File is not in valid format.Please select correct file");
				     booleanstatus=false;
				     all.add(d);
				     e1.printStackTrace();
				     }
           try {
				     workbook = new XSSFWorkbook(file);
				  } 
		      catch (Exception e1) {
				     Details d = new Details();
				       d.setErrormessage("File is not in valid format.Please select correct file");
				       test.log(Status.FAIL, "Step 1: File is not in valid format.Please select correct file");
				       all.add(d);
				       booleanstatus=false;
				     e1.printStackTrace();
				     throw new Exception(e1);
		          }
           XSSFSheet ips1 = workbook.getSheetAt(0);
           //System.out.println("FirstRowNum"+ips1.getFirstRowNum());
           //System.out.println("LastRowNum"+ips1.getLastRowNum());
           //
       //    XSSFRow row = ips1.getRow(0);
           CommonUtilFunctions cu = new CommonUtilFunctions();
           for (int currentColumnNum = 0; currentColumnNum < 1; currentColumnNum++)
           {
           //XSSFCell cell = row.getCell(currentColumnNum); //get the cell
           for (int currentRowNum = ips1.getFirstRowNum()+1; currentRowNum <= ips1.getLastRowNum(); currentRowNum++)
           {
           System.out.println("RowNum"+currentRowNum);
           
           cells_uUserName = cu.columnIdentification("UserName",ips1);
           System.out.println(cells_uUserName);
           cellvalue_uUserName=cu.getCellFormattedStringValue(cells_uUserName.get(currentRowNum),workbook);
           
           cells_uPassword = cu.columnIdentification("Password",ips1);
           System.out.println(cells_uPassword);
           cellvalue_uPassword=cu.getCellFormattedStringValue(cells_uPassword.get(currentRowNum),workbook);
           
           cells_uFileName = cu.columnIdentification("UFTU_FileName",ips1);
           System.out.println(cells_uFileName);
           cellvalue_uFileName=cu.getCellFormattedStringValue(cells_uFileName.get(currentRowNum),workbook);
		
           cells_uContentType = cu.columnIdentification("UFTU_ContentType",ips1);
           System.out.println(cells_uContentType);
           cellvalue_uContentType=cu.getCellFormattedStringValue(cells_uContentType.get(currentRowNum),workbook);
           
           cells_uDocumenTtitle = cu.columnIdentification("UFTU_DocumentTitle",ips1);
           System.out.println(cells_uDocumenTtitle);
           cellvalue_uDocumentTitle=cu.getCellFormattedStringValue(cells_uDocumenTtitle.get(currentRowNum),workbook);
           
           cells_uDocumentAuthor = cu.columnIdentification("UFTU_DocumentAuthor",ips1);
           System.out.println(cells_uDocumentAuthor);
           cellvalue_uDocumentAuthor=cu.getCellFormattedStringValue(cells_uDocumentAuthor.get(currentRowNum),workbook);
           
           cells_uDocumentSecurityGroup = cu.columnIdentification("UFTU_DocumentSecurityGroup",ips1);
           System.out.println(cells_uDocumentSecurityGroup);
           cellvalue_uDocumentSecurityGroup=cu.getCellFormattedStringValue(cells_uDocumentSecurityGroup.get(currentRowNum),workbook);
           
           cells_uDocumentAccount = cu.columnIdentification("UFTU_DocumentAccount",ips1);
           System.out.println(cells_uDocumentAccount);
           cellvalue_uDocumentAccount=cu.getCellFormattedStringValue(cells_uDocumentAccount.get(currentRowNum),workbook);
           
           cells_uDocumentName = cu.columnIdentification("UFTU_DocumentName",ips1);
           System.out.println(cells_uDocumentName);
           cellvalue_uDocumentName=cu.getCellFormattedStringValue(cells_uDocumentName.get(currentRowNum),workbook);
           
           cells_uDocumentID = cu.columnIdentification("UFTU_DocumentId",ips1);
           System.out.println(cells_uDocumentID);
           cellvalue_uDocumentID=cu.getCellFormattedStringValue(cells_uDocumentID.get(currentRowNum),workbook);
           
           cells_ujobPackageName = cu.columnIdentification("SEJRLoader_jobPackageName",ips1);
           System.out.println(cells_ujobPackageName);
           cellvalue_ujobPackageName=cu.getCellFormattedStringValue(cells_ujobPackageName.get(currentRowNum),workbook);
           
           cells_ujobDefinitionName = cu.columnIdentification("SEJRLoader_jobDefinitionName",ips1);
           System.out.println(cells_ujobDefinitionName);
           cellvalue_ujobDefinitionName=cu.getCellFormattedStringValue(cells_ujobDefinitionName.get(currentRowNum),workbook);
           
           cells_uLoaderParamList1 = cu.columnIdentification("SEJRLoader_ParamList1",ips1);
           System.out.println(cells_uLoaderParamList1);
           cellvalue_uLoaderParamList1=cu.getCellFormattedStringValue(cells_uLoaderParamList1.get(currentRowNum),workbook);
           
           cells_uLoaderParamList2 = cu.columnIdentification("SEJRLoader_ParamList2",ips1);
           System.out.println(cells_uLoaderParamList2);
           cellvalue_uLoaderParamList2=cu.getCellFormattedStringValue(cells_uLoaderParamList2.get(currentRowNum),workbook);
           
           cells_uLoaderParamList3 = cu.columnIdentification("SEJRLoader_ParamList3",ips1);
           System.out.println(cells_uLoaderParamList3);
           cellvalue_uLoaderParamList3=cu.getCellFormattedStringValue(cells_uLoaderParamList3.get(currentRowNum),workbook);
           
           cells_uImportjobPackageName = cu.columnIdentification("SEJRRequestImport_jobPackageName",ips1);
           System.out.println(cells_uImportjobPackageName);
           cellvalue_uImportjobPackageName=cu.getCellFormattedStringValue(cells_uImportjobPackageName.get(currentRowNum),workbook);
           
           cells_uImportjobDefinitionName = cu.columnIdentification("SEJRRequestImport_jobDefinitionName",ips1);
           System.out.println(cells_uImportjobDefinitionName);
           cellvalue_uImportjobDefinitionName=cu.getCellFormattedStringValue(cells_uImportjobDefinitionName.get(currentRowNum),workbook);
           
           cells_uImportParamList1 = cu.columnIdentification("SEJRRequestImport_ParamList1",ips1);
           System.out.println(cells_uImportParamList1);
           cellvalue_uImportParamList1=cu.getCellFormattedStringValue(cells_uImportParamList1.get(currentRowNum),workbook);
           
           cells_uImportParamList2 = cu.columnIdentification("SEJRRequestImport_PL2_JournalSource",ips1);
           System.out.println(cells_uImportParamList2);
           cellvalue_uImportParamList2=cu.getCellFormattedStringValue(cells_uImportParamList2.get(currentRowNum),workbook);
           
           cells_uImportParamList3 = cu.columnIdentification("SEJRRequestImport_PL3_LedgerID",ips1);
           System.out.println(cells_uImportParamList3);
           cellvalue_uImportParamList3=cu.getCellFormattedStringValue(cells_uImportParamList3.get(currentRowNum),workbook);
           
           cells_uImportParamList4 = cu.columnIdentification("SEJRRequestImport_PL4_GroupID",ips1);
           System.out.println(cells_uImportParamList4);
           cellvalue_uImportParamList4=cu.getCellFormattedStringValue(cells_uImportParamList4.get(currentRowNum),workbook);
           
           cells_uImportParamList5 = cu.columnIdentification("SEJRRequestImport_ParamList5",ips1);
           System.out.println(cells_uImportParamList5);
           cellvalue_uImportParamList5=cu.getCellFormattedStringValue(cells_uImportParamList5.get(currentRowNum),workbook);
           
           cells_uImportParamList6 = cu.columnIdentification("SEJRRequestImport_ParamList6",ips1);
           System.out.println(cells_uImportParamList6);
           cellvalue_uImportParamList6=cu.getCellFormattedStringValue(cells_uImportParamList6.get(currentRowNum),workbook);
           
           cells_uImportParamList7 = cu.columnIdentification("SEJRRequestImport_ParamList7",ips1);
           System.out.println(cells_uImportParamList7);
           cellvalue_uImportParamList7=cu.getCellFormattedStringValue(cells_uImportParamList7.get(currentRowNum),workbook);
           
           }
           }
           test.log(Status.PASS, "Step 1: Data successfully imported");
           
           /*
            * Create authStr for username and password
            */
           
           String authStr = cellvalue_uUserName+":"+cellvalue_uPassword;
           
       //    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
          
           byte[] authBytes = authStr.getBytes("UTF-8");
            auth = Base64.encodeBase64String(authBytes);
       //    conn.setRequestProperty("Authorization", "Basic " + auth);
           System.out.println("Auth :"+ auth);
           
           
           /*
            * Code to call utilDecodeBase 
            */
           if(booleanstatus){
           ERP_utilDecodeBase eudb = new ERP_utilDecodeBase();
           try{
           encStr=eudb.utilDecodeBase(GIfile_fp);
           System.out.println("encstr"+encStr);
       	test.log(Status.PASS, "Step 2: Content converted to Base64");
           } catch (Exception e) {
   			// TODO Auto-generated catch block
        	   booleanstatus=false;
   			test.log(Status.FAIL, "Step 2: Content conversion to Base64 failed: " + e);
   			e.printStackTrace();
   			throw new Exception(e);
   		}

           }
           
           
		/* 
		 * Call uploadFiletoUCM
		 */
		
           if(booleanstatus){
		   ERP_uploadFileToUcm euftu = new ERP_uploadFileToUcm();
		  
			resultuploadFile=euftu.triggerUploadFileToUcm(auth,encStr,cellvalue_uFileName,cellvalue_uContentType,cellvalue_uDocumentTitle,cellvalue_uDocumentAuthor,cellvalue_uDocumentSecurityGroup,cellvalue_uDocumentAccount,cellvalue_uDocumentName,cellvalue_uDocumentID);
			if(resultuploadFile.contains("Error"))
			{
				booleanstatus=false;
				test.log(Status.FAIL, "Step 3: uploadFileToUcm Failed.Please contact Functional team:"+resultuploadFile);
				throw new Exception(resultuploadFile);
			}
			else
			{
			test.log(Status.PASS, "Step 3: uploadFileToUcm successful" );
			}
		} 
           

			/* 
			 * Call submitESSJobRequest-Loader
			 */
		   if(booleanstatus){
				ERP_submitESSJobRequest_Loader esejr = new ERP_submitESSJobRequest_Loader();
			
					   resultsubmitESSJobRequest_Loader=esejr.triggersubmitESSJobRequest_Loader(auth,resultuploadFile,cellvalue_ujobPackageName,cellvalue_ujobDefinitionName,cellvalue_uLoaderParamList1,cellvalue_uLoaderParamList2,cellvalue_uLoaderParamList3);
					   if(resultsubmitESSJobRequest_Loader.contains("Error"))
						{
							booleanstatus=false;
							test.log(Status.FAIL, "Step 4: Load File to Interface unsuccessful: ");
							throw new Exception(resultsubmitESSJobRequest_Loader);		
						}
					   else{
						   test.log(Status.PASS, "Step 4: Load File to Interface Successful" );
				
				}
		 }
		   
			   /* 
				 * Call ERP_getESSJobStatus-Loader
				 */
			//Thread.sleep(20000);
		   if(booleanstatus){
			
			   ERP_getESSJobStatus egejs = new ERP_getESSJobStatus();
				   
					   while(!resultgetESSJobStatus_Loader.equals("SUCCEEDED"))
					   {
					   resultgetESSJobStatus_Loader=egejs.triggergetESSJobStatus(auth,resultsubmitESSJobRequest_Loader);
					   Thread.sleep(20000);
					  
						if (resultgetESSJobStatus_Loader.contains("Error")) {
							booleanstatus=false;
							test.log(Status.FAIL,"Step 5: Load File to Interface Job resulted in Error, Check reason befor proceeding");
							throw new Exception(resultgetESSJobStatus_Loader);
						}
						 
				} 
					   test.log(Status.PASS, "Step 5: Load Import Job Status Successful" );
			}
							   /* 
					 * Call submitESSJobRequest-Request Import
					 */
		   if(booleanstatus){
					
				   ERP_submitESSJobRequest_RequestImport esejri = new ERP_submitESSJobRequest_RequestImport();

						   resultsubmitESSJobRequest_ReqImport=esejri.triggersubmitESSJobRequest_ReqImport(auth,cellvalue_uImportjobPackageName,cellvalue_uImportjobDefinitionName,cellvalue_uImportParamList1,cellvalue_uImportParamList2,cellvalue_uImportParamList3,cellvalue_uImportParamList4,cellvalue_uImportParamList5,cellvalue_uImportParamList6,cellvalue_uImportParamList7);
						   if(resultsubmitESSJobRequest_ReqImport.contains("Error")){
							   test.log(Status.FAIL, "Step 6: Journal Import Job execution unsuccessful: " );
								
								booleanstatus=false;
								throw new Exception(resultsubmitESSJobRequest_ReqImport);	
						   }
						   else   
						   {
							   test.log(Status.PASS, "Step 6: Journal Import Job executed" );
					}
					}
					
		   	
					   /* 
						 * Call ERP_getESSJobStatus-Request Import
						 */
					
		   			if(booleanstatus){
					   ERP_getESSJobStatus egejsi = new ERP_getESSJobStatus();
						  
							   while(!resultgetESSJobStatus_ReqImport.equals("SUCCEEDED"))
							   {
							   resultgetESSJobStatus_ReqImport=egejsi.triggergetESSJobStatus(auth,resultsubmitESSJobRequest_ReqImport);
							   Thread.sleep(20000);
								if (resultgetESSJobStatus_ReqImport.contains("Error")) {
									booleanstatus=false;
									test.log(Status.FAIL,"Step 7: Journal Import Job resulted in Error, Check reason befor proceeding");
									throw new Exception(resultgetESSJobStatus_ReqImport);
								}	
								
							   }
							   test.log(Status.PASS, "Step 7: Journal Import Job Status successful" );
					}
					
						/* 
							 * Call ERP_getESSJobStatus-Request Import
							 */
		   					if(booleanstatus){
						
						   ERP_downloadESSJobExecutionDetails edejed = new ERP_downloadESSJobExecutionDetails();
							   
								   resultsubmitESSJobRequest_ReqImport_New= Integer.parseInt(resultsubmitESSJobRequest_ReqImport)+1;
								   String resultsubmitESSJobRequest_ReqImport_New1=Integer.toString(resultsubmitESSJobRequest_ReqImport_New);
								   String retunrstatus=edejed.triggerdownloadESSJobExecutionDetails(auth,resultsubmitESSJobRequest_ReqImport_New1,resultsubmitESSJobRequest_ReqImport_New);
								   if (retunrstatus.contains("Error")) {
									   test.log(Status.FAIL, "Step 8: Download of Journal Import File unsuccessful" );
										booleanstatus=false;
										throw new Exception("Step 8: Download of Journal Import File unsuccessful");
								   }
								   else{
								   File f = new File("C:\\Automation_OCTS\\Output\\"+resultsubmitESSJobRequest_ReqImport_New+".zip");
								   if(f.exists()){
									   System.out.println("Output File exists");
									test.log(Status.PASS, "Step 8: Download of Journal Import File successful" );
									   }
									   else{
										   System.out.println("Output File not found!");
										test.log(Status.FAIL, "Step 8: Download of Journal Import File unsuccessful" );
										booleanstatus=false;
										throw new Exception("Output File not found");
									   }
								   }
							}
		 }
		catch(Exception e)
		{
			throw e;
		}
	 }
	 
			}
		
	 
	
	
