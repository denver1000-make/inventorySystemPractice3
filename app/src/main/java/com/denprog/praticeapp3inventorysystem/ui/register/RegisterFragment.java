package com.denprog.praticeapp3inventorysystem.ui.register;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denprog.praticeapp3inventorysystem.R;
import com.denprog.praticeapp3inventorysystem.callback.SimpleLoadingCallback;
import com.denprog.praticeapp3inventorysystem.databinding.FragmentRegisterBinding;
import com.denprog.praticeapp3inventorysystem.room.entities.User;
import com.denprog.praticeapp3inventorysystem.util.Validator;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private RegisterViewModel viewModel;
    private ProgressDialog progressDialog;
    private ActivityResultLauncher<PickVisualMediaRequest> imagePicker = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri o) {
            viewModel.convertUriToBitmap(o, requireContext(), new SimpleLoadingCallback<>() {
                @Override
                public void onFinished(Bitmap data) {
                    binding.pickProfileImage.setImageBitmap(data);
                    viewModel.bitmapMutableLiveData.setValue(data);
                    progressDialog.dismiss();
                    binding.registerAction.setEnabled(true);
                }

                @Override
                public void onLoading() {
                    if (!progressDialog.isShowing()) {
                        progressDialog.show();
                    }
                    binding.registerAction.setEnabled(false);
                }

                @Override
                public void onError(String message) {
                    progressDialog.dismiss();
                    binding.registerAction.setEnabled(true);
                    showErrorMessage(message);
                }
            });
        }
    });

    public void showErrorMessage (String message) {
        new AlertDialog.Builder(requireContext()).setMessage(message).create().show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait");
        progressDialog.setIndeterminate(true);
        binding.pickProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePicker.launch(new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build());
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.setRegisterViewModel(viewModel);
        binding.registerAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = viewModel.firstNameField.get();
                String middleName = viewModel.middleNameField.get();
                String lastName = viewModel.lastNameField.get();
                String userName = viewModel.userNameField.get();
                String email = viewModel.emailField.get();
                String password = viewModel.passwordField.get();
                String confirmPassword = viewModel.confirmPasswordField.get();

                String firstNameError = Validator.validateName(firstName);
                String lastNameError = Validator.validateName(lastName);
                String emailError = Validator.validateEmail(email);
                String passwordError = Validator.validatePassword(password);
                String usernameError = Validator.validateName(userName);
                String confirmPasswordError = Validator.confirmPasswordError(password, confirmPassword);
                Bitmap bitmap = viewModel.bitmapMutableLiveData.getValue();
                String profileError = bitmap == null ? "No Selected Image" : null;

                binding.firstNameInput.setError(firstNameError);
                binding.lastNameInput.setError(lastNameError);
                binding.emailInput.setError(emailError);
                binding.passwordInput.setError(passwordError);
                binding.usernameInput.setError(usernameError);
                binding.confirmPasswordInput.setError(confirmPasswordError);
                if (profileError != null) {
                    showErrorMessage(profileError);
                }

                if (!doErrorExist(firstNameError, lastNameError, emailError, passwordError, usernameError, confirmPasswordError, profileError)) {
                    viewModel.register(
                            requireContext(),
                            viewModel.bitmapMutableLiveData.getValue(),
                            firstName,
                            middleName,
                            lastName,
                            userName,
                            email,
                            password, new SimpleLoadingCallback<User>() {
                        @Override
                        public void onFinished(User data) {
                            progressDialog.dismiss();
                            NavController navController = Navigation.findNavController(requireActivity(), R.id.loginRegFragmentCotainerView);
                            RegisterFragmentDirections.ActionRegisterFragmentToLoginFragment directions =RegisterFragmentDirections.actionRegisterFragmentToLoginFragment();
                            directions.setEmail(data.email);
                            directions.setPassword(data.password);
                            NavOptions navOptions = new NavOptions.Builder().setPopUpTo(navController.getGraph().getStartDestinationId(), true).build();
                            navController.navigate(directions, navOptions);

                        }

                        @Override
                        public void onLoading() {
                            if (!progressDialog.isShowing()) {
                                progressDialog.show();
                            }
                        }

                        @Override
                        public void onError(String message) {
                            showErrorMessage(message);
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private boolean doErrorExist(String ...errors) {
        for (String error : errors) {
            if (error != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        progressDialog = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        progressDialog = null;
    }
}