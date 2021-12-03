/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a3.vendingmachine;

import java.io.IOException;

/**
 * An exception that is thrown when an impossible transaction is performed
 *
 * @author Anne
 */
public class InsufficientFundsException extends Exception {

    public void howToRecover() throws IOException {
        System.out.println("Insufficient Funds ... Try Again");
        AuditDao.logError(0);
    }// End of howToRecover

}// End of class
