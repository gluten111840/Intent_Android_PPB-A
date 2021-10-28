package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class DetailKontak extends AppCompatActivity {

    Button pesan, panggil;
    TextView nama, no_hp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kontak);

        pesan = findViewById(R.id.pesan);
        panggil = findViewById(R.id.panggil);
        nama  = findViewById(R.id.nama_lengkap);
        no_hp = findViewById(R.id.no_hp);

        Intent intent = getIntent();
        String s_nama = intent.getStringExtra("nama_lengkap");
        String s_no_hp = intent.getStringExtra("no_hp");
        nama.setText(s_nama);
        no_hp.setText(s_no_hp);

        panggil.setOnClickListener(v -> {
            Intent intent1 = new Intent(Intent.ACTION_DIAL);
            intent1.setData(Uri.parse("tel:" + s_no_hp));
            startActivity(intent1);
        });

        pesan.setOnClickListener(v -> {
            Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",s_no_hp,null));
            startActivity(intent2);
        });

    }
}