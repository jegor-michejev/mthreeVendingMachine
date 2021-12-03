/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a3.vendingmachine;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The view file for the Vending Machine
 * @author Anne
 */
class VendingMachineView {

    Scanner userInput;

    VendingMachineView() {
        this.userInput = new Scanner(System.in);
    }

    /**
     * Prints the menu, along with available operations for user to choose.
     * @param inventory The list of items that are displayed
     * @param funds The current balance inserted into the machine.
     */
    void printMenu(ArrayList<Item> inventory, BigDecimal funds) {

        System.out.println("\nBalance: $" + String.format("%.2f", funds.setScale(2, RoundingMode.DOWN)));
        System.out.println("Menu: ");
        for (int i = 0; i < inventory.size(); i++) {

            System.out.println((i + 1) + ". " + inventory.get(i).getName() + " $" + String.format("%.2f", inventory.get(i).getPrice()));

        }

        System.out.println("\nChoose an action: ");
        System.out.println("1. Insert coins");
        System.out.println("2. Choose an item");
        System.out.println("0. Exit");
    }// End of printMenu

    int askForAction() {
        return Integer.parseInt(userInput.nextLine());

    }

    BigDecimal askUserForCoins() {
        System.out.println("Insert funds: ");
        return new BigDecimal(userInput.nextLine());
    }

    int askUserForPurchase() {
        System.out.println("Pick what you are buying.");
        System.out.println("Or type '0' if you want to go back");
        return askForAction();
    }

    void displayChange(int[] coins) {

        System.out.println("\nReceipt: ");
        for (int i = 0; i < coins.length; i++) {
            BigDecimal currentCoin = ServiceLayer.Change.values()[i].getValue().divide(new BigDecimal(100));
            System.out.println("$" + String.format("%.2f", currentCoin) + " coins: " + coins[i]);
        }

    }

}// End of class
