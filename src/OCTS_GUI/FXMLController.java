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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

@SuppressWarnings("rawtypes")
public class FXMLController implements Initializable {

    String inputfile_fp;
    String GIfile_fp;
    String testModule;
    Task copyWorker;
    String newLine = "\n";
    String Item;
    
    
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
    TextField filePath1;
    
    @FXML
    TextField filePath2;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	//loadSplashScreen();
    	List<String> list = new ArrayList<String>();
        list.add("HCM->Employee");
        list.add("HCM->Benefit");
        list.add("HCM->Payroll");
        list.add("Finance->Accounts_Payable");
        list.add("Finance->Accounts_Receivable");
        list.add("Finance->General_Ledger");
        ObservableList<String> obList = FXCollections.observableList(list);
        comboBox.setItems(obList);
    }
    
    

    public void selectFilePath1(ActionEvent event) {
    	outputTextScreen.appendText(newLine);
    	fileButton1.setDisable(true);
    	FileChooser chooser = new FileChooser();
        File file = new File(System.getProperty("user.dir"));
        chooser.setInitialDirectory(file);
        file = chooser.showOpenDialog(fileButton1.getParent().getScene().getWindow());
        inputfile_fp = file.getPath();
        filePath1.setText(inputfile_fp);
        outputTextScreen.appendText("Test Excel Sheet Selected: "+inputfile_fp+newLine);
        fileButton1.setDisable(false);
        fileButton2.setDisable(false);  
    }
    
    public void selectFilePath2(ActionEvent event) {
    	fileButton2.setDisable(true);
    	FileChooser chooser = new FileChooser();
    	File file = new File(System.getProperty("user.dir"));
        chooser.setInitialDirectory(file);
        file = chooser.showOpenDialog(fileButton2.getParent().getScene().getWindow());
        GIfile_fp = file.getPath();
        filePath2.setText(GIfile_fp);
        outputTextScreen.appendText("Process Related Test Data Selected: "+GIfile_fp+newLine);
        fileButton2.setDisable(false); 
        executeWS.setDisable(false);
    }

    /**
     *
     * @param event
     */
    @FXML
    public void executeWSButton(ActionEvent event) {
    	outputTextScreen.appendText(newLine+"Webservice Execution for Process: "+Item+" Started"+newLine);
    	executeWS.setDisable(true);
    	progressBar.setProgress(0);
    	copyWorker = createWorker();
    	progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(copyWorker.progressProperty());
    	new Thread(copyWorker).start();
        executeWS.setDisable(false);
    }
   
   
    @FXML
    public void syncButton1(ActionEvent event) {
    	//TODO
    	fileButton1.setDisable(false);
    	outputTextScreen.appendText(newLine+"HCM Sync Complete"+newLine);
    }
    
    @FXML
    public void syncButton2(ActionEvent event) {
    	//TODO
    	fileButton1.setDisable(false);
    	outputTextScreen.appendText(newLine+"ERP Sync Complete"+newLine);
    }
    
    public void setCombo(){
    	 Item = comboBox.getValue();
    	 System.out.println(Item);
    	 outputTextScreen.appendText("Process Selected : "+Item+newLine);
    	 if(Item.contains("HCM"))
    	 {
    		sync1.setDisable(false);
    		sync2.setDisable(true);
    		if(Item.equals("HCM->Employee")){
    			testModule = "";
    		}
    		else if (Item.equals("HCM->Benefit")){
    			testModule = "";
    		}
    		else 
    		{
    			testModule = "";
    		}
    	 }
    	 else if (Item.contains("Finance"))
    	 {
    		 sync1.setDisable(true);
    		 sync2.setDisable(false);
    		 if(Item.equals("Finance->Accounts_Payable")){
     			testModule = "";
     		}
     		else if (Item.equals("Finance->Accounts_Receivable")){
     			testModule = "";
     		}
     		else 
     		{
     			testModule = "ERP_Financial_Webservice_MainClass";
     		}
    	 }
    }
    
    
	public Task createWorker() {
        return new Task() {
          @Override
          protected Object call() throws Exception {
        	  TestNG_Invoke_WS ws = new TestNG_Invoke_WS();
        	  String output = ws.wstriggertestng(inputfile_fp, GIfile_fp,testModule);
              while(output.contains("Completed"))
              {
            	  updateProgress(1.0,1.0);
            	  //progressBar.setAccessibleText("Completed");
            	  System.out.println(output);
            	  outputTextScreen.appendText("Webservice Execution for Process: "+Item+" Completed"+newLine);
                  outputTextScreen.appendText("Please check the output result generate at : C:/Automation_OCTS/Results"+newLine);
                  textOutput.setText("Test Execution Completed");
            	  return true;
              }
              return true;
          }
        };
      }
	
	/*public void loadSplashScreen() {
		try {
			StackPane pane = (StackPane) FXMLLoader.load(getClass().getResource("/SplashScreen.fxml"));
			root.getChildren().setAll(pane);
			FadeTransition 	fadeIn = new FadeTransition(Duration.seconds(3),pane);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);
			fadeIn.play();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
}