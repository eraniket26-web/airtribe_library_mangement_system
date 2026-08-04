package com.airtribe.lms.model;

import java.time.LocalDate;

public class Loan {

    private String loanId;
    private Book book;
    private Patron patron;
    private LocalDate borrowedDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Loan() {  }

    public Loan(Book book, Patron patron, LocalDate borrowedDate, LocalDate returnDate, LocalDate dueDate) {
        this.book = book;
        this.patron = patron;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
