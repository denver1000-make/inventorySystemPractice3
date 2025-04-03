package com.denprog.praticeapp3inventorysystem.room.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class InventoryItemImage {
    @PrimaryKey(autoGenerate = true)
    public long inventoryItemImageId;
    public String imagePath;
    public long itemId;

    public InventoryItemImage(String imagePath, long itemId) {
        this.imagePath = imagePath;
        this.itemId = itemId;
    }
}
