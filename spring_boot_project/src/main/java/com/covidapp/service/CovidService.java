package com.covidapp.service;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.covidapp.model.City;
import com.covidapp.model.CityInfection;



@Service
public interface CovidService {

	public List<City> getCities();
	
	public ArrayList<CityInfection> getInfectionsByCitiesAndFromAndTo(String cityID,String from,String until);
	
	public void exportCSV(Writer writer, String cityID,String from,String until);

	
}
