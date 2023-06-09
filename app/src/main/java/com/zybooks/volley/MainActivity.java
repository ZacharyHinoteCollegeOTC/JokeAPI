package com.zybooks.volley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private Button btnRequest;

    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;

    //private String url = "https://icanhazdadjoke.com/"; - This needed special changes to work, so we shifted to another API.
    private String url = "https://v2.jokeapi.dev/joke/Programming,Miscellaneous,Pun,Spooky,Christmas";
    /*This specifies those categories because the general random joke generator had dark jokes like "What's the difference between
    Harry Potter and the Jews? Harry escaped the chamber.*/

    private TextView view;

    private String setup;
    private String punchline;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        btnRequest = (Button) findViewById(R.id.buttonRequest);

        view = (TextView)findViewById(R.id.textView);


        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                click();

            }
        }

        );

    }

    public void click() {

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        requestQueue.start();


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    setup = response.getString("setup");
                    punchline = response.getString("punchline");
                    view.setText(setup);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.setText("error");
            }
        });


        requestQueue.add(jsonObjectRequest);

    }


}


