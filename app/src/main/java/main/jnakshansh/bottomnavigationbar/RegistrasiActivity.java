package main.jnakshansh.bottomnavigationbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrasiActivity extends AppCompatActivity {

    EditText username,password,email,noTelp,confPassword;
    Button register,checkUsername;
    ProgressDialog progressDialog;
    LinearLayout pas,ulpas,noTelpp,emaill;
    boolean visibility = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confPassword = (EditText) findViewById(R.id.passwordulang);
        email = (EditText) findViewById(R.id.editTextEmail);
        noTelp = (EditText) findViewById(R.id.editTextPhone);
        register = (Button) findViewById(R.id.buatakun);
        checkUsername = (Button) findViewById(R.id.checkUname);
        progressDialog = new ProgressDialog(RegistrasiActivity.this);

        pas = (LinearLayout) findViewById(R.id.linierpass);
        ulpas = (LinearLayout) findViewById(R.id.linierulangpass);
        noTelpp = (LinearLayout) findViewById(R.id.liniernoTelp);
        emaill = (LinearLayout) findViewById(R.id.linieremail);


        checkUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sUsername = username.getText().toString();
                if (!sUsername.equals("")) {
                    checkUsername(sUsername);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sUsername = username.getText().toString();
                String semail = email.getText().toString();
                String sPassword = password.getText().toString();
                String sConfPassword = confPassword.getText().toString();
                String snoTelp = noTelp.getText().toString();

                if(sPassword.equals("") || sUsername.equals("") || snoTelp.equals("") || sConfPassword.equals("") || semail.equals("")){
                    Toast.makeText(getApplicationContext(), "Tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
                else{


                    if (sPassword.equals(sConfPassword) && !sPassword.equals("")){
                        createDataToServer(sUsername,semail,sPassword,snoTelp);

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Password tidak sesuai", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }

    public void createDataToServer (final String username, final String email, final  String password, final String noTelp){
        if (checkNetworkConnenction()){
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("[{\"status\":\"ok\"}]")){
                                    Toast.makeText(getApplicationContext(), "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                                    Intent intentRegisBerhasil = new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(intentRegisBerhasil);
                                }else{
                                    Toast.makeText(getApplicationContext(), "Registrasi Gagal " + resp , Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Registrasi Gagal server tidak merespon", Toast.LENGTH_SHORT).show();

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("email", email);
                    params.put("password", password);
                    params.put("noTelp", noTelp);
                    return params;
                }
            };

            VolleyConnection.getInstance(getApplicationContext()).addToRequestQue(stringRequest);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                }
            }, 2000);
        }else {
            Toast.makeText(getApplicationContext(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
        }
    }


    public void checkUsername (final String username){
        if (checkNetworkConnenction()){
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_CHECKUSERNAME_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("[{\"status\":\"ok\"}]")){
                                    Toast.makeText(getApplicationContext(), "Username tidak tersedia" + resp, Toast.LENGTH_SHORT).show();
                                    //visibility = false;
                                    pas.setVisibility(View.INVISIBLE);
                                    ulpas.setVisibility(View.INVISIBLE);
                                    noTelpp.setVisibility(View.INVISIBLE);
                                    emaill.setVisibility(View.INVISIBLE);

                                }else{
                                    Toast.makeText(getApplicationContext(), "Username tersedia berhasil " + resp , Toast.LENGTH_SHORT).show();
                                    //visibility = true;

                                    pas.setVisibility(View.VISIBLE);
                                    ulpas.setVisibility(View.VISIBLE);
                                    noTelpp.setVisibility(View.VISIBLE);
                                    emaill.setVisibility(View.VISIBLE);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    return params;
                }
            };

            VolleyConnection.getInstance(getApplicationContext()).addToRequestQue(stringRequest);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                }
            }, 2000);
        }else {
            Toast.makeText(getApplicationContext(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean checkNetworkConnenction(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}