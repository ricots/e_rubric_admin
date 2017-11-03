package unikama.acer.e_rubic_admin.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import unikama.acer.e_rubic_admin.MainActivity;
import unikama.acer.e_rubic_admin.R;
import unikama.acer.e_rubic_admin.koneksi.config;

public class daftar extends AppCompatActivity {
    EditText nid, nama_dosen;
    Button btndaftar,login;
    ProgressDialog PD;
    private boolean loggedIn = false;
    SharedPreferences sp;
    SharedPreferences.Editor spe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        Toolbar toolbar1 = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("E-RUBRIC");

        nid = (EditText) findViewById(R.id.user);
        nama_dosen = (EditText) findViewById(R.id.pass);
        btndaftar = (Button) findViewById(R.id.daftar);
        PD = new ProgressDialog(this);
        PD.setMessage("silahkan tunggu.....");
        PD.setCancelable(false);

        sp = this.getSharedPreferences("isi data", 0);
        spe = sp.edit();
        PD = new ProgressDialog(this);

        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //regis_user();
                PD.show();
                if((nid.getText().toString().equalsIgnoreCase("admin")) &&(nama_dosen.getText().toString().equalsIgnoreCase("admin"))){
                    Toast.makeText(daftar.this,"login sukses",Toast.LENGTH_LONG).show();
                    PD.dismiss();
                    sp = daftar.this.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    spe = sp.edit();
                    spe.putBoolean(config.LOGGEDIN_SHARED_PREF, true);
                    spe.putString(config.EMAIL_SHARED_PREF, nid.getText().toString());
                    spe.commit();
                    Intent in = new Intent(daftar.this,MainActivity.class);
                    startActivity(in);
                }else{
                    Toast.makeText(daftar.this,"login gagal",Toast.LENGTH_LONG).show();
                    PD.dismiss();
                }
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
            Intent intent = new Intent(daftar.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void regis_user() {
        final String input_nid = nid.getText().toString();
        final String input_nama = nama_dosen.getText().toString();

        if ((input_nid.equals("")) && (input_nama.equals(""))) {
            Toast.makeText(daftar.this, "harap isi data", Toast.LENGTH_LONG).show();
            PD.dismiss();
        } else {
            PD.show();
            StringRequest postRequest = new StringRequest(Request.Method.POST, config.REGIS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            PD.dismiss();
                            sp = daftar.this.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            spe = sp.edit();
                            spe.putBoolean(config.LOGGEDIN_SHARED_PREF, true);
                            spe.putString(config.EMAIL_SHARED_PREF, input_nid);
                            spe.commit();
                            Toast.makeText(getApplicationContext(),
                                    "berhasil registrasi",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(daftar.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PD.dismiss();
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(config.KEY_NID, input_nid);
                    params.put(config.KEY_NAMA_DOSEN, input_nama);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(daftar.this);
            requestQueue.add(postRequest);
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
