package com.dkiktenko.starlists.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.dkiktenko.starlists.MainActivity;
import com.dkiktenko.starlists.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginRegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_registration_activity);

        mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        } else if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, new LoginFragment());
            transaction.commit();
        }
    }
}