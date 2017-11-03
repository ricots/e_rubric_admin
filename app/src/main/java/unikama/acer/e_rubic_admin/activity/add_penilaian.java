package unikama.acer.e_rubic_admin.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import unikama.acer.e_rubic_admin.R;
import unikama.acer.e_rubic_admin.koneksi.config;

public class add_penilaian extends AppCompatActivity {
    Spinner spin_kat;
    private JSONArray result;
    private ArrayList<String> kat;
    TextView id_katnya;
    EditText add_nama_nilai;
    ProgressDialog PD;
    Button save_nilai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_penilaian);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("TAMBAH PENILAIAN");

        PD = new ProgressDialog(this);
        PD.setMessage("silahkan tunggu.....");
        PD.setCancelable(false);

        kat = new ArrayList<String>();
        spin_kat = (Spinner) findViewById(R.id.spin_kategori);
        id_katnya = (TextView) findViewById(R.id.id_katnya);
        save_nilai = (Button) findViewById(R.id.save_nilai);
        add_nama_nilai = (EditText) findViewById(R.id.add_nama_nilai);
        spin_kat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_katnya.setText(get_id_kat(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        save_nilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_nilai();
            }
        });

        getData();
    }

    private void getData(){
        //Creating a string request
        final ProgressDialog loading = ProgressDialog.show(this,"Loading Data", "Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(config.KATEGORI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            result = j.getJSONArray("list_kategori");

                            //Calling method getStudents to get the students from the JSON Array
                            getnama_kat(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),"silahkan coba lagi",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getnama_kat(JSONArray j){
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                kat.add(json.getString("nama_kategori"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spin_kat.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, kat));
    }

    private String get_id_kat(int position){
        String id_kat="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            id_kat = json.getString("id_kategori");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return id_kat;
    }

    public void add_nilai() {
        final String input_add_nilai = add_nama_nilai.getText().toString();
        final String input_add_idkat = id_katnya.getText().toString();

        if (input_add_nilai.equals("")) {
            Toast.makeText(add_penilaian.this, "harap isi data", Toast.LENGTH_LONG).show();
            PD.dismiss();
        } else {
            PD.show();
            StringRequest postRequest = new StringRequest(Request.Method.POST, config.ADD_NILAI,
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
                    params.put("nama_penilaian", input_add_nilai);
                    params.put("id_kategori", input_add_idkat);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(add_penilaian.this);
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
