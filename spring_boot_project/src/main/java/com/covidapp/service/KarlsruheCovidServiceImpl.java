package com.covidapp.service;

import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.covidapp.model.City;
import com.covidapp.model.CityInfection;
import com.covidapp.model.CityInfectionList;
import com.covidapp.model.CityList;

@Service
public class KarlsruheCovidServiceImpl implements CovidService {

	@Autowired(required=true) private RestTemplate restTemplate;
	
	Logger log = LogManager.getLogger(KarlsruheCovidServiceImpl.class);
	
	@PostConstruct
    public void init() {
		log.info("Covid Data App");
		}
	
	@Override
	public List<City> getCities() {
		
		String baseUrl = "https://api.corona-karlsruhe.info/v1/cities";
		URI uri;
		try {
			uri = new URI(baseUrl);
			CityList result = restTemplate.getForObject(uri,CityList.class);

			List<City> cities = result.getCities();
			
			log.info("List of cities"+cities);
		
			return cities;
		} catch (URISyntaxException e) {
			log.error("URI exception"+e);
		}catch (Exception e) {
			log.error("Error while fetching data"+e);
		}
		
		 
		return new ArrayList<City>();
	}

	@Override
	public ArrayList<CityInfection> getInfectionsByCitiesAndFromAndTo(String cityID, String from, String until) {
		
		String baseUrl = "https://api.corona-karlsruhe.info/v1/city_infections";
		
		if(from == null) {
			from = "";
		}
		
		if(until == null) {
			until = "";
		}
		
		try {
			if(from.isEmpty() && until.isEmpty()) {
				baseUrl = baseUrl + "?cityId=" +cityID;
			}else if(from.isEmpty()) {
				baseUrl = baseUrl + "?cityId=" +cityID +"&until="+until;
			}else if(until.isEmpty()) {
				baseUrl = baseUrl + "?cityId=" +cityID +"&from="+from;
			}else if(!from.isEmpty() && !until.isEmpty()) {
				baseUrl = baseUrl + "?cityId=" +cityID +"&from="+from +"&until="+until;
			}
			
			log.info("Final url: "+baseUrl);
			
			URI uri = new URI(baseUrl);
			
			CityInfectionList cityInfections = restTemplate.getForObject(uri,CityInfectionList.class);
			
			log.info("List of city infections : "+cityInfections.getCityInfections());
			
			return cityInfections.getCityInfections();
		}catch (Exception e) {
			log.error("Error while processing data" + e);
		}
		
		return new ArrayList<CityInfection>();
	}
	
	
	@Override
	public void exportCSV(Writer writer, String cityID, String from, String until) {
		
		ArrayList<CityInfection> cityInfectionsList = getInfectionsByCitiesAndFromAndTo(cityID, from, until);
		
		try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
			csvPrinter.printRecord("date", "totalCases", "activeCases");
            for (CityInfection cityInfection : cityInfectionsList) {
                csvPrinter.printRecord(cityInfection.getDate(), cityInfection.getTotalCases(), cityInfection.getActiveCases());
            }
        } catch (Exception e) {
        	log.error("Error While writing CSV " + e);
        }
		
	}
	
	

}