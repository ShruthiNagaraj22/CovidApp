package com.covidapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class City {


	private Integer cityId;
	
	private String cityName;
	
	private Integer population;
	
	
	public Integer getCityId() {
	return cityId;
	}
	
	
	public void setCityId(Integer cityId) {
	this.cityId = cityId;
	}
	
	
	public String getCityName() {
	return cityName;
	}
	
	
	public void setCityName(String cityName) {
	this.cityName = cityName;
	}
	
	
	public Integer getPopulation() {
	return population;
	}
	
	
	public void setPopulation(Integer population) {
	this.population = population;
	}


}
