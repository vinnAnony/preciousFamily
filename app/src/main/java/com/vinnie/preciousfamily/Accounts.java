package com.vinnie.preciousfamily;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vinnie.preciousfamily.connection.CheckNetwork;

import org.json.JSONException;
import org.json.JSONObject;

public class Accounts extends AppCompatActivity {

    ConstraintLayout cl;
    EditText usname,passw;
    Button btnLogin,btnPesa;

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "first_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";
    private String username;
    private String password;
    private ProgressDialog pDialog;
    //private String login_url = "http://192.168.100.7:8012/pf/tes.php";
    private String login_url = "https://vinn.fun/preciousfam/tes.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        cl = findViewById(R.id.conslay);
        usname = findViewById(R.id.input_username);
        passw = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.btn_login);
        btnPesa = findViewById(R.id.btn_mpesa);

        btnPesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Accounts.this,LipaMpesa.class));
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username  = usname.getText().toString();
                password = passw.getText().toString();

                if(CheckNetwork.isInternetAvailable(Accounts.this)) //returns true if internet available
                {

                    login();
                }
                else
                {
                    Snackbar snackbar = Snackbar
                            .make(cl, "No Internet Connection", Snackbar.LENGTH_INDEFINITE).setActionTextColor(Color.YELLOW).setAction("RETRY", new View.OnClickListener() {
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
        });

    }
    private void displayLoader() {
        pDialog = new ProgressDialog(Accounts.this,R.style.MyAlertDialogStyle);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }
    private void login() {

        displayLoader();
        JSONObject request = new JSONObject();

        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD, password);
            Log.d("Username", username);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, login_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();

                        try {
                            //Check if user got logged in successfully
                            Log.d("StatusResponse", String.valueOf(response));
                            if (response.getInt(KEY_STATUS) == 0) {
                                //Toast.makeText(Accounts.this, response.getString(KEY_FULL_NAME), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Accounts.this,AccHome.class));

                            }else{
                                Toast.makeText(getApplicationContext(),response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Log.d("Error",error.getMessage());
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

}
