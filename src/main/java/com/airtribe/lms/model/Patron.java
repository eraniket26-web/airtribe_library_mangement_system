package com.airtribe.lms.model;

import java.util.ArrayList;
import java.util.List;

public class Patron extends Person {

    private String patronId;

    private List<Loan> borrowedBooks;
    private List<Loan> currentBorrowedBooks;

    public Patron() {  }

    public Patron(String name,
                  String email,
                  long phoneNumber,
                  List<Loan> borrowedBooks,
                  List<Loan> currentBorrowedBooks) {

        super(name, email, phoneNumber);
        this.borrowedBooks = new ArrayList<>();
        this.currentBorrowedBooks = new ArrayList<>();

    }


    public String getId() {
        return patronId;
    }

    public void setId(String patronId) {
        this.patronId = patronId;
    }


    public List<Loan> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Loan> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public List<Loan> getCurrentBorrowedBooks() {
        return currentBorrowedBooks;
    }

    public void setCurrentBorrowedBooks(List<Loan> currentBorrowedBooks) {
        this.currentBorrowedBooks = currentBorrowedBooks;
    }
}
