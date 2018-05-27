package com.example.shubhanshu.medicgolist;

/**
 * Created by shubham on 30/3/18.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FragmentA extends Fragment implements View.OnClickListener{

    int medicineid = 0;
    String city = "kolkata";
    String state="west bengal";
    final int upvote=1;
    int vote;
    final int downvote=-1;
    String url = MedicConstant.url+"medicGoServer/get_medicine_by_id.php";
    String url2=MedicConstant.url+"medicGoServer/voting.php";
    RequestQueue mRequestQueue;
    View view;
    Button ubtn,dbtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getArguments()!=null){

            medicineid=Integer.parseInt(getArguments().getString("medicine_id"));
            Log.e("zzz",medicineid+"");
        }
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 5*1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());

        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        view = inflater.inflate(R.layout.fragment_a, container, false);
        dbtn  = view.findViewById(R.id.downvotebutton);
        ubtn = view.findViewById(R.id.upvotebutton);
        dbtn.setOnClickListener( this);
        ubtn.setOnClickListener(this);

        return view;
    }
    public void VolleyCaller(){
        VolleyVote();
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upvotebutton:
                vote=upvote;
                ubtn.setEnabled(false);
                dbtn.setEnabled(false);
                break;
            case R.id.downvotebutton:
                //switchFragment(SettingsFragment.TAG);
                vote=downvote;
                dbtn.setEnabled(false);
                ubtn.setEnabled(false);
                break;
        }
        VolleyCaller();
    }
    public void VolleyVote(){
        StringRequest request = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("aaa",response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String > mydata  = new HashMap<String, String>();

                mydata.put("medicine_id",medicineid+"");
                mydata.put("city",city);
                mydata.put("state",state);
                mydata.put("vote",vote+"");
                return mydata;
            }
        };
        mRequestQueue.add(request);
    }
}
