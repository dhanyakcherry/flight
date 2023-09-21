package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Flights")
public class Airline {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
	@Id
	@JsonProperty
    private String code;
    @JsonProperty
    private String name;
    
    @JsonProperty
//    @Column(columnDefinition = "VARCHAR(255)")
//    @Convert(converter = BooleanToStringConverter.class)
    private Boolean is_lowcost;
    @JsonProperty
    private String logo;
    
    // Other fields, getters, and setters
}