package com.airtribe.lms.utility;

public class BookIdGenerator {

    public static int idCounter = 0;
    public static String generateNextBookId(){
        return "BOOK" + String.format("%04d", ++idCounter);
    }


}
