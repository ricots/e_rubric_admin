package unikama.acer.e_rubic_admin.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import unikama.acer.e_rubic_admin.activity.detail_histori;
import unikama.acer.e_rubic_admin.koneksi.config;
import unikama.acer.e_rubic_admin.oop.Item_history;

public class adapter_histori extends RecyclerView.Adapter<adapter_histori.DataObjectHolder>{
    private ArrayList<Item_history> mDataset;
    private Context context;
    TextView id_nilai_histori;
    public adapter_histori(ArrayList<Item_history> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_histori, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        context = parent.getContext();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {
        holder.nid_histori.setText("Nid Dosen : " +mDataset.get(position).getNid());
        holder.mapel.setText("Mata Pelajaran : " + mDataset.get(position).getMapel());
        holder.sekolah.setText("Sekolah/Kampus : " +mDataset.get(position).getSekolah());
        holder.kelas.setText("Kelas : " +mDataset.get(position).getKelas());
        holder.id_nilai_histori.setText(mDataset.get(position).getId_nilai_histori());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView  nid_histori,mapel,sekolah,kelas,id_nilai_histori;


        public DataObjectHolder(View itemView) {
            super(itemView);
            nid_histori = (TextView) itemView.findViewById(R.id.nid_histori);
            mapel = (TextView) itemView.findViewById(R.id.mapel_histori);
            sekolah = (TextView) itemView.findViewById(R.id.sekolah);
            kelas = (TextView) itemView.findViewById(R.id.kelas_histori);
            id_nilai_histori = (TextView) itemView.findViewById(R.id.id_nilai_histori);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("aksi");
                    builder.setMessage(nid_histori.getText().toString());
                    builder.setNeutralButton("CETAK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String idnya = id_nilai_histori.getText().toString();
                            String urlnya = config.CETAK_PDF + idnya;
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlnya));
                            context.startActivity(intent);
                        }
                    });

                    builder.setPositiveButton("HAPUS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //RequestQueue requestQueue = Volley.newRequestQueue(context);
                            String idnya = id_nilai_histori.getText().toString();
                            delete(idnya);
                        }
                    });
                    builder.setNegativeButton("Detail", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                          Intent goDetail = new Intent(context, detail_histori.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("id",id_nilai_histori.getText().toString());
                            goDetail.putExtras(bundle);
                            v.getContext().startActivity(goDetail);
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            });
        }
    }

    public void delete(final String idnya) {
        final ProgressDialog loading = ProgressDialog.show(context,"Loading Data", "Please wait...",false,false);

        StringRequest hapusRequest = new StringRequest(Request.Method.GET, config.DELETE_NILAI + idnya,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        //item_et.setText("");
                        Toast.makeText(context,
                                response.toString(),
                                Toast.LENGTH_SHORT).show();
                        System.out.println("erornya " +response);
                        /*daftar_pelaporan fragment = new daftar_pelaporan();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame,fragment);
                        fragmentTransaction.commit();*/

                        //Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(context,
                        error.toString(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_hasil_nilai",idnya);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(hapusRequest);

    }
}