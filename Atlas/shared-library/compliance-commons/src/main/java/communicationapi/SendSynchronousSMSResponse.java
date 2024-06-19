
package communicationapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SendSynchronousSMSResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SendSynchronousSMSResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IsSent" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ResponseStatusHeader" type="{urn:CommunicationAPI}responseStatusHeader"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SendSynchronousSMSResponse", propOrder = {
    "isSent",
    "responseStatusHeader"
})
public class SendSynchronousSMSResponse {

    @XmlElement(name = "IsSent")
    protected boolean isSent;
    @XmlElement(name = "ResponseStatusHeader", required = true)
    protected ResponseStatusHeader responseStatusHeader;

    /**
     * Gets the value of the isSent property.
     * 
     */
    public boolean isIsSent() {
        return isSent;
    }

    /**
     * Sets the value of the isSent property.
     * 
     */
    public void setIsSent(boolean value) {
        this.isSent = value;
    }

    /**
     * Gets the value of the responseStatusHeader property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseStatusHeader }
     *     
     */
    public ResponseStatusHeader getResponseStatusHeader() {
        return responseStatusHeader;
    }

    /**
     * Sets the value of the responseStatusHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseStatusHeader }
     *     
     */
    public void setResponseStatusHeader(ResponseStatusHeader value) {
        this.responseStatusHeader = value;
    }

}
