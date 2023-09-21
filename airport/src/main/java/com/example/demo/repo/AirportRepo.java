package com.example.demo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Airport;

@Repository
public interface AirportRepo extends JpaRepository<Airport, Long> {
	@Query(value=" from Airport where iata =:iataCode")
	public Airport getAirportByIataCode(String iataCode);

	@Query(value = "select * from airports where name like :name% ",nativeQuery=true)
	Optional<List<Airport>> getAirportSuggestions(@Param("name") String name);

 

}
