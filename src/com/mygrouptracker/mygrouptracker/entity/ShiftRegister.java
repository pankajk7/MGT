package com.mygrouptracker.mygrouptracker.entity;

public class ShiftRegister {
	public String id;
	public String shiftName;
	
	public ShiftRegister(String id, String shiftString){
		this.id=id;
		this.shiftName=shiftString;
	}
	
	@Override
	public String toString() {
	 return	shiftName;
	};
}
