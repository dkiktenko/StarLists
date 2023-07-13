package com.dkiktenko.starlists.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dkiktenko.starlists.BugReport.ReportABugActivity;
import com.dkiktenko.starlists.MainActivity;
import com.dkiktenko.starlists.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    static final String TAG = "LoginFragment";

    private final View.OnClickListener loginButtonClickHandler = v -> {
        validateCredentials();
    };
    private final View.OnClickListener forgotPasswordClickHandler = view -> {
        // TODO: Implement showing of the "Forgot Password" fragment
    };
    private final View.OnClickListener signUpClickHandler = view -> {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, new RegistrationFragment())
                .hide(this)
                .addToBackStack(RegistrationFragment.TAG)
                .commit();
    };

    private @NonNull TextInputEditText emailField;
    private @NonNull TextInputEditText passwordField;
    private @NonNull FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        emailField = ViewCompat.requireViewById(view, R.id.email);
        passwordField = ViewCompat.requireViewById(view, R.id.password);

        setupReportABug(view);
        setupLoginButton(view);
        setupForgotPasswordButton(view);
        setupSignUpButton(view);
    }


    private void validateCredentials() {
        Editable email = emailField.getText();
        Editable password = passwordField.getText();

        if (TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password)) {
            Log.w(TAG, "Email and/or password left empty");
            Toast.makeText(requireContext(), R.string.email_password_cannot_be_empty_warning, Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email.toString(), password.toString()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success");
                openAppEntryPoint();
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.getException());
                Toast.makeText(requireContext(), R.string.email_password_incorrect, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupReportABug(@NonNull View view) {
        ViewCompat.requireViewById(view, R.id.report_a_bug_button).setOnClickListener(v -> ReportABugActivity.Show(requireActivity()));
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
        requireActivity().finish();
    }
}