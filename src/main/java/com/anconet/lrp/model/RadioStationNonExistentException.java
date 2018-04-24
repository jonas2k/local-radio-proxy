package com.anconet.lrp.model;

public class RadioStationNonExistentException extends Exception {

	private static final long serialVersionUID = -6261906720667565723L;

	public RadioStationNonExistentException() {
		super();
	}

	public RadioStationNonExistentException(String message) {
		super(message);
	}
}