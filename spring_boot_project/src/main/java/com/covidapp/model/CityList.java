package com.covidapp.model;

import java.util.ArrayList;
import java.util.List;


public class CityList {

	private List<City> cities;

	public List<City> getCities() {
		return cities;
	}

	public CityList(List<City> cities) {
		super();
		this.cities = cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	
	public CityList() {
		cities = new ArrayList<>();
	}
}
