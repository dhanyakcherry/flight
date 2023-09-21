package com.example.demo.model;




import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Airports")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airport {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonProperty("code")
    private String iata;
    @JsonProperty("name")
    private String name;
    @JsonProperty("lat")
    private String latitude;
    @JsonProperty("lon")
    private String longitude;
    @JsonProperty("country")
    private String country;
   
   
    
}