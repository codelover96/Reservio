package model;


public class Reservation {
    private int customer_id; // ID of customer making a reservation.
    private int event_id; // ID of the event to reserve a ticket.

    /**
     * Create a Reservation object
     * @param customer_id customer's id
     * @param event_id id of reserved event
     */
    public Reservation(int customer_id, int event_id){
        this.customer_id = customer_id;
        this.event_id = event_id;
    }

    /**
     * Get customer's id
     * @return customer's id
     */
    public int getCustomerID() {
        return customer_id;
    }

    /**
     * Get event's id
     * @return event's id
     */
    public int getEventID() {
        return event_id;
    }

    // Note: Methods setCustomerID and set setEventID may not be used, because reservations are final and can not be changed
    /**
     * Set customer id
     * @param customer_id customer's id
     */
    public void setCustomerId(int customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * Set event id
     * @param event_id event's id
     */
    public void setEventId(int event_id) {
        this.event_id = event_id;
    }

    @Override
    public String toString(){
        return customer_id + ", " + event_id;
    }

    public String toCSV(){
        return customer_id +","+ event_id;
    }
}