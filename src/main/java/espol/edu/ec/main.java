package espol.edu.ec;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Book {
    String title;
    String author;
    int quantity;
    boolean available;

    public Book(String title, String author, int quantity) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.available = true;
    }
}

class Library {
    ArrayList<Book> catalog;

    public Library() {
        this.catalog = new ArrayList<>();
    }

    public void displayCatalog() {
        System.out.println("Catalog:");
        for (Book book : catalog) {
            System.out.println("Title: " + book.title + ", Author: " + book.author + ", Quantity: " + book.quantity);
        }
    }
}

class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.catalog.add(new Book("Book1", "Author1", 5));
        library.catalog.add(new Book("Book2", "Author2", 3));
        library.catalog.add(new Book("Book3", "Author3", 7));

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Library Book Checkout System!");

        System.out.println("\n1. Book Selection and Checkout");
        library.displayCatalog();
        ArrayList<Book> selectedBooks = new ArrayList<>();
        int totalBooks = 0;

        while (true) {
            System.out.print("Enter the title of the book to checkout (or type 'done' to finish): ");
            String inputTitle = scanner.nextLine();

            if (inputTitle.equalsIgnoreCase("done")) {
                break;
            }

            Book selectedBook = null;

            for (Book book : library.catalog) {
                if (book.title.equalsIgnoreCase(inputTitle)) {
                    selectedBook = book;
                    break;
                }
            }

            if (selectedBook != null) {
                System.out.print("Enter the quantity to checkout: ");
                int quantity = scanner.nextInt();

                if (quantity > 0 && quantity <= selectedBook.quantity) {
                    selectedBook.quantity = quantity;
                    selectedBook.available = false;
                    selectedBooks.add(selectedBook);
                    totalBooks += quantity;
                } else {
                    System.out.println("Invalid quantity. Please enter a valid quantity.");
                }
            } else {
                System.out.println("Book not found. Please enter a valid title.");
            }

            scanner.nextLine();
        }

        System.out.println("\n2. Quantity Validation");
      
        System.out.println("\n3. Due Date Calculation");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        Date dueDate = new Date(currentDate.getTime() + (14 * 24 * 60 * 60 * 1000));

        System.out.println("\n4. Late Fee Calculation");
        double lateFeeRate = 1.0;
        double totalLateFees = 0;

        for (Book book : selectedBooks) {
            long daysLate = Math.max(0, (currentDate.getTime() - dueDate.getTime()) / (24 * 60 * 60 * 1000));
            double lateFee = lateFeeRate * daysLate * book.quantity;
            totalLateFees += lateFee;
        }

        System.out.println("\n5. Book Availability");
        for (Book book : selectedBooks) {
            if (!book.available) {
                System.out.println("Error: Book '" + book.title + "' is not available. Please re-select books.");
                System.exit(-1);
            }
        }

        System.out.println("\n6. Maximum Books per Checkout");
        int maxBooksPerCheckout = 10;

        if (totalBooks > maxBooksPerCheckout) {
            System.out.println("Error: Maximum books per checkout exceeded. Please adjust your selection.");
            System.exit(-1);
        }

        System.out.println("\n7. User Confirmation");
        System.out.println("Selected Books:");
        for (Book book : selectedBooks) {
            System.out.println("Title: " + book.title + ", Quantity: " + book.quantity + ", Due Date: " +
                    dateFormat.format(dueDate));
        }
        System.out.println("Total Late Fees: $" + totalLateFees);

        System.out.print("Do you want to confirm the checkout? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Checkout canceled. Please make changes to your selections.");
            System.exit(-1);
        }

        System.out.println("\n8. Return Process");
        System.out.println("\n9. Output");
        System.out.println("Checkout successful!");
        System.out.println("Due Date: " + dateFormat.format(dueDate));
        System.out.println("Total Late Fees: $" + totalLateFees);
        System.out.println("\n10. Error Handling");

        scanner.close();
    }
}

