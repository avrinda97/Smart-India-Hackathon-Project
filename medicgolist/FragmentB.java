package com.example.shubhanshu.medicgolist;

/**
 * Created by shubham on 30/3/18.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentB extends Fragment {

    String name;
    int id;
    int upvote;
    int downvote;
    String price;
    String desc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);
        if(getArguments()!=null){
            Log.e("sss","kkkk");
            name = getArguments().getString("name");
            id = Integer.parseInt(getArguments().getString( "medicine_id"));
            desc =  "100 people found this popular";//getArguments().getString("description");
            price = getArguments().getString("price");
            upvote=Integer.parseInt(getArguments().getString("upvote"));
            downvote=Integer.parseInt(getArguments().getString("downvote"));
        }
        desc = "Upvotes : "+upvote+ " / Downvotes :"+downvote;
        TextView brand = (TextView) view.findViewById(R.id.brand_name);
        TextView priceView = (TextView) view.findViewById(R.id.price_value);
        TextView popularity = (TextView) view.findViewById(R.id.popularity_value);
        Log.e("aaa",name+" "+price+" ");
        brand.setText(name);
        priceView.setText(price);
        popularity.setText(desc);
        Button btn =(Button) view.findViewById(R.id.medical_stores_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),MapsActivity.class));
            }
        });

        return view;
    }


}