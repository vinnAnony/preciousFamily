package com.vinnie.preciousfamily;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vinnie.preciousfamily.Lists.FamilyAdapter;
import com.vinnie.preciousfamily.Lists.Family_list;
import com.vinnie.preciousfamily.connection.CheckNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Members extends AppCompatActivity {

    private ProgressDialog pDialog;

    //private static final String HI ="http://192.168.100.7:8012/pf/fetch_fam.php" ;
    private static final String HI ="http://vinnjeru.com/preciousfam/fetch_fam.php" ;
    private List<Family_list> list_data;
    private RecyclerView rv;
    private FamilyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        rv = findViewById(R.id.recyview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        list_data=new ArrayList<>();
        adapter=new FamilyAdapter(list_data);
        rv.setAdapter(adapter);

        pDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        if(CheckNetwork.isInternetAvailable(Members.this)) //returns true if internet available
        {

            getFams();
        }
        else
        {
            pDialog.dismiss();

            Snackbar snackbar = Snackbar
                    .make(rv, "No Internet Connection", Snackbar.LENGTH_INDEFINITE).setActionTextColor(Color.YELLOW).setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(getIntent());
                            finish();
                            overridePendingTransition(0, 0);
                        }
                    });
            snackbar.show();
        }

    }

    private void getFams() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, HI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pDialog.dismiss();
                    Log.d("Success response", response);

                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("data");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        Family_list listData=new Family_list(ob.getString("family_name")
                                ,ob.getInt("members_no"));
                        list_data.add(listData);
                    }
                    rv.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Failed response", "Failed");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error response", error.getMessage());
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
