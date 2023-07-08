package com.dkiktenko.starlists.BugReport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dkiktenko.starlists.R;

public class ReportABugActivity extends AppCompatActivity {

    private static final @NonNull String TAG = "ReportABugActivity";

    private final @NonNull View.OnClickListener submitButtonClickHandler = v -> {
        EditText input = findViewById(R.id.bug_report);
        if (input == null) {
            Log.w(TAG, "No input field found");
            Toast.makeText(this, "No input field found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        CheckBox contactAuthorizationCheckbox = findViewById(R.id.contact_authorization_checkbox);
        if (contactAuthorizationCheckbox == null) {
            Log.w(TAG, "No contact authorization checkbox found");
            Toast.makeText(this, "No contact authorization checkbox found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Editable inputText = input.getText();
        if ((inputText == null) || (TextUtils.isEmpty(inputText.toString()))) {
            Log.w(TAG, "No description of bug to submit");
            Toast.makeText(this, R.string.bug_report_empty_warning, Toast.LENGTH_SHORT).show();
        } else {
            // TODO: Form submission
            finish();
            Toast.makeText(this, R.string.bug_report_submitted, Toast.LENGTH_SHORT).show();
        }
    };

    public static void Show(@NonNull Activity activity) {
        activity.startActivity(new Intent(activity, ReportABugActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_a_bug_activity);

        Button submitButton = findViewById(R.id.report_a_bug_button_button);
        if (submitButton == null) {
            throw new RuntimeException("No submit bug report button found");
        }

        submitButton.setOnClickListener(submitButtonClickHandler);
    }
}