package com.example.intent;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Frag extends AppCompatActivity {
    Fragment frag1, frag2;
    Button btn1, btn2, tutup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coba_fragment);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        tutup = (Button) findViewById(R.id.tutup);
        btn1.setOnClickListener(view -> {
            frag1 = new Frag1();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment, frag1);
            ft.addToBackStack(null);
            ft.commit();
        });
        btn2.setOnClickListener(view -> {
            frag2 = new Frag2();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment, frag2);
            ft.addToBackStack(null);
            ft.commit();
        });
        tutup.setOnClickListener(view -> finish());
    }
}
