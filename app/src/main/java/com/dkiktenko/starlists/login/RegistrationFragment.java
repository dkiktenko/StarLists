package com.dkiktenko.starlists.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.dkiktenko.starlists.BugReport.ReportABugActivity;
import com.dkiktenko.starlists.MainActivity;
import com.dkiktenko.starlists.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RegistrationFragment extends Fragment {

    static final String TAG = "RegistrationFragment";
    static final int MIN_PASSWORD_LENGTH_REQUIRED = 8;

    private static final Pattern letterPattern = Pattern.compile("[a-zA-z]");
    private static final Pattern digitPattern = Pattern.compile("[0-9]");
    private static final Pattern specialCharacterPatter = Pattern.compile("[`^.,/;':\"!@#$%&*()_+=|<>?{}\\[\\]~-]");

    private @NonNull TextInputEditText firstNameField;
    private @NonNull TextInputLayout firstNameFieldLayout;
    private @NonNull TextInputEditText lastNameField;
    private @NonNull TextInputLayout lastNameFieldLayout;
    private @NonNull TextInputEditText emailAddressField;
    private @NonNull TextInputLayout emailAddressFieldLayout;
    private @NonNull TextInputEditText initialPasswordField;
    private @NonNull TextInputLayout initialPasswordFieldLayout;
    private @NonNull TextInputEditText confirmedPasswordField;
    private @NonNull TextInputLayout confirmedPasswordFieldLayout;
    private @NonNull CheckBox registrationCheckbox;

    private @NonNull FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.registration_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        firstNameField = ViewCompat.requireViewById(view, R.id.first_name);
        firstNameFieldLayout = ViewCompat.requireViewById(view, R.id.first_name_layout);
        lastNameField = ViewCompat.requireViewById(view, R.id.last_name);
        lastNameFieldLayout = ViewCompat.requireViewById(view, R.id.last_name_layout);
        emailAddressField = ViewCompat.requireViewById(view, R.id.email_address);
        emailAddressFieldLayout = ViewCompat.requireViewById(view, R.id.email_address_layout);
        initialPasswordField = ViewCompat.requireViewById(view, R.id.initial_password);
        initialPasswordFieldLayout = ViewCompat.requireViewById(view, R.id.initial_password_layout);
        confirmedPasswordField = ViewCompat.requireViewById(view, R.id.confirmed_password);
        confirmedPasswordFieldLayout = ViewCompat.requireViewById(view, R.id.password_confirmed_layout);
        registrationCheckbox = ViewCompat.requireViewById(view, R.id.registration_checkbox);

        setupReportABug(view);

        setupFirstNameFieldValidation();
        setupLastNameFieldValidation();
        setupEmailFieldValidation();
        setupInitialPasswordFieldValidation();
        setupConfirmedPasswordFieldValidation();
        setupRegisterButton(view);
    }

    private void setupRegisterButton(@NonNull View view) {
        ViewCompat.requireViewById(view, R.id.register_button).setOnClickListener(v -> {

            boolean nonEmailPasswordFieldsValid = validateNonEmailPasswordFields();
            String email = validateEmail(emailAddressField.getText());
            CharSequence password = validatePassword(initialPasswordField.getText());
            CharSequence confirmedPassword = validateConfirmedPassword(confirmedPasswordField.getText());

            if (!nonEmailPasswordFieldsValid || TextUtils.isEmpty(email) ||
                    TextUtils.isEmpty(password) ||
                    TextUtils.isEmpty(confirmedPassword)) {
                // TODO: Error handling
                return;
            }

            if (!registrationCheckbox.isChecked()) {
                // TODO: Error handling
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password.toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(requireActivity(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);
                        } else {
                            // TODO: Error handling
                        }
                    });
        });
    }

    private void setupReportABug(@NonNull View view) {
        ViewCompat.requireViewById(view, R.id.report_a_bug_button).setOnClickListener(v -> ReportABugActivity.Show(requireActivity()));
    }

    private void setupFirstNameFieldValidation() {
        firstNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable fieldText) {
                validateName(fieldText, firstNameFieldLayout);
            }
        });
        firstNameField.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validateName(firstNameField.getText(), firstNameFieldLayout);
            }
        });
    }

    private void setupLastNameFieldValidation() {
        lastNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable fieldText) {
                validateName(fieldText, lastNameFieldLayout);
            }
        });
        lastNameField.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validateName(lastNameField.getText(), lastNameFieldLayout);
            }
        });
    }

    private void setupEmailFieldValidation() {
        emailAddressField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence fieldText, int start, int before, int count) {
                validateEmail(fieldText);
            }

            @Override
            public void afterTextChanged(Editable fieldText) {
            }
        });
        emailAddressField.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validateEmail(emailAddressField.getText());
            }
        });
    }

    private void setupInitialPasswordFieldValidation() {
        initialPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence fieldText, int start, int before, int count) {
                validatePassword(fieldText);
            }

            @Override
            public void afterTextChanged(Editable fieldText) {
            }
        });
        initialPasswordField.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validatePassword(initialPasswordField.getText());
            }
        });
    }

    private void setupConfirmedPasswordFieldValidation() {
        confirmedPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                validateConfirmedPassword(text);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        confirmedPasswordField.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validateConfirmedPassword(confirmedPasswordField.getText());
            }
        });
    }

    private @Nullable CharSequence validateConfirmedPassword(@Nullable CharSequence text) {
        confirmedPasswordFieldLayout.setErrorEnabled(true);
        if (!TextUtils.isEmpty(text)) {
            Editable initialPassword = initialPasswordField.getText();
            if ((initialPassword == null) ||
                    (!initialPassword.toString().contentEquals(text))) {
                confirmedPasswordFieldLayout.setError(getString(R.string.password_does_not_match));
            } else {
                confirmedPasswordFieldLayout.setError(null);
                confirmedPasswordFieldLayout.setErrorEnabled(false);
                return text;
            }
        } else {
            confirmedPasswordFieldLayout.setError(getString(R.string.field_required));
        }
        return null;
    }

    /**
     * Validates that all fields that are not either the email or password are valid (i.e., have input)
     *
     * @return True, if all non-email and password fields are valid. Otherwise, false.
     */
    private boolean validateNonEmailPasswordFields() {
        CharSequence firstName = validateName(firstNameField.getText(), firstNameFieldLayout);
        CharSequence lastName = validateName(lastNameField.getText(), lastNameFieldLayout);

        return ((firstName != null) && (lastName != null));
    }

    private @Nullable CharSequence validateName(@Nullable CharSequence enteredValue, @NonNull TextInputLayout fieldLayout) {
        if (TextUtils.isEmpty(enteredValue)) {
            fieldLayout.setErrorEnabled(true);
            fieldLayout.setError(getString(R.string.field_required));
            return null;
        } else {
            fieldLayout.setError(null);
            fieldLayout.setErrorEnabled(false);
            return enteredValue;
        }
    }

    /**
     * Validates the input within the email address field
     *
     * @return Email address as string if valid. Otherwise, null.
     */
    private @Nullable String validateEmail(@Nullable CharSequence input) {
        emailAddressFieldLayout.setErrorEnabled(true);
        if (!TextUtils.isEmpty(input)) {
            // check to confirm email format is correct (@ symbol is present and a period
            // is included within the domain name after the @ symbol with at least one
            // character separating the two)
            String emailInput = input.toString();
            int atSymbolIndex = emailInput.indexOf("@");
            if (atSymbolIndex != -1) {
                int periodSymbolIndex = emailInput.indexOf(".", atSymbolIndex);
                if ((periodSymbolIndex != -1) &&
                        (periodSymbolIndex != (atSymbolIndex + 1))) {
                    emailAddressFieldLayout.setError(null);
                    emailAddressFieldLayout.setErrorEnabled(false);
                    return emailInput;
                }
            }
            emailAddressFieldLayout.setError(getString(R.string.invalid_email_format_warning));
            return null;
        }

        emailAddressFieldLayout.setError(getString(R.string.field_required));
        return null;
    }

    private @Nullable CharSequence validatePassword(@Nullable CharSequence input) {
        initialPasswordFieldLayout.setErrorEnabled(true);
        if (!TextUtils.isEmpty(input)) {
            validateConfirmedPassword(confirmedPasswordField.getText());
            // check to password contains a letter, a digit, and a special character
            if (!letterPattern.matcher(input).find()) {
                initialPasswordFieldLayout.setError(getString(R.string.password_field_must_contain_letter_warning));
            } else if (!digitPattern.matcher(input).find()) {
                initialPasswordFieldLayout.setError(getString(R.string.password_field_must_contain_digit_warning));
            } else if (!specialCharacterPatter.matcher(input).find()) {
                initialPasswordFieldLayout.setError(getString(R.string.password_field_must_contain_special_character_warning));
            } else if (input.length() < MIN_PASSWORD_LENGTH_REQUIRED) {
                initialPasswordFieldLayout.setError(getString(R.string.password_field_must_meet_minimum_length_warning));
            } else {
                initialPasswordFieldLayout.setError(null);
                initialPasswordFieldLayout.setErrorEnabled(false);
                return input;
            }
            return null;
        }

        initialPasswordFieldLayout.setError(getString(R.string.field_required));
        return null;
    }
}