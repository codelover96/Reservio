package model;

/**
 * Theatrical event class
 * Inherits all from Event class and has one more property i.e. the main actor of the Theatrical performance.
 *
 * @author codelover96
 */
public class TheatricalEvent extends Event {
    private String actor;
    public TheatricalEvent(int id, String title, String location, String theaterName, String date, String actor) {
        super(id, title, location, theaterName, date);
        this.actor = actor;
    }

    public void setActor(String actor){
        this.actor=actor;
    }

    public String getActor(){
        return actor;
    }

    @Override
    public String toString(){
        return super.toString() + ", " + actor;
    }

    @Override
    public String toCSV(){
        return super.toCSV() +","+actor;
    }
}
