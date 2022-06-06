package com.covidapp.model;

import java.util.ArrayList;

public class CityInfectionList {

	public String cityName;
    public ArrayList<CityInfection> cityInfections;
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public ArrayList<CityInfection> getCityInfections() {
		return cityInfections;
	}
	public void setCityInfections(ArrayList<CityInfection> cityInfections) {
		this.cityInfections = cityInfections;
	}
	public CityInfectionList(String cityName, ArrayList<CityInfection> cityInfections) {
		super();
		this.cityName = cityName;
		this.cityInfections = cityInfections;
	}
	public CityInfectionList() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "CityInfections [cityName=" + cityName + ", cityInfections=" + cityInfections + "]";
	}
    
    
}

