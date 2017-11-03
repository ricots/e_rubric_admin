package unikama.acer.e_rubic_admin.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import unikama.acer.e_rubic_admin.MainActivity;
import unikama.acer.e_rubic_admin.R;
import unikama.acer.e_rubic_admin.koneksi.config;

public class masuk extends AppCompatActivity {
    EditText nid_daftar;
    ProgressDialog PD;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    Button nid_masuk;
    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Masukkan Nid Yang Terdaftar");

        sp = this.getSharedPreferences("isi data", 0);
        spe = sp.edit();
        PD = new ProgressDialog(this);

        nid_daftar = (EditText) findViewById(R.id.nid_daftar);
        nid_masuk = (Button) findViewById(R.id.nid_masuk);
        nid_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(masuk.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void login() {
        //Getting values from edit texts
        final String input_nid = nid_daftar.getText().toString();
        PD.setMessage("Login Process...");
        showDialog();
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.MASUK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success from server
                        if (response.contains(config.LOGIN_SUCCESS)) {
                            hideDialog();
                            sp = masuk.this.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            spe = sp.edit();

                            //Adding values to editor
                            spe.putBoolean(config.LOGGEDIN_SHARED_PREF, true);
                            spe.putString(config.EMAIL_SHARED_PREF, input_nid);

                            spe.commit();
                            gotohome();

                        } else {
                            hideDialog();
                            Toast.makeText(masuk.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        hideDialog();
                        Toast.makeText(masuk.this, "The server unreachable", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("nid", input_nid);

                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void gotohome() {
        Intent intent = new Intent(masuk.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showDialog() {
        if (!PD.isShowing())
            PD.show();
    }

    private void hideDialog() {
        if (PD.isShowing())
            PD.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }
}
