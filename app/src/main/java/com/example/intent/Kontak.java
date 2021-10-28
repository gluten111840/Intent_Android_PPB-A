package com.example.intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Kontak extends AppCompatActivity {
    private SQLiteDatabase dbku;
    private SQLiteOpenHelper Opendb;
    ImageButton add_data, update_data, read_data, delete_data;
    private KontakAdapter kAdapter;
    private KontakAdapterBaru bAdapter;
    String sNama, sNomor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontak);
        ListView lv;
        Objects.requireNonNull(getSupportActionBar()).setTitle("Kontak");

        lv = findViewById(R.id.listView);
        add_data = findViewById(R.id.add_data);
        update_data = findViewById(R.id.update_data);
        read_data = findViewById(R.id.read_data);
        delete_data = findViewById(R.id.delete_data);
        add_data.setOnClickListener(op);
        update_data.setOnClickListener(op);
        read_data.setOnClickListener(op);
        delete_data.setOnClickListener(op);

        ArrayList<KontakSuper> listKontak = new ArrayList<>();
        bAdapter = new KontakAdapterBaru(this, 0, listKontak);
        lv.setAdapter(bAdapter);

        Opendb = new SQLiteOpenHelper(this, "db.sql", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {}

            @Override
            public void onUpgrade(SQLiteDatabase db, int old_v, int new_v) {}
        };
        dbku = Opendb.getWritableDatabase();
        dbku.execSQL(getString(R.string.SQL_create_table));

        Button tutup = findViewById(R.id.tutup);
        tutup.setOnClickListener(op);
        ambilData();
    }

    @SuppressLint("NonConstantResourceId")
    View.OnClickListener op = v -> {
        switch (v.getId()) {
            case R.id.add_data:tampil_input();break;
            case R.id.read_data:read_data();break;
            case R.id.update_data:update_data();break;
            case R.id.delete_data:delete_data();break;
            case R.id.tutup:finish();break;
        }
    };

    private void delete_kontak(String eNama, String eNomor) {
        dbku.delete("kontakku", "nama='"+eNama+"' and nomor='"+eNomor+"'", null);

        ambilData();
    }

    private void delete_data() {
        View vDelete = LayoutInflater.from(this).inflate(R.layout.delete_kontak, null);
        AlertDialog.Builder create = new AlertDialog.Builder(this);
        create.setTitle("Hapus Kontak");

        final EditText eNama = vDelete.findViewById(R.id.eNama);
        final  EditText eNomor = vDelete.findViewById(R.id.eNomor);

        create.setView(vDelete);
        create.setPositiveButton("Ok", (dialogInterface, i) -> {
            delete_kontak(eNama.getText().toString(), eNomor.getText().toString());
            Toast.makeText(getBaseContext(), "Data Has Been Deleted", Toast.LENGTH_LONG).show();
            dialogInterface.dismiss();
        });

        create.setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> dialogInterface.cancel());
        create.show();
    }

    private void  edit_kontak(String old_nm, String old_nmr, String new_nm, String new_nmr) {
        ContentValues datanya = new ContentValues();

        datanya.put("nama", new_nm);
        datanya.put("nomor", new_nmr);
        dbku.update("kontakku", datanya, "nama='"+old_nm+"' and nomor='"+
                old_nmr+"'",null);
        ambilData();
    }

    private void update_data() {
        View vEdit = LayoutInflater.from(this).inflate(R.layout.edit_kontak, null);
        AlertDialog.Builder create = new AlertDialog.Builder(this);
        create.setTitle("Edit Kontak");

        final EditText old_nm = vEdit.findViewById(R.id.old_nm);
        final EditText old_nmr = vEdit.findViewById(R.id.old_nmr);
        final EditText new_nm = vEdit.findViewById(R.id.new_nm);
        final EditText new_nmr = vEdit.findViewById(R.id.new_nmr);

        create.setView(vEdit);
        create.setPositiveButton("Ok", (dialogInterface, i) -> {
            edit_kontak(old_nm.getText().toString(), old_nmr.getText().toString(),
                    new_nm.getText().toString(), new_nmr.getText().toString());
            Toast.makeText(getBaseContext(), "Data Has Been Updated", Toast.LENGTH_LONG).show();
            dialogInterface.dismiss();
        });

        create.setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> dialogInterface.cancel());

        create.show();
    }

    @SuppressLint("Range")
    private void search_kontak(String nm) {
        bAdapter.clear();
        bAdapter.notifyDataSetChanged();

        @SuppressLint("Recycle") Cursor cur = dbku.rawQuery("select * from kontakku where nama like '%" + nm + "%'", null);
        Toast.makeText(this, "Terdapat sejumlah " + cur.getCount(), Toast.LENGTH_LONG).show();

        if (cur.getCount() > 0) {
            if (cur.moveToFirst()) {
                do {
                    insertKontak(cur.getString(cur.getColumnIndex("nama")),
                            cur.getString(cur.getColumnIndex("nomor")));
                } while (cur.moveToNext());
            }
        }
    }

    private void read_data() {
        View vSearch = LayoutInflater.from(this).inflate(R.layout.search_kontak,null);
        AlertDialog.Builder create = new AlertDialog.Builder(this);
        create.setTitle("Cari Kontak");

        final EditText nm = vSearch.findViewById(R.id.eNama);

        create.setView(vSearch);
        create.setPositiveButton("Ok", (dialogInterface, i) -> {
            search_kontak(nm.getText().toString());
            dialogInterface.dismiss();
        });

        create.setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> dialogInterface.cancel());

        create.show();
    }

    private void tampil_input() {
        LayoutInflater li = LayoutInflater.from(this);
        View inputnya = li.inflate(R.layout.input_kontak,null);

        AlertDialog.Builder dialognya = new AlertDialog.Builder(this);
        dialognya.setTitle("Tambah Kontak");
        dialognya.setView(inputnya);
        final EditText eNama = inputnya.findViewById(R.id.eNama);
        final EditText eNomor = inputnya.findViewById(R.id.eNomor);

        dialognya
                .setCancelable(false)
                .setPositiveButton("Ok", (dialogInterface, i) -> toast_addData(eNama, eNomor))
                .setNegativeButton("Batal", (dialogInterface, i) -> dialogInterface.cancel());
        dialognya.show();
    }

    private void toast_addData(EditText eNama, EditText eNomor) {
        ContentValues dataku = new ContentValues();
        sNama = eNama.getText().toString();
        sNomor = eNomor.getText().toString();

        dataku.put("nama", sNama);
        dataku.put("nomor", sNomor);

        dbku.insert("kontakku",null,dataku);
        KontakSuper newKontak = new KontakSuper(sNama, sNomor);
        bAdapter.add(newKontak);
        Toast.makeText(this,"Data Tersimpan", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        dbku.close();
        Opendb.close();
        super.onStop();
    }

    private void insertKontak(String nama, String nomor) {
        KontakSuper newKontak = new KontakSuper(nama, nomor);
        bAdapter.add(newKontak);
    }

    @SuppressLint("Range")
    private void ambilData() {
        bAdapter.clear();
        bAdapter.notifyDataSetChanged();
        @SuppressLint("Recycle") Cursor cur = dbku.rawQuery("select * from kontakku", null);
        Toast.makeText(this, "Terdapat sejumlah " + cur.getCount(), Toast.LENGTH_SHORT).show();
        int i = 0;
        if (cur.getCount() > 0) cur.moveToFirst();
        while (i < cur.getCount()) {
            insertKontak(cur.getString(cur.getColumnIndex("nama")),
                        cur.getString(cur.getColumnIndex("nomor")));
            cur.moveToNext();
            i++;
        }
    }

    public class KontakAdapterBaru extends ArrayAdapter<KontakSuper> {

        class ViewHolder {
            TextView nama, nomor;
            Button detail;
        }

        public KontakAdapterBaru(@NonNull Context context, int resource, @NonNull List<KontakSuper> objects) {
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
                viewKontak.detail = ConvertView.findViewById(R.id.detail);
                ConvertView.setTag(viewKontak);

                viewKontak.detail.setOnClickListener(view -> {
                    KontakSuper temp = bAdapter.getItem(position);
                    Intent intentku = new Intent(getBaseContext(), DetailKontak.class);
                    intentku.putExtra("nama_lengkap", temp.getNama());
                    intentku.putExtra("no_hp", temp.getNomor());
                    startActivityForResult(intentku, 0);
                });

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
}