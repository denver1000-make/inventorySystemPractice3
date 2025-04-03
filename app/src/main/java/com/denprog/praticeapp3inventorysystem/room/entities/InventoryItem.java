package com.denprog.praticeapp3inventorysystem.room.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class InventoryItem {
    @PrimaryKey(autoGenerate = true)
    public long sku;
    public String itemName;
    public float itemPrice;
    public int stockNumber;
    public long addedByUser;

    public InventoryItem(String itemName, float itemPrice, int stockNumber, long addedByUser) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.stockNumber = stockNumber;
        this.addedByUser = addedByUser;
    }
}
