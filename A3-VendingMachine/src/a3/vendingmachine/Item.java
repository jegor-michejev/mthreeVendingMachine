/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a3.vendingmachine;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * An object class for an item displayed in the vending machine
 *
 * @author Anne
 */
class Item {

    String name;
    int quantity;
    BigDecimal price;

    public Item(String name, int quantity, BigDecimal price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price.setScale(2, RoundingMode.DOWN);
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Item{" + "name=" + name + ", quantity=" + quantity + ", price=" + price + '}';
    }

}// End of class
