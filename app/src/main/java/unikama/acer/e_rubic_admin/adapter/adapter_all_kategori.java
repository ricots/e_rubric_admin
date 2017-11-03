package unikama.acer.e_rubic_admin.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import unikama.acer.e_rubic_admin.MainActivity;
import unikama.acer.e_rubic_admin.R;
import unikama.acer.e_rubic_admin.activity.daftar;
import unikama.acer.e_rubic_admin.koneksi.config;
import unikama.acer.e_rubic_admin.oop.Item_kategori;
import unikama.acer.e_rubic_admin.oop.Item_penilaian;

public class adapter_all_kategori extends RecyclerView.Adapter<adapter_all_kategori.DataObjectHolder>{
    private ArrayList<Item_kategori> mDataset;
    private Context context;
    String id_kat,result;
    final List<Item_penilaian> list_penilaian = new ArrayList<>();

    public adapter_all_kategori(ArrayList<Item_kategori> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_all_nilai, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        context = parent.getContext();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {
        holder.id_kategori.setText(mDataset.get(position).getId_kategori());
        holder.kategori.setText(mDataset.get(position).getNama_kategori());

        holder.kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = context.getSharedPreferences("idkat",0);
                SharedPreferences.Editor spe = sp.edit();
                id_kat = holder.id_kategori.getText().toString();
                spe.putString("idkat",id_kat);
                spe.commit();

                //id untuk simpan
                /*sp = context.getSharedPreferences("isi data", 0);
                spe = sp.edit();
                result = sp.getString("isi data","");
                Toast.makeText(context,result,Toast.LENGTH_LONG).show();*/

                final Dialog dialog = new Dialog(context);
                //dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                ///dialog.setTitle("");
                dialog.setContentView(R.layout.layout_all_nilai);
                //dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,R.drawable.keluar);
                /*FloatingActionButton btn = (FloatingActionButton) dialog.findViewById(R.id.simpan_nilai);
                
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"di klik ",Toast.LENGTH_LONG).show();

                        
                    }
                });*/
               /* ImageView imageView = (ImageView) dialog.findViewById(R.id.imgkeluar);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });*/

                final adapter_all_penilaian mAdapter;
                RecyclerView.LayoutManager layoutManager;
                final RequestQueue requestQueue;
                final RecyclerView recyle_check;

                recyle_check = (RecyclerView) dialog.findViewById(R.id.recyle_all_proses);
                mAdapter = new adapter_all_penilaian((ArrayList<Item_penilaian>) list_penilaian);
                recyle_check.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(context);
                recyle_check.setLayoutManager(layoutManager);
                requestQueue = Volley.newRequestQueue(context);
                requestQueue.getCache().remove(config.NILAI + id_kat);
                requestQueue.getCache().clear();
                list_penilaian.clear();
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        config.NILAI + id_kat, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                /*requestQueue.getCache().clear();
                list_penilaian.clear();*/
                        try {
                            if (!list_penilaian.isEmpty()) {
                                list_penilaian.clear();
                            }
                            //JSONObject emedded = response.getJSONObject("_embedded");
                            JSONArray episodes = response.getJSONArray("list_kategori");
                            for (int i = 0; i < episodes.length(); i++) {

                                JSONObject episode = episodes.getJSONObject(i);
                                String id_pen = episode.getString("id_penilaian");
                                String nama_pen = episode.getString("nama_penilaian");
                                //String scr = episode.getString("score");
                                //String ulas = episode.getString("ulasan");

                                Item_penilaian lostItem = new Item_penilaian(
                                        id_pen,nama_pen
                                );
                                list_penilaian.add(lostItem);
                                mAdapter.notifyDataSetChanged();
                            }
                            recyle_check.setAdapter(mAdapter);
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

                Volley.newRequestQueue(context).add(jsonObjReq);
                dialog.show();
                //dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.keluar);


            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView  nama_penilaian,id_kategori,update_kategori;
        Button kategori;
        FloatingActionButton update_kat;
        ProgressDialog PD;

        public DataObjectHolder(View itemView) {
            super(itemView);
            id_kategori = (TextView) itemView.findViewById(R.id.allid_kategori);
            update_kategori = (TextView) itemView.findViewById(R.id.update_kategori);
            kategori = (Button) itemView.findViewById(R.id.allkategori);
            update_kat = (FloatingActionButton) itemView.findViewById(R.id.update_kat);
           /* PD = new ProgressDialog(context);
            PD.setMessage("silahkan tunggu.....");
            PD.setCancelable(false);*/

            update_kat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String input_kategori = update_kategori.getText().toString();
                    final String input_idkategori = id_kategori.getText().toString();
                    if (input_kategori.equals("")) {
                        Toast.makeText(context, "harap isi data", Toast.LENGTH_LONG).show();
                        //PD.dismiss();
                    } else {
                        //PD.show();
                        StringRequest postRequest = new StringRequest(Request.Method.POST, config.UPDATE_KATEGORI,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        //PD.dismiss();
                                        Log.d("sukses ",response.toString());
                                        Toast.makeText(context,
                                                response.toString(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //PD.dismiss();
                                Log.d("erornya ",error.toString());
                                Toast.makeText(context,
                                        error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("nama_kategori", input_kategori);
                                params.put("id_kategori", input_idkategori);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(postRequest);
                    }
               }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public void clear() {
        int size = this.list_penilaian.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.list_penilaian.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
}