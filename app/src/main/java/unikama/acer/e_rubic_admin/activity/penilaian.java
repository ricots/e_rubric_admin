package unikama.acer.e_rubic_admin.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import unikama.acer.e_rubic_admin.R;
import unikama.acer.e_rubic_admin.adapter.adapter_kategori;
import unikama.acer.e_rubic_admin.koneksi.config;
import unikama.acer.e_rubic_admin.oop.Item_kategori;

public class penilaian extends AppCompatActivity {
    /*List<Item> list_android;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adp_android;
    Item item;
    RequestQueue requestQueue;*/

    RecyclerView recyclerView;
    private List<Item_kategori> list_kat = new ArrayList<>();
    private adapter_kategori mAdapter;

    private RecyclerView.LayoutManager layoutManager;
    RequestQueue requestQueue;
    EditText general_komen, solusi;
    Button savesolusi;
    ProgressDialog PD;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penilaian);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("PENILAIAN");

        PD = new ProgressDialog(this);
        PD.setMessage("silahkan tunggu.....");
        PD.setCancelable(false);

        sp = this.getSharedPreferences("isi data", 0);
        spe = sp.edit();
        result = sp.getString("isi data","");
        //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

        recyclerView = (RecyclerView) findViewById(R.id.list_nilai);
        mAdapter = new adapter_kategori((ArrayList<Item_kategori>) list_kat);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        requestQueue = Volley.newRequestQueue(this);

        general_komen = (EditText) findViewById(R.id.general_komen);
        solusi = (EditText) findViewById(R.id.solusi);
        savesolusi = (Button) findViewById(R.id.savesolusi);
        savesolusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solusi();
            }
        });
        /*recyclerView = (RecyclerView) findViewById(R.id.list_nilai);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        list_android = new ArrayList<Item>();
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        getjsondata();*/
        loadData();

    }

    private void loadData() {
        requestQueue.getCache().clear();
        list_kat.clear();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                config.KATEGORI, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray episodes = response.getJSONArray("list_kategori");
                    for (int i = 0; i < episodes.length(); i++) {

                        JSONObject episode = episodes.getJSONObject(i);
                        String id_kat = episode.getString("id_kategori");
                        String nama_kat = episode.getString("nama_kategori");

                        Item_kategori lostItem = new Item_kategori(
                                id_kat, nama_kat);
                        list_kat.add(lostItem);
                        //mAdapter.notifyDataSetChanged();
                    }
                    recyclerView.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })

        {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONObject(jsonString), cacheEntry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(JSONObject response) {
                super.deliverResponse(response);
            }

            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };

        Volley.newRequestQueue(this).add(jsonObjReq);
    }


    public void solusi(){
        final String input_komen = general_komen.getText().toString();
        final String input_solusi = solusi.getText().toString();

        if ((input_komen.equals("")) && (input_solusi.equals(""))) {
            Toast.makeText(penilaian.this, "harap isi data", Toast.LENGTH_LONG).show();
            PD.dismiss();
        } else {
            PD.show();
            StringRequest postRequest = new StringRequest(Request.Method.POST, config.SARAN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(),
                                    response.toString(),
                                    Toast.LENGTH_SHORT).show();
                            PD.dismiss();
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
                    params.put("id_hasil_nilai", result);
                    params.put("general_comment", input_komen);
                    params.put("solusi", input_solusi);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(penilaian.this);
            requestQueue.add(postRequest);
        }
    }

  /*  public void getjsondata() {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, "http://mydeveloper.id/e_rubic/api.php",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            //Log.d("hasilnya ", response.toString());
                            //JSONArray android_ver = response.getJSONArray("android");
                            *//*JSONObject questionMark = response.getJSONObject("question_mark");
                            Iterator keys = questionMark.keys();*//*
                            JSONArray hasiljson = response.getJSONArray("penilaian");

                            for (int a = 0; a < hasiljson.length(); a++) {
                                item= new Item();
                                JSONObject result = hasiljson.getJSONObject(a);
                                item.setId_kategori(result.getString("id_kategori"));
                                item.setNama_kategori(result.getString("nama_kategori"));

                                JSONArray array = result.getJSONArray("detail");
                                ArrayList<String> nilai = new ArrayList<>();
                                for(int b = 0; b < array.length(); b++) {
                                    JSONObject json_array = array.getJSONObject(b);
//                                    map.put(json_array.getString("id_penilaian"),json_array.getString("nama_penilaian")+"\n");
                                    nilai.add(json_array.getString("nama_penilaian")+"\n");
                                }
                                item.setNama_penilaian(nilai.toString());
                                list_android.add(item);

                            }
                            adp_android = new Adapter(list_android, getApplicationContext());
                            recyclerView.setAdapter(adp_android);
                            //Log.d("jsonnya", String.valueOf(item));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("ini kesalahannya " + e.getMessage());
                        }
                        //adp_android.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ini kesalahannya",error.toString());
                        //System.out.println("ini kesalahannya " + error.getMessage());
                    }
                });

        requestQueue.add(jsonRequest);
    }*/

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
