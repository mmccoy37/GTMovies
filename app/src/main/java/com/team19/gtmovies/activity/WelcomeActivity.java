package com.team19.gtmovies.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.team19.gtmovies.CurrentState;
import com.team19.gtmovies.R;

public class WelcomeActivity extends AppCompatActivity {
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    /**
     * called when "Login" button does onClick
     * @param view from XML/activity
     */
    public void userLogin(View view) {
        int success = 1;
        setResult(success, new Intent().putExtra("login", true));
        finish();
    }

    /**
     * called when "Register" button does onClick
     * @param view from XML/activity
     */
    public void userRegister(View view) {
        int success = 1;
        setResult(success, new Intent().putExtra("login", false));
        finish();
    }

    /**
     * hidden method. don't tell Matt if you find this.
     * @param view from XML/activity
     */
    public void disguisedToast(View view) {
        Context context = getApplicationContext();
        CharSequence hi = "Why hello there";
        CharSequence au = "Achievement Unlocked!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = null;

        if (count == 4) toast = Toast.makeText(context, hi, duration);
        else if (count == 7) toast = Toast.makeText(context, "Stop", duration);
        else if (count == 10) toast = Toast.makeText(context, au, Toast.LENGTH_LONG);
        if (toast != null) toast.show();
        count += 1;
    }

    @Override
    public void onBackPressed() {
    }
}
