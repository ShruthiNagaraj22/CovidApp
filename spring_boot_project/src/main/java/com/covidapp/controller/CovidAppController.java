package com.covidapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.covidapp.model.City;
import com.covidapp.model.CityInfection;
import com.covidapp.service.KarlsruheCovidServiceImpl;


@CrossOrigin(origins = "http://localhost:4200")
@RestController

@RequestMapping({ "/covidApp" })
public class CovidAppController {

	Logger log = LogManager.getLogger(CovidAppController.class);

	@Autowired private KarlsruheCovidServiceImpl service;
	
	@GetMapping(path = "/getCities",produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public List<City> getCities(){
		return service.getCities();
	}
	
	@GetMapping(path = "/city_infections",produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ArrayList<CityInfection> getinfections(@RequestParam(value = "cityId", required = true) String cityID,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "until", required = false) String until
			) {
		
		log.info("City="+cityID);
		log.info("from="+ from);
		log.info("until="+ until);
		return service.getInfectionsByCitiesAndFromAndTo(cityID, from, until);
	}
	
	@GetMapping(path = "/export_csv")
	@ResponseBody
	public void exportCSV(HttpServletResponse servletResponse, @RequestParam(value = "cityId", required = true) String cityID,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "until", required = false) String until
			) throws HttpMediaTypeNotAcceptableException {
		log.info("City="+cityID);
		log.info("from="+ from);
		log.info("until="+ until);
		servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"data.csv\"");
        
		try {
			service.exportCSV(servletResponse.getWriter(), cityID, from, until);
			
		} catch (Exception e) {
			log.error("Exception occured"+e);
		}
		
	}
	
	
}