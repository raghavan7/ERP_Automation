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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SplashFXMLController implements Initializable {

	@FXML
	AnchorPane rootPane1;
   
	Parent root;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         new SplashScreen().start();
    }
    
    class SplashScreen extends Thread{
    	@Override
    	public void run() {
    			try {
					Thread.sleep(9400);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						
						root = FXMLLoader.load(getClass().getResource("/MainUIFX.fxml"));
					} catch (IOException  e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        // Create the Scene
			   
			        Scene scene = new Scene(root);
				
			        Stage stage = new Stage();
			        // Set the Scene to the Stage
			        stage.setScene(scene);
				
			        // Set the Title to the Stage
			        stage.setTitle("Oracle ERP Tool");
			
			        //Disable resize option
			        stage.setResizable(false);
			   
			        // Display the Stage
			        stage.show();
			   
			        //Hide the Splash Screen
			        rootPane1.getScene().getWindow().hide();	        
				}
			});
    		
    	}
    }
    
    
}