package com.iekun.ef.jms.vo.receive;

public class RcvSniffer {

	int 		 valid;
	int 		 syncStatus;
	int 		 pci;
	int 		 power;
	int 		 frequency;
	
	/**
	 * @return the syncStatus
	 */
	public int getSyncStatus() {
		return syncStatus;
	}
	/**
	 * @param syncStatus the syncStatus to set
	 */
	public void setSyncStatus(int syncStatus) {
		this.syncStatus = syncStatus;
	}
	
	/**
	 * @return the power
	 */
	public int getPower() {
		return power;
	}
	/**
	 * @param power the power to set
	 */
	public void setPower(int power) {
		this.power = power;
	}
	/**
	 * @return the frequency
	 */
	public int getFrequency() {
		return frequency;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	/**
	 * @return the pci
	 */
	public int getPci() {
		return pci;
	}
	/**
	 * @param pci the pci to set
	 */
	public void setPci(int pci) {
		this.pci = pci;
	}
	
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}

}
