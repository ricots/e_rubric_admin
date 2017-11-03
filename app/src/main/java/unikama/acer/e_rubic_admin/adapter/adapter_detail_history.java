package unikama.acer.e_rubic_admin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import unikama.acer.e_rubic_admin.R;
import unikama.acer.e_rubic_admin.oop.Item_detail_histori;

public class adapter_detail_history extends RecyclerView.Adapter<adapter_detail_history.DataObjectHolder>{
    private ArrayList<Item_detail_histori> mDataset;
    private Context context;
    String id_kat,result;
    public adapter_detail_history(ArrayList<Item_detail_histori> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detail_histori, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        context = parent.getContext();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {
        holder.kategori_histori.setText("Sekolah/Universitas : " + mDataset.get(position).getNama_kategori());
        holder.penilaian_histori.setText("Kelas : " +mDataset.get(position).getNama_penilaian());
        holder.mapel_histori.setText("Nama Kategori : " + mDataset.get(position).getMapel());
        holder.sklh_univ.setText("Nama Penilaian : " + mDataset.get(position).getSekolah());
        holder.kelas_histori.setText("Mata Pelajaran : " + mDataset.get(position).getKelas());
        holder.hari_histori.setText("Hari/Tanggal : " +mDataset.get(position).getHari_tanggal());
        holder.waktu_histori.setText("Waktu : " +mDataset.get(position).getWaktu());
        holder.ulasan_history.setText("ulasan : " + mDataset.get(position).getUlasan());
        holder.score_histori.setText("score : " + mDataset.get(position).getScore());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView  kategori_histori,penilaian_histori,mapel_histori,kelas_histori,hari_histori,waktu_histori,ulasan_history,
        score_histori,sklh_univ,solusi_histori;

        public DataObjectHolder(View itemView) {
            super(itemView);
            kategori_histori = (TextView) itemView.findViewById(R.id.kategori_histori);
            penilaian_histori = (TextView) itemView.findViewById(R.id.penilaian_histori);
            mapel_histori = (TextView) itemView.findViewById(R.id.mapel_historinya);
            kelas_histori = (TextView) itemView.findViewById(R.id.kelas_historinya);
            hari_histori = (TextView) itemView.findViewById(R.id.hari_histori);
            waktu_histori = (TextView) itemView.findViewById(R.id.waktu_histori);
            score_histori = (TextView) itemView.findViewById(R.id.score_histori);
            ulasan_history = (TextView) itemView.findViewById(R.id.ulasan_histori);
            sklh_univ = (TextView) itemView.findViewById(R.id.sklh_univ);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}