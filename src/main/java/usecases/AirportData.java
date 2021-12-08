package usecases;

import entities.Airport;
import gateway.InteractDatabase;

import java.util.ArrayList;

public class AirportData {
    public static ArrayList<Airport> getAirportList() {
        // Returns list of Airports
        return InteractDatabase.getObjectList(Airport.class);
    }

    public static void postAirport(Airport toStore) {
        // Serializes <toStore>
        InteractDatabase.post(toStore, Airport.class);
    }

    public static Airport getAirportByIata(String iataCode) {
        ArrayList<Airport> airportList = getAirportList();
        for (Airport airport : airportList) {
            if (airport.getIataCode().equals(iataCode)) {
                return airport;
            }
        }
        return null;
    }

    public static Airport getAirportByName(String name) {
        ArrayList<Airport> airportList = getAirportList();
        for (Airport airport : airportList) {
            if (airport.getCity().toLowerCase().contains(name.toLowerCase())) {
                return airport;
            }
        }
        return null;
    }

    public static void printAirports() {
        System.out.println("Airport List:");
        for (Airport port : getAirportList()) {
            System.out.println("-----");
            System.out.println("city: " + port.getCity());
            System.out.println("iata: " + port.getIataCode());
        }
    }
}