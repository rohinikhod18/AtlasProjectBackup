
package communicationapi;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the communicationapi package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final String URN_COMMUNICATION_API = "urn:CommunicationAPI";
    private static final  QName _SendASynchronousEmail_QNAME = new QName(URN_COMMUNICATION_API, "SendASynchronousEmail");
    private static final  QName _SendASynchronousEmailResponse_QNAME = new QName(URN_COMMUNICATION_API, "SendASynchronousEmailResponse");
    private static final  QName _SendASynchronousSMS_QNAME = new QName(URN_COMMUNICATION_API, "SendASynchronousSMS");
    private static final  QName _SendASynchronousSMSResponse_QNAME = new QName(URN_COMMUNICATION_API, "SendASynchronousSMSResponse");
    private static final  QName _SendSynchronousEmail_QNAME = new QName(URN_COMMUNICATION_API, "SendSynchronousEmail");
    private static final  QName _SendSynchronousEmailResponse_QNAME = new QName(URN_COMMUNICATION_API, "SendSynchronousEmailResponse");
    private static final  QName _SendSynchronousSMS_QNAME = new QName(URN_COMMUNICATION_API, "SendSynchronousSMS");
    private static final  QName _SendSynchronousSMSResponse_QNAME = new QName(URN_COMMUNICATION_API, "SendSynchronousSMSResponse");
    private static final  QName _SendBulkEmailWithTemplate_QNAME = new QName(URN_COMMUNICATION_API, "sendBulkEmailWithTemplate");
    private static final  QName _SendBulkEmailWithTemplateResponse_QNAME = new QName(URN_COMMUNICATION_API, "sendBulkEmailWithTemplateResponse");
    private static final  QName _SendBulkEmailWithoutTemplate_QNAME = new QName(URN_COMMUNICATION_API, "sendBulkEmailWithoutTemplate");
    private static final  QName _SendBulkEmailWithoutTemplateResponse_QNAME = new QName(URN_COMMUNICATION_API, "sendBulkEmailWithoutTemplateResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: communicationapi
     * 
     */
   
    /**
     * Create an instance of {@link SendASynchronousEmail }
     * 
     */
    public SendASynchronousEmail createSendASynchronousEmail() {
        return new SendASynchronousEmail();
    }

    /**
     * Create an instance of {@link SendASynchronousEmailResponse }
     * 
     */
    public SendASynchronousEmailResponse createSendASynchronousEmailResponse() {
        return new SendASynchronousEmailResponse();
    }

    /**
     * Create an instance of {@link SendASynchronousSMS }
     * 
     */
    public SendASynchronousSMS createSendASynchronousSMS() {
        return new SendASynchronousSMS();
    }

    /**
     * Create an instance of {@link SendASynchronousSMSResponse }
     * 
     */
    public SendASynchronousSMSResponse createSendASynchronousSMSResponse() {
        return new SendASynchronousSMSResponse();
    }

    /**
     * Create an instance of {@link SendSynchronousEmail }
     * 
     */
    public SendSynchronousEmail createSendSynchronousEmail() {
        return new SendSynchronousEmail();
    }

    /**
     * Create an instance of {@link SendSynchronousEmailResponse }
     * 
     */
    public SendSynchronousEmailResponse createSendSynchronousEmailResponse() {
        return new SendSynchronousEmailResponse();
    }

    /**
     * Create an instance of {@link SendSynchronousSMS }
     * 
     */
    public SendSynchronousSMS createSendSynchronousSMS() {
        return new SendSynchronousSMS();
    }

    /**
     * Create an instance of {@link SendSynchronousSMSResponse }
     * 
     */
    public SendSynchronousSMSResponse createSendSynchronousSMSResponse() {
        return new SendSynchronousSMSResponse();
    }

    /**
     * Create an instance of {@link SendBulkEmailWithTemplate }
     * 
     */
    public SendBulkEmailWithTemplate createSendBulkEmailWithTemplate() {
        return new SendBulkEmailWithTemplate();
    }

    /**
     * Create an instance of {@link SendBulkEmailWithTemplateResponse }
     * 
     */
    public SendBulkEmailWithTemplateResponse createSendBulkEmailWithTemplateResponse() {
        return new SendBulkEmailWithTemplateResponse();
    }

    /**
     * Create an instance of {@link SendBulkEmailWithoutTemplate }
     * 
     */
    public SendBulkEmailWithoutTemplate createSendBulkEmailWithoutTemplate() {
        return new SendBulkEmailWithoutTemplate();
    }

    /**
     * Create an instance of {@link SendBulkEmailWithoutTemplateResponse }
     * 
     */
    public SendBulkEmailWithoutTemplateResponse createSendBulkEmailWithoutTemplateResponse() {
        return new SendBulkEmailWithoutTemplateResponse();
    }

    /**
     * Create an instance of {@link RequestHeader }
     * 
     */
    public RequestHeader createRequestHeader() {
        return new RequestHeader();
    }

    /**
     * Create an instance of {@link EmailMessage }
     * 
     */
    public EmailMessage createEmailMessage() {
        return new EmailMessage();
    }

    /**
     * Create an instance of {@link Message }
     * 
     */
    public Message createMessage() {
        return new Message();
    }

    /**
     * Create an instance of {@link MessageAttributes }
     * 
     */
    public MessageAttributes createMessageAttributes() {
        return new MessageAttributes();
    }

    /**
     * Create an instance of {@link ResponseStatusHeader }
     * 
     */
    public ResponseStatusHeader createResponseStatusHeader() {
        return new ResponseStatusHeader();
    }

    /**
     * Create an instance of {@link SmsMessage }
     * 
     */
    public SmsMessage createSmsMessage() {
        return new SmsMessage();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendASynchronousEmail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = URN_COMMUNICATION_API, name = "SendASynchronousEmail")
    public JAXBElement<SendASynchronousEmail> createSendASynchronousEmail(SendASynchronousEmail value) {
        return new JAXBElement<>(_SendASynchronousEmail_QNAME, SendASynchronousEmail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendASynchronousEmailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = URN_COMMUNICATION_API, name = "SendASynchronousEmailResponse")
    public JAXBElement<SendASynchronousEmailResponse> createSendASynchronousEmailResponse(SendASynchronousEmailResponse value) {
        return new JAXBElement<>(_SendASynchronousEmailResponse_QNAME, SendASynchronousEmailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendASynchronousSMS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = URN_COMMUNICATION_API, name = "SendASynchronousSMS")
    public JAXBElement<SendASynchronousSMS> createSendASynchronousSMS(SendASynchronousSMS value) {
        return new JAXBElement<>(_SendASynchronousSMS_QNAME, SendASynchronousSMS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendASynchronousSMSResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = URN_COMMUNICATION_API, name = "SendASynchronousSMSResponse")
    public JAXBElement<SendASynchronousSMSResponse> createSendASynchronousSMSResponse(SendASynchronousSMSResponse value) {
        return new JAXBElement<>(_SendASynchronousSMSResponse_QNAME, SendASynchronousSMSResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendSynchronousEmail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = URN_COMMUNICATION_API, name = "SendSynchronousEmail")
    public JAXBElement<SendSynchronousEmail> createSendSynchronousEmail(SendSynchronousEmail value) {
        return new JAXBElement<>(_SendSynchronousEmail_QNAME, SendSynchronousEmail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendSynchronousEmailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = URN_COMMUNICATION_API, name = "SendSynchronousEmailResponse")
    public JAXBElement<SendSynchronousEmailResponse> createSendSynchronousEmailResponse(SendSynchronousEmailResponse value) {
        return new JAXBElement<>(_SendSynchronousEmailResponse_QNAME, SendSynchronousEmailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendSynchronousSMS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = URN_COMMUNICATION_API, name = "SendSynchronousSMS")
    public JAXBElement<SendSynchronousSMS> createSendSynchronousSMS(SendSynchronousSMS value) {
        return new JAXBElement<>(_SendSynchronousSMS_QNAME, SendSynchronousSMS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendSynchronousSMSResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = URN_COMMUNICATION_API, name = "SendSynchronousSMSResponse")
    public JAXBElement<SendSynchronousSMSResponse> createSendSynchronousSMSResponse(SendSynchronousSMSResponse value) {
        return new JAXBElement<>(_SendSynchronousSMSResponse_QNAME, SendSynchronousSMSResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendBulkEmailWithTemplate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = URN_COMMUNICATION_API, name = "sendBulkEmailWithTemplate")
    public JAXBElement<SendBulkEmailWithTemplate> createSendBulkEmailWithTemplate(SendBulkEmailWithTemplate value) {
        return new JAXBElement<>(_SendBulkEmailWithTemplate_QNAME, SendBulkEmailWithTemplate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendBulkEmailWithTemplateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = URN_COMMUNICATION_API, name = "sendBulkEmailWithTemplateResponse")
    public JAXBElement<SendBulkEmailWithTemplateResponse> createSendBulkEmailWithTemplateResponse(SendBulkEmailWithTemplateResponse value) {
        return new JAXBElement<>(_SendBulkEmailWithTemplateResponse_QNAME, SendBulkEmailWithTemplateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendBulkEmailWithoutTemplate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = URN_COMMUNICATION_API, name = "sendBulkEmailWithoutTemplate")
    public JAXBElement<SendBulkEmailWithoutTemplate> createSendBulkEmailWithoutTemplate(SendBulkEmailWithoutTemplate value) {
        return new JAXBElement<>(_SendBulkEmailWithoutTemplate_QNAME, SendBulkEmailWithoutTemplate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendBulkEmailWithoutTemplateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = URN_COMMUNICATION_API, name = "sendBulkEmailWithoutTemplateResponse")
    public JAXBElement<SendBulkEmailWithoutTemplateResponse> createSendBulkEmailWithoutTemplateResponse(SendBulkEmailWithoutTemplateResponse value) {
        return new JAXBElement<>(_SendBulkEmailWithoutTemplateResponse_QNAME, SendBulkEmailWithoutTemplateResponse.class, null, value);
    }

}
