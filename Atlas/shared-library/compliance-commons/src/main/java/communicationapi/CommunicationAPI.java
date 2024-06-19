package communicationapi;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.0
 * 2015-10-08T14:41:21.065+05:30
 * Generated source version: 3.1.0
 * 
 */
@WebService(targetNamespace = "urn:CommunicationAPI", name = "CommunicationAPI")
@XmlSeeAlso({ObjectFactory.class})
public interface CommunicationAPI {

    @RequestWrapper(localName = "sendBulkEmailWithoutTemplate", targetNamespace = "urn:CommunicationAPI", className = "communicationapi.SendBulkEmailWithoutTemplate")
    @WebMethod
    @ResponseWrapper(localName = "sendBulkEmailWithoutTemplateResponse", targetNamespace = "urn:CommunicationAPI", className = "communicationapi.SendBulkEmailWithoutTemplateResponse")
    public void sendBulkEmailWithoutTemplate(
        @WebParam(name = "RequestHeader", targetNamespace = "")
        communicationapi.RequestHeader requestHeader,
        @WebParam(name = "Message", targetNamespace = "")
        communicationapi.EmailMessage message,
        @WebParam(mode = WebParam.Mode.OUT, name = "IsSent", targetNamespace = "")
        javax.xml.ws.Holder<java.lang.Boolean> isSent,
        @WebParam(mode = WebParam.Mode.OUT, name = "ResponseStatusHeader", targetNamespace = "")
        javax.xml.ws.Holder<ResponseStatusHeader> responseStatusHeader
    );

    @RequestWrapper(localName = "sendBulkEmailWithTemplate", targetNamespace = "urn:CommunicationAPI", className = "communicationapi.SendBulkEmailWithTemplate")
    @WebMethod
    @ResponseWrapper(localName = "sendBulkEmailWithTemplateResponse", targetNamespace = "urn:CommunicationAPI", className = "communicationapi.SendBulkEmailWithTemplateResponse")
    public void sendBulkEmailWithTemplate(
        @WebParam(name = "RequestHeader", targetNamespace = "")
        communicationapi.RequestHeader requestHeader,
        @WebParam(name = "Message", targetNamespace = "")
        communicationapi.EmailMessage message,
        @WebParam(mode = WebParam.Mode.OUT, name = "IsSent", targetNamespace = "")
        javax.xml.ws.Holder<java.lang.Boolean> isSent,
        @WebParam(mode = WebParam.Mode.OUT, name = "ResponseStatusHeader", targetNamespace = "")
        javax.xml.ws.Holder<ResponseStatusHeader> responseStatusHeader
    );

    @RequestWrapper(localName = "SendASynchronousSMS", targetNamespace = "urn:CommunicationAPI", className = "communicationapi.SendASynchronousSMS")
    @WebMethod(operationName = "SendASynchronousSMS")
    @ResponseWrapper(localName = "SendASynchronousSMSResponse", targetNamespace = "urn:CommunicationAPI", className = "communicationapi.SendASynchronousSMSResponse")
    public void sendASynchronousSMS(
        @WebParam(name = "RequestHeader", targetNamespace = "")
        communicationapi.RequestHeader requestHeader,
        @WebParam(name = "Message", targetNamespace = "")
        communicationapi.SmsMessage message,
        @WebParam(mode = WebParam.Mode.OUT, name = "IsSent", targetNamespace = "")
        javax.xml.ws.Holder<java.lang.Boolean> isSent,
        @WebParam(mode = WebParam.Mode.OUT, name = "ResponseStatusHeader", targetNamespace = "")
        javax.xml.ws.Holder<ResponseStatusHeader> responseStatusHeader
    );

    @RequestWrapper(localName = "SendASynchronousEmail", targetNamespace = "urn:CommunicationAPI", className = "communicationapi.SendASynchronousEmail")
    @WebMethod(operationName = "SendASynchronousEmail")
    @ResponseWrapper(localName = "SendASynchronousEmailResponse", targetNamespace = "urn:CommunicationAPI", className = "communicationapi.SendASynchronousEmailResponse")
    public void sendASynchronousEmail(
        @WebParam(name = "RequestHeader", targetNamespace = "")
        communicationapi.RequestHeader requestHeader,
        @WebParam(name = "Message", targetNamespace = "")
        communicationapi.EmailMessage message,
        @WebParam(mode = WebParam.Mode.OUT, name = "IsSent", targetNamespace = "")
        javax.xml.ws.Holder<java.lang.Boolean> isSent,
        @WebParam(mode = WebParam.Mode.OUT, name = "ResponseStatusHeader", targetNamespace = "")
        javax.xml.ws.Holder<ResponseStatusHeader> responseStatusHeader
    );

    @RequestWrapper(localName = "SendSynchronousEmail", targetNamespace = "urn:CommunicationAPI", className = "communicationapi.SendSynchronousEmail")
    @WebMethod(operationName = "SendSynchronousEmail")
    @ResponseWrapper(localName = "SendSynchronousEmailResponse", targetNamespace = "urn:CommunicationAPI", className = "communicationapi.SendSynchronousEmailResponse")
    public void sendSynchronousEmail(
        @WebParam(name = "RequestHeader", targetNamespace = "")
        communicationapi.RequestHeader requestHeader,
        @WebParam(name = "Message", targetNamespace = "")
        communicationapi.EmailMessage message,
        @WebParam(mode = WebParam.Mode.OUT, name = "IsSent", targetNamespace = "")
        javax.xml.ws.Holder<java.lang.Boolean> isSent,
        @WebParam(mode = WebParam.Mode.OUT, name = "ResponseStatusHeader", targetNamespace = "")
        javax.xml.ws.Holder<ResponseStatusHeader> responseStatusHeader
    );

    @RequestWrapper(localName = "SendSynchronousSMS", targetNamespace = "urn:CommunicationAPI", className = "communicationapi.SendSynchronousSMS")
    @WebMethod(operationName = "SendSynchronousSMS")
    @ResponseWrapper(localName = "SendSynchronousSMSResponse", targetNamespace = "urn:CommunicationAPI", className = "communicationapi.SendSynchronousSMSResponse")
    public void sendSynchronousSMS(
        @WebParam(name = "RequestHeader", targetNamespace = "")
        communicationapi.RequestHeader requestHeader,
        @WebParam(name = "Message", targetNamespace = "")
        communicationapi.SmsMessage message,
        @WebParam(mode = WebParam.Mode.OUT, name = "IsSent", targetNamespace = "")
        javax.xml.ws.Holder<java.lang.Boolean> isSent,
        @WebParam(mode = WebParam.Mode.OUT, name = "ResponseStatusHeader", targetNamespace = "")
        javax.xml.ws.Holder<ResponseStatusHeader> responseStatusHeader
    );
}
