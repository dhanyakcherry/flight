
import React, { useState } from 'react';
import './FlightBooking.css';
import axios from 'axios';
import Autosuggest from 'react-autosuggest';


const FlightBooking = () => {
  const [tripType, setTripType] = useState('oneWay');
  const [departureCity, setDepartureCity] = useState('');
  const [arrivalCity, setArrivalCity] = useState('');
  const [flightClass, setFlightClass] = useState('');
  const [departureDate, setDepartureDate] = useState('');
  const [returnDate, setReturnDate] = useState('');
  const [searchResults, setSearchResults] = useState([]);

  const cities = [
    { cityName: 'New York', iataCode: 'JFK' },
    { cityName: 'Los Angeles', iataCode: 'LAX' },
    // Add more cities and IATA codes as needed
  ];
  const [departureCitySuggestions, setDepartureCitySuggestions] = useState([]);
  const [arrivalCitySuggestions, setArrivalCitySuggestions] = useState([]);
  
  // Define getSuggestions function to filter city suggestions based on user input
  const getSuggestions = (value) => {
    const inputValue = value.trim().toLowerCase();
    return cities.filter((city) =>
      city.cityName.toLowerCase().includes(inputValue)
    );
  };
  
  // Define getSuggestionValue function to specify what gets displayed in the input field
  const getSuggestionValue = (suggestion) => suggestion.cityName;
  
  // Define renderSuggestion function to render individual suggestions
  const renderSuggestion = (suggestion) => (
    <div>
      {suggestion.cityName} ({suggestion.iataCode})
    </div>
  );
  
  // Event handlers for input value changes
  const handleDepartureCityChange = (event, { newValue }) => {
    setDepartureCity(newValue);
  };
  
  const handleArrivalCityChange = (event, { newValue }) => {
    setArrivalCity(newValue);
  };
  
  // Autosuggest input props
  const departureCityInputProps = {
    placeholder: 'Enter departure city',
    value: departureCity,
    onChange: handleDepartureCityChange,
  };
  
  const arrivalCityInputProps = {
    placeholder: 'Enter arrival city',
    value: arrivalCity,
    onChange: handleArrivalCityChange,
  };  
  
  const handleTripTypeChange = (e) => {
    setTripType(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    // Implement flight search logic here
    const mockResults =
      {
        iataF: departureCity,
        iataT: arrivalCity,
        classType:flightClass,
        date:departureDate
    };
    try {

      // Make the POST request using Axios

      const response = await axios.post('http://localhost:8089/routes',mockResults);
      setSearchResults(response.data);
      // Handle the response here (e.g., display a success message)
      console.log('Response:', response.data);
    } catch (error) {
      // Handle errors (e.g., display an error message)
      console.error('Error:', error);
    }
    console.log('Searching for flights...');
  };

  return (
 
    <div className="flight-booking-container">
      <div className="booking-header">
        <h1 className="booking-heading">Book Your Flight</h1>
        <div className="trip-type-selection">
          <div className="form-check form-check-inline">
            <input
              type="radio"
              className="form-check-input"
              id="oneWay"
              value="oneWay"
              checked={tripType === 'oneWay'}
              onChange={handleTripTypeChange}
            />
            <label className="form-check-label" htmlFor="oneWay">
              One Way
            </label>
          </div>
          <div className="form-check form-check-inline">
            <input
              type="radio"
              className="form-check-input"
              id="roundTrip"
              value="roundTrip"
              checked={tripType === 'roundTrip'}
              onChange={handleTripTypeChange}
            />
            <label className="form-check-label" htmlFor="roundTrip">
              Round Trip
            </label>
          </div>
        </div>
      </div><br></br>
      <form className="flight-booking-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Departure City:</label>
          <Autosuggest
          suggestions={departureCitySuggestions}
          onSuggestionsFetchRequested={({ value }) => {
            setDepartureCitySuggestions(getSuggestions(value));
          }}
          onSuggestionsClearRequested={() => {
            setDepartureCitySuggestions([]);
          }}
          getSuggestionValue={getSuggestionValue}
          renderSuggestion={renderSuggestion}
          inputProps={departureCityInputProps}
        />
          <input
            type="text"
            className="form-control"
            placeholder="Enter departure city"
            value={departureCity}
            onChange={(e) => setDepartureCity(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label>Arrival City:</label>
          <Autosuggest
          suggestions={arrivalCitySuggestions}
          onSuggestionsFetchRequested={({ value }) => {
            setArrivalCitySuggestions(getSuggestions(value));
          }}
          onSuggestionsClearRequested={() => {
            setArrivalCitySuggestions([]);
          }}
          getSuggestionValue={getSuggestionValue}
          renderSuggestion={renderSuggestion}
          inputProps={arrivalCityInputProps}
        />
          <input
            type="text"
            className="form-control"
            placeholder="Enter arrival city"
            value={arrivalCity}
            onChange={(e) => setArrivalCity(e.target.value)}
          />
        </div>
        <div className="form-group mt-3">
        <label>Choose class:</label>
<select className="form-control mr-2" value={flightClass} onChange={(e) => setFlightClass(e.target.value)}>

<option value="Economy">Economy Class</option>
<option value="Business">Business Class</option>
<option value="First">First Class </option>
</select>
</div>
        <div className="form-group">
          <label>Departure Date:</label>
          <input
            type="date"
            className="form-control"
            value={departureDate}
            onChange={(e) => setDepartureDate(e.target.value)}
          />
        </div>
        {tripType === 'roundTrip' && (
          <div className="form-group">
            <label>Return Date:</label>
            <input
              type="date"
              className="form-control"
              value={returnDate}
              onChange={(e) => setReturnDate(e.target.value)}
            />
          </div>
        )}
        <button type="submit" className="btn btn-primary">
          Search Flights
        </button>
        
      </form>
      <br></br>
      {searchResults && (
        <div className="search-results">
          
          {searchResults.map((result, index) => (
            <div className="result-card" key={index}>
              <h3>Flight Details</h3>
              
              <p><strong>Airline IATA</strong> {result.airLineIata}</p>
              <p><strong>Maximum Duration</strong> {result.max_duration}</p>
              
              
            </div>
          ))}
        </div>
      )}


    </div>
  );
};

export default FlightBooking;