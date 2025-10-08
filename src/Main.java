/*
TODO:
1.σε κάθε εισαγωγή, επεξεργασία και διαγραφή να ελέγξεις εάν υπάρχει βάση id
2.1.Θα πρέπει τις λειτουργίες αναζήτησης, εισαγωγή, διαγραφής, επεξεργασίας να μην είναι μέσα στην main.
2.2.Πρέπει να φτιάξεις collection και να μην είναι μέσα στην main η λογική σου.
3.πως μία μέθοδος αναζήτησης με id μπορεί να εφαρμοστεί σε οποιαδήποτε ArrayList από objects.
4.το id πρέπει να το δίνεις εσύ.

NOTES:
-Βασική ερώτηση -> Πως μπορείς να κάνεις το project καλύτερο
-Δεν πρέπει να ξεφύγεις από το console application
-Ρώτα το chatgpt πως μπορείς να κάνεις καλύτερη την λογική της εφαρμογής π.χ.
    -Οι κρατήσεις να έχουν παραπάνω στοιχεία από customer_id και event_id.
        όπως π.χ. ώρα και μέρα της κράτησης.
    -Το id να μην το δίνει ο χρήστης αλλά ούτε να μπορεί να το αλλάξει.
*/

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;
import model.*;
import ui.Menu;
import service.CustomerCollection;
/**
 * Class Main
 * @author codelover96
 */

public class Main {
    private Scanner in = new Scanner(System.in);
    private List<TheatricalEvent> theatrical_events = new ArrayList<>();
    private List<MusicalEvent> musical_events = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private CustomerCollection customerCollection;

    private final String theatrical_csv = "theatrical.csv";
    private final String musical_csv = "musical.csv";
    private final String reservations_csv = "reservations.csv";
    private final String csvResourcesPath = "/csv/";
    private final File theatrical_csv_file = new File(Paths.get("resources", "csv", theatrical_csv).toUri());
    private final File musical_csv_file = new File(Paths.get("resources", "csv", musical_csv).toUri());
    private final File reservations_csv_file = new File(Paths.get("resources", "csv", reservations_csv).toUri());

    /**
     * app starting point
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main m = new Main();
        m.createObjectCollections(m.csvResourcesPath);
        Menu.printWelcomeMenu();
        m.mainMenu();
    }

    /**
     * Load all data from CSV files
     */
    private void createObjectCollections(String resourcesPath) {
        // CSV FILES
        String customers_csv_filename = "customers.csv";
        customerCollection = new CustomerCollection(resourcesPath, customers_csv_filename);
        loadMusicalFromCsv();
        loadTheatricalFromCsv();
        loadReservationsFromCsv();
    }

    /**
     * Store all data from ArrayLists to CSV files
     */
    private void saveCollectionsToCsv() {
        customerCollection.saveCustomersToCsv();
        saveMusicalToCsv();
        saveTheatricalToCsv();
        saveReservationsToCsv();
    }

    /**
     * Save musical_events ArrayList to CSV file 'musical.csv'
     */
    private void saveMusicalToCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(musical_csv_file))){
            for (MusicalEvent m : musical_events) {
                bw.write(m.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Cannot open output file " + musical_csv_file);
        }
    }

    /**
     * Save theatrical_events ArrayList to CSV 'theatrical.csv'
     */
    private void saveTheatricalToCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(theatrical_csv_file))){
            for (TheatricalEvent t : theatrical_events) {
                bw.write(t.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Cannot open output file " + theatrical_csv_file);
        }
    }

    /**
     * Save reservations ArrayList to CSV file 'reservations.csv'
     */
    private void saveReservationsToCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(reservations_csv_file))){
            for (Reservation r : reservations) {
                bw.write(r.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Cannot open output file " + reservations_csv_file);
        }
    }



    /**
     * Read theatrical.csv file and store theatrical events to ArrayList
     */
    private void loadTheatricalFromCsv() {
        int id;
        String title;
        String theater_name;
        String location;
        String date;
        String actor;
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Main.class.getResourceAsStream(csvResourcesPath+theatrical_csv)), StandardCharsets.UTF_8))){
            while( (line = br.readLine()) != null && !line.isEmpty()){
                String[] tokens = line.split("");
                id = Integer.parseInt(tokens[0]);
                title = tokens[1];
                theater_name = tokens[2];
                location = tokens[3];
                date = tokens[4];
                actor = tokens[5];
                TheatricalEvent t = new TheatricalEvent(id, title, theater_name, location, date, actor);
                theatrical_events.add(t);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read musical.csv file and store musical events to ArrayList
     */
    private void loadMusicalFromCsv() {
        int id;
        String title;
        String theater_name;
        String location;
        String date;
        String singer;
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Main.class.getResourceAsStream(csvResourcesPath+musical_csv)), StandardCharsets.UTF_8))){
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                String[] tokens = line.split(",");
                id = Integer.parseInt(tokens[0]);
                title = tokens[1];
                theater_name = tokens[2];
                location = tokens[3];
                date = tokens[4];
                singer = tokens[5];
                MusicalEvent m = new MusicalEvent(id, title, theater_name, location, date, singer);
                musical_events.add(m);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read the reservations.csv file and store reservations to ArrayList
     */
    private void loadReservationsFromCsv() {
        int customer_id;
        int event_id;
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Main.class.getResourceAsStream(csvResourcesPath+reservations_csv)), StandardCharsets.UTF_8))){
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                String[] tokens = line.split(",");
                customer_id = Integer.parseInt(tokens[0]);
                event_id = Integer.parseInt(tokens[1]);
                Reservation r = new Reservation(customer_id, event_id);
                reservations.add(r);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Display main menu and choose operation
     */
    private void mainMenu() {
        int choice;
        do {
            Menu.printMainMenu();
            System.out.println("Choose: ");
            try {
                choice = Integer.parseInt(in.nextLine().trim());
            } catch (InputMismatchException e) {
                System.out.println("Wrong input type: "+e);
                break;
            }
            switch (choice) {
                case 1:
                    // Εισαγωγή, Διόρθωση, Διαγραφή Θεατρικών Παραστάσεων
                    manageTheatricalEvents();
                    break;
                case 2:
                    // Εισαγωγή, Διόρθωση, Διαγραφή Μουσικών Παραστάσεων
                    manageMusicalEvents();
                    break;
                case 3:
                    // Εισαγωγή, Διαγραφή Πελατών, Διόρθωση Ονόματος Πελάτη
                    manageCustomers();
                    break;
                case 4:
                    // Κράτηση εισιτηρίου
                    manageReservations();
                    break;
                case 5:
                    // Εμφάνιση κρατήσεων ανά παράσταση
                    break;
                case 6:
                    //
                    System.out.println("Save and Exit...");
                    saveCollectionsToCsv();
                    in.close();
            }
        } while (choice != 6);
    }

    /**
     * Display theatrical events menu and choose function.
     */
    private void manageTheatricalEvents() {
        int choice;
        int id;
        do {
            System.out.println("=== Theatrical Event Management ===");
            Menu.printManagerMenu();
            System.out.println("Επιλογή: ");

            try {
                choice = Integer.parseInt(in.nextLine().trim());
            } catch (InputMismatchException e) {
                System.out.println("Wrong input type...");
                break;
            }

            switch (choice) {
                case 1:
                    // εισαγωγή στοιχείων θεατρικής παράστασης ένα προς ένα.
                    System.out.println("Enter new theatrical event...");
                    insertNewTheatrical();
                    saveTheatricalToCsv();
                    break;
                case 2:
                    // επεξεργασία στοιχείων θεατρικής παράστασης
                    System.out.println("Edit theatrical event...");
                    System.out.println("Give ID:");
                    id = Integer.parseInt(in.nextLine().trim());
                    editTheatricalByID(id);
                    saveTheatricalToCsv();
                    break;
                case 3:
                    // επιλογή θεατρικής παράστασης προς διαγραφή και διαγραφή της
                    System.out.println("Give ID:");
                    id = Integer.parseInt(in.nextLine().trim());
                    deleteTheatricalByID(id);
                    saveTheatricalToCsv();
                    break;
                case 4:
                    // προβολή όλων το θεατρικών παραστάσεων
                    printAllTheatrical();
                    break;
            }
        } while (choice != 5);
    }

    /**
     * Display musical event menu and choose function
     */
    private void manageMusicalEvents() {
        int choice;
        int id;
        do {
            System.out.println("=== Musical Event Management ===");
            Menu.printManagerMenu();
            System.out.println("Choose: ");
            try {
                choice = Integer.parseInt(in.nextLine().trim());
            } catch (InputMismatchException e) {
                System.out.println("Wrong input type...");
                break;
            }
            switch (choice) {
                case 1:
                    insertNewMusical();
                    saveMusicalToCsv();
                    break;
                case 2:
                    System.out.println("Edit musical event...");
                    System.out.println("Give ID:");
                    id = Integer.parseInt(in.nextLine().trim());
                    editMusicalByID(id);
                    saveMusicalToCsv();
                    break;
                case 3:
                    System.out.println("Give ID:");
                    id = Integer.parseInt(in.nextLine().trim());
                    deleteMusicalByID(id);
                    saveMusicalToCsv();
                    break;
                case 4:
                    printAllMusical();
                    break;
            }
        } while (choice != 5);
    }

    /**
     * Add a new Musical event to 'musical_events' ArrayList
     */
    public void insertNewMusical() {
        int id = 0;
        String title;
        String theater_name;
        String location;
        String date;
        String singer;
        System.out.println("Enter Musical event ID:");
        try {
            id = Integer.parseInt(in.nextLine().trim());
        } catch (InputMismatchException e) {
            System.out.println("Wrong input type...");
            System.exit(1);
        }

        System.out.println("Enter musical event title...");
        title = in.nextLine().trim();
        // check if already exists in musical_events
        if (getMusicalByID(id) != null) {
            System.out.println("Musical event with ID "+id+" already exists.");
            return;
        }
        System.out.println("Enter theater name:");
        theater_name = in.nextLine().trim();

        System.out.println("Enter location:");
        location = in.nextLine().trim();

        System.out.println("Enter date:");
        System.out.println("(in ΥΥYY-ΜΜ-DD format)");
        date = in.nextLine().trim();

        System.out.println("Enter singer's name:");
        singer = in.nextLine().trim();
        MusicalEvent m = new MusicalEvent(id, title, theater_name, location, date, singer);
        musical_events.add(m);
        System.out.println("New musical event added: ");
        System.out.println(m);
    }

    /**
     * Add a new Theatrical event to 'theatrical_events' ArrayList
     */
    public void insertNewTheatrical() {
        int id = 0;
        String title;
        String theater_name;
        String location;
        String date;
        String actor;
        System.out.println("Enter Theatrical event ID:");
        try {
            id = Integer.parseInt(in.nextLine().trim());
        } catch (InputMismatchException e) {
            System.out.println("Wrong input format...");
            System.exit(1);
        }

        // check if already exists in theatrical_events
        if (getTheatricalByID(id) != null) {
            System.out.println("Theatrical event with ID "+id+" already exists.");
            return;
        }

        System.out.println("Enter theater event title");
        title = in.nextLine().trim();

        System.out.println("Enter theater name");
        theater_name = in.nextLine().trim();

        System.out.println("Enter event location");
        location = in.nextLine().trim();


        System.out.println("Enter new date:");
        System.out.println("(in ΥΥYY-ΜΜ-DD format)");
        date = in.nextLine().trim();
        System.out.println("Enter actor name:");
        actor = in.nextLine().trim();
        TheatricalEvent t = new TheatricalEvent(id, title,theater_name, location, date, actor);
        theatrical_events.add(t);
        System.out.println("Added new theatrical event:");
        System.out.println(t);
    }

    /**
     * Edit a theatrical event with a given I D
     * @param id of theatrical event to make edits
     */
    public void editTheatricalByID(int id) {
        TheatricalEvent t = getTheatricalByID(id);
        if ( t == null ){
            System.out.println("No theatrical event with ID: "+id);
            return;
        }

        System.out.println("New title:");
        String title = in.nextLine().trim();
        if (!title.isBlank())
            t.setTitle(title.trim());

        System.out.println("New theater name:");
        String theater_name = in.nextLine().trim();
        if (!theater_name.isBlank())
            t.setTheaterName(theater_name.trim());

        System.out.println("New location:");
        String location = in.nextLine().trim();
        if (!location.isBlank())
            t.setLocation(location);

        System.out.println("New date:");
        String date = in.nextLine().trim();
        if (!date.isBlank())
            t.setDate(date);

        System.out.println("New actor:");
        String actor = in.nextLine().trim();
        if (!actor.isBlank())
            t.setActor(actor);

        System.out.println("Updated:" + t);
    }

    /**
     * Edit a musical event with α given ID
     * @param id of musical event to make edits
     */
    public void editMusicalByID(int id) {
        MusicalEvent m = getMusicalByID(id);
        if (m == null) {
            System.out.println("No musical event with ID: " + id);
            return;
        }
        System.out.println("Edit: ");
        System.out.println(m.toString().trim());
        System.out.println("...Press enter for no change...");
        System.out.println("New Title:");
        String title = in.nextLine().trim();
        if (!title.isBlank())
            m.setTitle(title.trim());

        System.out.println("New theater name:");
        String theater_name = in.nextLine().trim();
        if (!theater_name.isBlank())
            m.setTheaterName(theater_name);

        System.out.println("New location:");
        String location = in.nextLine().trim();
        if (!location.isBlank())
            m.setLocation(location);

        System.out.println("Νew date:");
        String date = in.nextLine().trim();
        if (!date.isBlank())
            m.setDate(date);

        System.out.println("New singer:");
        String singer = in.nextLine().trim();
        if (!singer.isBlank())
            m.setSinger(singer);

        System.out.println("Updated: " + m);
    }

    /**
     * Delete a theatrical event from 'theatrical_events' ArrayList
     * @param id of theatrical event to delete
     */
    public void deleteTheatricalByID(int id) {
        TheatricalEvent t = getTheatricalByID(id);
        if ( t == null ){
            System.out.println("No theatrical event with ID: "+id);
            return;
        }
        System.out.println("Delete; (y/n)");
        System.out.println(t);
        String choice = in.nextLine().trim();
        if (choice.equals("y")) {
            theatrical_events.remove(t);
            System.out.println("Deleted!");
        } else {
            System.out.println("Abort...");
        }
    }

    /**
     * Delete a musical event from 'musical_events' ArrayList
     * @param id of musical event to delete
     */
    public void deleteMusicalByID(int id) {
        MusicalEvent m = getMusicalByID(id);
        if ( m == null ){
            System.out.println("No musical event with ID: "+id);
            return;
        }
        System.out.println("Delete? (y/n)");
        System.out.println(m);
        String choice = in.nextLine().trim();
        if (choice.equals("y")) {
            musical_events.remove(m);
            System.out.println("Delete!");
        } else {
            System.out.println("Abort...");
        }
    }



    /**
     * Get Theatrical event by given ID
     * @param eventID The id of the event to retrieve
     * @return Null if not found. Else return the Theatrical object with α given ID
     */
    public TheatricalEvent getTheatricalByID(int eventID) {
        for (TheatricalEvent t : theatrical_events)
            if (t.getId() == eventID)
                return t;
        return null;
    }

    /**
     * Get Musical event by given ID
     * @param eventID The id of the event to retrieve
     * @return Null if not found. Else return the Musical object with α given ID
     */
    public MusicalEvent getMusicalByID(int eventID) {
        for (MusicalEvent m : musical_events) {
            if (m.getId() == eventID)
                return m;
        }
        return null;
    }



    /**
     * Customer Management.
     * Display a user menu and select customer operation.
     */
    private void manageCustomers() {
        int id;
        int choice;
        do {
            Menu.printCustomerManagerMenu();
            System.out.println("Choose: ");
            try {
                choice = Integer.parseInt(in.nextLine().trim());
            }catch (NumberFormatException e) {
                System.out.println("Wrong input type...");
                break;
            }
            switch (choice) {
                case 1:
                    customerCollection.insertNewCustomer();
                    customerCollection.saveCustomersToCsv();
                    break;
                case 2:
                    System.out.println("Edit customer...");
                    System.out.println("Give customer ID:");
                    try {
                        id = Integer.parseInt(in.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong input format...");
                        break;
                    }
                    customerCollection.editCustomerByID(id);
                    customerCollection.saveCustomersToCsv();
                    break;
                case 3:
                    System.out.println("Delete...");
                    System.out.println("Give customer ID to delete: ");
                    id = Integer.parseInt(in.nextLine().trim());
                    customerCollection.deleteCustomerByID(id);
                    customerCollection.saveCustomersToCsv();
                    break;
            }
        } while (choice != 4);
    }


    /**
     * Manage reservations
     * Display the user menu and select α desired operation.
     */
    private void manageReservations() {
        int choice;
        do {
            Menu.printReservationManagerMenu();
            System.out.println("Choose: ");
            try {
                choice = Integer.parseInt(in.nextLine().trim());
            } catch (InputMismatchException e) {
                System.out.println("Wrong input type... " + e);
                break;
            }
            switch (choice) {
                case 1:
                    reserveTheatrical();
                    saveReservationsToCsv();
                    break;
                case 2:
                    reserveMusical();
                    saveReservationsToCsv();
                    break;
            }
        } while (choice != 3);
    }

    /**
     * Make a reservation to a theatrical event.
     */
    public void reserveTheatrical() {

        System.out.println("Theatrical event reservation");
        int event_id = 0;
        int customer_id = 0;
        System.out.println("Give event ID:");
        try {
            event_id = Integer.parseInt(in.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Wrong input type...");
            System.exit(1);
        }

        TheatricalEvent t = getTheatricalByID(event_id);
        if (t == null) {
            System.out.println("Theatrical event with ID " + event_id + " does not exist.");
            return;
        }

        System.out.println("Δώσε το ID του πελάτη:");
        try{
            customer_id = Integer.parseInt(in.nextLine().trim());
        }catch (NumberFormatException e){
            System.out.println("Wrong input format...");
            System.exit(1);
        }

        if (customerCollection.exists(customer_id)) {
            System.out.println("Customer with ID " + customer_id + " does not exist.");
            return;
        }

        Reservation n = new Reservation(customer_id, event_id);
        reservations.add(n);
        System.out.println("Successful Reservation!");
    }

    /**
     * Make a reservation to a musical event
     */
    public void reserveMusical() {
        System.out.println("Musical event reservation");
        int event_id = 0;
        int customer_id;
        System.out.println("Give event ID:");
        try {
            event_id = Integer.parseInt(in.nextLine().trim());
        } catch (InputMismatchException e) {
            System.out.println("Wrong input type...");
            System.exit(1);
        }
        MusicalEvent m = getMusicalByID(event_id);
        if (m == null) {
            System.out.println("Musical event with ID " + event_id + " does not exist.");
            return;
        }
        System.out.println("Give customer's ID:");
        customer_id = Integer.parseInt(in.nextLine().trim());
        if (customerCollection.exists(customer_id)) {
            System.out.println("Customer with ID " + customer_id + " does not exist.");
            return;
        }
        Reservation n = new Reservation(customer_id, event_id);
        reservations.add(n);
        System.out.println("Successful Reservation!");
    }

    /**
     * Print all Theatrical events
     */
    public void printAllTheatrical(){
        for (TheatricalEvent t : theatrical_events){
            System.out.println(t);
        }
    }

    /**
     * Print all Musical events
     */
    public void printAllMusical(){
        for (MusicalEvent m : musical_events){
            System.out.println(m);
        }
    }
}