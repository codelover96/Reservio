package ui;

public class Menu {
    public static void printWelcomeMenu(){
        System.out.println("Welcome to Reservio, your event manager!");
        System.out.println();
    }

    public static void printMainMenu(){
        System.out.println("Choose:");
        System.out.println("1... Manage Theatrical Events");
        System.out.println("2... Manage Musical Events");
        System.out.println("3... Customer Management");
        System.out.println("4... Reservations");
        System.out.println("5... Statistics");
        System.out.println("6... Exit");
    }

    public static void printManagerMenu(){
        System.out.println("1. Entry");
        System.out.println("2. Edit");
        System.out.println("3. Delete");
        System.out.println("4. Show All");
        System.out.println("5. Back");
    }

    public static void printCustomerManagerMenu(){
        System.out.println("Customer Management");
        System.out.println("1. Insert New Customer");
        System.out.println("2. Edit Customer info");
        System.out.println("3. Delete Customer");
        System.out.println("4. Back");
    }

    public static void printReservationManagerMenu(){
        System.out.println("Reservations Management");
        System.out.println("1. Theatrical Reservations");
        System.out.println("2. Musical Reservations");
        System.out.println("3. Back");
    }
}
