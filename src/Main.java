/*
TODO:
1.σε κάθε εισαγωγή, επεξεργασία και διαγραφή να ελέγξεις εάν υπάρχει βάση id
2.1.Θα πρέπει τις λειτουργίες αναζήτησης, εισαγωγή, διαγραφής, επεξεργασίας να μην είναι μέσα στην main.
2.2.Πρέπει να φτιάξεις collection και να μην είναι μέσα στην main η λογική σου.
3.πως μία μέθοδος αναζήτησης με id μπορεί να εφαρμοστεί σε οποιαδήποτε ArrayList από objects.
4.το id πρέπει να το δίνεις εσύ.
5.Αντί για nextInt να το αντικαταστήσεις με nextLine και να κάνεις parseInt!
6.Αφαίρεσε όλα τα στοιχεία που προδίδουν το project.
    6.1. Άλλαξε ονόματα στις κλάσεις, στις μεθόδους και στις μεταβλητές
    6.2. Αντικατέστησε το javadoc από τα ελληνικά στα αγγλικά.
    6.3. Βάλε άλλα τυχαία δεδομένα στα αρχεία csv
8.Dont use printWriter instead use BufferedReader
9.Όλες οι μέθοδοι που διαβάζουν/γράφουν στα csv να γίνουν όπως η loadCustomersCsv()

NOTES
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

public class Main {
    // Αρχικοποίηση μεταβλητών
    private Scanner in = new Scanner(System.in);
    private ArrayList<Customer> customers = new ArrayList<Customer>();
    private ArrayList<TheatricalEvent> theatrical_events = new ArrayList<TheatricalEvent>();
    private ArrayList<MusicalEvent> musical_events = new ArrayList<MusicalEvent>();
    private ArrayList<Reservation> reservations = new ArrayList<Reservation>();

    // CSV FILES
    private final String customers_csv = "customers.csv";
    private final String theatrical_csv = "theatrical.csv";
    private final String musical_csv = "musical.csv";
    private final String reservations_csv = "reservations.csv";
    private final String csvResourcesPath = "/csv/";
    private final File customers_csv_file = new File(Paths.get("resources", "csv", customers_csv).toUri());
    private final File theatrical_csv_file = new File(Paths.get("resources", "csv", theatrical_csv).toUri());
    private final File musical_csv_file = new File(Paths.get("resources", "csv", musical_csv).toUri());
    private final File reservations_csv_file = new File(Paths.get("resources", "csv", reservations_csv).toUri());

    /**
     * Η κύρια μέθοδος για την έναρξη της εφαρμογής
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main m = new Main();
        m.loadAllFromCsv();
        System.out.println("Διαχείριση Θεατρικών και Μουσικών Παραστάσεων");
        System.out.println();
        m.mainMenu();
    }

    /**
     * Αρχικοποίηση όλων των δεδομένων από τα αρχεία CSV.
     * Δηλαδή των πελατών, των παραστάσεων και των κρατήσεων.
     */
    private void loadAllFromCsv() {
        loadCustomersCsv();
        loadMusicalCsv();
        loadTheatricalCsv();
        loadReservationsCsv();
    }

    /**
     * Αποθήκευση των Arraylist που δημιουργήσαμε σε CSV αρχεία,
     * για τους Πελάτες, τις Μουσικές, τις Θεατρικές παραστάσεις αλλά και για τους
     * Πελάτες.
     */
    private void saveToCsv() {
        saveCustomersToCsv();
        saveMusicalToCsv();
        saveTheatricalToCsv();
        saveReservationsToCsv();
    }

    /**
     * Αποθήκευση των πελατών στο αρχείο CSV 'customers.csv'
     */
    private void saveCustomersToCsv() {
        try {
            // Ο PrintWriter χρησιμοποιείται για την εκτύπωση ενός αντικειμένου σε κάποιο
            // αρχείο.
            // Εδώ εκτυπώνουμε ένα-ένα τα περιεχόμενα του ArrayList customers στο αρχείο
            // customers.csv
            PrintWriter pr = new PrintWriter(customers_csv_file);
            {
                for (Customer c : customers) {
                    pr.println(c.toCSV());
                }
            }
            pr.close();// κλείνει το αρχείο
            // Εάν δε βρεθεί το αρχείο customers.csv, πέτα το παρακάτω exception.
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open output file " + customers_csv_file);
        }
    }

    /**
     * Αποθήκευση των μουσικών παραστάσεων στο αρχείο CSV 'musical.csv'
     */
    private void saveMusicalToCsv() {
        try {
            PrintWriter pr = new PrintWriter(musical_csv_file);
            {
                for (MusicalEvent m : musical_events) {
                    pr.println(m.toCSV());
                }
            }
            pr.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open output file " + musical_csv_file);
        }
    }

    /**
     * Αποθήκευση των θεατρικών παραστάσεων στο αρχείο CSV 'theatrical.csv'
     */
    private void saveTheatricalToCsv() {
        try {
            PrintWriter pr = new PrintWriter(theatrical_csv_file);
            {
                for (TheatricalEvent t : theatrical_events) {
                    pr.println(t.toCSV());
                }
            }
            pr.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open output file " + theatrical_csv_file);
        }
    }

    /**
     * Αποθήκευση των κρατήσεων στο αρχείο CSV 'reservations.csv'
     */
    private void saveReservationsToCsv() {
        try {
            PrintWriter pr = new PrintWriter(reservations_csv_file);
            {
                for (Reservation r : reservations) {
                    pr.println(r.toCSV());
                }
            }
            pr.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open output file " + reservations_csv_file);
        }
    }

    /**
     * Load customers from CSV file
     */
    private void loadCustomersCsv() {
        String name = null;
        int customer_id = 0;
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Main.class.getResourceAsStream(csvResourcesPath+customers_csv)), StandardCharsets.UTF_8))){
            while ((line = br.readLine()) != null && !line.isEmpty()){
                // δεν χρειαζόμαστε το \r\n επειδή το readLine του BufferedReader ούτως ή άλλως επιστρέφει γραμμή-γραμμή
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
     * Load theatrical events from CSV file
     */
    private void loadTheatricalCsv() {
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
     * Load Musical events from CSV file
     */
    private void loadMusicalCsv() {
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
     * Load Reservations from CSV file
     */
    private void loadReservationsCsv() {
        /*
         * το πρώτο νούμερο είναι το ID του πελάτη που έχει κάνει κράτηση
         * το δεύτερο νούμερο είναι το ID του Event που έχει γίνει κράτηση
         * ΟΠΟΤΕ:
         * Ο πελάτης με ID 1 έχει κάνει κράτηση στην παράσταση με ID 2
         * Ο πελάτης με ID 1 έχει κάνει κράτηση στην παράσταση με ID 3
         */
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
     * Εκτύπωση του αρχικού μενού και επιλογή λειτουργίας από τον χρήστη.
     */
    private void mainMenu() {
        int choice = 0;
        do {
            Menu.printMainMenu();
            System.out.println("Επιλογή: ");
            // έλεγχος εάν o χρήστης έχει πληκτρολογήσει κάτι άλλο εκτός από ακέραιο πχ χαρακτήρα
            try {
                choice = Integer.parseInt(in.nextLine().trim()); // choice μπορεί να είναι από το 1 έως το 6
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
                    System.out.println("Αποθήκευση και έξοδος...");
                    saveToCsv();
                    in.close();
            }
        } while (choice != 6); // όσο ο χρήστης επιλέγει οτιδήποτε άλλο εκτός από 6
    }

    /**
     * Display theatrical event menu and call the corresponding method
     */
    private void manageTheatricalEvents() {
        int choice;
        int id;
        do {
            System.out.println("=== Διαχείριση Θεατρικών Παραστάσεων ===");
            Menu.printManagerMenu();
            System.out.println("Επιλογή: ");

            try {
                choice = Integer.parseInt(in.nextLine().trim());
            } catch (InputMismatchException e) {
                System.out.println("Wrong input format...");
                break;
            }

            switch (choice) {
                case 1:
                    // εισαγωγή στοιχείων θεατρικής παράστασης ένα προς ένα.
                    System.out.println("Εισαγωγή νέας θεατρικής παράστασης...");
                    insertNewTheatrical();
                    saveTheatricalToCsv();
                    break;
                case 2:
                    // επεξεργασία στοιχείων θεατρικής παράστασης
                    System.out.println("Επεξεργασία στοιχείων θεατρικής παράστασης...");
                    System.out.println("Δώσε το ID της θεατρικής παράστασης:");
                    id = Integer.parseInt(in.nextLine().trim());
                    editTheatricalByID(id);
                    saveTheatricalToCsv();
                    break;
                case 3:
                    // επιλογή θεατρικής παράστασης προς διαγραφή και διαγραφή της
                    System.out.println("Δώσε το ID της θεατρικής παράστασης:");
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
     * Display musical event menu and call the appropriate method
     */
    private void manageMusicalEvents() {
        int choice;
        int id;
        MusicalEvent m;
        do {
            System.out.println("Μουσικές Παραστάσεις");
            Menu.printManagerMenu();
            System.out.println("Επιλογή: ");
            try {
                choice = Integer.parseInt(in.nextLine().trim());
            } catch (InputMismatchException e) {
                System.out.println("Wrong input format...");
                break;
            }
            switch (choice) {
                case 1:
                    // εισαγωγή στοιχείων μουσικής παράστασης ένα προς ένα.
                    insertNewMusical();
                    saveMusicalToCsv();
                    break;
                case 2:
                    // επεξεργασία στοιχείων μουσικής παράστασης
                    System.out.println("Επεξεργασία στοιχείων μουσικής παράστασης...");
                    System.out.println("Δώσε το ID της μουσικής παράστασης:");
                    id = Integer.parseInt(in.nextLine().trim());
                    editMusicalByID(id);
                    saveMusicalToCsv();
                    break;
                case 3:
                    // επιλογή μουσικής παράστασης προς διαγραφή και διαγραφή της
                    System.out.println("Δώσε το ID της μουσικής παράστασης:");
                    id = Integer.parseInt(in.nextLine().trim());
                    deleteMusicalByID(id);
                    saveMusicalToCsv();
                    break;
                case 4:
                    // προβολή όλων το μουσικών παραστάσεων
                    int i;
                    for (i = 0; i < musical_events.size(); i++) {
                        m = musical_events.get(i);
                        System.out.println(m.toString());
                    }
                    break;
            }
        } while (choice != 5);
    }

    /**
     * Add new Musical event to ArrayList musical_events
     */
    public void insertNewMusical() {
        int id = 0;
        String title;
        String theater_name;
        String location;
        String date;
        String singer;
        System.out.println("Εισάγετε το ID:");
        try {
            id = Integer.parseInt(in.nextLine().trim());
        } catch (InputMismatchException e) {
            System.out.println("Wrong input format...");
            System.exit(1);
        }

        System.out.println("Εισάγετε τον τίτλο της μουσικής παράστασης...");
        title = in.nextLine().trim();
        // check if already exists in musical_events
        if (getMusicalByID(id) != null) {
            System.out.println("Αυτή η μουσική παράσταση υπάρχει ήδη.");
            return;
        }
        System.out.println("Εισάγετε τo όνομα του θεάτρου");
        theater_name = in.nextLine().trim();

        System.out.println("Εισάγετε την τοποθεσία της παράστασης");
        location = in.nextLine().trim();

        System.out.println("Εισάγετε την ημερομηνία της παράστασης");
        System.out.println("(σε μορφή ΥΥYY-ΜΜ-DD)");
        date = in.nextLine().trim();

        System.out.println("Εισάγετε το όνομα του πρωταγωνιστή");
        singer = in.nextLine().trim();
        MusicalEvent m = new MusicalEvent(id, title, theater_name, location, date, singer);
        musical_events.add(m);
        System.out.println("Εισάγατε νέα μουσική παράσταση:");
        System.out.println(m.toString());
    }

    /**
     * Add new Theatrical event to Arraylist theatrical_events
     */
    public void insertNewTheatrical() {
        int id = 0;
        String title;
        String theater_name;
        String location;
        String date;
        String actor;
        System.out.println("Εισάγετε το ID της νέας θεατρικής παράστασης:");
        try {
            id = Integer.parseInt(in.nextLine().trim());
        } catch (InputMismatchException e) {
            System.out.println("Wrong input format...");
            System.exit(1);
        }

        // check if already exists in theatrical_events
        if (getTheatricalByID(id) != null) {
            System.out.println("Αυτή η θεατρική παράσταση υπάρχει ήδη.");
            return;
        }

        System.out.println("Εισάγετε τον τίτλο της θεατρικής παράστασης...");
        title = in.nextLine().trim();

        System.out.println("Εισάγετε το όνομα του θεάτρου");
        theater_name = in.nextLine().trim();

        System.out.println("Εισάγετε την τοποθεσία της παράστασης");
        location = in.nextLine().trim();


        System.out.println("Εισάγετε την ημερομηνία της παράστασης");
        System.out.println("(σε μορφή ΥΥYY-ΜΜ-DD)");
        date = in.nextLine().trim();
        System.out.println("Εισάγετε το όνομα του πρωταγωνιστή");
        actor = in.nextLine().trim();
        TheatricalEvent t = new TheatricalEvent(id, title,theater_name, location, date, actor);
        theatrical_events.add(t);
        System.out.println("Εισάγατε νέα θεατρική παράσταση:");
        System.out.println(t.toString());
    }

    /**
     * Edit a theatrical event with given ID
     *
     * @param id of theatrical event to make edits
     */
    public void editTheatricalByID(int id) {
        TheatricalEvent t = getTheatricalByID(id);
        if ( t == null ){
            System.out.println("Δεν υπάρχει Θεατρική Παράσταση με ID: "+id);
            return;
        }
        // System.out.println("Επεξεργασία:");
        // System.out.println(t.toString());

        System.out.println("Νέος τίτλος:");
        String title = in.nextLine().trim();
        if (!title.isBlank())
            t.setTitle(title.trim());

        System.out.println("Νέο όνομα θεάτρου:");
        String theater_name = in.nextLine().trim();
        if (!theater_name.isBlank())
            t.setTheaterName(theater_name.trim());

        System.out.println("Νέα τοποθεσία:");
        String location = in.nextLine().trim();
        if (!location.isBlank())
            t.setLocation(location);

        System.out.println("Νέα ημερομηνία:");
        String date = in.nextLine().trim();
        if (!date.isBlank())
            t.setDate(date);

        System.out.println("Νέα πρωταγωνιστής:");
        String actor = in.nextLine().trim();
        if (!actor.isBlank())
            t.setActor(actor);

        System.out.println("Ενημερώθηκε:" + t.toString());
    }

    /**
     * Edit a musical event with given ID
     *
     * @param id of musical event to make edits
     */
    public void editMusicalByID(int id) {
        MusicalEvent m = getMusicalByID(id);
        if (m == null) {
            System.out.println("Δεν υπάρχει Μουσική Παράσταση με ID: " + id);
            return;
        }
        System.out.println("Επεξεργασία: ");
        System.out.println(m.toString().trim());
        System.out.println("Πάτησε enter για καμία αλλαγή...");
        System.out.println("Νέος τίτλος:");
        String title = in.nextLine().trim();
        System.out.println("new title is: " + title);
        if (!title.isBlank())
            m.setTitle(title.trim());

        System.out.println("Νέo όνομα θεάτρου:");
        String theater_name = in.nextLine().trim();
        if (!theater_name.isBlank())
            m.setTheaterName(theater_name);

        System.out.println("Νέα τοποθεσία:");
        String location = in.nextLine().trim();
        if (!location.isBlank())
            m.setLocation(location);

        System.out.println("Νέα ημερομηνία:");
        String date = in.nextLine().trim();
        if (!date.isBlank())
            m.setDate(date);

        System.out.println("Νέα πρωταγωνιστής:");
        String singer = in.nextLine().trim();
        if (!singer.isBlank())
            m.setSinger(singer);

        System.out.println("Ενημερώθηκε: " + m.toString());
    }

    /**
     * Delete a theatrical event from ArrayList theatrical_events
     * @param id of theatrical event to delete
     */
    public void deleteTheatricalByID(int id) {
        TheatricalEvent t = getTheatricalByID(id);
        if ( t == null ){
            System.out.println("Δεν υπάρχει Θεατρική Παράσταση με ID: "+id);
            return;
        }
        System.out.println("Διαγραφή; (y/n)");
        System.out.println(t.toString());
        String choice = in.nextLine().trim();
        if (choice.equals("y")) {
            theatrical_events.remove(t);
            System.out.println("Διαγράφηκε!");
        } else {
            System.out.println("Ακυρώθηκε...");
        }
    }

    /**
     * Delete a musical event from ArrayList musical_events
     *
     * @param id of musical event to delete
     */
    public void deleteMusicalByID(int id) {
        MusicalEvent m = getMusicalByID(id);
        if ( m == null ){
            System.out.println("Δεν υπάρχει Μουσική Παράσταση με ID: "+id);
            return;
        }
        System.out.println("Διαγραφή; (y/n)");
        System.out.println(m.toString());
        String choice = in.nextLine().trim();
        if (choice.equals("y")) {
            musical_events.remove(m);
            System.out.println("Διαγράφηκε!");
        } else {
            System.out.println("Ακυρώθηκε...");
        }
    }

    /**
     * Delete a customer from ArrayList customers.
     *
     * @param id of customer to delete
     */
    public void deleteCustomerByID(int id) {
        Customer c = getCustomerByID(id);
        if ( c == null ){
            System.out.println("Δεν υπάρχει Πελάτης με ID: "+id);
            return;
        }
        System.out.println("Διαγραφή; (y/n)");
        System.out.println(c.toString());
        String choice = in.nextLine().trim();
        if (choice.equals("y")) {
            customers.remove(c);
            System.out.println("Διαγράφηκε!");
        } else {
            System.out.println("Ακυρώθηκε...");
        }
    }

    /**
     * Get a theatrical event by given ID
     *
     * @param eventID The id of the event to retrieve
     * @return Null if not found. Else return the Theatrical object with given ID
     */
    public TheatricalEvent getTheatricalByID(int eventID) {
        for (TheatricalEvent t : theatrical_events)
            if (t.getId() == eventID)
                return t;
        return null;
    }

    /**
     * Get a musical event by given ID
     *
     * @param eventID The id of the event to retrieve
     * @return Null if not found. Else return the Musical object with given ID
     */
    public MusicalEvent getMusicalByID(int eventID) {
        for (MusicalEvent m : musical_events) {
            if (m.getId() == eventID)
                return m;
        }
        return null;
    }

    /**
     * Get a customer object with given ID
     *
     * @param customerId the id of the customer to retrtive
     * @return null if not found. Else return the Customer object with given ID
     */
    public Customer getCustomerByID(int customerId) {
        // Για κάθε πελάτη c μέσα στο ArrayList customers
        for (Customer c : customers) {
            if (c.getCustomerId() == customerId)
                return c;
        }
        return null;
    }

    /**
     * Manage Customers.
     * Display a user menu to select desired operation to customer data.
     */
    private void manageCustomers() {
        int id = 0;
        int choice = 0;
        do {
            Menu.printCustomerManagerMenu();
            System.out.println("Επιλογή: ");
            try {
                // διαβάζουμε την είσοδο, αφαιρούμε τα κενά και μετατρέπουμε σε ακέραιο.
                choice = Integer.parseInt(in.nextLine().trim());
            }catch (NumberFormatException e) {
                // όταν δεν μπορούμε να μετατρέψουμε τη συμβολοσειρά σε ακέραιο.
                System.out.println("Wrong input format...");
                break; // σταματάει την τρέχουσα επάναληψη
            }
            // ανάλογα την επιλογή που δίνει ο χρήστης, εκτελείται το αντίστοιχο case
            switch (choice) {
                case 1:
                    // εισαγωγή νέου πελάτη και διάβασμα ένα προς ένα τα στοιχεία του από την κονσόλα.
                    insertNewCustomer();
                    saveCustomersToCsv();
                    break;
                case 2:
                    // επεξεργασία στοιχείων πελάτη
                    System.out.println("Επεξεργασία στοιχείων πελάτη");
                    System.out.println("Δώσε το ID του πελάτη:");
                    try {
                        id = Integer.parseInt(in.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong input format...");
                        break;
                    }
                    editCustomerByID(id);
                    saveCustomersToCsv();
                    break;
                case 3:
                    System.out.println("Διαγραφή πελάτη");
                    System.out.println("Δώσε το ID του πελάτη:");
                    id = Integer.parseInt(in.nextLine().trim());
                    deleteCustomerByID(id);
                    saveCustomersToCsv();
                    break;
            }
        } while (choice != 4);
    }

    /**
     * Add new customer object to ArrayList customers.
     */
    public void insertNewCustomer() {
        int id = 0;
        String name;
        System.out.println("Εισάγετε το ID του πελάτη:");
        try {
            id = Integer.parseInt(in.nextLine().trim());
        } catch (InputMismatchException e) {
            System.out.println("Wrong input format...");
            System.exit(1);
        }
        System.out.println("Εισάγετε το όνομα του πελάτη...");
        name = in.nextLine().trim();
        // Ελέγχουμε εάν ο πελάτης με τάδε ID, υπάρχει ήδη.
        if (getCustomerByID(id) != null) {
            // null != null
            System.out.println("Αυτός ο πελάτης υπάρχει ήδη.");
            return;
        }
        Customer c = new Customer(id, name);
        customers.add(c);
        System.out.println("Προσθέσατε νέος πελάτης:");
        System.out.println(c.toString());
    }

    /**
     * Edit a customer with the given ID
     * @param id of customer to make edits.
     */
    public void editCustomerByID(int id) {
        Customer c = getCustomerByID(id);
        if ( c == null ){
            System.out.println("Δεν υπάρχει Πελάτης με ID: "+id);
            return;
        }
        System.out.println("Επεξεργασία: ");
        System.out.println(c.toString());
        System.out.println("Πάτησε enter για καμία αλλαγή...");
        System.out.println("Νέο όνομα πελάτη:");
        String name = in.nextLine().trim();
        if (!name.isBlank())
            c.setCustomerName(name.trim());
        System.out.println("Ενημερώθηκε: " + c.toString());
    }

    /**
     * Manage reservations
     * Display a user menu and select desired operation.
     */
    private void manageReservations() {
        int choice = 0;
        // όσο το choice είναι διάφορο του 3, εκτελώ το do-while
        do {
            //Εμφανίζουμε το menu
            Menu.printReservationManagerMenu();
            System.out.println("Επιλογή: ");
            //ζητάμε από τον χρήστη να πληκτρολογήσει
            try {
                choice = Integer.parseInt(in.nextLine().trim());
            } catch (InputMismatchException e) {
                System.out.println("Wrong input type... " + e);
                break;
            }

            //ανάλογα το τι πάτησε ο χρήστης εκτελούμε το κατάλληλο case
            switch (choice) {
                case 1:
                    // θεατρική παράσταση
                    reserveTheatrical();
                    saveReservationsToCsv();
                    break;
                case 2:
                    // μουσική παράσταση
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
        // πρώτα ζητάμε από τον χρήστη να μας δώσει το id
        // ελέγχουμε εάν μπορεί να διαβαστεί ως ακέραιος
        // ψάχνουμε τη θεατρική παράσταση με το τάδε id

        System.out.println("Κράτηση εισιτηρίου για Θεατρική παράσταση.");
        int event_id = 0;
        int customer_id = 0;
        System.out.println("Δώσε το ID της θεατρικής παράστασης:");
        try {
            event_id = Integer.parseInt(in.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Wrong input format...");
            System.exit(1);
        }

        // ελέγχουμε εάν είναι υπάρχει θεατρική παράσταση με το τάδε id
        TheatricalEvent t = getTheatricalByID(event_id);
        // εάν είναι null, τότε ΔΕΝ υπάρχει
        if (t == null) {
            System.out.println("Δεν υπάρχει θεατρική παράσταση με ID " + event_id);
            return;
        }

        System.out.println("Δώσε το ID του πελάτη:");
        try{
            customer_id = Integer.parseInt(in.nextLine().trim());
        }catch (NumberFormatException e){
            System.out.println("Wrong input format...");
            System.exit(1);
        }

        Customer c = getCustomerByID(customer_id);
        if (c == null) {
            System.out.println("Δεν υπάρχει πελάτης με ID " + customer_id);
            return;
        }

        // εφόσον δεν είναι null
        // δημιουργούμε ένα αντικείμενο της κλάσης Reservation
        // το προσθέτουμε στο ArrayList
        Reservation n = new Reservation(customer_id, event_id);
        reservations.add(n);
        System.out.println("Επιτυχής Κράτηση...");
    }

    /**
     * Make a reservation to a musical event
     */
    public void reserveMusical() {
        System.out.println("Κράτηση εισιτηρίου για μουσική παράσταση.");
        int event_id = 0;
        int customer_id;
        System.out.println("Δώσε το ID της μουσικής παράστασης:");
        try {
            event_id = Integer.parseInt(in.nextLine().trim());
        } catch (InputMismatchException e) {
            System.out.println("Wrong input format...");
            System.exit(1);
        }
        MusicalEvent m = getMusicalByID(event_id);
        if (m == null) {
            System.out.println("Δεν υπάρχει μουσική παράσταση με ID " + event_id);
            return;
        }
        System.out.println("Δώσε το ID του πελάτη:");
        customer_id = Integer.parseInt(in.nextLine().trim());
        Customer c = getCustomerByID(customer_id);
        if (c == null) {
            System.out.println("Δεν υπάρχει πελάτης με ID " + customer_id);
            return;
        }
        Reservation n = new Reservation(customer_id, event_id);
        reservations.add(n);
        System.out.println("Επιτυχής Κράτηση...");
    }


    public void printAllTheatrical(){
        for (TheatricalEvent t : theatrical_events){
            System.out.println(t.toString());
        }
    }

    public void printAllMusical(){
        for (MusicalEvent m : musical_events){
            System.out.println(m.toString());
        }
    }
}