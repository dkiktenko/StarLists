package com.dkiktenko.starlists.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.dkiktenko.starlists.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        setupFirstNameFieldValidation();
        setupLastNameFieldValidation();
        setupEmailFieldValidation();
        setupInitialPasswordFieldValidation();
        setupConfirmedPasswordFieldValidation();
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
                if (fieldText != null) {
                    if (!TextUtils.isEmpty(fieldText.toString())) {
                        firstNameFieldLayout.setError(null);
                        firstNameFieldLayout.setErrorEnabled(false);
                        return;
                    }
                }

                firstNameFieldLayout.setErrorEnabled(true);
                firstNameFieldLayout.setError(getString(R.string.field_required));
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
                if (fieldText != null) {
                    if (!TextUtils.isEmpty(fieldText.toString())) {
                        lastNameFieldLayout.setError(null);
                        lastNameFieldLayout.setErrorEnabled(false);
                        return;
                    }
                }

                lastNameFieldLayout.setErrorEnabled(true);
                lastNameFieldLayout.setError(getString(R.string.field_required));
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
                emailAddressFieldLayout.setErrorEnabled(true);
                if (fieldText != null) {
                    String fieldTextString = fieldText.toString();
                    if (!TextUtils.isEmpty(fieldTextString)) {
                        // check to confirm email format is correct (@ symbol is present and a period
                        // is included within the domain name after the @ symbol with at least one
                        // character separating the two)
                        int atSymbolIndex = fieldTextString.indexOf("@");
                        if (atSymbolIndex != -1) {
                            int periodSymbolIndex = fieldTextString.indexOf(".", atSymbolIndex);
                            if ((periodSymbolIndex != -1) &&
                                    (periodSymbolIndex != (atSymbolIndex + 1))) {
                                emailAddressFieldLayout.setError(null);
                                emailAddressFieldLayout.setErrorEnabled(false);
                                return;
                            }
                        }
                        emailAddressFieldLayout.setError(getString(R.string.invalid_email_format_warning));
                        return;
                    }
                }

                emailAddressFieldLayout.setError(getString(R.string.field_required));
            }

            @Override
            public void afterTextChanged(Editable fieldText) {
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
                initialPasswordFieldLayout.setErrorEnabled(true);
                if ((fieldText != null) && (!TextUtils.isEmpty(fieldText))) {
                    validateConfirmedPassword(confirmedPasswordField.getText());
                    // check to password contains a letter, a digit, and a special character
                    if (!letterPattern.matcher(fieldText).find()) {
                        initialPasswordFieldLayout.setError(getString(R.string.password_field_must_contain_letter_warning));
                    } else if (!digitPattern.matcher(fieldText).find()) {
                        initialPasswordFieldLayout.setError(getString(R.string.password_field_must_contain_digit_warning));
                    } else if (!specialCharacterPatter.matcher(fieldText).find()) {
                        initialPasswordFieldLayout.setError(getString(R.string.password_field_must_contain_special_character_warning));
                    } else if (fieldText.length() < MIN_PASSWORD_LENGTH_REQUIRED) {
                        initialPasswordFieldLayout.setError(getString(R.string.password_field_must_meet_minimum_length_warning));
                    } else {
                        initialPasswordFieldLayout.setError(null);
                        initialPasswordFieldLayout.setErrorEnabled(false);
                    }
                    return;
                }

                initialPasswordFieldLayout.setError(getString(R.string.field_required));
            }

            @Override
            public void afterTextChanged(Editable fieldText) {
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
    }

    private void validateConfirmedPassword(@Nullable CharSequence text) {
        confirmedPasswordFieldLayout.setErrorEnabled(true);
        if ((text != null) && (!TextUtils.isEmpty(text))) {
            Editable initialPassword = initialPasswordField.getText();
            if ((initialPassword == null) ||
                    (!initialPassword.toString().contentEquals(text))) {
                confirmedPasswordFieldLayout.setError(getString(R.string.password_does_not_match));
            } else {
                confirmedPasswordFieldLayout.setError(null);
                confirmedPasswordFieldLayout.setErrorEnabled(false);
            }
            return;
        }
        confirmedPasswordFieldLayout.setError(getString(R.string.field_required));
    }
}