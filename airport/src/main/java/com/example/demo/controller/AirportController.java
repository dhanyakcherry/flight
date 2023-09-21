package com.example.demo.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Airport;
import com.example.demo.repo.AirportRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class AirportController {
	@Autowired
	private AirportRepo repo;

	
    @GetMapping(value = "/getAllAirports")
    public ResponseEntity<?> getRequest() {
        try {
            // Load the JSON file from the specified path
            File jsonFile = new File("C:\\Users\\249175\\Desktop\\flight_airport.json");

            // Create an instance of the ObjectMapper to work with JSON
            ObjectMapper objectMapper = new ObjectMapper();

            // Read the JSON data from the file into a JsonNode
            JsonNode jsonArray = objectMapper.readTree(jsonFile);

            // Create a list to store the airline details
            List<Airport> airportDetails = new ArrayList<>();

            // Iterate through the JSON array and deserialize each object into an Airline instance
            for (JsonNode object : jsonArray) {
                Airport airport = objectMapper.treeToValue(object, Airport.class);
                repo.save(airport);
                airportDetails.add(airport);
            }

            // Return the list of airline details in the response
            return new ResponseEntity<>(airportDetails, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // If there's an error, return a bad request response
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/getAirport/{iataCode}")
    public Airport getAirportByIataCode(@PathVariable String iataCode) {
    	Airport airport=repo.getAirportByIataCode(iataCode);
    	return airport;
    }
    @GetMapping("/searchAirport/{starting}")
    public ResponseEntity<List<Airport>> getAirportBysearch(@PathVariable String starting ) {	
    	return ResponseEntity.ok().body(repo.getAirportSuggestions(starting).get());

    }


}
