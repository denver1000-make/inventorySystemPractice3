package com.denprog.praticeapp3inventorysystem.ui.manage_inventories;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denprog.praticeapp3inventorysystem.MainActivityArgs;
import com.denprog.praticeapp3inventorysystem.MainActivityViewModel;
import com.denprog.praticeapp3inventorysystem.R;
import com.denprog.praticeapp3inventorysystem.callback.SimpleClickCallback;
import com.denprog.praticeapp3inventorysystem.databinding.FragmentInventoryItemListBinding;
import com.denprog.praticeapp3inventorysystem.room.entities.InventoryItem;
import com.denprog.praticeapp3inventorysystem.room.views.InventoryItemWithImage;
import com.denprog.praticeapp3inventorysystem.ui.manage_inventories.placeholder.PlaceholderContent;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class InventoryManagementFragment extends Fragment {
    private InventoryManagementViewModel viewModel;
    private FragmentInventoryItemListBinding binding;
    private MainActivityViewModel mainActivityViewModel;
    private InventoryManagementRecyclerViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInventoryItemListBinding.inflate(inflater, container, false);
        adapter = new InventoryManagementRecyclerViewAdapter(new SimpleClickCallback<InventoryItemWithImage>() {
            @Override
            public void doThing(InventoryItemWithImage data) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                MainActivityArgs args = MainActivityArgs.fromBundle(requireActivity().getIntent().getExtras());
                InventoryManagementFragmentDirections.ActionInventoryManagementToAddItemFragment directions = InventoryManagementFragmentDirections.actionInventoryManagementToAddItemFragment(args.getUserId());
                directions.setItemToUpdate(data);
                navController.navigate(directions);
            }
        });
        binding.list.setAdapter(adapter);
        binding.list.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.openAddItemWindow.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            MainActivityArgs args = MainActivityArgs.fromBundle(requireActivity().getIntent().getExtras());
            navController.navigate(InventoryManagementFragmentDirections.actionInventoryManagementToAddItemFragment(args.getUserId()));
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.viewModel = new ViewModelProvider(this).get(InventoryManagementViewModel.class);
        this.mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        MainActivityArgs args = MainActivityArgs.fromBundle(requireActivity().getIntent().getExtras());
        viewModel.fetchInventoryItems(args.getUserId());
        viewModel.listMutableLiveData.observe(getViewLifecycleOwner(), inventoryItems -> {
            adapter.refresh(inventoryItems);
        });
    }
}