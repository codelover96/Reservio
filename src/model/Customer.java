package model;

/**
 * Customer class
 * @author codelover96
 */
public class Customer {
    private final int id;
    private String name;

    public Customer(int i, String n){
        this.id = i;
        this.name = n;
    }

    public String getCustomerName() {
        return name;
    }

    public void setCustomerName(String name) {
        this.name = name;
    }

    public int getCustomerId() {
        return id;
    }

    @Override
    public String toString(){
        return id + ", " + name;
    }

    public String toCSV(){
        return id + "," + name;
    }
}
