package com.example.shubhanshu.medicgolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    ArrayList<MedicineDetail> dummyList = new ArrayList<MedicineDetail>();

    SharedPreference sharedPreference;

    String name="";
    static String url = MedicConstant.url+"medicGoServer/get_medicines.php";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        name = intent.getStringExtra("medicine_name");
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
                    updateUi(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("aa", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String > mydata  = new HashMap<String, String>();
                mydata.put("medicine_name",name);
                return mydata;
            }
        };
        mRequestQueue.add(request);

    }
    public void updateUi(String data) throws JSONException {
        MedicineAdapter medicineAdapter = new MedicineAdapter(this,dummyList);
        ListView listView = (ListView)findViewById(R.id.medicine_list_item);
        try {
            JSONObject object = new JSONObject(""+data);
            JSONArray jsonArray = (JSONArray) object.get("results");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                String name = (String) obj.get("name");
                String price =getResources().getString(R.string.Rs)+" "+ (String) obj.get("price");

                int id = Integer.parseInt((String) obj.get("medicine_id"));
                MedicineDetail detail = new MedicineDetail(name,price,"",id);
                dummyList.add(detail);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView.setAdapter(medicineAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                MedicineDetail detail = dummyList.get(position);
                String name = detail.getMedicineName();
                String price = detail.getPrice();
                String id4 = ""+detail.getId();
//                Product pp = new Product(Integer.parseInt(id4),name,"",Double.parseDouble(price));

                Intent intent = new Intent(MainActivity.this, AlternativeActivity.class);
                Log.e("id",detail.getId()+"");
                intent.putExtra("medicine_id",detail.getId()+"");
                startActivity(intent);

            }
        });
    }

}
