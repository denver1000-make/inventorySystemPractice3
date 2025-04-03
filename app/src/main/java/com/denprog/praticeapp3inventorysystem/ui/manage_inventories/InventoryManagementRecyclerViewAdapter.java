package com.denprog.praticeapp3inventorysystem.ui.manage_inventories;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denprog.praticeapp3inventorysystem.callback.SimpleClickCallback;
import com.denprog.praticeapp3inventorysystem.room.entities.InventoryItem;
import com.denprog.praticeapp3inventorysystem.room.views.InventoryItemWithImage;
import com.denprog.praticeapp3inventorysystem.ui.manage_inventories.placeholder.PlaceholderContent.PlaceholderItem;
import com.denprog.praticeapp3inventorysystem.databinding.FragmentInventoryItemBinding;
import com.denprog.praticeapp3inventorysystem.util.AsyncRunner;
import com.denprog.praticeapp3inventorysystem.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class InventoryManagementRecyclerViewAdapter extends RecyclerView.Adapter<InventoryManagementRecyclerViewAdapter.ViewHolder> {

    private final List<InventoryItemWithImage> mValues = new ArrayList<>();
    private final SimpleClickCallback<InventoryItemWithImage> callback;

    public InventoryManagementRecyclerViewAdapter(SimpleClickCallback<InventoryItemWithImage> callback) {
        this.callback = callback;
    }

    public void refresh(List<InventoryItemWithImage> mValues) {
        this.mValues.clear();
        this.mValues.addAll(mValues);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentInventoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.binding.itemNameDisplay.setText(holder.mItem.itemName);
        holder.binding.itemStockAmount.setText(holder.mItem.stockNumber + " Stocks Left");
        holder.binding.itemPriceDisplay.setText(holder.mItem.itemPrice + " Pesos");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.doThing(holder.mItem);
            }
        });
        AsyncRunner.runAsync(new AsyncRunner.AsyncRunnerCallback<Bitmap>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public Bitmap onExecute() throws Exception {
                return FileUtil.getBitmapFromPath(holder.mItem.imagePath, holder.binding.getRoot().getContext());
            }

            @Override
            public void onFinish(Bitmap data) {

            }

            @Override
            public void onUI(Bitmap data) {
                holder.binding.itemImage.setImageBitmap(data);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FragmentInventoryItemBinding binding;
        public InventoryItemWithImage mItem;

        public ViewHolder(FragmentInventoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.sku + "'";
        }
    }
}