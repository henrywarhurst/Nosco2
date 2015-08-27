package com.henrywarhurst.facerecog;

public class Prediction {
	private long personId;
	private int confidence;
	public Prediction(long personId, int confidence) {
		this.personId = personId;
		this.confidence = confidence;
	}
	
	public long getPersonId() {
		return personId;
	}
	
	public int getConfidence() {
		return confidence;
	}
}
