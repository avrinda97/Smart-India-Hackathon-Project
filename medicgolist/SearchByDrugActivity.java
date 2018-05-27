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

public class SearchByDrugActivity extends AppCompatActivity {
    String url = MedicConstant.url+"medicGoServer/get_active_ingredient_name.php";
    String name ="";
    ArrayList<MedicineDetail> alternativeDrugList = new ArrayList<MedicineDetail>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_drug);
        Intent intent = getIntent();
        name = intent.getStringExtra("medicine_name");
        Volley();
    }
    public void searchDrug(View v){
        EditText et = findViewById(R.id.getname);
        String name1 = et.getText().toString();
        name=name1;
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

                }catch (JSONException e) {
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
                mydata.put("active_ingredient_name",name);
                return mydata;
            }
        };
        mRequestQueue.add(request);

    }

    public void updateUi(String response) throws JSONException {
        Log.e("aaaaa",response);
        final SearchByDrugAdapter searchByDrugAdapter = new SearchByDrugAdapter(this,alternativeDrugList );
        ListView listView = (ListView) findViewById(R.id.drug_list);

        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("results");


        for(int i=0;i<jsonArray.length();i++){
            JSONObject obj = (JSONObject) jsonArray.get(i);
            String name = obj.getString("name");
            int id = Integer.parseInt(obj.getString("active_ingredient_id"));
          //  Log.d("actid",id+"");
            alternativeDrugList.add(new MedicineDetail(name,"","",id));

        }
        listView.setAdapter(searchByDrugAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                MedicineDetail detail = alternativeDrugList.get(position);
                Intent intent = new Intent(SearchByDrugActivity.this, DrugAlternativeActivity.class);

                intent.putExtra("medicine_id",detail.getId()+"");

                Log.d("clicked on ",detail.getId()+"");

                intent.putExtra("name",detail.getMedicineName());
                intent.putExtra("price",detail.getPrice());
                intent.putExtra("description",detail.getDescription());
                startActivity(intent);

            }
        });


    }
}
