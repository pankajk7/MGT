package com.mygrouptracker.mygrouptracker.entity;

public class States {
	
	public String id;
	public String stateName;
	public String stateAbbreviation;
	
	public States(){}
	
	public States(String id, String stateName, String stateAbbreviation){
		this.id = id;
		this.stateAbbreviation = stateAbbreviation;
		this.stateName = stateName;
	}
	
	@Override
	public String toString() {
		return stateName;
	}

}
