package com.airtribe.lms.utility;

public class PatronIdGenerator {

    public static int idCounter = 0;
    public static String generateNextPatronId(){
        return "PAT" + String.format("%04d", ++idCounter);
    }



}
