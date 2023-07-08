package com.dkiktenko.starlists.BugReport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dkiktenko.starlists.R;

public class ReportABugActivity extends AppCompatActivity {

    public static void Show(@NonNull Activity activity) {
        activity.startActivity(new Intent(activity, ReportABugActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_a_bug_activity);
    }
}