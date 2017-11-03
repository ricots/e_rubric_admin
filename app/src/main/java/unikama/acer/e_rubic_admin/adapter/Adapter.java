package unikama.acer.e_rubic_admin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import unikama.acer.e_rubic_admin.R;
import unikama.acer.e_rubic_admin.oop.Item;

/**
 * Created by ACER on 10/24/2016.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context context;
    List<Item> oop;


    public Adapter(List<Item> oop, Context context){
        super();
        //Getting all the superheroes
        this.oop = oop;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_nilai, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Item model_nilai =  oop.get(position);
        holder.id_kategori.setText(model_nilai.getId_kategori());
        holder.nama_kategori.setText(model_nilai.getNama_kategori());
        holder.id_penilaian.setText(model_nilai.getId_penilaian());
        holder.nama_penilaian.setText(model_nilai.getNama_penilaian());
        holder.model = model_nilai;
    }

    @Override
    public int getItemCount() {
        return oop.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView id_kategori,nama_kategori,id_penilaian;
        public TextView nama_penilaian;
        Item model;
        public ViewHolder(View itemView) {
            super(itemView);
            id_kategori = (TextView) itemView.findViewById(R.id.id_kategori);
            nama_kategori = (TextView) itemView.findViewById(R.id.kategori);
            id_penilaian= (TextView) itemView.findViewById(R.id.id_penilaian);
            nama_penilaian= (TextView) itemView.findViewById(R.id.nama_penilaian);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
