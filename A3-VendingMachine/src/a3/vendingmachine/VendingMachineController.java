/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a3.vendingmachine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * A class for controlling the logic of the Vending Machine
 *
 * @author Anne
 */
public class VendingMachineController {

    static boolean isDone = false; // Whn true, the program should terminate
    private final VendingMachineView view = new VendingMachineView();
    private VendingMachineDAO inventory;
    ArrayList<Item> inventoryPurchasable = new ArrayList();

    ServiceLayer serviceLayer;

    public VendingMachineController() throws FileNotFoundException {
        this.inventory = new VendingMachineDAO();
        updateAvailableItems();
        serviceLayer = new ServiceLayer();

    }// End if constructor

    /**
     * Starts operation, and continues until the user stated they're done.
     *
     * @throws FileNotFoundException
     * @throws IOException
     * @throws InsufficientFundsException
     */
    public void start() throws FileNotFoundException, IOException, InsufficientFundsException {

        while (!isDone) {

            updateAvailableItems();
            view.printMenu(inventoryPurchasable, serviceLayer.getFunds());
            decideProgression(view.askForAction());
        }// End of while loop

    }// End of start

    /**
     * This method is mostly obsolete. It is left in as a lesson to read the
     * specification more carefully
     */
    private void updateAvailableItems() {

        inventoryPurchasable = inventory.getList();

        // I reread the specification and it asks to display all items
        /*inventoryPurchasable = new ArrayList();
        inventory.getList().stream().filter((i) -> (i.getQuantity() > 0)).forEachOrdered((i) -> {
            boolean add = inventoryPurchasable.add(i);
        });*/
    }

    /**
     * A method that executes an operation chosen by the user.
     *
     * @param askForAction an integer representing a user's action.
     * @throws IOException
     * @throws InsufficientFundsException
     */
    private void decideProgression(int askForAction) throws IOException, InsufficientFundsException {
        switch (askForAction) {
            case 0: // Exit the program
                isDone = true;
                break;
            case 1: // Insert money into the machine
                insertCoins(view.askUserForCoins());
                break;
            case 2: // Purchase an item
                try {
                    commitTransaction(view.askUserForPurchase());
                } catch (NoItemInventoryException e) {
                    e.howToRecover();
                }
                break;
            default:

        }
    }// End of decide progression

    /**
     * Essentially a redirect to the Service Layer's insert funds
     *
     * @param askUserForCoins the money inserted into the machine
     */
    private void insertCoins(BigDecimal askUserForCoins) {
        serviceLayer.insertFunds(askUserForCoins);
    }// End of insertCoins

    /**
     * Execute a purchase of an item.
     *
     * @param selection the index number of a chosen item
     * @throws IOException
     * @throws InsufficientFundsException
     * @throws NoItemInventoryException
     */
    private void commitTransaction(int selection) throws IOException, InsufficientFundsException, NoItemInventoryException {

        // Checks if a valid selectio is made
        if (selection < 0 || selection > inventoryPurchasable.size() + 1) {
            selection = 0; // Sets the selection to "0. Back"
        }
        // Ideally would let the user know they inputed a bad integer

        boolean transactionSuccessful = false;
        Item currentItem;

        switch (selection) {
            case 0:
                return;
            default:
                currentItem = inventoryPurchasable.get(selection - 1);

                if (currentItem.getQuantity() < 1) {
                    throw new NoItemInventoryException();
                }
                try {
                    // validate the purchase
                    transactionSuccessful = serviceLayer.validateTransaction(currentItem.getPrice());
                } catch (InsufficientFundsException e) {
                    e.howToRecover();
                }// End of try .. catch block
        }// End of switch statement

        // I ftransaction is valid, item must deplete by one and the action should be logged
        if (transactionSuccessful) {
            currentItem.setQuantity(currentItem.getQuantity() - 1);
            int[] coins = serviceLayer.calculateChange(inventoryPurchasable.get(selection - 1).getPrice());
            inventory.save();
            view.displayChange(coins);
        }

    }// End of commit transaction

}// End of class
