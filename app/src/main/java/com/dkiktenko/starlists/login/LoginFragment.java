package com.dkiktenko.starlists.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.dkiktenko.starlists.MainActivity;
import com.dkiktenko.starlists.R;

public class LoginFragment extends Fragment {

    private final View.OnClickListener loginButtonClickHandler = v -> {
        // TODO: Implement authentication
        openAppEntryPoint();
    };
    private final View.OnClickListener forgotPasswordClickHandler = view -> {
        // TODO: Implement showing of the "Forgot Password" fragment
    };
    private final View.OnClickListener signUpClickHandler = view -> {
        // TODO: Implement showing of the "Sign Up" fragment
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if (validateCredentials()) {
            openAppEntryPoint();
            return;
        }

        setupLoginButton(view);
        setupForgotPasswordButton(view);
        setupSignUpButton(view);
    }


    private boolean validateCredentials() {
        // TODO: Implement validation of credentials if user is already signed in
        return false;
    }

    private void setupLoginButton(@NonNull View view) {
        ViewCompat.requireViewById(view, R.id.login_button).setOnClickListener(loginButtonClickHandler);
    }

    private void setupForgotPasswordButton(@NonNull View view) {
        ViewCompat.requireViewById(view, R.id.forgot_password_label).setOnClickListener(forgotPasswordClickHandler);
    }

    private void setupSignUpButton(@NonNull View view) {
        ViewCompat.requireViewById(view, R.id.sign_up_label).setOnClickListener(signUpClickHandler);
    }

    private void openAppEntryPoint() {
        Intent intent = new Intent(requireContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}