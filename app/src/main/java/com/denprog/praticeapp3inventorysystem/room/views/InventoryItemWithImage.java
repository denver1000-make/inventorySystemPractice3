package com.denprog.praticeapp3inventorysystem.room.views;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.DatabaseView;

@DatabaseView("SELECT InventoryItem.itemName, InventoryItem.sku, InventoryItemImage.imagePath, InventoryItem.itemPrice, InventoryItem.stockNumber, InventoryItem.addedByUser FROM InventoryItem INNER JOIN InventoryItemImage ON InventoryItem.sku = InventoryItemImage.itemId")
public class InventoryItemWithImage implements Parcelable {
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

    protected InventoryItemWithImage(Parcel in) {
        itemName = in.readString();
        sku = in.readLong();
        itemPrice = in.readFloat();
        stockNumber = in.readInt();
        addedByUser = in.readInt();
        imagePath = in.readString();
    }

    public static final Creator<InventoryItemWithImage> CREATOR = new Creator<InventoryItemWithImage>() {
        @Override
        public InventoryItemWithImage createFromParcel(Parcel in) {
            return new InventoryItemWithImage(in);
        }

        @Override
        public InventoryItemWithImage[] newArray(int size) {
            return new InventoryItemWithImage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(itemName);
        parcel.writeLong(sku);
        parcel.writeFloat(itemPrice);
        parcel.writeInt(stockNumber);
        parcel.writeInt(addedByUser);
        parcel.writeString(imagePath);
    }
}
