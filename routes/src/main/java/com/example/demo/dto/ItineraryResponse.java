package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryResponse {
	public long id;
	public Airport flyingFrom;
	public Airport flyingTo;
	public Airline airline;

}
