package com.denprog.praticeapp3inventorysystem.ui.manage_inventories.sub;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denprog.praticeapp3inventorysystem.R;
import com.denprog.praticeapp3inventorysystem.callback.SimpleLoadingCallback;
import com.denprog.praticeapp3inventorysystem.databinding.FragmentAddItemBinding;
import com.denprog.praticeapp3inventorysystem.room.entities.InventoryItem;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddItemFragment extends Fragment {

    private AddItemViewModel mViewModel;
    private FragmentAddItemBinding binding;
    private ProgressDialog dialog;
    private ActivityResultLauncher<PickVisualMediaRequest> imagePicker = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri o) {
            if (o != null) {
                mViewModel.convertUriToBitmap(o, requireContext(), new SimpleLoadingCallback<Bitmap>() {
                    @Override
                    public void onFinished(Bitmap data) {
                        mViewModel.itemImageMutableLiveData.setValue(data);
                        dialog.hide();
                    }

                    @Override
                    public void onLoading() {
                        if (dialog.isShowing()) {return;}
                        dialog.show();
                    }

                    @Override
                    public void onError(String message) {
                        showError(message);
                        dialog.hide();
                    }
                });
            }
        }
    });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddItemBinding.inflate(inflater, container, false);
        dialog = new ProgressDialog(requireContext());
        dialog.setTitle("Loading");
        dialog.setMessage("Please Wait");
        dialog.setIndeterminate(true);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(AddItemViewModel.class);
        mViewModel.addItemFormStateMutableLiveData.observe(getViewLifecycleOwner(), addItemFormState -> {
            binding.actionOnItem.setEnabled(addItemFormState.isDataValid != null && addItemFormState.isDataValid);
            binding.itemNameField.setError(addItemFormState.itemNameError);
            binding.itemPriceField.setError(addItemFormState.itemPriceError);
            binding.itemStockField.setError(addItemFormState.itemStockAmountError);
        });

        mViewModel.itemImageMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                binding.itemImageField.setImageBitmap(bitmap);
            }
        });

        mViewModel.mutableLiveData.observe(getViewLifecycleOwner(), addItemFragmentState -> {
            if (addItemFragmentState instanceof AddItemFragmentState.InsertMode) {
                View.OnClickListener clickListener = view -> {
                    String itemName = binding.itemNameField.getText().toString();
                    String itemPrice = binding.itemPriceField.getText().toString();
                    String itemStockAmount = binding.itemStockField.getText().toString();
                    Bitmap bitmap = mViewModel.itemImageMutableLiveData.getValue();
                    if (bitmap == null) {
                        showError("Select a profile image.");
                        return;
                    }
                    mViewModel.insertItem(requireContext(), itemName, itemPrice, itemStockAmount, ((AddItemFragmentState.InsertMode) addItemFragmentState).userId, bitmap, new SimpleLoadingCallback<InventoryItem>() {
                        @Override
                        public void onFinished(InventoryItem data) {
                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                            navController.popBackStack();
                            dialog.hide();
                        }

                        @Override
                        public void onLoading() {
                            if (dialog.isShowing()) {
                                return;
                            }
                            dialog.show();
                        }

                        @Override
                        public void onError(String message) {
                            dialog.hide();
                        }
                    });
                };
                mViewModel.onClickListenerMutableLiveData.setValue(clickListener);
            } else if (addItemFragmentState instanceof AddItemFragmentState.UpdateMode) {
                InventoryItem inventoryItem = ((AddItemFragmentState.UpdateMode) addItemFragmentState).inventoryItem;
            }
        });

        mViewModel.onClickListenerMutableLiveData.observe(getViewLifecycleOwner(), new Observer<View.OnClickListener>() {
            @Override
            public void onChanged(View.OnClickListener onClickListener) {
                binding.actionOnItem.setOnClickListener(onClickListener);
            }
        });

        binding.itemImageField.setOnClickListener(view -> imagePicker.launch(new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build()));

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String itemName = binding.itemNameField.getText().toString();
                String itemPrice = binding.itemPriceField.getText().toString();
                String itemStockAmount = binding.itemStockField.getText().toString();
                mViewModel.onDataChange(itemName, itemPrice, itemStockAmount);
            }
        };

        binding.itemNameField.addTextChangedListener(textWatcher);
        binding.itemStockField.addTextChangedListener(textWatcher);
        binding.itemPriceField.addTextChangedListener(textWatcher);

        AddItemFragmentArgs args = AddItemFragmentArgs.fromBundle(getArguments());
        if (args.getItemToUpdate() != null) {
            mViewModel.mutableLiveData.setValue(new AddItemFragmentState.UpdateMode(args.getItemToUpdate()));
        } else {
            mViewModel.mutableLiveData.setValue(new AddItemFragmentState.InsertMode(args.getUserId()));
        }

    }

    private void showError(String s) {
        new AlertDialog.Builder(requireContext()).setTitle("Error").setMessage(s).create().show();
    }

}