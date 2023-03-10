/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author giahu
 */
public class Cart {

    private Map<Integer, Item> map = null;

    public Cart() {
        map = new HashMap<>();
    }

    public void add(Item item) {
        int id = item.getProduct().getId();
        System.out.println("item id: " + id);
        if (map.containsKey(id)) {
            Item oldItem = map.get(id);
            oldItem.setQuantity(oldItem.getQuantity() + item.getQuantity());
        } else {
            map.put(id, item);
        }
    }

    public void minus(Item item) {
        int id = item.getProduct().getId();
        if (map.containsKey(id)) {
            Item oldItem = map.get(id);
            if (oldItem.getQuantity() > 1) {
                oldItem.setQuantity(oldItem.getQuantity() - item.getQuantity());
            } else {
                map.remove(id);
            }
        }
    }

    public int getTotalQuantity() {
        return map.size();
    }

    public void update(int id, int quantity) {
        Item item = map.get(id);
        item.setQuantity(quantity);
    }

    public void remove(int id) {
        map.remove(id);
    }

    public void empty() {
        map.clear();
    }

    public Map<Integer, Item> getMap() {
        return map;
    }

    public Collection<Item> getItems() {
        return map.values();
    }
}
