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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentC extends Fragment {

    View view;
    ArrayList<String > storeList;
    String url=MedicConstant.url+"medicGoServer/check_availability_in_medical_store.php";
    int medicineID=4;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_c, container, false);
        Volley();
        storeList = new ArrayList<String>();
        if(getArguments()!=null){
            medicineID = Integer.parseInt(getArguments().getString("medicine_id"));
        }
        Log.e("aaa",medicineID+"");
        return view;
    }

    public void Volley(){
        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 5*1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("aaa",response);
                    updateUi(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String > mydata  = new HashMap<String, String>();
                mydata.put("medicine_id",medicineID+"");
                mydata.put("pincode","700058");

                return mydata;
            }
        };
        mRequestQueue.add(request);

    }
    public void updateUi(String data) throws JSONException {
     //   Log.e("aaa",data);
        JSONObject obj1 = new JSONObject(data);
        JSONArray arr = obj1.getJSONArray("results");
        for(int i=0;i<arr.length();i++){
            JSONObject obj2 = (JSONObject) arr.get(i);
            String medicalStorename = obj2.getString("name");
            String  pincode = obj2.getString("pincode");
            storeList.add(medicalStorename);
        }
        ListView listView = view.findViewById(R.id.medicine_store_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,  storeList);
        listView.setAdapter(adapter);
    }

}