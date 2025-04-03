package com.denprog.praticeapp3inventorysystem.room.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class InventoryItem implements Parcelable {
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

    protected InventoryItem(Parcel in) {
        sku = in.readLong();
        itemName = in.readString();
        itemPrice = in.readFloat();
        stockNumber = in.readInt();
        addedByUser = in.readLong();
    }

    public static final Creator<InventoryItem> CREATOR = new Creator<InventoryItem>() {
        @Override
        public InventoryItem createFromParcel(Parcel in) {
            return new InventoryItem(in);
        }

        @Override
        public InventoryItem[] newArray(int size) {
            return new InventoryItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(sku);
        parcel.writeString(itemName);
        parcel.writeFloat(itemPrice);
        parcel.writeInt(stockNumber);
        parcel.writeLong(addedByUser);
    }
}
