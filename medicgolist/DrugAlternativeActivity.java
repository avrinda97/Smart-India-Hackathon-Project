package com.example.shubhanshu.medicgolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class DrugAlternativeActivity extends AppCompatActivity {
    String url =MedicConstant.url+"medicGoServer/get_by_drug.php";
    String medicineid;
    String city="kolkata";
    String state="west bengal";

    ArrayList<MedicineDetail> drugAlternativeList=new ArrayList<MedicineDetail>();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_alternative);
        Intent intent = getIntent();
        medicineid=intent.getStringExtra("medicine_id");
        Log.e("id",""+medicineid);
        Volley();
    }

    public void Volley(){
        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 5*1024 * 1024); // 1MB cap
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
                mydata.put("active_ingredient_id",medicineid+"");
                mydata.put("city",city);
                mydata.put("state",state);

                return mydata;
            }
        };
        mRequestQueue.add(request);

    }

    public void updateUi(String response) throws JSONException {
        Log.e("aaaba",response);
        final SearchByDrugAdapteralter searchByDrugAdapteralter = new SearchByDrugAdapteralter(this,drugAlternativeList);
        ListView listView = (ListView) findViewById(R.id.drug_alter_list);

        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("results");


        for(int i=0;i<jsonArray.length();i++){

            JSONObject obj = (JSONObject) jsonArray.get(i);
            String name = obj.getString("name");
            int id =  Integer.parseInt(obj.getString("medicine_id"));
            String price =getResources().getString(R.string.Rs)+" "+ obj.getString("price");
            Log.d("aaa",""+name+" "+price);
                      drugAlternativeList.add(new MedicineDetail(name,price,"",id));
        }

      listView.setAdapter(searchByDrugAdapteralter);


    }
}
