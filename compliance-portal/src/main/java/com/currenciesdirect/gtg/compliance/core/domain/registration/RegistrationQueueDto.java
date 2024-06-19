package com.currenciesdirect.gtg.compliance.core.domain.registration;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.BaseQueueDto;

/**
 * The Class RegisrationQueueDto.
 */
public class RegistrationQueueDto extends BaseQueueDto {

	/** The registration queue. */
	private List<RegistrationQueueData> registrationQueue;
	
	/**
	 * Gets the registration queue.
	 *
	 * @return the registration queue
	 */
	public List<RegistrationQueueData> getRegistrationQueue() {
		return registrationQueue;
	}

	/**
	 * Sets the registration queue.
	 *
	 * @param registrationQueue
	 *            the new registration queue
	 */
	public void setRegistrationQueue(List<RegistrationQueueData> registrationQueue) {
		this.registrationQueue = registrationQueue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((registrationQueue == null) ? 0 : registrationQueue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistrationQueueDto other = (RegistrationQueueDto) obj;
		if (registrationQueue == null) {
			if (other.registrationQueue != null)
				return false;
		} else if (!registrationQueue.equals(other.registrationQueue)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "RegisrationQueueDto [registrationQueue=" + registrationQueue+"]";
	}
}
