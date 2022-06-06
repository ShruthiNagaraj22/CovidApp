package com.covidapp.model;

public class CityInfection {

	public String date;
    public int totalCases;
    public int activeCases;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getTotalCases() {
		return totalCases;
	}
	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}
	public int getActiveCases() {
		return activeCases;
	}
	public void setActiveCases(int activeCases) {
		this.activeCases = activeCases;
	}
	public CityInfection(String date, int totalCases, int activeCases) {
		super();
		this.date = date;
		this.totalCases = totalCases;
		this.activeCases = activeCases;
	}
	public CityInfection() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "CityInfection [date=" + date + ", totalCases=" + totalCases + ", activeCases=" + activeCases + "]";
	}
    
	
}
