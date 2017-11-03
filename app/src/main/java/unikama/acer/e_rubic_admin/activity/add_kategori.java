package unikama.acer.e_rubic_admin.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class add_kategori extends AppCompatActivity {
    EditText add_nama_kat;
    Button save_kat;
    ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kategori);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("TAMBAH KATEGORI");

        PD = new ProgressDialog(this);
        PD.setMessage("silahkan tunggu.....");
        PD.setCancelable(false);
        add_nama_kat = (EditText) findViewById(R.id.add_nama_kat);
        save_kat = (Button) findViewById(R.id.save_kat);
        save_kat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_kat();
            }
        });
    }

    public void add_kat() {
        final String input_add_kat = add_nama_kat.getText().toString();

        if (input_add_kat.equals("")) {
            Toast.makeText(add_kategori.this, "harap isi data", Toast.LENGTH_LONG).show();
            PD.dismiss();
        } else {
            PD.show();
            StringRequest postRequest = new StringRequest(Request.Method.POST, config.ADD_KAT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            PD.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    response.toString(),
                                    Toast.LENGTH_SHORT).show();
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
                    params.put("nama_kategori", input_add_kat);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(add_kategori.this);
            requestQueue.add(postRequest);
        }
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
