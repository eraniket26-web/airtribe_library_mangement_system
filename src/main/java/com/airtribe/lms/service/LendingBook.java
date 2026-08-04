package com.airtribe.lms.service;

import com.airtribe.lms.exception.BookNotFoundException;
import com.airtribe.lms.model.Book;
import com.airtribe.lms.model.Loan;
import com.airtribe.lms.model.Patron;

public interface LendingBook {
     Loan checkOutBook(Book book, Patron patron);
     void returnBook(Book book, Patron patron) throws BookNotFoundException;
}
