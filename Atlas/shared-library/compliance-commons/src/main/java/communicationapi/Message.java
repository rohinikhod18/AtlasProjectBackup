
package communicationapi;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for message complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="message"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Locale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Event" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DeliveryDelayInSeconds" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="CustomerEmail" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CustomerNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CustomerType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Site" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EventData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
  *         &lt;element name="OrganizationLegalEntity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
*       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "message", propOrder = {
    "locale",
    "event",
    "customerEmail",
    "customerType",
    "source"
    
})
@XmlSeeAlso({
    EmailMessage.class,
    SmsMessage.class
})
public class Message implements Serializable{

    @XmlElement(name = "Locale", required = true)
    protected String locale;
    @XmlElement(name = "Event", required = true)
    protected String event;
    @XmlElement(name = "CustomerEmail", required = true)
    protected String customerEmail;
    @XmlElement(name = "CustomerType")
    protected String customerType;
    @XmlElement(name = "Source")
    protected String source;
  
   
    public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	/**
     * Gets the value of the locale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocale(String value) {
        this.locale = value;
    }

    /**
     * Gets the value of the event property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEvent() {
        return event;
    }

    /**
     * Sets the value of the event property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEvent(String value) {
        this.event = value;
    }

   /**
     * Gets the value of the customerEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * Sets the value of the customerEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerEmail(String value) {
        this.customerEmail = value;
    }


	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}


	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}    

}