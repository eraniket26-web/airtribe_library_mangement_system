package com.airtribe.lms.service;

import com.airtribe.lms.exception.BookNotFoundException;
import com.airtribe.lms.inventory.Library;
import com.airtribe.lms.model.Book;
import com.airtribe.lms.model.Loan;
import com.airtribe.lms.model.Patron;
import com.airtribe.lms.model.Status;
import com.airtribe.lms.utility.LoanIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class LendingService implements LendingBook {

    private static final Logger logger = LoggerFactory.getLogger(LendingService.class);
    private final Library library;

    public LendingService(Library library) {
        this.library = library;
    }

    @Override
    public Loan checkOutBook(Book book, Patron patron) {

        if (book == null || patron == null) {
            throw new IllegalArgumentException("Book and Patron cannot be null");
        }

        if (book.getStatus() != Status.AVAILABLE) {
            logger.warn("Book {} is not available", book.getIsbn());
            throw new IllegalStateException("Book is already borrowed");
        }


        LocalDate borrowDate = LocalDate.now();
        Loan loan = new Loan();
        // Checking if book is available to lend
        if(book.getStatus() == Status.AVAILABLE){
            //If the book is available then we can lend it to the patron
            book.setStatus(Status.BORROWED);
            logger.info("Book '{}' has been checked out to patron '{}'.", book.getTitle(), patron.getName());
            loan.setLoanId(LoanIdGenerator.generateNextLoanId());
            loan.setBook(book);
            loan.setPatron(patron);
            loan.setBorrowedDate(borrowDate);
            loan.setDueDate(borrowDate.plusWeeks(2)); // Assuming a 2-week lending period

            //Push loan object to library for record purpose
            library.addLoan(loan);

        } else {
            logger.warn("Book '{}' is not available for checkout.", book.getTitle());
        }

        return loan;
    }

    @Override
    public void returnBook(Book book, Patron patron) throws BookNotFoundException {

        if (book == null || patron == null) {throw new IllegalArgumentException("Book and Patron cannot be null");}
        // Checking if the book is currently borrowed
         Loan loan = library.getLoanByBookId(book.getBookId());
        if (loan == null) {
            logger.warn("No active loan found for book {}", book.getIsbn());
            throw new BookNotFoundException("Active loan not found");
        }

        if (loan.getPatron().getId().equals(patron.getId())) {
            book.setStatus(Status.AVAILABLE);
            loan.setReturnDate(java.time.LocalDate.now());
            library.updateBook(book);
            logger.info("Book '{}' has been returned by patron '{}'.", book.getTitle(), patron.getName());
        } else {
            logger.warn("Book '{}' was not borrowed by patron '{}'.", book.getTitle(), patron.getName());
        }
    }
}
