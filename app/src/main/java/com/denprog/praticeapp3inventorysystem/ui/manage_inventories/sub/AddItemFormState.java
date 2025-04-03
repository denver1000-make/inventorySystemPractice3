package com.denprog.praticeapp3inventorysystem.ui.manage_inventories.sub;

public class AddItemFormState {
    public String itemNameError = null;
    public String itemPriceError = null;
    public String itemStockAmountError = null;
    public Boolean isDataValid = false;

    public AddItemFormState(String itemNameError, String itemPriceError, String itemStockAmountError, Boolean isDataValid) {
        this.itemNameError = itemNameError;
        this.itemPriceError = itemPriceError;
        this.itemStockAmountError = itemStockAmountError;
        this.isDataValid = isDataValid;
    }
}
