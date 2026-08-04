package com.airtribe.lms.utility;

public class LoanIdGenerator {

    public static int idCounter = 0;
    public static String generateNextLoanId(){
        return "LEND" + String.format("%04d", ++idCounter);
    }
}
