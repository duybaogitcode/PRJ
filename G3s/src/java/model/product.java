/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Locale.Category;

/**
 *
 * @author duyba
 */
public class product {

    private int id;
    private String name;
    private String image;
    private String description;
    private float price;
    private float discount;
    private String categoryId;

    public product() {
    }

    public product(int id, String name, String image, String description, float price, float discount, String categoryId) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryID) {
        this.categoryId = categoryID;
    }

}
