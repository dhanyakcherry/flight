package com.example.demo.controller;

import com.example.demo.model.Airline;
import com.example.demo.repo.AirlineRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AirlineController {
	
	@Autowired
	private AirlineRepo repo;

    @GetMapping(value = "/getAllAirlines")
    public ResponseEntity<?> getRequest() {
        try {
            // Load the JSON file from the specified path
            File jsonFile = new File("C:\\Users\\249175\\Desktop\\data.json");

            // Create an instance of the ObjectMapper to work with JSON
            ObjectMapper objectMapper = new ObjectMapper();

            // Read the JSON data from the file into a JsonNode
            JsonNode jsonArray = objectMapper.readTree(jsonFile);

            // Create a list to store the airline details
            List<Airline> airlineDetails = new ArrayList<>();

            // Iterate through the JSON array and deserialize each object into an Airline instance
            for (JsonNode object : jsonArray) {
                Airline airline = objectMapper.treeToValue(object, Airline.class);
                repo.save(airline);
                airlineDetails.add(airline);
            }

            // Return the list of airline details in the response
            return new ResponseEntity<>(airlineDetails, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // If there's an error, return a bad request response
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/getAirline/{iataCode}")
    public Airline getAirlineByIataCode(@PathVariable String iataCode) {
    	Airline air=repo.getAirlineByIataCode(iataCode);
    	return air;
    }
}
