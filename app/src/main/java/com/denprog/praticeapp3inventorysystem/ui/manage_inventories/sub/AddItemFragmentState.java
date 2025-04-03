package com.denprog.praticeapp3inventorysystem.ui.manage_inventories.sub;

import com.denprog.praticeapp3inventorysystem.room.entities.InventoryItem;

public class AddItemFragmentState {
    public static final class InsertMode extends AddItemFragmentState {
        public long userId;

        public InsertMode(long userId) {
            this.userId = userId;
        }
    }

    public static final class UpdateMode extends AddItemFragmentState {
        public InventoryItem inventoryItem;

        public UpdateMode(InventoryItem inventoryItem) {
            this.inventoryItem = inventoryItem;
        }
    }
}
