package com.denprog.praticeapp3inventorysystem.room.views;

import androidx.room.DatabaseView;

@DatabaseView("SELECT InventoryItem.itemName, InventoryItem.sku, InventoryItemImage.imagePath, InventoryItem.itemPrice, InventoryItem.stockNumber, InventoryItem.addedByUser FROM InventoryItem INNER JOIN InventoryItemImage ON InventoryItem.sku = InventoryItemImage.itemId")
public class InventoryItemWithImage {
    public String itemName;
    public long sku;
    public float itemPrice;
    public int stockNumber;
    public int addedByUser;
    public String imagePath;

    public InventoryItemWithImage(String itemName, long sku, float itemPrice, int stockNumber, int addedByUser, String imagePath) {
        this.itemName = itemName;
        this.sku = sku;
        this.itemPrice = itemPrice;
        this.stockNumber = stockNumber;
        this.addedByUser = addedByUser;
        this.imagePath = imagePath;
    }
}
