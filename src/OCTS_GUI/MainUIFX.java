/*===============================================================================================================================
        CLASS Name:    MainUIFX
        CREATED BY:    Raghavendran Ramasubramanian
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Main class to open the UI of the application                    
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/


package OCTS_GUI;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainUIFX extends Application {

	@SuppressWarnings("static-access")
	@Override
    public void start(Stage primaryStage) {
        
        
        // Create the FXMLLoader 
		FXMLLoader loader = new FXMLLoader();
		
        try{
           
           Parent root = loader.load(getClass().getResource("/SplashScreen.fxml"));
           
           // Create the Scene
       
            Scene scene = new Scene(root);
		
       // Set the Scene to the Stage
       primaryStage.setScene(scene);
		
       // Set the Title to the Stage
       primaryStage.setTitle("Oracle ERP Tool");
	
       //Disable resize option
       primaryStage.setResizable(false);
       
       // Display the Stage
       primaryStage.show();
       
       //Thread.sleep(7000);
       
       //primaryStage.getScene().getWindow().hide();
        }
        catch (Exception e)
        {
            System.out.println(e);
            primaryStage.close();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
public void stop(){
    Platform.exit();
    System.exit(0);
}
    
}
