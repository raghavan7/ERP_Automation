/*===============================================================================================================================
        CLASS Name:    FXMLController
        CREATED BY:    Raghavendran Ramasubramanian
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Controller to Run and handle inputs from UI                    
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/

package OCTS_GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Common_Utility.CommonUtilFunctions;
import Common_Utility.CompareExcel;
import Common_Utility.ReporterBaseTest;
import Common_Utility.TextToExcel;
import Common_Utility.XmlToExcelConverter;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import OCTS_Automation_Main_Modules.TestNG_Invoke_WS;
import OCTS_HCM_Webservices.ERP_executeXMLQuery;
import OCTS_HCM_Webservices.ERP_getSessionID;

@SuppressWarnings({ "rawtypes", "deprecation" })
public class FXMLController implements Initializable {

	String inputfile_fp;
	String GIfile_fp;
	String testModule;
	Task copyWorker;
	String newLine = "\n";
	String Item;
	String sessionID;
	String xml;
	String rows;
	String[] ar;
	String txt2excel;
	String File1;
	String cmpxls;
	FileInputStream file = null;
	XSSFWorkbook workbook;

	@FXML
	AnchorPane root;

	@FXML
	Text textOutput;

	@FXML
	ComboBox<String> comboBox;

	@FXML
	ProgressBar progressBar;

	@FXML
	TextArea outputTextScreen;

	@FXML
	ScrollPane outputScreen;

	@FXML
	Button syncButton;

	@FXML
	Button fileButton1;

	@FXML
	Button fileButton2;

	@FXML
	Button executeWS;

	@FXML
	Button validate;

	@FXML
	Button sync1;

	@FXML
	Button sync2;

	@FXML
	Button validSync;

	@FXML
	TextField filePath1;

	@FXML
	TextField filePath2;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		List<String> list = new ArrayList<String>();
		list.add("HCM->Employee");
		list.add("HCM->Benefit");
		list.add("HCM->Payroll");
		list.add("Finance->Accounts_Payable");
		list.add("Finance->Accounts_Receivable");
		list.add("Finance->General_Ledger");
		ObservableList<String> obList = FXCollections.observableList(list);
		comboBox.setItems(obList);
		ReporterBaseTest initFolder = new ReporterBaseTest();
		initFolder.folderCreate();
	}

	public void setCombo() {
		Item = comboBox.getValue();
		System.out.println(Item);
		outputTextScreen.appendText("Process Selected : " + Item + newLine);
		if (Item.contains("HCM")) {
			sync1.setDisable(false);
			sync2.setDisable(true);
			if (Item.equals("HCM->Employee")) {
				testModule = "";
			} else if (Item.equals("HCM->Benefit")) {
				testModule = "";
			} else {
				testModule = "";
			}
		} else if (Item.contains("Finance")) {
			sync1.setDisable(true);
			sync2.setDisable(false);
			if (Item.equals("Finance->Accounts_Payable")) {
				testModule = "";
			} else if (Item.equals("Finance->Accounts_Receivable")) {
				testModule = "";
			} else {
				testModule = "ERP_Financial_Webservice_MainClass";
			}
		}
	}

	@FXML
	public void syncButton1(ActionEvent event) {
		ERP_getSessionID sessID = new ERP_getSessionID();
		ERP_executeXMLQuery getXML = new ERP_executeXMLQuery();
		XmlToExcelConverter conv = new XmlToExcelConverter();
        TextToExcel tte = new TextToExcel();
        CompareExcel ce = new CompareExcel();
		try {
			File1 = chooseFile();
			if (!(File1 == null))
			{
			ar = readExcel();
			sessionID = sessID.getSessionID(ar[0], ar[1]);
			xml = getXML.getXMLQuery(sessionID);
			xml = xml.substring(0, xml.length() - 11);
			FileUtils.writeStringToFile(new File("C:\\Automation_OCTS\\Data\\HCM_Employee_Output.xml"), xml);
			rows = conv.getAndReadXml(xml);
			txt2excel = tte.readTextFile(File1);
			cmpxls = ce.readXLSXFile();
			}
			else
			{
				throw new Exception("No Input Test File has been selected, Please click on Sync HCM button again to select a File");
			}
		} catch (Exception e) {
			e.printStackTrace();
			outputTextScreen.appendText(newLine + e + newLine);
		}
		if (sessionID.contains("Error") || xml.contains("Error") || rows.contains("Error") || rows.contains("0 Rows")|| txt2excel.contains("Error") || cmpxls.contains("Error")) {
			outputTextScreen.appendText(newLine + "Session ID: " + sessionID + newLine);
			outputTextScreen.appendText(newLine + "HCM Sync xml: " +xml + newLine);
			outputTextScreen.appendText(newLine + "Xml To Excel: " +rows + newLine);
			outputTextScreen.appendText(newLine + txt2excel + newLine);
			outputTextScreen.appendText(newLine + cmpxls + newLine);
			outputTextScreen.appendText(newLine + "HCM Sync Failed" + newLine);
		} else {
			outputTextScreen.appendText(newLine + rows + newLine);
			outputTextScreen.appendText(newLine + txt2excel + newLine);
			outputTextScreen.appendText(newLine + cmpxls + newLine);
			outputTextScreen.appendText("HCM Sync Complete" + newLine);
			validSync.setDisable(false);
		}

	}

	@FXML
	public void syncButton2(ActionEvent event) {
		validSync.setDisable(false);
		outputTextScreen.appendText(newLine + "ERP Sync Complete" + newLine);
	}

	@FXML
	public void validButton(ActionEvent event) {
		// TODO
		fileButton1.setDisable(false);
		outputTextScreen.appendText(newLine + "Sync Validation Complete" + newLine);
	}

	public void selectFilePath1(ActionEvent event) {
		outputTextScreen.appendText(newLine);
		fileButton1.setDisable(true);
		FileChooser chooser = new FileChooser();
		File file = new File("C:\\Automation_OCTS\\");
		chooser.setInitialDirectory(file);
		file = chooser.showOpenDialog(fileButton1.getParent().getScene().getWindow());
		inputfile_fp = file.getPath();
		filePath1.setText(inputfile_fp);
		outputTextScreen.appendText("Test Excel Sheet Selected: " + inputfile_fp + newLine);
		fileButton1.setDisable(false);
		fileButton2.setDisable(false);
	}

	public void selectFilePath2(ActionEvent event) {
		fileButton2.setDisable(true);
		FileChooser chooser = new FileChooser();
		File file = new File("C:\\Automation_OCTS\\");
		chooser.setInitialDirectory(file);
		file = chooser.showOpenDialog(fileButton2.getParent().getScene().getWindow());
		GIfile_fp = file.getPath();
		filePath2.setText(GIfile_fp);
		outputTextScreen.appendText("Process Related Test Data Selected: " + GIfile_fp + newLine);
		fileButton2.setDisable(false);
		executeWS.setDisable(false);
	}

	/**
	 *
	 * @param event
	 */
	@FXML
	public void executeWSButton(ActionEvent event) {
		outputTextScreen.appendText(newLine + "Webservice Execution for Process: " + Item + " Started" + newLine);
		executeWS.setDisable(true);
		progressBar.setProgress(0);
		copyWorker = createWorker();
		progressBar.progressProperty().unbind();
		progressBar.progressProperty().bind(copyWorker.progressProperty());
		new Thread(copyWorker).start();
		executeWS.setDisable(false);
	}

	public Task createWorker() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				TestNG_Invoke_WS ws = new TestNG_Invoke_WS();
				String output = ws.wstriggertestng(inputfile_fp, GIfile_fp, testModule);
				while (output.contains("Completed")) {
					updateProgress(1.0, 1.0);
					// progressBar.setAccessibleText("Completed");
					System.out.println(output);
					outputTextScreen.appendText("Webservice Execution for Process: " + Item + " Completed" + newLine);
					outputTextScreen.appendText(
							"Please check the output result generate at : C:/Automation_OCTS/Results" + newLine);
					textOutput.setText("Test Execution Completed");
					return true;
				}
				return true;
			}
		};
	}

	public String[] readExcel() throws Exception {
		try {
			ar = new String[2];
			file = new FileInputStream(new File("C:\\Automation_OCTS\\Data\\ERP_InputDatasheet.xlsx"));
			workbook = new XSSFWorkbook(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		CommonUtilFunctions cu = new CommonUtilFunctions();
		XSSFSheet ips1 = workbook.getSheetAt(0);
		for (int currentColumnNum = 0; currentColumnNum < 1; currentColumnNum++) {
			for (int currentRowNum = ips1.getFirstRowNum() + 1; currentRowNum <= ips1
					.getLastRowNum(); currentRowNum++) {
				System.out.println("RowNum" + currentRowNum);

				ArrayList<Cell> cells_uUserName = cu.columnIdentification("UserName", ips1);
				System.out.println(cells_uUserName);
				ar[0] = cu.getCellFormattedStringValue(cells_uUserName.get(currentRowNum), workbook);

				ArrayList<Cell> cells_uPassword = cu.columnIdentification("Password", ips1);
				System.out.println(cells_uPassword);
				ar[1] = cu.getCellFormattedStringValue(cells_uPassword.get(currentRowNum), workbook);

			}
		}
		return ar;
	}
	
	public String chooseFile() {
		FileChooser chooser = new FileChooser();
		File file = new File("C:\\Automation_OCTS\\");
		chooser.setInitialDirectory(file);
		file = chooser.showOpenDialog(fileButton1.getParent().getScene().getWindow());
		
		if (file == null)
		{
			return null;
		}
		else
		{
			File1 = file.getPath();
			return File1;	
		}
		
		
	}
}