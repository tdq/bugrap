package com.example.bugrap.model;

/**
 * 
 * @author nikolaigorokhov
 *
 */
public class Distribution {
	private int closedAmount;
	private int nonResolvedAmount;
	private int unassignedAmount;
	
	/**
	 * 
	 * @param closedAmount
	 * @param nonResolvedAmount
	 * @param unassignedAmount
	 */
	public Distribution(int closedAmount, int nonResolvedAmount, int unassignedAmount) {
		this.closedAmount = closedAmount;
		this.nonResolvedAmount = nonResolvedAmount;
		this.unassignedAmount = unassignedAmount;
	}
	
	public int getClosedAmount() {
		return closedAmount;
	}

	public int getNonResolvedAmount() {
		return nonResolvedAmount;
	}

	public int getUnassignedAmount() {
		return unassignedAmount;
	}
}
