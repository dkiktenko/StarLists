package com.dkiktenko.starlists.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dkiktenko.starlists.MainActivity;
import com.dkiktenko.starlists.R;

public class Login extends AppCompatActivity {

    private final View.OnClickListener loginButtonClickHandler = v -> {
        // TODO: Implement authentication
        openAppEntryPoint();
    };
    private View.OnClickListener forgotPasswordClickHandler = view -> {
        // TODO: Implement showing of the "Forgot Password" fragment
    };
    private View.OnClickListener signUpClickHandler = view -> {
        // TODO: Implement showing of the "Sign Up" fragment
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if (validateCredentials()) {
            openAppEntryPoint();
            return;
        }

        setupLoginButton();
        setupForgotPasswordButton();
        setupSignUpButton();
    }


    private boolean validateCredentials() {
        // TODO: Implement validation of credentials if user is already signed in
        return false;
    }

    private void setupLoginButton() {
        Button loginButton = findViewById(R.id.login_button);
        if (loginButton == null) {
            throw new RuntimeException("No \"login\" button found");
        }

        loginButton.setOnClickListener(loginButtonClickHandler);
    }

    private void setupForgotPasswordButton() {
        TextView forgotPasswordText = findViewById(R.id.forgot_password_label);
        if (forgotPasswordText == null) {
            throw new RuntimeException("No \"forgot password\" button found");
        }

        forgotPasswordText.setOnClickListener(forgotPasswordClickHandler);
    }

    private void setupSignUpButton() {
        TextView signUpText = findViewById(R.id.sign_up_label);
        if (signUpText == null) {
            throw new RuntimeException("No \"sign up\" button found");
        }

        signUpText.setOnClickListener(signUpClickHandler);
    }

    private void openAppEntryPoint() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}