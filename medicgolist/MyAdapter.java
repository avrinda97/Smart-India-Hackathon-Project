package com.example.shubhanshu.medicgolist;

/**
 * Created by shubham on 30/3/18.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MyAdapter extends FragmentStatePagerAdapter {

    private final Bundle fragmentBundle;
    public MyAdapter(FragmentManager fm, Bundle data) {
        super(fm);
        fragmentBundle = data;

    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            final FragmentA fragmentA = new FragmentA();
            fragmentA.setArguments(fragmentBundle);
            return fragmentA;
        }
        else if(position==1){
            final FragmentB fragmentB = new FragmentB();
            fragmentB.setArguments(fragmentBundle);
            return fragmentB;
        }
        else if(position==2){
            final FragmentC fragmentC = new FragmentC();
            fragmentC.setArguments(fragmentBundle);
            return fragmentC;
        }
        else{
            return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "Reviews";
        }
        else if(position==1){
            return "Details";
        }
        else if(position==2){
            return "Available In";
        }
        else{
            return null;
        }
    }
}
