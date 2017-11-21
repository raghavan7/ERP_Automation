/*===============================================================================================================================
        CLASS Name:    ERP_submitESSJobRequest_Loader
        CREATED BY:    Koushik Kadimcherla
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Method to trigger ERP_submitESSJobRequest_Loader webservice                    
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/

package OCTS_Financial_Webservice;
import javax.xml.soap.*;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ERP_submitESSJobRequest_Loader {

String resultsubmitESSJobRequest_Loader;

@SuppressWarnings({ "unused" })
public String triggersubmitESSJobRequest_Loader(String auth,String resultuploadFile, String cellvalue_ujobPackageName, String cellvalue_ujobDefinitionName, String cellvalue_uLoaderParamList1, String cellvalue_uLoaderParamList2, String cellvalue_uLoaderParamList3) throws Exception
{
    //try {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage soapmsg = factory.createMessage();
        SOAPPart soapPart = soapmsg.getSOAPPart();

         //   String authorization = "MjM1ODU5OldlbGNvbWUx";
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
        SOAPElement soapBodyElem = soapBody.addChildElement("submitESSJobRequest", myNamespace);
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("jobPackageName", myNamespace);
       // soapBodyElem1.addTextNode("/oracle/apps/ess/financials/commonModules/shared/common/interfaceLoader");
        soapBodyElem1.addTextNode(cellvalue_ujobPackageName);
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("jobDefinitionName", myNamespace);
        soapBodyElem2.addTextNode(cellvalue_ujobDefinitionName);
        SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("paramList", myNamespace);
        soapBodyElem3.addTextNode(cellvalue_uLoaderParamList1);
        SOAPElement soapBodyElem4 = soapBodyElem.addChildElement("paramList", myNamespace);
        soapBodyElem4.addTextNode(resultuploadFile);
        SOAPElement soapBodyElem5 = soapBodyElem.addChildElement("paramList", myNamespace);
        soapBodyElem5.addTextNode(cellvalue_uLoaderParamList1);
        SOAPElement soapBodyElem6 = soapBodyElem.addChildElement("paramList", myNamespace);
        soapBodyElem6.addTextNode(cellvalue_uLoaderParamList1);
       
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
            System.out.println(list.item(0).getTextContent());
            resultsubmitESSJobRequest_Loader=list.item(0).getTextContent();
            soapConnection.close();
        } catch (Exception e) {
        	resultsubmitESSJobRequest_Loader="submitESSJobRequest-Loader: Error occurred while sending SOAP Request to Server";
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
		return resultsubmitESSJobRequest_Loader;
    }



}