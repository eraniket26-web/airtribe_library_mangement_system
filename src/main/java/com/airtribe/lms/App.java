package com.airtribe.lms;

import com.airtribe.lms.inventory.Library;
import com.airtribe.lms.model.Book;
import com.airtribe.lms.model.Loan;
import com.airtribe.lms.model.Patron;
import com.airtribe.lms.service.LendingService;
import com.airtribe.lms.service.PatronManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

import static com.airtribe.lms.constants.Constants.*;

/**
 * Hello world!
 *
 */
public class App  {
    private final Scanner scanner;

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private final Library library = new Library();
    private final LendingService lendingService = new LendingService(library);
    private final PatronManager patronManager = new PatronManager(library);


    public App() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {

        int choice;

        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    searchBook();
                    break;
                case 3:
                    removeBook();
                    break;
                case 4:
                    updateBook();
                    break;
                case 5:
                    addPatron();
                    break;
                case 6:
                    updatePatron();
                    break;
                case 7:
                    checkoutBook();
                    break;
                case 8:
                    returnBook();
                    break;
                case 9:
                    showAvailableBooks();
                    break;
                case 10:
                    viewHistory();
                     break;
                case 0:
                   logger.info("Exiting Library System...");
                    break;

                default:
                    logger.info("Invalid option");
            }


        } while(choice != 0);

    }

    private void displayMenu(){
       logger.info(MENU);
    }



    private void addBook(){

        logger.info(ENTER_TITLE);
        String title = scanner.nextLine();

        logger.info(ENTER_AUTHOR);
        String author = scanner.nextLine();

        logger.info(ENTER_ISBN);
        String isbn = scanner.nextLine();

        logger.info(ENTER_PUBLICATION_YEAR);
        int year = scanner.nextInt();
        scanner.nextLine();

        Book book = new Book(title, author, isbn, year);
        library.addBooks(book);

       logger.info(BOOK_ADDED_SUCCESSFULLY);
    }



    private void searchBook(){

        logger.info("Enter search keyword:");
        String query = scanner.nextLine();
        List<Book> books = library.searchBooks(query);
        if(books.isEmpty()){
            logger.info("No books found");
            return;
        }
        logger.info("Books found:");
        books.forEach(System.out::println);
    }



    private void removeBook(){

        try{
            logger.info(ENTER_ISBN);
            String isbn = scanner.nextLine();
            Book book = library.findBookByISBN(isbn);
            if(book != null){
                library.removeBook(book);
            }
            logger.info(BOOK_REMOVED_SUCCESSFULLY);
        }catch (Exception e){
            logger.error("Error removing book: {}", e.getMessage());
        }
    }

    private void updateBook(){

        try{
            logger.info(ENTER_ISBN);
            String isbn = scanner.nextLine();
            Book book = library.findBookByISBN(isbn);
            if(book != null){
                logger.info(ENTER_TITLE);
                String title = scanner.nextLine();
                if(!title.isEmpty()){
                    book.setTitle(title);
                }
                logger.info(ENTER_AUTHOR);
                String author = scanner.nextLine();
                if(!author.isEmpty()){
                    book.setAuthor(author);
                }
                logger.info(ENTER_PUBLICATION_YEAR);
                int year = scanner.nextInt();
                scanner.nextLine();
                book.setPublicationYear(year);
                library.updateBook(book);
            }
            logger.info("Book updated successfully");
        }catch (Exception e){
            logger.error("Error updating book: {}", e.getMessage());
        }
    }

    private void addPatron(){

         logger.info(ENTER_NAME);
        String name = scanner.nextLine();
        logger.info(ENTER_EMAIL);
        String email = scanner.nextLine();
        logger.info(ENTER_PHONE_NUMBER);
        long phNo = scanner.nextLong();
        scanner.nextLine(); // consume the newline character

        Patron patron = new Patron(name, email,phNo);
        patronManager.addPatron(patron);
        logger.info("Patron registered");
    }


    private void updatePatron(){

        logger.info("Enter Patron ID to update:");
        String id = scanner.nextLine();
        try {
            Patron patron = library.findPatron(id);
            if (patron == null) {
                logger.info("Patron not found");
                return;
            }

            logger.info("Enter new name (leave blank to keep current):");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                patron.setName(name);
            }
            logger.info("Enter new email (leave blank to keep current):");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                patron.setEmail(email);
            }
            logger.info("Enter new phone number (leave blank to keep current):");
            String phoneInput = scanner.nextLine();
            if (!phoneInput.isEmpty()) {
                long phoneNumber = Long.parseLong(phoneInput);
                patron.setPhoneNumber(phoneNumber);
            }
            library.updatePatron(patron);
        }catch (Exception e) {
            logger.error("Error finding patron: {}", e.getMessage());
        }
    }

    private void checkoutBook() {
        try {
            logger.info(ENTER_ISBN);
            String isbn = scanner.nextLine();
            logger.info(ENTER_PATRON_ID);
            String patronId = scanner.nextLine();
            Book book = library.findBookByISBN(isbn);
            Patron patron = library.findPatron(patronId);
            if (book != null && patron != null) {
                Loan loan = lendingService.checkOutBook(book, patron);
                logger.info("Book checked out. Loan ID: {}", loan.getLoanId());
            } else {
                logger.info("Book or patron not found");
            }
        }catch (Exception e) {
            logger.error("Error during checkout: {}", e.getMessage());
        }
    }



    private void returnBook(){
        try{
         logger.info("Enter ISBN:");
        String isbn = scanner.nextLine();
        logger.info(ENTER_PATRON_ID);
        String patronId = scanner.nextLine();
        Book book = library.findBookByISBN(isbn);
        Patron patron = library.findPatron(patronId);
        lendingService.returnBook(book, patron);
        logger.info("Book returned successfully");
       }catch (Exception e) {
           logger.error("Error during return: {}", e.getMessage());
       }
    }



    private void viewHistory(){
        logger.info(ENTER_PATRON_ID);
        String id = scanner.nextLine();
        patronManager.showPatronBorrowedHistory(id);
    }



    private void showAvailableBooks(){

        library.getBooks()
                .stream()
                .filter(book -> book.getStatus() == com.airtribe.lms.model.Status.AVAILABLE)
                .forEach(System.out::println);
    }

    public static void main( String[] args )
    {
        App app = new App();
        app.start();
    }
}
