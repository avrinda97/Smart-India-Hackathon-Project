package com.example.shubhanshu.medicgolist;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Map;
public class UserReviewActivity extends AppCompatActivity {

    int seed = (int) (Math.random()*10);
    int increment = (int)(Math.random()*3);
    int medicineid = seed+increment;
    String city = "kolkata";
    String state="west bengal";
    final int upvote=1;
    int vote;
    final int downvote=-1;
    String url = MedicConstant.url+"medicGoServer/get_medicine_by_id.php";
    String url2=MedicConstant.url+"medicGoServer/voting.php";
    RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_review);
        Cache cache = new DiskBasedCache(getCacheDir(), 5*1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());

        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        Volley();
    }

    public void VolleyCaller(){
        VolleyVote();
        medicineid+=increment;
        Toast.makeText(UserReviewActivity.this,"Review updated",Toast.LENGTH_SHORT).show();
        Log.d("mmmm",medicineid+"");

        Volley();
    }
    public void setUpvote(View v){
        vote=upvote;
        VolleyCaller();

    }
    public void setDownvote(View v){
        vote=downvote;
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
    public void Volley(){
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

       JSONObject obj = new JSONObject(data);
        JSONArray arr = obj.getJSONArray("results");
        JSONObject obj2 = (JSONObject) arr.get(0);

        String name = obj2.getString("name");
        //JSONArray content = (JSONArray) obj.get("active_ingredients");
        String inp ="";
        TextView tv1 = findViewById(R.id.review_medicine_name);
        //TextView tv2 = findViewById(R.id.review_medicine_detail);
        Log.e("namesss",name);
        tv1.setText(name);
        //tv2.setText(inp);
    }
}
