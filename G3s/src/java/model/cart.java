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
public class cart extends product {
    product pr;
    int quantity;

    public cart() {
    }
     public cart(product pr, int quantity) {
        this.pr = pr;
        this.quantity = quantity;
        
    }
   

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

   
    
    
}
