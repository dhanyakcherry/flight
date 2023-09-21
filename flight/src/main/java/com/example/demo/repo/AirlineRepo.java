package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Airline;

public interface AirlineRepo extends JpaRepository<Airline, Long> {
	@Query(value = " from Airline where code =:iataCode")
	public Airline getAirlineByIataCode(String iataCode);

}
