package com.airtribe.lms.service;

import com.airtribe.lms.inventory.Library;
import com.airtribe.lms.model.Loan;
import com.airtribe.lms.model.Patron;
import com.airtribe.lms.utility.PatronIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PatronManager {

  private final Library library;
  private static final Logger logger = LoggerFactory.getLogger(PatronManager.class);

  public PatronManager(Library library) {
    this.library = library;
  }


  public void addPatron(Patron patron){
      validatePatron(patron);
      patron.setId(PatronIdGenerator.generateNextPatronId());
      library.savePatron(patron);
      logger.info("Patron added successfully. Id: {}", patron.getId());

  }

  public void updatePatron(Patron patron){
      validatePatron(patron);
      Patron existingPatron = library.findPatron(patron.getId());
      if(existingPatron == null){
          logger.error("Patron with id {} not found", patron.getId());
          throw new IllegalArgumentException("Patron not found");
      }

      existingPatron.setName(patron.getName());
      existingPatron.setEmail(patron.getEmail());
      existingPatron.setPhoneNumber(patron.getPhoneNumber());

      logger.info("Patron updated successfully. Id: {}", patron.getId());
      library.updatePatron(patron);
  }

  public void showPatronBorrowedHistory(String patronId){

      List<Patron> patrons = library.getPatrons();
      Patron patron = patrons.stream()
              .filter(p -> p.getId().equals(patronId))
                .findFirst().orElse(null);
      if(patron != null){
          for(Loan loan : patron.getBorrowedBooks()){
             logger.info("Book: {},); Borrowed Date: {}, Due Date: {}, Return Date: {}",
                     loan.getBook().getTitle(),
                     loan.getBorrowedDate(),
                     loan.getDueDate(),
                     loan.getReturnDate() != null ? loan.getReturnDate() : "Not Returned"
             );
          }
      }


  }

    private void validatePatron(Patron patron) {

        if (patron == null) {
            throw new IllegalArgumentException("Patron cannot be null");
        }
    }

}
