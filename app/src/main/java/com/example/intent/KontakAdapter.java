package com.example.intent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

public class KontakAdapter extends ArrayAdapter<KontakSuper> {

    private static class ViewHolder {
        TextView nama, nomor;
        Button telepon;
    }

    public KontakAdapter(@NonNull Context context, int resource,
                         List<KontakSuper> objects) {
        super(context, resource, objects);
    }

    @SuppressLint("CutPasteId")
    public View getView(int position, View ConvertView, ViewGroup parent) {
        KontakSuper dtkontak = getItem(position);
        ViewHolder viewKontak;
        if(ConvertView==null) {
            viewKontak = new ViewHolder();
            ConvertView = LayoutInflater.from(getContext()).inflate(R.layout.tv_kontak, parent,false);
            viewKontak.nama = ConvertView.findViewById(R.id.nama);
            viewKontak.nomor = ConvertView.findViewById(R.id.nomor);
            ConvertView.setTag(viewKontak);
            Button btn = ConvertView.findViewById(R.id.detail);
            btn.setTag(position);
        }
        else {
            viewKontak = (ViewHolder) ConvertView.getTag();
        }

        viewKontak.nama.setText(dtkontak.getNama());
        viewKontak.nomor.setText(dtkontak.getNomor());

        return ConvertView;
    }
}
