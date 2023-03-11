/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author admin
 */
public class OrderHeader {
     private int id;
    private Timestamp date;
    private String status;
    private int customerId;
    private float total;

    public OrderHeader() {
    }

    public OrderHeader(String status, int customerId, float total) {
        this.status = status;
        this.customerId = customerId;
        this.total = total;
    }

    public OrderHeader(int id, Timestamp date, String status, int customerId, float total) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.customerId = customerId;
        this.total = total;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getTotal() {
        return total;
    }
    
    
    
}
