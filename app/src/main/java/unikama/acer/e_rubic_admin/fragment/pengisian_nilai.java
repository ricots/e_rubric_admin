package unikama.acer.e_rubic_admin.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import unikama.acer.e_rubic_admin.R;
import unikama.acer.e_rubic_admin.activity.penilaian;
import unikama.acer.e_rubic_admin.koneksi.config;

/**
 * Created by Admin on 5/25/2016.
 */
public class pengisian_nilai extends Fragment {

    EditText univ, kelas,mapel,topik,hari,waktu,autoD8;
    Button daftar_pengevaluasi;
    ProgressDialog PD;
    String nid;
    SharedPreferences sp;
    SharedPreferences.Editor spe;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pengisian, container, false);
        PD = new ProgressDialog(getActivity());
        PD.setMessage("silahkan tunggu.....");
        PD.setCancelable(false);
        SharedPreferences sp = getActivity().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        nid = sp.getString(config.EMAIL_SHARED_PREF, "Not Available");

        univ = (EditText) v.findViewById(R.id.univ);
        kelas = (EditText) v.findViewById(R.id.kelas);
        mapel = (EditText) v.findViewById(R.id.mapel);
        topik = (EditText) v.findViewById(R.id.topik);
        hari = (EditText) v.findViewById(R.id.hari);
        waktu = (EditText) v.findViewById(R.id.waktu);

        autoD8 = (EditText)v.findViewById(R.id.tes);

        SimpleDateFormat dateF = new SimpleDateFormat("dMMMyyyy", Locale.getDefault());
        SimpleDateFormat timeF = new SimpleDateFormat("HHmm", Locale.getDefault());
        String date = dateF.format(Calendar.getInstance().getTime());
        String time = timeF.format(Calendar.getInstance().getTime());

        autoD8.setText(time+date);

        sp = getActivity().getSharedPreferences("isi data", 0);
        spe = sp.edit();

        daftar_pengevaluasi = (Button) v.findViewById(R.id.daftar_pengevaluasi);
        daftar_pengevaluasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pengevaluasi();

            }
        });
        return v;
    }

    public void pengevaluasi() {
        final String input_univ = univ.getText().toString();
        final String input_kls = kelas.getText().toString();
        final String input_mapel = mapel.getText().toString();
        final String input_topik = topik.getText().toString();
        final String input_hari = hari.getText().toString();
        final String input_waktu = waktu.getText().toString();

        if ((input_univ.equals("")) && (input_kls.equals("")) && (input_mapel.equals("")) && (input_topik.equals("")) && (input_hari.equals("")) && (input_waktu.equals(""))) {
            Toast.makeText(getActivity(), "harap isi data", Toast.LENGTH_LONG).show();
            PD.dismiss();
        } else {
            PD.show();
            StringRequest postRequest = new StringRequest(Request.Method.POST, config.PENGEVALUASI,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            PD.dismiss();
                            Toast.makeText(getActivity(),
                                    response.toString(),
                                    Toast.LENGTH_SHORT).show();
                            spe.putString("isi data", autoD8.getText().toString());
                            spe.commit();
                            Intent in = new Intent(getActivity(),penilaian.class);
                            startActivity(in);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PD.dismiss();
                    Toast.makeText(getActivity(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id_hasil_nilai", autoD8.getText().toString());
                    params.put(config.KEY_NID, nid);
                    params.put(config.KEY_UNIV, input_univ);
                    params.put(config.KEY_kls, input_kls);
                    params.put(config.KEY_mapel, input_mapel);
                    params.put(config.KEY_topik, input_topik);
                    params.put(config.KEY_hari_tanggal, input_hari);
                    params.put(config.KEY_waktu, input_waktu);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(postRequest);
        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
