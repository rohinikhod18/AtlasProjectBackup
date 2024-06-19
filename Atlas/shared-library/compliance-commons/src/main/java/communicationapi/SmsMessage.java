
package communicationapi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for smsMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="smsMessage"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{urn:CommunicationAPI}message"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Attributes" type="{urn:CommunicationAPI}messageAttributes" maxOccurs="unbounded"/&gt;
 *         &lt;element name="MobileNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MessageText" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "smsMessage", propOrder = {
    "attributes",
    "mobileNumber",
    "messageText"
})
public class SmsMessage extends Message
{

    @XmlElement(name = "Attributes", required = true)
    protected List<MessageAttributes> attributes;//NOSONAR
    @XmlElement(name = "MobileNumber", required = true)
    protected String mobileNumber;
    @XmlElement(name = "MessageText", required = true)
    protected String messageText;

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
     * Gets the value of the mobileNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * Sets the value of the mobileNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobileNumber(String value) {
        this.mobileNumber = value;
    }

    /**
     * Gets the value of the messageText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageText() {
        return messageText;
    }

    /**
     * Sets the value of the messageText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageText(String value) {
        this.messageText = value;
    }

}
