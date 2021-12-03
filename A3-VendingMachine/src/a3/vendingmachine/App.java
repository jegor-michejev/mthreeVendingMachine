/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a3.vendingmachine;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The main executive class
 * @author Anne
 */
public class App {

    public static void main(String[] args) throws FileNotFoundException, IOException, InsufficientFundsException {
        
        VendingMachineController controller = new VendingMachineController();
        controller.start();
        
        
        
        
    }// End of main

}// End of class


