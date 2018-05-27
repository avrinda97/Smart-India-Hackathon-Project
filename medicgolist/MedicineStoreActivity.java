package com.example.shubhanshu.medicgolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MedicineStoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_store);

    }

    public void submit(View view){
        EditText et1 ,et2;
        Intent intent = new Intent(MedicineStoreActivity.this,MedicineBillGenerator.class);
        et1 = findViewById(R.id.storename);
        et2 = findViewById(R.id.storepincode);

        String name = et1.getText().toString();
        String pincode = et2.getText().toString();

        intent.putExtra("name",name);
        intent.putExtra("pin",pincode);

        startActivity(intent);
    }

}
