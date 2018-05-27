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

public class AlternativeActivity extends AppCompatActivity {
    String url ="http://192.168.0.108/medicGoServer/get_alternative_drugs.php";
    String city="kolkata";
    String state="west bengal";
    ArrayList<MedicineDetail> alternativeList = new ArrayList<MedicineDetail>();
    int medicineid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternative);
        Intent intent = getIntent();
        medicineid = Integer.parseInt(intent.getStringExtra("medicine_id"));
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
                mydata.put("medicine_id",medicineid+"");
                mydata.put("city",city);
                mydata.put("state",state);
                return mydata;
            }
        };
        mRequestQueue.add(request);

    }
    public void updateUi(String data) throws JSONException {
        final AlternativeAdapter alternativeAdapter = new AlternativeAdapter(this, alternativeList);
        ListView listView = (ListView) findViewById(R.id.alternative_list_item);
        try {
            JSONObject object = new JSONObject("" + data);
            JSONArray jsonArray = (JSONArray) ((JSONArray) (object.get("results")));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String name = (String) obj.get("name");
                String price =  getResources().getString(R.string.Rs)+" "+(String) obj.get("price");
                int upvote = Integer.parseInt( obj.getString("up_vote"));
                int downvote =Integer.parseInt(obj.getString("down_vote"));


                int id = Integer.parseInt((String) obj.get("medicine_id"));
                MedicineDetail detail = new MedicineDetail(name, price, "", id,upvote,downvote,"","");
                alternativeList.add(detail);
            }


            listView.setAdapter(alternativeAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    MedicineDetail detail = alternativeList.get(position);
                    Intent intent = new Intent(AlternativeActivity.this, MedicineInfo.class);
                    intent.putExtra("medicine_id",detail.getId()+"");
                    intent.putExtra("name",detail.getMedicineName());
                    intent.putExtra("price",detail.getPrice());
                    intent.putExtra("description",detail.getDescription());
                    intent.putExtra("upvote",detail.getUpvote()+"");
                    intent.putExtra("downvote",detail.getDownvote()+"");
                    startActivity(intent);

                }
            });
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
