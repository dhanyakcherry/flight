package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostModel {
	
	@Id
	private String iataF;
	private String iataT;
	private String classType;
	private LocalDate date;
	

}
