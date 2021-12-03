/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a3.vendingmachine;

import java.io.IOException;

/**
 * An exception for when an item has ran out of stock
 * @author Anne
 */
public class NoItemInventoryException extends Exception {

    /**
     * A method for recovering and logging the error.
     * @throws IOException 
     */
    public void howToRecover() throws IOException{
        System.out.println("Item Unavailable");
        AuditDao.logError(1);
    }
    
}// End of class
