
package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class FxTicketDetailList.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "linked_instruction_details", "main_customer_instruction", "drawdown_customer_instructions" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class FxTicketDetailList {

	/** The linked instruction details. */
	@JsonProperty("linked_instruction_details")
	private Object linkedInstructionDetails;

	/** The main customer instruction. */
	@JsonProperty("main_customer_instruction")
	private MainCustomerInstruction mainCustomerInstruction;

	/** The drawdown customer instructions. */
	@JsonProperty("drawdown_customer_instructions")
	private Object drawdownCustomerInstructions;

	/**
	 * Gets the linked instruction details.
	 *
	 * @return the linked instruction details
	 */
	@JsonProperty("linked_instruction_details")
	public Object getLinkedInstructionDetails() {
		return linkedInstructionDetails;
	}

	/**
	 * Sets the linked instruction details.
	 *
	 * @param linkedInstructionDetails
	 *            the new linked instruction details
	 */
	@JsonProperty("linked_instruction_details")
	public void setLinkedInstructionDetails(Object linkedInstructionDetails) {
		this.linkedInstructionDetails = linkedInstructionDetails;
	}

	/**
	 * Gets the main customer instruction.
	 *
	 * @return the main customer instruction
	 */
	@JsonProperty("main_customer_instruction")
	public MainCustomerInstruction getMainCustomerInstruction() {
		return mainCustomerInstruction;
	}

	/**
	 * Sets the main customer instruction.
	 *
	 * @param mainCustomerInstruction
	 *            the new main customer instruction
	 */
	@JsonProperty("main_customer_instruction")
	public void setMainCustomerInstruction(MainCustomerInstruction mainCustomerInstruction) {
		this.mainCustomerInstruction = mainCustomerInstruction;
	}

	/**
	 * Gets the drawdown customer instructions.
	 *
	 * @return the drawdown customer instructions
	 */
	@JsonProperty("drawdown_customer_instructions")
	public Object getDrawdownCustomerInstructions() {
		return drawdownCustomerInstructions;
	}

	/**
	 * Sets the drawdown customer instructions.
	 *
	 * @param drawdownCustomerInstructions
	 *            the new drawdown customer instructions
	 */
	@JsonProperty("drawdown_customer_instructions")
	public void setDrawdownCustomerInstructions(Object drawdownCustomerInstructions) {
		this.drawdownCustomerInstructions = drawdownCustomerInstructions;
	}

}
