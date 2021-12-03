/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a3.vendingmachine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * A class that handles all the operations involving validating transactions
 *
 * @author Anne
 */
public class ServiceLayer {
    
    static BigDecimal insertedMoney = BigDecimal.ZERO;

    // An enum representing the coins given away by the machine
    enum Change {
        TWO_DOLLAR(new BigDecimal(200)),
        ONE_DOLLAR(new BigDecimal(100)),
        FIFTY_CENTS(new BigDecimal(50)),
        TWENTY_CENTS(new BigDecimal(20)),
        TEN_CENTS(new BigDecimal(10)),
        FIVE_CENTS(new BigDecimal(5)),
        TWO_CENTS(new BigDecimal(2)),
        ONE_CENTS(new BigDecimal(1));
        
        private final BigDecimal coinValue;
        
        Change(BigDecimal coinValue) {
            this.coinValue = coinValue;
        }
        
        public BigDecimal getValue() {
            return coinValue;
        }
        
    }// End of enum

    /**
     * Method used to add the balance to the vending machine.
     *
     * @param coins The value of inserted.
     */
    public void insertFunds(BigDecimal coins) {
        // The value is rounded down in case the user adds a third+ digit after the second
        insertedMoney = insertedMoney.add(coins.setScale(2, RoundingMode.DOWN));
    }// End of insertFunds

    /**
     * A getter for the value of already inserted money.
     *
     * @return the current balance for the vending machine.
     */
    public BigDecimal getFunds() {
        return insertedMoney;
    }// End of getFunds

    /**
     * A method for validating that a transaction is possible
     *
     * @param price The price of the item to be purchased
     * @return whether or not the transaction is valid.
     * @throws InsufficientFundsException
     */
    public boolean validateTransaction(BigDecimal price) throws InsufficientFundsException {
        
        int decision; // BigDecimal compare method returns an int
        decision = insertedMoney.compareTo(price);
        if (!(decision >= 0)) {
            throw new InsufficientFundsException(); // The item cannot be afforded
        }
        return decision >= 0;
    }// End of validateTransaction

    /**
     * A method for giving out the change, separated by the type of coin. A
     * concern I have is I had to make the method public to test it, but I feel
     * that it should not be, as its effects affect the balance value (making it
     * zero)
     *
     * @param price Price of a purchased item
     * @return An array, signified the amount of each coin
     */
    public int[] calculateChange(BigDecimal price) throws InsufficientFundsException {
        validateTransaction(price); // Cannot be too safe
        int[] coins = new int[Arrays.asList(Change.values()).size()];
        BigDecimal leftover = insertedMoney.subtract(price.setScale(2, RoundingMode.DOWN))
                .multiply(new BigDecimal(100));

        // Iterating through all the coin types
        for (int i = 0; i < coins.length; i++) {
            BigDecimal currentCoinValue = Arrays.asList(Change.values()).get(i).getValue();
            BigDecimal amountOfCoins = leftover.divide(currentCoinValue);
            coins[i] = amountOfCoins.intValue();
            leftover = (leftover.remainder(currentCoinValue));
        }// end of for loop

        insertedMoney = BigDecimal.ZERO; // Since the change is dispensed, inserted Money left is 0

        return coins;
    }// End of calculateChange

}//End of class
