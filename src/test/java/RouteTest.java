import entities.Airport;
import entities.Flight;
import entities.Plane;
import entities.Route;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class RouteTest {
    public Route r;

    @Before
    public void setUp() throws Exception {
        List<Flight> flights = new ArrayList<>();

        flights.add(new Flight(new GregorianCalendar(2021, Calendar.DECEMBER, 30), new Plane("sdfinweo", 50, 50, 50, true), 9000, 30, new Airport("grse", "5235"), new Airport("gadgd", "235")));

        Airport t = new Airport("Toronto", "84681");

        Airport m = new Airport("Montreal", "235346");

        r = new Route(t, m, flights.get(0).getDate(), flights);
    }

    @Test(timeout = 50)
    public void TestgetPriceofFlights(){
        assertEquals(9000, r.getPriceofFlights(), 1);
    }

    @Test(timeout = 50)
    public void TestTotalDuration(){
        assertEquals(30, r.getTotalDuration(), 1);
    }
}
