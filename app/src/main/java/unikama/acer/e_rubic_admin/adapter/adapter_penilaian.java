package unikama.acer.e_rubic_admin.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import unikama.acer.e_rubic_admin.R;
import unikama.acer.e_rubic_admin.koneksi.config;
import unikama.acer.e_rubic_admin.oop.Item_penilaian;

public class adapter_penilaian extends RecyclerView.Adapter<adapter_penilaian.DataObjectHolder>{
    private ArrayList<Item_penilaian> mDataset;
    private Context context;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    String result;

    public adapter_penilaian(ArrayList<Item_penilaian> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_proses_nilai, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        context = parent.getContext();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {
        holder.id_penilaian.setText(mDataset.get(position).getId_penilaian());
        holder.nama_penilaian.setText(mDataset.get(position).getNama_penilaian());
        sp = context.getSharedPreferences("isi data", 0);
        spe = sp.edit();
        result = sp.getString("isi data","");
        //Toast.makeText(context,result,Toast.LENGTH_LONG).show();

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int angka = 100;
                final String input_score = holder.score.getText().toString();
                final String input_ulasan = holder.ulasan.getText().toString();

                if ((input_score.equals("")) && (input_ulasan.equals(""))) {
                    Toast.makeText(context, "harap isi data", Toast.LENGTH_LONG).show();
                    //PD.dismiss();
                } else {
                    //PD.show();
                    StringRequest postRequest = new StringRequest(Request.Method.POST, config.INPUT_PENILAIAN,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //PD.dismiss();
                                    Toast.makeText(context,
                                            response.toString(),
                                            Toast.LENGTH_SHORT).show();
                                    holder.score.setEnabled(false);
                                    holder.ulasan.setEnabled(false);
                                    holder.btn.setEnabled(false);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //PD.dismiss();
                            Toast.makeText(context,
                                    error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("id_hasil_nilai", result);
                            params.put("id_penilaian", holder.id_penilaian.getText().toString());
                            params.put("score", input_score);
                            params.put("ulasan", input_ulasan);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    requestQueue.add(postRequest);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView nama_penilaian,id_penilaian;
        EditText score, ulasan;
        FloatingActionButton btn;

        public DataObjectHolder(View itemView) {
            super(itemView);
            id_penilaian = (TextView) itemView.findViewById(R.id.id_penilaian);
            nama_penilaian = (TextView) itemView.findViewById(R.id.nama_penilaian);
            score = (EditText) itemView.findViewById(R.id.score);
            ulasan = (EditText) itemView.findViewById(R.id.ulasan);

            /*sp = context.getSharedPreferences("isi data", 0);
            spe = sp.edit();
            result = sp.getString("isi data","");
            Toast.makeText(context,result,Toast.LENGTH_LONG).show();*/
            btn = (FloatingActionButton) itemView.findViewById(R.id.simpan_nilai);

                /*btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String input_score = score.getText().toString();
                        final String input_ulasan = ulasan.getText().toString();

                        if ((input_score.equals("")) && (input_ulasan.equals(""))) {
                            Toast.makeText(context, "harap isi data", Toast.LENGTH_LONG).show();
                            //PD.dismiss();
                        } else {
                            //PD.show();
                            StringRequest postRequest = new StringRequest(Request.Method.POST, config.INPUT_PENILAIAN,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            //PD.dismiss();
                                            Toast.makeText(context,
                                                    response.toString(),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //PD.dismiss();
                                    Toast.makeText(context,
                                            error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("id_hasil_nilai", result);
                                    params.put("id_penilaian", id_penilaian.getText().toString());
                                    params.put("score", input_score);
                                    params.put("ulasan", input_ulasan);
                                    return params;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            requestQueue.add(postRequest);
                        }
                    }
                });*/
        }
    }

}