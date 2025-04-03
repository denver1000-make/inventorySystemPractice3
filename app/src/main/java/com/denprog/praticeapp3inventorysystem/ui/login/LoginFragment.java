package com.denprog.praticeapp3inventorysystem.ui.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.denprog.praticeapp3inventorysystem.databinding.FragmentLoginBinding;
import com.denprog.praticeapp3inventorysystem.room.entities.User;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private FragmentLoginBinding binding;
    private ProgressDialog dialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        mViewModel.loginFormStateMutableLiveData.observe(getViewLifecycleOwner(), new Observer<>() {
            @Override
            public void onChanged(LoginFormState loginFormState) {
                if (loginFormState == null) { return; }
                binding.emailLoginField.setError(loginFormState.emailError);
                binding.passwordLoginField.setError(loginFormState.passwordError);
                binding.loginAction.setEnabled(loginFormState.isDataValid);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding =  FragmentLoginBinding.inflate(inflater, container, false);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String email = binding.emailLoginField.getText().toString();
                String password = binding.passwordLoginField.getText().toString();
                mViewModel.onDataChanged(email, password);
            }
        };
        binding.loginAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.login(binding.emailLoginField.getText().toString(), binding.passwordLoginField.getText().toString(), new SimpleLoadingCallback<User>() {
                    @Override
                    public void onFinished(User data) {
                        dialog.hide();
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.loginRegFragmentCotainerView);
                        navController.navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity(data.userId));
                    }

                    @Override
                    public void onLoading() {
                        if (!dialog.isShowing()) {
                            dialog.show();
                        }
                    }

                    @Override
                    public void onError(String message) {
                        showError(message);
                        dialog.hide();
                    }
                });
            }
        });
        binding.redirectToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.loginRegFragmentCotainerView);
                navController.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment());
            }
        });
        binding.emailLoginField.addTextChangedListener(textWatcher);
        binding.passwordLoginField.addTextChangedListener(textWatcher);
        this.dialog = new ProgressDialog(requireContext());
        dialog.setIndeterminate(true);
        dialog.setTitle("Loading");
        dialog.setMessage("Please Wait");
        return binding.getRoot();
    }

    public void showError(String message) {
        new AlertDialog.Builder(requireContext()).setMessage(message).create().show();
    }

}