package model;

/**
 * Represents events. E.g. Theatrical, Musical, Dance or other similar events.
 * Class contains get/set methods for all event properties.
 * An event has an integer ID, a String title, a geographical location, theater name and a date.
 */
public class Event{
    private final int id;
    private String title;
    private String location;
    private String theaterName;
    private String date;

    public Event(int id, String title, String location, String theaterName, String date) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.theaterName = theaterName;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName){
        this.theaterName = theaterName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setLocation(String l){
        this.location = l;
    }

    public String getLocation(){
        return location;
    }

    public void setDate(String d){
        this.date = d;
    }

    public String getDate(){
        return date;
    }

    @Override
    public String toString(){
        return id + ", " + title + ", " + location + ", " + date;
    }

    public String toCSV(){
        return id + "," + title + "," + location + "," + date;
    }

}
