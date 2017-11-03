package unikama.acer.e_rubic_admin.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import unikama.acer.e_rubic_admin.adapter.adapter_histori;
import unikama.acer.e_rubic_admin.koneksi.config;
import unikama.acer.e_rubic_admin.oop.Item_history;

public class history_all extends Fragment {
    private LinearLayoutManager layoutManager;
    private List<Item_history> list_histori = new ArrayList<>();
    private adapter_histori mAdapter;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    String nid;
    ProgressDialog PD;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_all_history, container, false);
        setHasOptionsMenu(true);
        PD = new ProgressDialog(getActivity());
        PD.setMessage("silahkan tunggu.....");
        PD.setCancelable(false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyle_all_history);
        mAdapter = new adapter_histori((ArrayList<Item_history>) list_histori);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        requestQueue = Volley.newRequestQueue(getActivity());
        load_histori();
        return v;
    }

    public void load_histori(){
        requestQueue.getCache().clear();
        list_histori.clear();
        PD.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                config.HISTORI_all, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                PD.dismiss();
                try {
                    JSONArray episodes = response.getJSONArray("histori");
                    for (int i = 0; i < episodes.length(); i++) {

                        JSONObject episode = episodes.getJSONObject(i);
                        String nd = episode.getString("nid");
                        String mapel = episode.getString("Mata_Pelajaran");
                        String sklh = episode.getString("Sekolah_Universitas");
                        String kelas = episode.getString("Kelas");
                        String id = episode.getString("id_hasil_nilai");

                        Item_history lostItem = new Item_history(
                                nd,mapel,sklh,kelas,id
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
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
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

        Volley.newRequestQueue(getActivity()).add(jsonObjReq);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.load){
            requestQueue.getCache().clear();
            list_histori.clear();
            load_histori();
            //
            //return true;
        }else {
            Toast.makeText(getActivity(), "tidak ada history", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
