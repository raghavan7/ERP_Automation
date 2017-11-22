/*===============================================================================================================================
        CLASS Name:    ERP_FrontEnd_Template.java
        CREATED BY:    Koushik Kadimcherla
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Placeholder                    
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/

package OCTS_GUI;
import javax.swing.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import OCTS_Automation_Main_Modules.TestNG_Invoke_WS;
import Common_Utility.Details;

import java.awt.event.*;

import net.miginfocom.swing.MigLayout;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "unused" })
public class ERP_FrontEnd_UI extends JFrame {
/**
* 
*/
private static final long serialVersionUID = 1L;

String testModule;
private Image image = null;
private String Filepath;
String message;
String cmboName;
String cmboitem;
String fp;
String fp1;
String inputfile_fp;
String GIfile_fp;
Object cmboType;
String cmboTyp;
String sourcefile;
String cmboDrugName;

private JTextField textField_2;
    static JFrame frame;
    List<Details> all;
    List<Details> all_l;
    XSSFSheet ips1;
ArrayList<Cell> cells_sn;
ArrayList<Cell> cells_dn;
ArrayList<Cell> cells_jn;
FileInputStream file = null;
FileInputStream file_l = null;
FileInputStream file1 = null;
XSSFWorkbook workbook = null;
XSSFWorkbook workbook1 = null;
XSSFWorkbook workbook_l = null;
boolean boolFlag;
DefaultListModel<String> listModel;
File files;
JPanel jp1;
JPanel jp2;
JPanel jp3;
String filenames = "";
XSSFSheet ips_l; 
private JTextField textField;
private JTextField textField_1;
File thedir;
File thedir1;
File thedir2;
File thedir3;

    public  ERP_FrontEnd_UI() throws IOException {
        
         //This will create the title you see in the upper left of the window    
        setTitle("ERP Automation");  
        
        setSize(621,376); //set size so the user can "see" it
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int iCoordX = (objDimension.width - this.getWidth()) / 2;
        int iCoordY = (objDimension.height - this.getHeight()) / 2;
        this.setLocation(iCoordX, iCoordY); 
        //Here we are creating the object
     //   JTabbedPane jtp = new JTabbedPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //This creates the template on the windowed application that we will be using
     //  getContentPane().add(jtp);
       
      //  jp1 = new JPanel();//This will create the first tab

        //jp2 = new JPanel();//This will create the second tab
       // jp3 = new JPanel();//This will create the third tab
      getContentPane().setLayout(new MigLayout("", "[59px][][100px:n:100px,grow][100px:n:160px,grow,left][75px:n:75px,grow][]", "[14px][][25px:n:25px][][][][][]"));

       //This adds the first tab to our tabbed pane object and names it
      // jtp.addTab("ERP Automation", jp1);
   //    all = new ArrayList<Details>();
     //  all_l = new ArrayList<Details>();
      this.image = new ImageIcon("C:\\Users\\koushik\\Pictures\\2015-05-13\\test.JPG").getImage();
      
       JLabel lblSourceFile = new JLabel("INPUT DATA FILE");
       lblSourceFile.setFont(new Font("Tahoma", Font.BOLD, 11));
       getContentPane().add(lblSourceFile, "cell 2 1,alignx left");
       
       textField = new JTextField();
       textField.setEnabled(false);
       getContentPane().add(textField, "cell 3 1,growx");
       textField.setColumns(10);
       
       listModel = new DefaultListModel<String>();
       
       JButton button_1 = new JButton("Browse File");
       button_1.addActionListener(new ActionListener() {
       	public void actionPerformed(ActionEvent e) {
       		thedir = new File("C:\\Automation_OCTS");
       		thedir1 = new File("C:\\Automation_OCTS\\Data");
       		thedir2 = new File("C:\\Automation_OCTS\\Output");
       		thedir3 = new File("C:\\Automation_OCTS\\Results");
       		if(!thedir.exists() && !thedir1.exists() && !thedir2.exists() && !thedir3.exists())
       		{
       			boolean result = false;
       			
       			try
       			{
       				thedir.mkdir();
       				thedir1.mkdir();
       				thedir2.mkdir();
       				thedir3.mkdir();
       				result=true;
       			}
       			catch(SecurityException se)
       			{
       				//handle it
       				se.printStackTrace();
       			}
       			if(result)
       			{
       				System.out.println("Base directory created");
       			}
       		}
       		//JFileChooser Filechoose=new JFileChooser("C:\\Users\\koushik\\Documents\\Oracle Fusion");
       		JFileChooser Filechoose=new JFileChooser("C:\\Automation_OCTS\\Data");
       		Filechoose.setMultiSelectionEnabled(true);
       		int retval=Filechoose.showOpenDialog(null);
       		                if (retval == JFileChooser.APPROVE_OPTION) {
       		                    //... The user selected a file, get it, use it.
       		                     files = Filechoose.getSelectedFile();
       		           
       		                   Filepath = files.getPath();
       		                   inputfile_fp = Filepath.replaceAll("\\\\", "\\\\\\\\");
       		            System.out.println(inputfile_fp);
       		       
       		         textField.setText(inputfile_fp);              
       		                }
       	
       	}
       });
       getContentPane().add(button_1, "cell 4 1");
       
       JLabel lblGiInterfaceFile = new JLabel("GI INTERFACE FILE");
       lblGiInterfaceFile.setFont(new Font("Tahoma", Font.BOLD, 11));
       getContentPane().add(lblGiInterfaceFile, "cell 2 2,alignx trailing");
       
       textField_1 = new JTextField();
       textField_1.setEnabled(false);
       textField_1.setColumns(10);
       getContentPane().add(textField_1, "cell 3 2,growx");
       
       JButton button = new JButton("Browse File");
       button.addActionListener(new ActionListener() {
       	public void actionPerformed(ActionEvent e) {
       	//JFileChooser Filechoose=new JFileChooser("C:\\Users\\koushik\\Documents\\Oracle Fusion");
       		JFileChooser Filechoose=new JFileChooser("C:\\Automation_OCTS\\Data");
       		Filechoose.setMultiSelectionEnabled(true);
       		int retval=Filechoose.showOpenDialog(null);
       		                if (retval == JFileChooser.APPROVE_OPTION) {
       		                    //... The user selected a file, get it, use it.
       		                     files = Filechoose.getSelectedFile();
       		           
       		                   Filepath = files.getPath();
       		                   GIfile_fp = Filepath.replaceAll("\\\\", "\\\\\\\\");
       		            System.out.println(GIfile_fp);
       		       
       		         textField_1.setText(GIfile_fp);              
       		                }
       	}
       });
       getContentPane().add(button, "cell 4 2");
    

       
       JButton btnNewButton_2 = new JButton("EXECUTE WEBSERVICES");
       btnNewButton_2.addActionListener(new ActionListener() {
       	public void actionPerformed(ActionEvent e) {
       		if(e.getSource()==btnNewButton_2)
       		{
       			
       			//ERP_Webservice_MainDriver_Script ems = new ERP_Webservice_MainDriver_Script();
       			TestNG_Invoke_WS wttn = new TestNG_Invoke_WS();
       			try {
					//ems.ERPDriver(inputfile_fp,GIfile_fp);
       				testModule = "ERP_Financial_Webservice_MainClass";
       				wttn.wstriggertestng(inputfile_fp, GIfile_fp,testModule);
       				
       			/*	//call Webservice Jar file
       				ProcessBuilder pb = new ProcessBuilder("java", "-jar", "C:\\Users\\koushik\\Documents\\Triggerwebservice.jar "+ inputfile_fp + GIfile_fp ,">>" ,"C:\\Users\\koushik\\Documents\\log.txt");
       				Process p = pb.start();
       				*/
       				
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
       		}
       	}
       });
       getContentPane().add(btnNewButton_2, "cell 3 3,alignx center");
             
      
        setVisible(true); //otherwise you won't "see" it 
        
        
           }
    
    
    public static void main (String []args) throws IOException{
    ERP_FrontEnd_UI tab = new ERP_FrontEnd_UI();
       
       
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
        /*
		   frame = new JFrame();
		  
			frame.setBounds(100, 100, 450, 300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
		
	}

}



