package com.example.shubhanshu.medicgolist;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.app.ActionBar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class MedicineInfo extends AppCompatActivity {

    ActionBar mActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_info);
        Intent intent = getIntent();

        String name=intent.getStringExtra("name");
        String desc=intent.getStringExtra("description");
        int id = Integer.parseInt(intent.getStringExtra("medicine_id"));
        String price =intent.getStringExtra("price");
        int downvote = Integer.parseInt(intent.getStringExtra("downvote"));
        int upvote = Integer.parseInt(intent.getStringExtra("upvote"));
        Log.e("zzxxxz",name+" "+price);
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        bundle.putString("description",desc);
        bundle.putString("medicine_id",id+"");
        bundle.putString("price",price);
        bundle.putString("upvote",upvote+"");
        bundle.putString("downvote",downvote+"");

        mActionBar = getActionBar();
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),bundle));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
