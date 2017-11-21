/*===============================================================================================================================
        CLASS Name:    ERP_downloadESSJobExecutionDetails
        CREATED BY:    Koushik Kadimcherla
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Method to trigger ERP_downloadESSJobExecutionDetails webservice                    
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/
package OCTS_Financial_Webservice;

import java.io.FileOutputStream;
import javax.activation.DataHandler;
import javax.xml.soap.*;
import java.util.Iterator;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ERP_downloadESSJobExecutionDetails {

String resultdownloadESSJobExecutionDetails;

@SuppressWarnings({ "unused", "rawtypes" })
public String triggerdownloadESSJobExecutionDetails(String auth,String resultgetESSJobStatus_ReqImport,int resultsubmitESSJobRequest_ReqImport_New) throws Exception
{
    //try {
	String returnstatus = null;
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage soapmsg = factory.createMessage();
        SOAPPart soapPart = soapmsg.getSOAPPart();

          //  String authorization = "MjM1ODU5OldlbGNvbWUx";
        String authorization = auth;
            MimeHeaders hd = soapmsg.getMimeHeaders();
            hd.addHeader("Authorization", "Basic " + authorization);

        String myNamespaceURI = "http://xmlns.oracle.com/apps/financials/commonModules/shared/model/erpIntegrationService/types/";
        String myNamespace = "typ";
        String myNamespaceURI1 = "http://xmlns.oracle.com/apps/financials/commonModules/shared/model/erpIntegrationService/";
        String myNamespace1 = "erp";
        String destination = "https://ecqg-test.fin.us2.oraclecloud.com:443/publicFinancialCommonErpIntegration/ErpIntegrationService";



        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
        envelope.addNamespaceDeclaration(myNamespace1, myNamespaceURI1);
        System.out.println("\n");
        SOAPHeader header = envelope.getHeader();
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("downloadESSJobExecutionDetails", myNamespace);
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("requestId", myNamespace);       
        soapBodyElem1.addTextNode(resultgetESSJobStatus_ReqImport);
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("fileType", myNamespace);     
        soapBodyElem2.addTextNode("Out");
       
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(soapmsg,destination);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();
            
       
            Document document = soapResponse.getSOAPBody().extractContentAsDocument();
            NodeList list = document.getChildNodes();
         //   System.out.println(list.item(0).getTextContent());
              
            System.out.println(soapResponse.countAttachments()+"count");
            Iterator attachmentsIterator = soapResponse.getAttachments();
            while (attachmentsIterator.hasNext()) {
                AttachmentPart attachment = (AttachmentPart) attachmentsIterator.next();
             //   System.out.println("ATTACHMENT base 64=" + attachment.getBase64Content());
                DataHandler dh = attachment.getDataHandler();
            /*    System.out.println("ATTACHMENT CONTENT ID=" + attachment.getContentId());
                System.out.println("ATTACHMENT CONTENT LOCATION="+ attachment.getContentLocation());
                System.out.println("ATTACHMENT CONTENT ="+ attachment.getContent());
                System.out.println("ATTACHMENT TO_STRING ="+ attachment.toString());*/
                FileOutputStream fileStream = null;
             

                fileStream = new FileOutputStream("C:\\Automation_OCTS\\Output\\"+resultsubmitESSJobRequest_ReqImport_New+".zip");

                dh.writeTo(fileStream);

                fileStream.flush();
                returnstatus="true";
            }
            
            soapConnection.close();
        } catch (Exception e) {
        	 returnstatus ="DownloadESSJobExcutionDetails:Error occurred while sending SOAP Request to Server";
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
		return returnstatus;
    }



}