package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.Airline;
import com.example.demo.dto.Airport;
import com.example.demo.dto.ItineraryResponse;
import com.example.demo.model.PostModel;
import com.example.demo.model.Route;
import com.example.demo.repo.RouteRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin
@RestController
@RequestMapping("/routes")
public class RouteController {
	
	@Autowired
	RouteRepo repo;
	
	
	
	@Autowired
	RestTemplate airportConfig;
	
	List<Route> dataList = new ArrayList<>();

 

	@GetMapping
	public List<Route> getAllAirlineRoutes() {
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// Specify the path to your JSON file
			File jsonFile = new File("C:\\Users\\249175\\Desktop\\routes.json");

 

			// Read JSON data into a List of MyJsonObject
			dataList = objectMapper.readValue(jsonFile, new TypeReference<List<Route>>() {
			});

 

			// Now you can work with the 'dataList' which contains a list of JSON objects.
			for (Route obj : dataList) {
				repo.save(obj);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataList;

	}
	
	@PostMapping
	public List<Route> getRoute(@RequestBody PostModel model){
		
		List<Route> data = new ArrayList<>();
		List<Boolean> category = Arrays.asList(false, false, false);
		if (model.getClassType().contains("Business")) {
			category.set(0, true);
		} else if (model.getClassType().contains("Economy")) {
			category.set(1, true);
		} else if (model.getClassType().contains("First")) {
			category.set(2, true);
		}
		List<String> dayStatus = Arrays.asList("no", "no", "no", "no", "no", "no", "no");
		int day=model.getDate().getDayOfWeek().getValue();
		
		dayStatus.set(day - 1, "yes");
		for(Route r: repo.findAll()) {

			if((r.getIataFrom().equalsIgnoreCase(model.getIataF())) && (r.getIataTo().equalsIgnoreCase(model.getIataT())) ) {
				
				if((

				        (r.getDay1().equals(dayStatus.get(0)) && dayStatus.get(0).equals("yes")) ||

				        (r.getDay2().equals(dayStatus.get(1)) && dayStatus.get(1).equals("yes")) ||

				        (r.getDay3().equals(dayStatus.get(2)) && dayStatus.get(2).equals("yes")) ||

				        (r.getDay4().equals(dayStatus.get(3)) && dayStatus.get(3).equals("yes")) ||
				        (r.getDay5().equals(dayStatus.get(4)) && dayStatus.get(4).equals("yes")) || 
				        (r.getDay6().equals(dayStatus.get(5)) && dayStatus.get(5).equals("yes"))
				  ||(r.getDay7().equals(dayStatus.get(6)) && dayStatus.get(6).equals("yes"))
				    )&&
						(r.isClassBusiness() == category.get(0) || r.isClassEconomy() == category.get(1)
								 || r.isClassFirst() == category.get(2))){
					data.add(r);
					
				}
				
					
				
			}
		}
		return data;
		
	}
	@PostMapping("/getBestRoute")
	public List<List<Route>> getRoute(@RequestBody List<PostModel> models){
		List<List<Route>> fine = new ArrayList<>();
		
		for(PostModel model:models) {
			List<Route> data = new ArrayList<>();
		List<Boolean> category = Arrays.asList(false, false, false);
		if (model.getClassType().contains("Business")) {
			category.set(0, true);
		} else if (model.getClassType().contains("Economy")) {
			category.set(1, true);
		} else if (model.getClassType().contains("First")) {
			category.set(2, true);
		}
		List<String> dayStatus = Arrays.asList("no", "no", "no", "no", "no", "no", "no");
		int day=model.getDate().getDayOfWeek().getValue();
		
		dayStatus.set(day - 1, "yes");
		for(Route r: repo.findAll()) {

			if((r.getIataFrom().equalsIgnoreCase(model.getIataF())) && (r.getIataTo().equalsIgnoreCase(model.getIataT())) ) {
				
				if((

				        (r.getDay1().equals(dayStatus.get(0)) && dayStatus.get(0).equals("yes")) ||

				        (r.getDay2().equals(dayStatus.get(1)) && dayStatus.get(1).equals("yes")) ||

				        (r.getDay3().equals(dayStatus.get(2)) && dayStatus.get(2).equals("yes")) ||

				        (r.getDay4().equals(dayStatus.get(3)) && dayStatus.get(3).equals("yes")) ||
				        (r.getDay5().equals(dayStatus.get(4)) && dayStatus.get(4).equals("yes")) || 
				        (r.getDay6().equals(dayStatus.get(5)) && dayStatus.get(5).equals("yes"))
				  ||(r.getDay7().equals(dayStatus.get(6)) && dayStatus.get(6).equals("yes"))
				    )&&
						(r.isClassBusiness() == category.get(0) || r.isClassEconomy() == category.get(1)
								 || r.isClassFirst() == category.get(2))){
					data.add(r);
					
					
				}
				
					
				
			}
		}
		fine.add(data);
		}
		
		return fine;
		
	}
	@PostMapping("/getdetaileditinerary")
	public List<ItineraryResponse> fetchDetailedItinerary(@RequestBody List<Long> routeId){
		List<ItineraryResponse> itineraryList = new ArrayList<>();
		for (long id : routeId) {

 

			Route getRoute = repo.findById(id).get();
			String airlineIata = getRoute.getAirLineIata();
			String airportToIata = getRoute.getIataTo();
			String airportFromIata = getRoute.getIataFrom();
			Airline getAirline = airportConfig
					.getForObject("http://localhost:8080/getAirline/" + airlineIata, Airline.class);
			Airport getAirportTo = airportConfig
					.getForObject("http://localhost:8085/getAirport/" + airportToIata, Airport.class);
			Airport getAirportFrom = airportConfig
					.getForObject("http://localhost:8085/getAirport/" + airportFromIata, Airport.class);
			ItineraryResponse itineraryResponse = new ItineraryResponse(id, getAirportFrom,
					getAirportTo, getAirline);
			itineraryList.add(itineraryResponse);
		}

 

		return itineraryList;
	
	}
	
	
}
