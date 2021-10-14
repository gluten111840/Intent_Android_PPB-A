package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buka_kontak =  findViewById(R.id.buka_kontak);
        Button buka_browser =  findViewById(R.id.buka_browser);
        Button buka_galeri =  findViewById(R.id.buka_galeri);
        Button buka_youtube =  findViewById(R.id.buka_youtube);
        Button try_fragment = findViewById(R.id.buka_fragment);

        buka_kontak.setOnClickListener(operasi);
        buka_browser.setOnClickListener(operasi);
        buka_galeri.setOnClickListener(operasi);
        buka_youtube.setOnClickListener(operasi);
        try_fragment.setOnClickListener(operasi);
    }

    @SuppressLint("NonConstantResourceId")
    View.OnClickListener operasi = v -> {
        switch (v.getId()) {
            case R.id.buka_kontak:bukaKontak();break;
            case R.id.buka_browser:bukaBrowser();break;
            case R.id.buka_galeri:bukaGaleri();break;
            case R.id.buka_youtube:bukaYoutube();break;
            case R.id.buka_fragment:bukaFragment();break;
        }
    };

    private void bukaKontak() {
        Intent intentku = new Intent(getBaseContext(), Kontak.class);
        startActivityForResult(intentku, 0);
    }

    private void bukaYoutube() {
        Intent intentku = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com"));
        startActivity(intentku);
    }

    private void bukaGaleri() {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void bukaBrowser() {
        Intent intentku = new Intent(getBaseContext(), Browser.class);
        startActivityForResult(intentku, 0);
    }

    private void bukaFragment() {
        Intent intentku = new Intent(getBaseContext(), Frag.class);
        startActivityForResult(intentku, 0);
    }

}