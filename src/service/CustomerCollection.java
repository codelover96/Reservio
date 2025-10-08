package service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import model.Customer;

/**
 * A collection of Customer objects
 * @author codelover96
 */
public class CustomerCollection {
    private ArrayList<Customer> customers = new ArrayList<>();
    private Scanner in = new Scanner(System.in);
    private final File customers_csv_file;

    public CustomerCollection(String resourcesPath, String filename){
        loadCustomersFromCsv(resourcesPath, filename);
        customers_csv_file = new File(Paths.get("resources", "csv", filename).toUri());
    }

    /**
     * Read customers.csv file and store customers to ArrayList
     */
    private void loadCustomersFromCsv(String csvResourcesPath, String customers_csv_file) {
        String name;
        int customer_id;
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(CustomerCollection.class.getResourceAsStream(csvResourcesPath+customers_csv_file)), StandardCharsets.UTF_8))){
            while ((line = br.readLine()) != null && !line.isEmpty()){
                String[] tokens = line.split(",");
                customer_id = Integer.parseInt(tokens[0]);
                name = tokens[1];
                Customer c = new Customer(customer_id, name);
                customers.add(c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Save customers ArrayList to CSV file 'customers.csv'
     */
    public void saveCustomersToCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(customers_csv_file))){
            for (Customer c : customers) {
                bw.write(c.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Cannot open output file " + customers_csv_file);
        }
    }

    /**
     * Add a new customer object to 'customers' ArrayList.
     */
    public void insertNewCustomer() {
        int id = 0;
        String name;
        System.out.println("Give customer ID");
        try {
            id = Integer.parseInt(in.nextLine().trim());
        } catch (InputMismatchException e) {
            System.out.println("Wrong input type...");
            System.exit(1);
        }
        System.out.println("Give customer name...");
        name = in.nextLine().trim();
        if (getCustomerByID(id) != null) {
            // null != null
            System.out.println("This customer already exists.");
            return;
        }
        Customer c = new Customer(id, name);
        customers.add(c);
        System.out.println("New customer added: ");
        System.out.println(c);
    }

    /**
     * Edit a customer with the given ID
     * @param id of customer to make edits.
     */
    public void editCustomerByID(int id) {
        Customer c = getCustomerByID(id);
        if ( c == null ){
            System.out.println("No customer with ID: "+id);
            return;
        }
        System.out.println("Editing: ");
        System.out.println(c);
        System.out.println("...Press enter for no change...");
        System.out.println("New customer name");
        String name = in.nextLine().trim();
        if (!name.isBlank())
            c.setCustomerName(name.trim());
        System.out.println("Updated: " + c);
    }

    /**
     * Get Customer object with α given ID
     * @param customerId the id of the customer to retrieve
     * @return null if not found. Else return the Customer object with α given ID
     */
    public Customer getCustomerByID(int customerId) {
        for (Customer c : customers) {
            if (c.getCustomerId() == customerId)
                return c;
        }
        return null;
    }

    /**
     * Delete a Customer from customers ArrayList
     * @param id of customer to delete
     */
    public void deleteCustomerByID(int id) {
        Customer c = this.getCustomerByID(id);
        if ( c == null ){
            System.out.println("No customer with ID: "+id);
            return;
        }
        System.out.println("Delete? (y/n)");
        System.out.println(c);
        String choice = in.nextLine().trim();
        if (choice.equals("y")) {
            customers.remove(c);
            System.out.println("Deleted!");
        } else {
            System.out.println("Abort...");
        }
    }

    /**
     * Check if a Customer with the given ID exists in the collection
     * @param customerId the customer ID to search for.
     * @return true if exits, false if it does not exist.
     */
    public boolean exists(int customerId){
        return getCustomerByID(customerId) != null;
    }

    /**
     * Get next ID to assign to a new customer
     * @return next ID
     */
    public int getID(){
        return customers.size()+1;
    }
}
