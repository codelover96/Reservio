package service;

import java.util.ArrayList;
import model.Customer;

/**
 * A collection of Customer objects
 * @author codelover96
 */
public class CustomerCollection {
    private ArrayList<Customer> customers = new ArrayList<>();

    /**
     * Get next ID to assign to a new customer
     * @return next ID
     */
    public int getID(){
        return customers.size()+1;
    }
}
