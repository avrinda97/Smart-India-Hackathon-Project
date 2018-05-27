package com.example.shubhanshu.medicgolist;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SearchOrReview extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_or_review);
        LocationManager locationManager =(LocationManager)getSystemService(Context.LOCATION_SERVICE);
    }


    public void searchBtn(View v){
        Intent intent = new Intent(SearchOrReview.this,MainActivity.class);
        EditText et = findViewById(R.id.medicine_search_bar);
        String medicine = et.getText().toString();
        intent.putExtra("medicine_name",medicine);
        startActivity(intent);

    }


}
