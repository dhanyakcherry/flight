package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Route;

@Repository
public interface RouteRepo extends JpaRepository<Route, Long> {
//	@Query("SELECT ar FROM routes ar " +
//		       "WHERE ar.iataFrom = :from " +
//		       "AND ar.iataTo = :to " +
//		       "AND (:business = true AND ar.classBusiness = true OR " +
//		       "     :economy = true AND ar.classEconomy = true OR " +
//		       "     :first = true AND ar.classFirst = true) " +
//		       "AND  (ar.day1 = :day1 OR  ar.day2 = :day2 OR ar.day3 = :day3 OR ar.day4 = :day4 OR ar.day5 = :day5 OR ar.day6 = :day6 OR ar.day7 = :day7)")
//		List<Route> findBestRoutes(
//		    @Param("from") String from,
//		    @Param("to") String to,
//		    @Param("business") boolean business,
//		    @Param("economy") boolean economy,
//		    @Param("first") boolean first,
//		    @Param("day1") String day1,
//		    @Param("day2") String day2,
//		    @Param("day3") String day3,
//		    @Param("day4") String day4,
//		    @Param("day5") String day5,
//		    @Param("day6") String day6,
//		    @Param("day7") String day7
//		);



}
