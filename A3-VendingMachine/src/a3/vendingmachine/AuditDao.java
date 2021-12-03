/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a3.vendingmachine;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A class for logging errors or changes in the inventory
 *
 * @author Anne
 */
public class AuditDao {

    final static String LOG_FILE = "operationLog.txt"; // The log file location

    /**
     * A log entry for a successful transaction
     *
     * @param item The purchased item
     * @throws IOException
     */
    static void logPurchase(Item item) throws IOException {

        saveToFile("Purchased " + item.getName() + "\nat " + LocalDate.now() + " " + LocalTime.now());

    } // End of logPurchase

    /**
     * A log entry for an unsuccessful transaction
     *
     * @param i the error type 0 - insufficient funds error. 1 - item out of
     * stock error.
     * @throws IOException
     */
    static void logError(int i) throws IOException {
        String statement;
        switch (i) {
            case 0:
                statement = "Failed purchase attempt: insufficient funds";
                break;
            case 1:
                statement = "Failed purchase attempt: out of stock";
                break;
            default:
                statement = "Unknown issue";
        }// End of switch statement

        saveToFile(statement + "\nat " + LocalDate.now() + " " + LocalTime.now());

    }// End of logError

    /**
     * A method for writing the log to a file
     *
     * @param statement The log that will be written to the file.
     * @throws IOException
     */
    static void saveToFile(String statement) throws IOException {

        // FileWriter constructor takes a 'true' parameter to append new
        // statements at the end of the file
        try (PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            out.write(statement + "\n");

        }// End of try statement
    }// End of saveToFile

}// End of class
