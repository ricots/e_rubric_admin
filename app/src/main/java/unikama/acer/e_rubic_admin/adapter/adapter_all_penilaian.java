package unikama.acer.e_rubic_admin.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class adapter_all_penilaian extends RecyclerView.Adapter<adapter_all_penilaian.DataObjectHolder>{
    private ArrayList<Item_penilaian> mDataset;
    private Context context;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    String result;

    public adapter_all_penilaian(ArrayList<Item_penilaian> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_all_proses_nilai, parent, false);
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
                final String input_nilai = holder.update_aspek.getText().toString();
                final String input_idnilai = holder.id_penilaian.getText().toString();
                if (input_nilai.equals("")) {
                    Toast.makeText(context, "harap isi data", Toast.LENGTH_LONG).show();
                    //PD.dismiss();
                } else {
                    //PD.show();
                    StringRequest postRequest = new StringRequest(Request.Method.POST, config.UPDATE_PENILAIAN,
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
                            params.put("nama_penilaian", input_nilai);
                            params.put("id_penilaian", input_idnilai);
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
        EditText update_aspek;
        FloatingActionButton btn;

        public DataObjectHolder(View itemView) {
            super(itemView);
            id_penilaian = (TextView) itemView.findViewById(R.id.id_all_penilaian);
            nama_penilaian = (TextView) itemView.findViewById(R.id.nama_all_penilaian);
            update_aspek = (EditText) itemView.findViewById(R.id.update_aspek);
            btn = (FloatingActionButton) itemView.findViewById(R.id.simpan_update);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*final String input_nilai = update_aspek.getText().toString();
                        final String input_idnilai = id_penilaian.getText().toString();
                        if (input_nilai.equals("")) {
                            Toast.makeText(context, "harap isi data", Toast.LENGTH_LONG).show();
                            //PD.dismiss();
                        } else {
                            //PD.show();
                            StringRequest postRequest = new StringRequest(Request.Method.POST, config.UPDATE_PENILAIAN,
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
                                    params.put("nama_penilaian", input_nilai);
                                    params.put("id_penilaian", input_idnilai);
                                    return params;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            requestQueue.add(postRequest);
                        }*/
                    }
                });
        }
    }

}