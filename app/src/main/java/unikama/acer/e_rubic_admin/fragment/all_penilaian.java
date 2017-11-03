package unikama.acer.e_rubic_admin.fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

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
import unikama.acer.e_rubic_admin.activity.add_kategori;
import unikama.acer.e_rubic_admin.activity.add_penilaian;
import unikama.acer.e_rubic_admin.adapter.adapter_all_kategori;
import unikama.acer.e_rubic_admin.adapter.adapter_kategori;
import unikama.acer.e_rubic_admin.koneksi.config;
import unikama.acer.e_rubic_admin.oop.Item_kategori;

public class all_penilaian extends Fragment implements View.OnClickListener{
    RecyclerView recyclerView;
    private List<Item_kategori> list_kat = new ArrayList<>();
    private adapter_all_kategori mAdapter;

    private RecyclerView.LayoutManager layoutManager;
    RequestQueue requestQueue;
    FloatingActionButton fab_add,fab_kat,fab_nilai;
    private Boolean isFabOpen = false;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_all_penilaia, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.all_list_nilai);
        mAdapter = new adapter_all_kategori((ArrayList<Item_kategori>) list_kat);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        requestQueue = Volley.newRequestQueue(getActivity());
        loadData();
        fab_add = (FloatingActionButton) v.findViewById(R.id.fab_add);
        fab_kat = (FloatingActionButton) v.findViewById(R.id.fab_kat);
        fab_nilai = (FloatingActionButton) v.findViewById(R.id.fab_nilai);

        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_backward);
        fab_add.setOnClickListener(this);
        fab_kat.setOnClickListener(this);
        fab_nilai.setOnClickListener(this);
        return v;
    }

    public void animateFAB(){

        if(isFabOpen){

            fab_add.startAnimation(rotate_backward);
            fab_kat.startAnimation(fab_close);
            fab_nilai.startAnimation(fab_close);
            fab_kat.setClickable(false);
            fab_nilai.setClickable(false);
            isFabOpen = false;

        } else {

            fab_add.startAnimation(rotate_forward);
            fab_kat.startAnimation(fab_open);
            fab_nilai.startAnimation(fab_open);
            fab_kat.setClickable(true);
            fab_nilai.setClickable(true);
            isFabOpen = true;
        }
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

        Volley.newRequestQueue(getActivity()).add(jsonObjReq);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab_add:
                animateFAB();
                break;
            case R.id.fab_kat:
                Intent in = new Intent(getActivity(),add_kategori.class);
                startActivity(in);
                break;
            case R.id.fab_nilai:
                Intent add = new Intent(getActivity(),add_penilaian.class);
                startActivity(add);
                break;
        }
    }
}
