/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a3.vendingmachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Data Access Object for the vending machine
 * @author Anne
 */
class VendingMachineDAO {

    ArrayList<Item> inventory = new ArrayList();
    final String INVENTORY_LIST = "inventory.csv";

    public VendingMachineDAO() throws FileNotFoundException {
        Scanner sc = new Scanner(new File(INVENTORY_LIST));
        sc.useDelimiter(",|\\r\\n");
        //sc.nextLine();
        while (sc.hasNext()) {
            inventory.add(new Item(sc.next(), sc.nextInt(), new BigDecimal(sc.next())));
        }
        sc.close();
    }
    
    public Item find(int iterator){
        return inventory.get(inventory.hashCode());
    }
    
    public ArrayList<Item> getList(){
        return inventory;
    }
    
    public void save() throws IOException{
        PrintWriter out = new PrintWriter(new FileWriter(INVENTORY_LIST));
        inventory.forEach((i) -> {
            out.print(i.getName() + "," + i.getQuantity() + "," + i.getPrice() + "\r\n");
        });
        out.flush();
        out.close();
    }
    
    

}
