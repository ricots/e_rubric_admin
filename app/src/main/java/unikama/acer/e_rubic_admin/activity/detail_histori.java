package unikama.acer.e_rubic_admin.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import unikama.acer.e_rubic_admin.R;
import unikama.acer.e_rubic_admin.adapter.adapter_detail_history;
import unikama.acer.e_rubic_admin.koneksi.config;
import unikama.acer.e_rubic_admin.oop.Item_detail_histori;

public class detail_histori extends AppCompatActivity {
    String idnya;
    private LinearLayoutManager layoutManager;
    private List<Item_detail_histori> list_histori = new ArrayList<>();
    private adapter_detail_history mAdapter;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    TextView komennya,solusinya,rata1,bobot;
    ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_histori);
        PD = new ProgressDialog(this);
        PD.setMessage("silahkan tunggu.....");
        PD.setCancelable(false);

        Toolbar toolbar1 = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("DETAIL PENILAIAN");

        final Bundle bundle = getIntent().getExtras();
        idnya = bundle.getString("id");

        komennya =(TextView) findViewById(R.id.komennya);
        solusinya =(TextView) findViewById(R.id.solusinya);
        rata1 =(TextView) findViewById(R.id.rata);
        bobot =(TextView) findViewById(R.id.bobot);

        recyclerView = (RecyclerView) findViewById(R.id.recyle_detail_histori);
        mAdapter = new adapter_detail_history((ArrayList<Item_detail_histori>) list_histori);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        requestQueue = Volley.newRequestQueue(this);
        load_histori();
    }

    public void load_histori(){
        requestQueue.getCache().clear();
        list_histori.clear();
        PD.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                config.DETAIL_HISTORI + idnya, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                PD.dismiss();
                try {
                    JSONArray episodes = response.getJSONArray("histori_detail");
                    for (int i = 0; i < episodes.length(); i++) {

                        JSONObject episode = episodes.getJSONObject(i);
                        String nk = episode.getString("nama_kategori");
                        String np = episode.getString("nama_penilaian");
                        String mapel = episode.getString("Mata_Pelajaran");
                        String sklh = episode.getString("Sekolah_Universitas");
                        String kelas = episode.getString("Kelas");
                        String ht = episode.getString("hari_tanggal");

                        String wkt = episode.getString("waktu");
                        String scr = episode.getString("score");
                        String ulasan = episode.getString("ulasan");
                        String komen = episode.getString("komen");
                        String sls = episode.getString("solusi");
                        String rt = episode.getString("rata");
                        String bbt = episode.getString("keterangan");
                        komennya.setText("Komentar : " + komen);
                        solusinya.setText("Solusi : " + sls);
                        rata1.setText("Rata-Rata penilaian : " + rt);
                        bobot.setText("Keterangan : " + bbt);
                        Item_detail_histori lostItem = new Item_detail_histori(
                                nk,np,mapel,sklh,kelas,ht,wkt,scr,ulasan
                        );
                        Log.d("json",response.toString());
                        list_histori.add(lostItem);
                        mAdapter.notifyDataSetChanged();
                    }
                    recyclerView.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
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
