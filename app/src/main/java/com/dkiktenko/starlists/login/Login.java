package com.dkiktenko.starlists.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dkiktenko.starlists.MainActivity;
import com.dkiktenko.starlists.R;

public class Login extends AppCompatActivity {

    private final View.OnClickListener loginButtonClickHandler = v -> {
        // TODO: Implement authentication
        openAppEntryPoint();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        setupLoginButton();
    }

    private void setupLoginButton() {
        Button loginButton = findViewById(R.id.login_button);
        if (loginButton == null) {
            throw new RuntimeException("No login button found");
        }

        loginButton.setOnClickListener(loginButtonClickHandler);
    }

    private void openAppEntryPoint() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}