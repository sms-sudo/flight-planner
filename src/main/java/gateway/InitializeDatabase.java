package gateway;

import entities.Airport;
import entities.Flight;
import entities.Plane;
import entities.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import usecases.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// API stuff
// base url: https://api.aviationstack.com/v1/
// flight, flight_status, flight_date, dep_iata, arr_iata, airline_name, airline_iata, flight_iata
// routes, airports(city?), airlines airplanes or aircrafts?
// key: 8a0423ec6b7b5e44ae6bab41e07f150b
// https://www.baeldung.com/java-http-url-connection

public class InitializeDatabase {
    private static final String key = "a76bb2bb39ee510ad50fd18941d020ac"; // API key, limit of 100 requests

    /**
     * Resets all program data.
     * This is irreversible and idempotent.
     *
     * Sets database to a testing state
     */
    public static void resetTestData() {
        // Reset Files to blanks
        InteractDatabase.reset(UserList.class);
        InteractDatabase.reset(Airport.class);
        InteractDatabase.reset(Plane.class);
        InteractDatabase.reset(Flight.class);
        ArrayList<Airport> airports = new ArrayList<Airport>();
        ArrayList<Plane> planes = new ArrayList<Plane>();
        ArrayList<Flight> flights = new ArrayList<Flight>();
        // UserList Data Creation
        UserList userData = new UserList();
        userData.addUser(new User("user1", "111", "test1@email.ca", "(416)-000-0001"));
        userData.addUser(new User("user2", "222", "test2@email.com", "(416)-000-0002"));
        // Write User Data
        InteractDatabase.post(userData, UserList.class);
        // Airport Data Creation
        airports.add(new Airport("Toronto", "000"));
        airports.add(new Airport("Montreal", "001"));
        airports.add(new Airport("Vancouver", "002"));
        airports.add(new Airport("London", "003"));
        airports.add(new Airport("Paris", "004"));
        airports.add(new Airport("Hong Kong", "005"));
        // Write Airport Data
        for (Airport airport : airports) {
            InteractDatabase.post(airport, Airport.class);
        }
        // Plane Data Creation
        planes.add(new Plane("Boeing 747", 223, 7, 223 - 7, true, "001"));
        planes.add(new Plane("Apollo 11", 1738, 12, 1738 - 12, true, "002"));
        planes.add(new Plane("Falcon 1", 1337, 15, 1337 - 15, true, "003"));
        // Write Plane Data
        for (Plane plane : planes) {
            InteractDatabase.post(plane, Plane.class);
        }
        // Flight Data Creation
        GregorianCalendar date = new GregorianCalendar(2021, Calendar.DECEMBER, 6);
        flights.add(new Flight(date, planes.get(0), 10,2, airports.get(0), airports.get(1)));
        flights.add(new Flight(date, planes.get(1), 3, 7, airports.get(0), airports.get(2)));
        flights.add(new Flight(date, planes.get(2), 5, 3, airports.get(0), airports.get(3)));
        flights.add(new Flight(date, planes.get(0), 1, 2, airports.get(1), airports.get(0)));
        flights.add(new Flight(date, planes.get(1), 3, 4, airports.get(1), airports.get(2)));
        flights.add(new Flight(date, planes.get(2), 2, 2, airports.get(1), airports.get(4)));
        flights.add(new Flight(date, planes.get(0), 1, 2, airports.get(2), airports.get(0)));
        flights.add(new Flight(date, planes.get(1), 6, 4, airports.get(2), airports.get(1)));
        flights.add(new Flight(date, planes.get(2), 5, 5, airports.get(2), airports.get(5)));
        flights.add(new Flight(date, planes.get(0), 1, 2, airports.get(3), airports.get(1)));
        flights.add(new Flight(date, planes.get(1), 2, 4, airports.get(3), airports.get(4)));
        flights.add(new Flight(date, planes.get(2), 5, 1, airports.get(3), airports.get(5)));
        flights.add(new Flight(date, planes.get(0), 7, 2, airports.get(4), airports.get(0)));
        flights.add(new Flight(date, planes.get(1), 3, 4, airports.get(4), airports.get(3)));
        flights.add(new Flight(date, planes.get(2), 2, 2, airports.get(4), airports.get(5)));
        flights.add(new Flight(date, planes.get(0), 1, 2, airports.get(5), airports.get(2)));
        flights.add(new Flight(date, planes.get(1), 3, 4, airports.get(5), airports.get(3)));
        flights.add(new Flight(date, planes.get(2), 5, 6, airports.get(5), airports.get(4)));
        // Write Flight Data
        for (Flight flight : flights) {
            InteractDatabase.post(flight, Flight.class);
        }
    }

    /**
     * Fetches live airport data
     * @throws IOException if error writing to db
     * @throws JSONException if error reading from api
     *
     * Adds real-life airport data into program
     */
    public static void updateAirportDB() throws JSONException, IOException {
        JSONObject allAirports = new JSONObject(getEndpoint("http://api.aviationstack.com/v1/airports", key, 1000));
        JSONArray airportsArray = allAirports.getJSONArray("data");

        for (int i = 0; i < 1000; i++) {
            JSONObject a = airportsArray.getJSONObject(i);
            String airportName = a.getString("airport_name");
            String iataCode = a.getString("iata_code");
            Airport airport = new Airport(airportName, iataCode);
            AirportReadWriter.postAirport(airport);
        }
    }

    /**
     * Fetches live airplane data
     * @throws IOException if error writing to db
     * @throws JSONException if error reading from api
     *
     * Adds real-life airplane data into program
     */
    public static void updatePlaneDB() throws JSONException {
        JSONObject allPlanes = new JSONObject(getEndpoint("http://api.aviationstack.com/v1/airplanes", key, 1000));
        JSONArray planesArray = allPlanes.getJSONArray("data");

        for (int i = 0; i < 1000; i++) {
            JSONObject p = planesArray.getJSONObject(i);
            String name = p.getString("production_line");
            int seatCount = 150;
            int firstClassCount = 50;
            int economyCount = 100;
            boolean hasVacant = true;
            String iata = p.getString("iata_code_short");
            Plane plane = new Plane(name, seatCount, firstClassCount, economyCount, hasVacant, iata);
            PlaneReadWriter.postPlane(plane);
        }
    }

    /**
     * Fetches live flight data
     * @throws IOException if error writing to db
     * @throws JSONException if error reading from api
     * @throws ClassNotFoundException if error reading from db
     * @throws ParseException if error converting flight date
     *
     * Adds real-life flight data into program
     */
    public static void updateFlightDB() throws IOException, JSONException, ClassNotFoundException, ParseException {
        JSONObject allFlights = new JSONObject(getEndpoint("http://api.aviationstack.com/v1/flights", key, 100));
        JSONArray flightsArray = allFlights.getJSONArray("data");

        for (int i = 0; i < 100; i = i + 1) {
            JSONObject f = flightsArray.getJSONObject(i);
            JSONObject fDepart = f.getJSONObject("departure");
            JSONObject fArrive = f.getJSONObject("arrival");

            Calendar date = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date.setTime(sdf.parse(f.getString("flight_date")));

//            String iata = f.getJSONObject("aircraft").getString("iata");
            Plane plane = PlaneReadWriter.getPlaneList().get(0);

            SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date departTime = sdfTime.parse(fDepart.getString("scheduled").substring(0, 23));
            Date arriveTime = sdfTime.parse(fArrive.getString("scheduled").substring(0, 23));
            long diff = arriveTime.getTime() - departTime.getTime();
            long duration = (diff / (1000 * 60 * 60)) % 24;

            Random rand = new Random();
            double price = duration * 50 + rand.nextInt(500);;

            Airport src = AirportReadWriter.getAirportByIata(fDepart.getString("iata"));
            Airport dest = AirportReadWriter.getAirportByIata(fArrive.getString("iata"));
            Flight flight = new Flight(date, plane, price, duration, src, dest);
            FlightReadWriter.postFlight(flight);
        }
    }

    public static String getEndpoint(String endpoint, String key, int limit) {
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

        try {
            String x = endpoint + "?access_key=" + key +"&limit=" + limit;
            URL url = new URL(x);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setConnectTimeout(2500);
            con.setReadTimeout(2500);

            int status = con.getResponseCode();

            if (status > 299) {
                // connection is not successful
                reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            } else {
                // connection is successful
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();

            return responseContent.toString();
        } catch (IOException e) {
            System.out.println("error while reading from api");
            return null;
        }
    }

    public static void main(String[] args) throws JSONException, IOException, ParseException, ClassNotFoundException {
        resetTestData();
//        updatePlaneDB();
//        updateAirportDB();
//        updateFlightDB();

    }
}
