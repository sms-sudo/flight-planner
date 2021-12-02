package entities;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Represents a scheduled flight. Creates an object that contains the scheduled date, a plane object, the cost of
 * the flight, its duration, and the airports it is departing from and flying to.
 */

public class Flight implements Serializable {
    private Calendar date;
    private Plane plane;
    private double price; //TODO: since there are two types of seats in our plane object, shouldn't there be 2 prices?
    private double duration; //TODO: Assuming this will be in hours?
    private Airport sourceAirport;
    private Airport destinationAirport;

    public Flight(Calendar date, Plane plane, double price, double duration, Airport source, Airport destination){
        this.date = date;
        this.plane = plane;
        this.price = price;
        this.duration = duration;
        this.sourceAirport = source;
        this.destinationAirport = destination;
    }

    public Calendar getDate(){
        return date;
    }
    public Plane getPlane(){
        return this.plane;
    }
    public double getPrice(){
        return this.price;
    }
    public double getDuration(){
        return this.duration;
    }
    public Airport getSourceAirport(){
        return this.sourceAirport;
    }
    public Airport getDestinationAirport(){
        return this.destinationAirport;
    }
}
