package com.denprog.praticeapp3inventorysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.denprog.praticeapp3inventorysystem.ui.login.LoginFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
    }
}