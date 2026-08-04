package com.airtribe.lms.inventory;

import com.airtribe.lms.exception.BookNotFoundException;
import com.airtribe.lms.model.Book;
import com.airtribe.lms.model.Loan;
import com.airtribe.lms.model.Patron;
import com.airtribe.lms.model.Status;
import com.airtribe.lms.utility.BookIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.stream.Collectors;

import static com.airtribe.lms.constants.Constants.*;

public class Library {

    private static final Logger logger = LoggerFactory.getLogger(Library.class);
    private final List<Book> books;
    private final List<Patron> patrons = new ArrayList<>();
    private final List<Loan> loans = new ArrayList<>();

    public Library() {
        this.books = new ArrayList<>();
        loadSampleBooks();
    }

    private void loadSampleBooks() {
        books.add(new Book("Clean Code", "Robert C. Martin", "978-0132350884", 2008, Status.AVAILABLE));
        books.add(new Book("Effective Java", "Joshua Bloch", "978-0134685991", 2018,Status.AVAILABLE));
        books.add(new Book("Design Patterns", "Erich Gamma", "978-0201633610", 1994,Status.AVAILABLE));
        books.add(new Book("The Pragmatic Programmer", "Andrew Hunt", "978-0201616224", 1999,Status.AVAILABLE));
        books.add(new Book("Head First Design Patterns", "Eric Freeman", "978-0596007126", 2004,Status.AVAILABLE));
    }


    public void addBooks(Book book){
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        books.add(book);
        logger.info("Book added: {}", book.getTitle());
    }



    public void removeBook(Book book) throws BookNotFoundException {
        if (book == null || book.getIsbn() == null) {
            throw new IllegalArgumentException("Book or ISBN cannot be null");
        }

        boolean removed = books.removeIf(b -> b.getIsbn().equals(book.getIsbn()));

        if (!removed) {
            logger.error("Book with ISBN {} not found", book.getIsbn());
            throw new BookNotFoundException(String.format(BOOK_NOT_FOUND, book.getIsbn()));
        }

        logger.info("Removed book with ISBN {}", book.getIsbn());
    }


    public void updateBook(Book book) throws BookNotFoundException {

       Book existingBook  = books.stream()
                .filter(b -> b.getIsbn().equals(book.getIsbn()))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("Book with ISBN {} not found in library", book.getIsbn());
                    return new BookNotFoundException(String.format(BOOK_NOT_FOUND, book.getIsbn()));
                });

        existingBook .setTitle(book.getTitle());
        existingBook .setAuthor(book.getAuthor());
        existingBook .setPublicationYear(book.getPublicationYear());
        existingBook .setStatus(book.getStatus());



        logger.info("Updated book with ISBN {}", book.getIsbn());

    }


    public List<Book> searchBooks(String query){

        if(query == null || query.isEmpty()){
            logger.info("Search query is empty, returning all books");
            return books;
        }

       return books.stream()
                .filter(book -> book.getIsbn().equalsIgnoreCase(query) ||
                        book.getTitle().equalsIgnoreCase(query) ||
                        book.getAuthor().equalsIgnoreCase(query))
                .collect(Collectors.toList());

    }


    public Patron findPatron(String id) {

        return patrons.stream()
                .filter(p ->
                        p.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Patron not found"));
    }


    public void savePatron(Patron patron){
        if (patron == null) {
            throw new IllegalArgumentException(
                    "Patron cannot be null"
            );
        }
        this.patrons.add(patron);
    }


    public void updatePatron(Patron patron) {

        if(patron == null){
            logger.info("Patron is null, cannot update");
            return;
        }

      Patron existingPatron = patrons.stream()
                .filter(p -> p.getId().equals(patron.getId()))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("Patron with ID {} not found in library", patron.getId());
                    return new IllegalArgumentException("Patron with ID " + patron.getId() + " not found");
                });

        existingPatron.setName(patron.getName());
        existingPatron.setEmail(patron.getEmail());
        existingPatron.setPhoneNumber(patron.getPhoneNumber());
        existingPatron.setBorrowedBooks(patron.getBorrowedBooks());
        existingPatron.setCurrentBorrowedBooks(patron.getCurrentBorrowedBooks());


        logger.info("Updated patron with ID {}", patron.getId());

    }


    public List<Patron> getPatrons(){
        return Collections.unmodifiableList(patrons);
    }

    public void addLoan(Loan loan) {
        if(loan != null){
            logger.info("Change book status to BORROWED for book with ISBN {}", loan.getBook().getIsbn());
            books.stream()
                    .filter(b -> b.getIsbn().equalsIgnoreCase(loan.getBook().getIsbn()))
                    .findFirst()
                    .ifPresent(b -> b.setStatus(loan.getBook().getStatus()));


            patrons.stream()
                    .filter(p -> p.getId().equalsIgnoreCase(loan.getPatron().getId()))
                    .findFirst()
                    .ifPresent(p -> {
                        p.getBorrowedBooks().add(loan);
                        p.getCurrentBorrowedBooks().add(loan);
                    });

        }
        this.loans.add(loan);


    }

    public Loan getLoanByBookId(String bookId){
        logger.info("Searching for loan with book ID {}", bookId);
        return loans.stream()
                .filter(loan -> loan.getBook().getBookId().equalsIgnoreCase(bookId))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("Loan with book ID {} not found", bookId);
                    return new IllegalArgumentException("Loan with book ID " + bookId + " not found");
                });
    }

}
