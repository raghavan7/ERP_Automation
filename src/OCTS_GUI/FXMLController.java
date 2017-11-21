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
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import OCTS_Automation_Main_Modules.TestNG_Invoke_Financial_WS;

public class FXMLController implements Initializable {

    String inputfile_fp;
    String GIfile_fp;
    
    
    @FXML 
    ComboBox<String> comboBox;
    
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
    Button sync;
    
    @FXML
    TextField filePath1;
    
    @FXML
    TextField filePath2;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    	List<String> list = new ArrayList<String>();
        list.add("HCM->Employee");
        list.add("HCM->Benefit");
        list.add("HCM->Payroll");
        list.add("Finance->Acct_Payable_Auto_Invc");
        list.add("Finance->Acct_Receive_Auto_Invc");
        list.add("Finance->Gnrl_Ldgr_Jrnl_Entry");
        ObservableList<String> obList = FXCollections.observableList(list);
        comboBox.setItems(obList);
    }
    
    

    public void selectFilePath1(ActionEvent event) {
    	FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(fileButton1.getParent().getScene().getWindow());
        inputfile_fp = file.getPath();
        filePath1.setText(inputfile_fp);
        
    }
    
    public void selectFilePath2(ActionEvent event) {
    	FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(fileButton2.getParent().getScene().getWindow());
        GIfile_fp = file.getPath();
        filePath2.setText(GIfile_fp);
    }

    /**
     *
     * @param event
     */
    @FXML
    public void executeWSButton(ActionEvent event) {
    	TestNG_Invoke_Financial_WS ws = new TestNG_Invoke_Financial_WS();
        ws.wstriggertestng(inputfile_fp, GIfile_fp);
    }
   
    @FXML
    public void validateButton(ActionEvent event) {
    	//TODO
    }
    
    @FXML
    public void syncButton(ActionEvent event) {
    	//TODO
    }
    
    public void setCombo(){
    	 String Item = comboBox.getValue();
    	 System.out.println(Item);
    	 if(Item.contains("HCM"))
    	 {
    		sync.setDisable(false);
    		fileButton1.setDisable(false);
    		fileButton2.setDisable(false);
    		executeWS.setDisable(false);
    		validate.setDisable(false);
    	 }
    	 else
    	 {
    		 sync.setDisable(true);
    		fileButton1.setDisable(false);
    		fileButton2.setDisable(false);
    		executeWS.setDisable(false);
    		 validate.setDisable(false);
    	 }
    }
}
