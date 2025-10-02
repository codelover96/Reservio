package model;

public class Customer {
    private int id;
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
        String temp = id + ", " + name;
        return temp;
    }

    public String toCSV(){
        String temp = id + "," + name;
        return temp;
    }
}
