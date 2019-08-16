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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vinnie.preciousfamily.Lists.MembersAdapter;
import com.vinnie.preciousfamily.Lists.Members_list;
import com.vinnie.preciousfamily.connection.CheckNetwork;
import com.vinnie.preciousfamily.connection.savedInformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FamMembers extends AppCompatActivity {
    TextView theName;
    private ProgressDialog pDialog;

    //private static final String HI ="http://192.168.100.7:8012/pf/fetch_members.php" ;
    private static final String HI ="https://vinn.fun/preciousfam/fetch_members.php" ;
    private List<Members_list> list_data;
    private RecyclerView rv;
    private MembersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fam_members);

        theName = findViewById(R.id.famiName);
        theName.setText(savedInformation.familyName);

        rv = findViewById(R.id.lView);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        list_data=new ArrayList<>();
        adapter=new MembersAdapter(list_data);
        rv.setAdapter(adapter);

        pDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        if(CheckNetwork.isInternetAvailable(FamMembers.this)) //returns true if internet available
        {
            getMembers();
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
    private void getMembers() {

        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("famName", savedInformation.familyName);
            Log.d("Fam", savedInformation.familyName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, HI, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            pDialog.dismiss();
                            Log.d("Success response", String.valueOf(response));

                            JSONObject jsonObject=new JSONObject(String.valueOf(response));
                            JSONArray array=jsonObject.getJSONArray("data");
                            for (int i=0; i<array.length(); i++ ){
                                JSONObject ob=array.getJSONObject(i);
                                Members_list listData=new Members_list(ob.getString("first_name")
                                        ,ob.getString("middle_name"),
                                        ob.getString("surname"),
                                        ob.getString("phone"));
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
        requestQueue.add(jsArrayRequest);
    }

}
