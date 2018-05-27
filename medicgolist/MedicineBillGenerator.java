package com.example.shubhanshu.medicgolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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

public class MedicineBillGenerator extends AppCompatActivity {
    String url1=MedicConstant.url+"medicGoServer/billing.php";
    String url=MedicConstant.url+"medicGoServer/get_medicines.php";
    String name ="";
    String medicineId="";
    String mName="mamata medical stores";
    String storeName="";
    String pincode="700119";
    ArrayList<MedicineDetail> medicineList = new ArrayList<MedicineDetail>();
    RequestQueue mRequestQueue;
    Cache cache ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_bill_generator);
        cache= new DiskBasedCache(getCacheDir(), 5*1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        pincode = intent.getStringExtra("pin");

    }

    public void search(View v){
        EditText et = findViewById(R.id.insertMedicine);
        name = String.valueOf(et.getText());
        Log.e("ttt",name);
        Volley();
    }

    public void Volley(){
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("zz","working");
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
                mydata.put("medicine_name",name);

                return mydata;
            }
        };
        mRequestQueue.add(request);

    }
    public void Volley1(){

        StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("zz","working");
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
                mydata.put("medicine_id",medicineId);
                mydata.put("pincode",pincode);
                mydata.put("name",mName);
                return mydata;
            }
        };
        mRequestQueue.add(request);
    }

    public void updateUi(String data) throws JSONException {
        final MedicineAdapter alternativeAdapter = new MedicineAdapter(this,medicineList);
        ListView listView = (ListView) findViewById(R.id.medicine_bill_list);

        try {
            JSONObject object = new JSONObject("" + data);
            JSONArray jsonArray = (JSONArray) (JSONArray) (object.get("results"));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String name = (String) obj.get("name");
                String price =getResources().getString(R.string.Rs)+" "+ (String) obj.get("price");


                int id = Integer.parseInt((String) obj.get("medicine_id"));
                MedicineDetail detail = new MedicineDetail(name, price, "", id);
                medicineList.add(detail);
            }


            listView.setAdapter(alternativeAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    MedicineDetail detail = medicineList.get(position);
                    int mid = detail.getId();
                    medicineId="";
                    medicineId+=mid;
                    Volley1();


                }
            });
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
