/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Admin
 */
public class Item {
    private product pr;
    private  int quantity;

    public Item() {
    }

    public Item(product pr, int quantity) {
        this.pr = pr;
        this.quantity = quantity;
    }

    public product getPr() {
        return pr;
    }

    public void setPr(product pr) {
        this.pr = pr;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getTotal(){
    return this.pr.getPrice()*this.pr.getDiscount()*this.quantity;
    }
}
