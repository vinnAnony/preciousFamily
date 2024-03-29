package com.vinnie.preciousfamily;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vinnie.preciousfamily.Mpesa.Models.OAuth;
import com.vinnie.preciousfamily.Mpesa.Models.STKPush;
import com.vinnie.preciousfamily.Mpesa.NetworkUtills.ApiConstants;
import com.vinnie.preciousfamily.Mpesa.NetworkUtills.Request;
import com.vinnie.preciousfamily.Mpesa.Utils.Utils;
import com.vinnie.preciousfamily.connection.CheckNetwork;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LipaMpesa extends AppCompatActivity {

    RelativeLayout rl;
    private static String amount, phone;

    /**
     * @param push
     * @return
     */
    private static String generateJsonStringParams(STKPush push) {
        JSONObject postData = new JSONObject();

        try {
            postData.put("BusinessShortCode", push.getBusinessShortCode());
            postData.put("Password", push.getPassword());
            postData.put("Timestamp", push.getTimestamp());
            postData.put("TransactionType", push.getTransactionType());
            postData.put("Amount", push.getAmount());
            postData.put("PartyA", push.getPartyA());
            postData.put("PartyB", push.getPartyB());
            postData.put("PhoneNumber", push.getPhoneNumber());
            postData.put("CallBackURL", push.getCallBackURL());
            postData.put("AccountReference", push.getAccountReference());
            postData.put("TransactionDesc", push.getTransactionDesc());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData.toString();

    }

    TextView mmenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lipa_mpesa);

        rl = findViewById(R.id.rlyt);
        mmenu = findViewById(R.id.link_home);
        mmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LipaMpesa.this,MainActivity.class));
            }
        });

        Button btn = findViewById(R.id.btn_pay);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtPhone = findViewById(R.id.mNumber);
                EditText edtAmount = findViewById(R.id.amount);

                if(CheckNetwork.isInternetAvailable(LipaMpesa.this)) //returns true if internet available
                {
                    
                if (!edtPhone.getText().toString().isEmpty()
                        && !edtAmount.getText().toString().isEmpty()
                        && Utils.isNetworkAvailable(LipaMpesa.this)
                        && Utils.sanitizePhoneNumber(edtPhone.getText().toString()) != null) {


                    OAuth oAuth = new OAuth(
                            ApiConstants.safaricom_Auth_key,
                            ApiConstants.safaricom_Secret);


                    String url = ApiConstants.BASE_URL + ApiConstants.ACCESS_TOKEN_URL;

                    if (oAuth.getProduction() == ApiConstants.PRODUCTION_RELEASE)
                        url = ApiConstants.PRODUCTION_BASE_URL + ApiConstants.ACCESS_TOKEN_URL;

                    phone = edtPhone.getText().toString();
                    amount = edtAmount.getText().toString();


                    new AuthService(LipaMpesa.this).execute(url, oAuth.getOauth());


                } else {
                    Snackbar snackbar = Snackbar
                            .make(rl, "Fill all the required fields", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

                }
                else
                {
                    Snackbar snackbar = Snackbar
                            .make(rl, "No Internet Connection", Snackbar.LENGTH_INDEFINITE).setActionTextColor(Color.YELLOW).setAction("RETRY", new View.OnClickListener() {
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

    static class AuthService extends AsyncTask<String, Void, String> {

        final Context context;

        AuthService(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Basic " + strings[1]);
            return Request.get(strings[0], headers);
        }

        protected void onPostExecute(String result) {
            if (result == null) {
                Toast.makeText(context, "Error Getting Oauth Key", Toast.LENGTH_LONG).show();

                return;
            }

            try {

                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.get("access_token") != null) {

                    String token = jsonObject.get("access_token").toString();


                    STKPush stkPush = new
                            STKPush(
                            ApiConstants.safaricom_bussiness_short_code,
                            ApiConstants.DEFAULT_TRANSACTION_TYPE, amount,
                            Utils.sanitizePhoneNumber(phone),
                            ApiConstants.safaricom_party_b,
                            Utils.sanitizePhoneNumber(phone),
                            ApiConstants.callback_url,
                            Utils.sanitizePhoneNumber(phone),
                            "Pay");


                    String url = ApiConstants.BASE_URL + ApiConstants.PROCESS_REQUEST_URL;

                    if (stkPush.getProduction() == ApiConstants.PRODUCTION_RELEASE) {
                        url = ApiConstants.PRODUCTION_BASE_URL + ApiConstants.PROCESS_REQUEST_URL;
                    }


                    new PayService().execute(url, generateJsonStringParams(stkPush), token);

                }

                return;
            } catch (Exception ignored) {


            }
        }
    }


    static class PayService extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + strings[2]);
            return Request.post(strings[0], strings[1], headers);
        }

        protected void onPostExecute(String result) {

        }
    }

}
