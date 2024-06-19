
package communicationapi;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlTransient;


/**
 * <p>Java class for emailMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="emailMessage"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{urn:CommunicationAPI}message"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Attributes" type="{urn:CommunicationAPI}messageAttributes" maxOccurs="unbounded"/&gt;
 *         &lt;element name="EmailAttachments" type="{http://www.w3.org/2001/XMLSchema}base64Binary" maxOccurs="unbounded"/&gt;
 *         &lt;element name="AttachmentFileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "emailMessage", propOrder = {
    "attributes",
    "emailAttachments",
    "attachmentFileName"
})
public class EmailMessage
extends Message implements Serializable 
{

    @XmlElement(name = "Attributes", required = true)
    protected List<MessageAttributes> attributes; //NOSONAR
    @XmlElement(name = "EmailAttachments", required = true)
    @XmlMimeType("application/octet-stream")
    protected List<DataHandler> emailAttachments;//NOSONAR
    @XmlElement(name = "AttachmentFileName")
    protected String attachmentFileName;
    @XmlTransient
    protected DataHandler[] attachments;//NOSONAR


    /**
     * Gets the value of the attributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageAttributes }
     * 
     * 
     */
    public List<MessageAttributes> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<>();
        }
        return this.attributes;
    }
    

    /**
     * Gets the value of the emailAttachments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the emailAttachments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmailAttachments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataHandler }
     * 
     * 
     */
    public List<DataHandler> getEmailAttachments() {
        if (emailAttachments == null) {
            emailAttachments = new ArrayList<>();
        }
        return this.emailAttachments;
    }

    /**
     * Gets the value of the attachmentFileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttachmentFileName() {
        return attachmentFileName;
    }

    /**
     * Sets the value of the attachmentFileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttachmentFileName(String value) {
        this.attachmentFileName = value;
    }
    public void setAttributes(List<MessageAttributes> attributeList) {
		attributes = attributeList;
		
	}
    public void setAttachments(DataHandler[] attachments) 
	{
		this.attachments = attachments;
	}


}
