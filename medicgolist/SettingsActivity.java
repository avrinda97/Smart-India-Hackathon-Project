package com.example.shubhanshu.medicgolist;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
    public void setti(View v){
        EditText et = findViewById(R.id.seti);
        String loc = et.getText().toString();
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(SettingsActivity.this);
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("location", loc);
        Log.e("loc",loc);
        edit.commit();
    }

}
